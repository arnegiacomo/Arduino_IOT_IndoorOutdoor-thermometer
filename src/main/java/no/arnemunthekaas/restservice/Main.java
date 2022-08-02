package no.arnemunthekaas.restservice;

import com.google.gson.Gson;
import no.arnemunthekaas.db.TRDAO;
import no.arnemunthekaas.db.TemperatureReading;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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

            if (id != null) {
                try {
                    TemperatureReading tr = temperatureReadingDAO.selectTemperatureReading(Integer.parseInt(id));

                    String str = gson.toJson(tr);
                    System.out.println(Timestamp.from(Instant.now()) + " : " + str + "\nSuccessfully retrieved item : " + id);
                    return str;
                } catch (Exception e) {
                    System.out.println("Invalid ID provided.");
                    e.printStackTrace();
                }
            } else {
                List<TemperatureReading> trs = temperatureReadingDAO.selectTemperatureReading();

                String str = gson.toJson(trs);
                System.out.println(Timestamp.from(Instant.now()) + "\nSuccessfully retrieved all items.");

                return str;
            }

            return null;
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
