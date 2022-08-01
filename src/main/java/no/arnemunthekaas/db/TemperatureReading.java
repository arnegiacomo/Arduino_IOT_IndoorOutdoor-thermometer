package no.arnemunthekaas.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class TemperatureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private Timestamp timestamp;
    private float indoorTemp;
    private float outdoorTemp;

    /**
     * Empty constructor - Do not use this
     * @Deprecated
     */
    public TemperatureReading () {};

    /**
     * For use without timestamp (gets current timestamp from system time)
     *
     * @param indoorTemp Indoor temperature in celsius
     * @param outdoorTemp Outdoor temperature in celsius
     */
    public TemperatureReading (float indoorTemp, float outdoorTemp) {
        this.indoorTemp = indoorTemp;
        this.outdoorTemp = outdoorTemp;

        this.timestamp = Timestamp.from(Instant.now());
    }

    /**
     * For use with timestamp
     *
     * See https://playground.arduino.cc/Code/DateTime/ and https://www.postgresql.org/docs/current/datatype-datetime.html#DATATYPE-DATETIME-INPUT
     *
     * @param indoorTemp Indoor temperature in celsius
     * @param outdoorTemp Outdoor temperature in celsius
     * @param seconds Seconds since 1/1/1970
     **/
    public TemperatureReading (float indoorTemp, float outdoorTemp, int seconds) {
        this.indoorTemp = indoorTemp;
        this.outdoorTemp = outdoorTemp;

        // Convert seconds to ms
        this.timestamp = new Timestamp((long) seconds * 1000);
    }
}
