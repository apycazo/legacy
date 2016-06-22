package nox.apycazo.hibernateannotatedexample;

import java.util.Date;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class App 
{
    public static void main( String[] args ) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
        new App().test();
    }
    
    public void test() {
        // Se inserta
        Long id = insertFlight();
        insertFlight();
        insertFlight();
        insertFlight();

        // Se consulta y muestra por pantalla
        listFlights();

        // Se modifica
        updateFlight(id);

        // Se vuelve a consultar y mostrar por pantalla
        listFlights();
    }

    private void updateFlight(Long id) {
        Session s = HibernateUtil.getSession();
        s.beginTransaction();

        Flight vuelo = (Flight) s.load(Flight.class, id);
        vuelo.setFirstname("Nombre cambiado");

        s.getTransaction().commit();
    }

    private void listFlights() {
        Session s = HibernateUtil.getSession();
        s.beginTransaction();

        List<Flight> vuelos = s.createQuery("from Flight").list();
        for (Flight vuelo : vuelos)
            System.out.println(vuelo.toString());

        s.getTransaction().commit();
    }

    private Long insertFlight() {
        // Se obtiene la sesion
        Session s = HibernateUtil.getSession();
        s.beginTransaction();

        // Se instancia la clase Flight y se rellenan sus datos
        Flight f = new Flight();
        f.setFirstname("Nombre vuelo");
        f.setFecha(new Date());

        // Se salva en base de datos
        s.save(f);
        s.getTransaction().commit();

        return f.getId();
    }

}
