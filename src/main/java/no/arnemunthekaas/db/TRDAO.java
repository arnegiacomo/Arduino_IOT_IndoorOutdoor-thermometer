package no.arnemunthekaas.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TRDAO {

    private final EntityManagerFactory emf;

    /**
     * Constructs a Temperature reading data access object to insert/select entries from temperature database
     */
    public TRDAO() {
        emf = Persistence.createEntityManagerFactory("");
    }

    /**
     * Get a temperature reading from database, given an ID
     *
     * @param id ID of temperature reading to get
     * @return Temperature reading with given ID
     */
    public TemperatureReading selectTemperatureReading(int id) {
        EntityManager em = emf.createEntityManager();
        try {

            return em.find(TemperatureReading.class, id);

        } finally {
            em.close();
        }
    }

    /**
     * Insert temperature reading int database
     *
     * @param temperatureReading Temperature reading to be inserted
     */
    public void insertTemperatureReading(TemperatureReading temperatureReading) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            em.persist(temperatureReading);

            tx.commit();
        } catch (Throwable e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
