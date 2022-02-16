import lombok.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Browar {
    @Id
    private String name;
    private long wartość;
    @OneToMany(mappedBy = "browar")
    private List<Piwo> piwa;

    public Browar(String name, long wartość) {
        this.name=name;
        this.wartość=wartość;
    }
    @Override
    public String toString () {
        return "Browar:" + name + " wartosc: " + wartość ;
    }
}
@AllArgsConstructor
class BrowarService {

    private final EntityManagerFactory entityManagerFactory;

    public void addBrowar (Browar browar)
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(browar);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public List<Browar> findBrowar (String name)
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();

        List<Browar> browars = entityManager.createQuery("SELECT p FROM Browar p WHERE p.name = :name", Browar.class)
                .setParameter("name", name)
                .getResultList();
        entityManager.close();

        return browars;
    }

    public void displayBrowar ()
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();
        List<Browar> browars = entityManager.createQuery("SELECT p FROM Browar p", Browar.class).getResultList();
        entityManager.close();

        for (Browar browar : browars)
            System.out.println(browar.toString());

        entityManager.close();
        System.out.print("\n");

    }

    public void displayAllBrowars () {
        displayBrowar();
        PiwoService piwoService = new PiwoService(entityManagerFactory);
        piwoService.displayPiwo();
        System.out.print("\n");
    }
}