package org.project;

import org.project.app.config.Configuration;
import org.project.app.db.CreateConnection;
import org.project.app.db.InitializeDB;
import org.project.app.patients.PatientController;

import static spark.Spark.*;

public class MainApp {
    final static Configuration config = new Configuration();
    final static PatientController PATIENT_CONTROLLER = new PatientController();
    final static CreateConnection createConnection = new CreateConnection();
    final static InitializeDB initDb = new InitializeDB();
    final static String message = "Hello world";

    public static void main(String[] args)  {
        port(config.getApp_port());
        initDb.RunScript(createConnection.connect());

        get("/api", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.Greeting(request, response, message);
        });

        get("/api/patient", MainApp.PATIENT_CONTROLLER::getAll);

        get("/api/patient/:id", MainApp.PATIENT_CONTROLLER::getById);

        post("/api/patient", MainApp.PATIENT_CONTROLLER::create);

        put("/api/patient/:id", MainApp.PATIENT_CONTROLLER::update);

        delete("/api/patient/:id", MainApp.PATIENT_CONTROLLER::delete);

        System.out.println("Server is listening on port " + config.getApp_port());

    }
}

//TODO Do something with message about relation existence
//TODO add creating and updating data with hours minutes and seconds
//TODO add tests for patient endpoint
//TODO add healthproblem endpoint to fetch top 10 most vulnerable patients