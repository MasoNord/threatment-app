package org.project;
import org.project.app.patients.PatientController;

import static spark.Spark.*;

public class MainApp {
    final static PatientController PATIENT_CONTROLLER = new PatientController();
    final static String message = "Hello world";
    final static int port = 8000;

    public static void main(String[] args)  {
        port(MainApp.port);

        get("/api", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.Greeting(request, response, message);
        });

        get("/api/patient", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.getAll(request, response);
        });

        get("/api/patient/:id", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.getById(request, response);
        });

        post("/api/patient", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.create(request, response);
        });

        put("/api/patient/:id", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.update(request, response);
        });

        delete("/api/patient/:id", (request, response) -> {
            return MainApp.PATIENT_CONTROLLER.delete(request, response);
        });

        System.out.println("Server is listening on port " + MainApp.port);
    }
}
