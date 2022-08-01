package no.arnemunthekaas.db;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "TemperatureReading", schema = "temperaturedb")
public class TemperatureReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private Timestamp TIME_STAMP;
    private float INDOORTEMP;
    private float OUTDOORTEMP;

    /**
     * Empty constructor - Do not use this
     * @Deprecated
     */
    public TemperatureReading () {}

    /**
     * For use without timestamp (gets current timestamp from system time)
     *
     * @param indoorTemp Indoor temperature in celsius
     * @param outdoorTemp Outdoor temperature in celsius
     */
    public TemperatureReading (float indoorTemp, float outdoorTemp) {
        this.INDOORTEMP = indoorTemp;
        this.OUTDOORTEMP = outdoorTemp;

        this.TIME_STAMP = Timestamp.from(Instant.now());
    }

    /**
     * For use with timestamp
     *
     * See https://playground.arduino.cc/Code/Time/ and https://www.postgresql.org/docs/current/datatype-datetime.html#DATATYPE-DATETIME-INPUT
     *
     * @param indoorTemp Indoor temperature in celsius
     * @param outdoorTemp Outdoor temperature in celsius
     * @param seconds Seconds since 1/1/1970
     **/
    public TemperatureReading (float indoorTemp, float outdoorTemp, int seconds) {
        this.INDOORTEMP = indoorTemp;
        this.OUTDOORTEMP = outdoorTemp;

        // Convert seconds to ms
        this.TIME_STAMP = new Timestamp((long) seconds * 1000);
    }

    // GETTERS

    public int getID() {
        return ID;
    }

    public Timestamp getTIME_STAMP() {
        return TIME_STAMP;
    }

    public float getINDOORTEMP() {
        return INDOORTEMP;
    }

    public float getOUTDOORTEMP() {
        return OUTDOORTEMP;
    }

}
