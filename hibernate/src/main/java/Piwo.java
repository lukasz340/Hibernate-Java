import lombok.*;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import java.util.List;



@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Getter
@Setter
public class Piwo {
    @Id
    private String name;
    private long cena;
    @ManyToOne
    private Browar browar;


    public Piwo(String name, long cena, Browar browar) {
        this.name=name;
        this.cena=cena;
        this.browar=browar;
    }

    @Override
    public String toString () {
        return "Piwo:" + name + " Cena: " + cena + " Browar: " + browar.getName();
    }
}
@AllArgsConstructor
class PiwoService {

    private final EntityManagerFactory entityManagerFactory;

    public void addPiwo (Piwo piwo)
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(piwo);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deletePiwo(Piwo piwo) {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(piwo));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void displayPiwo()
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();
        List<Piwo> piwos = entityManager.createQuery("SELECT p FROM Piwo p", Piwo.class).getResultList();
        entityManager.close();

        for (Piwo piwo : piwos)
            System.out.println(piwo.toString());
        System.out.print("\n");

    }
    public List<Piwo> findPiwo (String name)
    {
        EntityManager entityManager;
        entityManager = entityManagerFactory.createEntityManager();

        List<Piwo> piwos = entityManager.createQuery("SELECT p FROM Piwo p WHERE p.name = :name", Piwo.class)
                .setParameter("name", name)
                .getResultList();
        entityManager.close();

        return piwos;
    }

}
