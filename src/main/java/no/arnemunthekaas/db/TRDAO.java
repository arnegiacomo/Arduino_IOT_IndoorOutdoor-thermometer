package no.arnemunthekaas.db;

import javax.persistence.*;
import java.util.List;

public class TRDAO {

    @PersistenceContext(name = "tempDB")
    private final EntityManagerFactory emf;

    /**
     * Constructs a Temperature reading data access object to insert/select entries from temperature database
     */
    public TRDAO() {
        emf = Persistence.createEntityManagerFactory("tempDB");
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
     * Gets all temperature readings
     *
     * @return All readings
     */
    public List<TemperatureReading> selectTemperatureReading() {
        String queryString = "SELECT t FROM TemperatureReading t";
        EntityManager em = emf.createEntityManager();

        List<TemperatureReading> trs;
        try {
            TypedQuery<TemperatureReading> query = em.createQuery(queryString, TemperatureReading.class);
            trs = query.getResultList();

        } finally {
            em.close();
        }

        return trs;
    }

    /**
     * Gets most recent temperature reading
     *
     * @return Most recent reading
     */
    public TemperatureReading selectMostRecentTemperatureReading() {
        String queryString = "SELECT t FROM TemperatureReading t ORDER BY t.TIME_STAMP DESC ";
        EntityManager em = emf.createEntityManager();

        TemperatureReading tr;

        try {
            TypedQuery<TemperatureReading> query = em.createQuery(queryString, TemperatureReading.class);
            tr = query.setMaxResults(1).getSingleResult();

        } finally {
            em.close();
        }

        return tr;
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
