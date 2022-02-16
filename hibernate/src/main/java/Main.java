import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("factory");
        BrowarService browarService = new BrowarService(emf);
        PiwoService piwoService = new PiwoService(emf);

        //dodawanie browarow
        browarService.addBrowar(new Browar("Amber", 5));
        browarService.addBrowar(new Browar("Desperados", 21));
        browarService.addBrowar(new Browar("Lomza", 7));

        List<Browar> browar1 = browarService.findBrowar("Amber");
        List<Browar> browar2 = browarService.findBrowar("Desperados");
        List<Browar> browar3 = browarService.findBrowar("Lomza");

        //dodawanie piw
        piwoService.addPiwo(new Piwo("Tyskie", 4, browar1.get(0)));
        piwoService.addPiwo(new Piwo("Zubr", 5, browar1.get(0)));
        piwoService.addPiwo(new Piwo("Desperados Tequila", 2, browar2.get(0)));
        piwoService.addPiwo(new Piwo("Desperados Czerwony", 5, browar2.get(0)));
        piwoService.addPiwo(new Piwo("Desperados Dark Spirit", 6, browar2.get(0)));
        piwoService.addPiwo(new Piwo("Lomza Jasna", 7, browar3.get(0)));
        piwoService.addPiwo(new Piwo("Lomza Miodowe", 4, browar3.get(0)));

        //wyswietlanie browarow
        browarService.displayBrowar();
        //
        piwoService.displayPiwo();

        List<Piwo> piwo1 = piwoService.findPiwo("Tyskie");
        //usuwanie piwa
        piwoService.deletePiwo(piwo1.get(0));
        //wyswietlanie piwa
        piwoService.displayPiwo();


        //wyswietlanie browarow z piwami tanszymi od 5
        long max=5;
        EntityManager entityManager = emf.createEntityManager();
        List<Browar> browars = entityManager.createQuery("SELECT DISTINCT browar FROM Browar browar JOIN browar.piwa p WHERE p.cena < :cena", Browar.class)
                .setParameter("cena", max)
                .getResultList();
        entityManager.close();

        int iterator2=0;
        for (Browar browar : browars) {
            boolean stop=false;
            int iterator = 0;
            for (Browar browarr : browars) {
                if(iterator==iterator2)
                    break;
                iterator++;
                if(browarr==browar){
                    stop=true;
                    break;
                }
            }
            if(!stop)
            System.out.println(browar.toString());
            iterator2++;
        }
        System.out.print("\n");
        //wyswietlanie wszystkiego
        browarService.displayAllBrowars();
    }
}