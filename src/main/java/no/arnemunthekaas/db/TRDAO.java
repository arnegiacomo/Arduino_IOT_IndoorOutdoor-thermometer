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
     * Gets most recent temperature reading
     *
     * @return Last temperature reading
     */
    public TemperatureReading selectTemperatureReading() {
        String queryString = "SELECT t FROM TemperatureReading t ";
        EntityManager em = emf.createEntityManager();

        List<TemperatureReading> trs = null;
        try {
            TypedQuery<TemperatureReading> query = em.createQuery(queryString, TemperatureReading.class);
            trs = query.getResultList();

        } finally {
            em.close();
        }

        return trs.get(trs.size()-1);
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
