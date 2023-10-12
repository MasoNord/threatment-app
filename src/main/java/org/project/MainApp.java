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

        get("/api", "application/json", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.Greeting(request, response, message);
        });

        get("/api/patient", "application/json", MainApp.PATIENT_CONTROLLER::getAll);

        get("/api/patient/:id", "application/json", MainApp.PATIENT_CONTROLLER::getById);

        post("/api/patient", "application/json", MainApp.PATIENT_CONTROLLER::create);

        put("/api/patient/:id", "application/json", MainApp.PATIENT_CONTROLLER::update);

        delete("/api/patient/:id", "application/json", MainApp.PATIENT_CONTROLLER::delete);

        System.out.println("Server is listening on port " + config.getApp_port());

    }
}
