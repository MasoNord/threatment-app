package org.project.app.patients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.app.modules.StatusCode;
import org.project.app.util.ValidUUID;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class PatientController{
    ObjectMapper m = new ObjectMapper();
    PatientService patientService = new PatientService();
    ValidUUID validUUID = new ValidUUID();

    public String Greeting(Request request, Response response, String message) {
        response.status(200);
        return message;
    }

    public String create(Request request, Response response) throws IOException {
        if (patientService.create(request) == null) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Has been received not all required fields");
        }

        response.status(StatusCode.CREATED.getCode());
        return m.writeValueAsString(patientService.create(request));
    }

    public String getAll(Request request, Response response) throws IOException {
        if (patientService.getAll().isEmpty())
            response.status(StatusCode.NO_CONTENT.getCode());

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(patientService.getAll());
    }

    public String getById(Request request, Response response) throws IOException {
        if (!validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        if (patientService.getByID(request.params(":id")) == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(patientService.getByID(request.params(":id")));
    }

    public String update(Request request, Response response) throws IOException {

        if (!validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        if (patientService.getByID(request.params(":id")) == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        response.status(StatusCode.OK.getCode());
        return m.writeValueAsString(patientService.update(request, request.params(":id")));
    }

    public String delete(Request request, Response response) throws IOException {
        if (!validUUID.validate(request.params(":id"))) {
            response.status(StatusCode.BAD_REQUEST.getCode());
            return m.writeValueAsString("Id is not a uuid format");
        }

        if (patientService.getByID(request.params(":id")) == null) {
            response.status(StatusCode.NOT_FOUND.getCode());
            return m.writeValueAsString("Patient's record was not found");
        }

        patientService.delete(request.params(":id"));

        response.status(StatusCode.NO_CONTENT.getCode());
        return m.writeValueAsString("The patient has been removed from the list");
    }
}
