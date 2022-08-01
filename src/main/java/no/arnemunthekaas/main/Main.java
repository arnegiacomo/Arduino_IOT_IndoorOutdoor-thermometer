package no.arnemunthekaas.main;

import no.arnemunthekaas.db.TRDAO;
import no.arnemunthekaas.db.TemperatureReading;

public class Main {

    public static void main(String[] args) {

        TRDAO test = new TRDAO();

        test.insertTemperatureReading(new TemperatureReading(10, 15));
    }
}
