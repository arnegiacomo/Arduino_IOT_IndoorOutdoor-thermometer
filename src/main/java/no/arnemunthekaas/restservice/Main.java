package no.arnemunthekaas.restservice;

import com.google.gson.Gson;
import no.arnemunthekaas.db.TRDAO;
import no.arnemunthekaas.db.TemperatureReading;

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
        get("/temperaturelogger/log/:id", (req, res) -> {

            Gson gson = new Gson();
            String id = req.params(":id");

            return gson.toJson(temperatureReadingDAO.selectTemperatureReading(Integer.parseInt(id)));
        });

        // Submit a temperature reading
        put("/temperaturelogger/log", (req, res) -> {

            Gson gson = new Gson();

            int indoorTemp = Integer.parseInt(req.queryParams("indoorTemp"));
            int outdoorTemp = Integer.parseInt(req.queryParams("outdoorTemp"));
            int seconds = Integer.parseInt(req.queryParams("seconds"));

            TemperatureReading tr = new TemperatureReading(indoorTemp, outdoorTemp, seconds);
            temperatureReadingDAO.insertTemperatureReading(tr);

            return gson.toJson(temperatureReadingDAO.selectTemperatureReading(tr.getID())) + " \n Has been succsessfully added to the database!";
        });
    }
}
