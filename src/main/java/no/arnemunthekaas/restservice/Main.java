package no.arnemunthekaas.restservice;

import com.google.gson.Gson;
import no.arnemunthekaas.db.TRDAO;
import no.arnemunthekaas.db.TemperatureReading;

import java.sql.Timestamp;
import java.time.Instant;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;


public class Main {

    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        TRDAO temperatureReadingDAO = new TRDAO();

        // Get a temperature reading given an ID
        get("/temperaturelogger/log/", (req, res) -> {

            Gson gson = new Gson();
            String id = req.queryParams("id");

            TemperatureReading tr = temperatureReadingDAO.selectTemperatureReading(Integer.parseInt(id));

            String str = gson.toJson(tr);

            System.out.println(Timestamp.from(Instant.now()) + " : " + str + "\nSuccessfully retrieved item : " + id);

            return str;
        });

        // Get most recent temperature reading
        get("/temperaturelogger/", (req, res) -> {

            Gson gson = new Gson();

            TemperatureReading tr = temperatureReadingDAO.selectTemperatureReading();

            String str = gson.toJson(tr);

            System.out.println(Timestamp.from(Instant.now()) + " : " + str + "\nSuccessfully retrieved item : " + tr.getID());

            return str;
        });

        // Submit a temperature reading
        put("/temperaturelogger/log/", (req, res) -> {

            Gson gson = new Gson();

            float indoorTemp = Float.parseFloat(req.queryParams("indoorTemp"));
            float outdoorTemp = Float.parseFloat(req.queryParams("outdoorTemp"));

            TemperatureReading tr = new TemperatureReading(indoorTemp, outdoorTemp);
            temperatureReadingDAO.insertTemperatureReading(tr);

            String str = gson.toJson(tr);

            if (temperatureReadingDAO.selectTemperatureReading(tr.getID()) != null) {
                System.out.println(Timestamp.from(Instant.now()) + " : " + str + "\nSuccessfully added item : " + tr.getID());
            }

            return str + " \nHas been succsessfully added to the database!";
        });
    }
}
