package org.project.app.patients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.app.modules.StatusCode;
import org.project.app.patients.entities.Patient;
import org.project.app.util.ValidUUID;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class PatientController{
    private final ObjectMapper m = new ObjectMapper();
    private final PatientService patientService = new PatientService();
    private final ValidUUID validUUID = new ValidUUID();

    public String Greeting(Request request, Response response, String message) {
        response.type("application/json");
        response.status(200);
        return message;
    }

    public String create(Request request, Response response) throws IOException {
        response.type("application/json");
        Patient patient = patientService.create(request);

        if (patient == null) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Has been received not all required fields");
        }

        response.status(StatusCode.CREATED.getCode());
        return m.writeValueAsString(patient);
    }

    public String getAll(Request request, Response response) throws IOException {
        response.type("application/json");

        if (patientService.getAll().isEmpty())
            response.status(StatusCode.NO_CONTENT.getCode());

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(patientService.getAll());
    }

    public String getById(Request request, Response response) throws IOException {
        response.type("application/json");

        Patient patient = patientService.getByID(request.params(":id"));
        if (!validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        if (patient == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(patient);
    }

    public String update(Request request, Response response) throws IOException {
        response.type("application/json");

        if (!this.validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        Patient patient = this.patientService.getByID(request.params(":id"));

        if (patient == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        Patient updatedPatient = this.patientService.update(request, request.params(":id"));

        if(updatedPatient == null) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Has been received wrong data types");
        }

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(updatedPatient);
    }

    public String delete(Request request, Response response) throws IOException {
        response.type("application/json");
        Patient patient = patientService.getByID(request.params(":id"));

        if (!validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        if (patient == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        patientService.delete(request.params(":id"));

        response.status(StatusCode.NO_CONTENT.getCode());
        return m.writeValueAsString("The patient has been removed from the list");
    }
}
