package org.project.patients;

import org.eclipse.jetty.util.IO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PutTest {
    private final String url = "http://localhost:4000/api/patient";
    private final String patientId = "fd8771d2-4ebe-43cc-b029-287aaf170e83";
    private final String dummyId = "bf055762-5c78-411b-9087-45fbd422fbb0";
    private final String wrongId = "1dds";
    private final String wrongDataType = "{\n" +
            "    \"name\": \"Diana\",\n" +
            "    \"hp\": {\n" +
            "        \"degree\": 1111,\n" +
            "        \"name\": \"Dementia\"\n" +
            "    },\n" +
            "    \"sex\": \"F\",\n" +
            "    \"crap\": \"Demons\"\n" +
            "}";
    private final String updatePatientData = "{\"name\":\"Anna\"}";

    @Test
    @DisplayName("Ensures that chosen field will be changed")
    public void updatePatientTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url + "/" + patientId))
                .PUT(HttpRequest.BodyPublishers.ofString(updatePatientData))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertTrue(response.body().contains("\"name\":\"Anna\""));
    }

    @Test
    @DisplayName("Ensures that the status code will be 400 in case was received wrong data types")
    public void wrongDataTypeTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url + "/" + patientId))
                .PUT(HttpRequest.BodyPublishers.ofString(wrongDataType))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 400 when was received wrong type of id")
    public void getBadRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + wrongId)).PUT(HttpRequest.BodyPublishers.ofString(updatePatientData)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 404 when was received not existing patient's id")
    public void getNotFountRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + dummyId)).PUT(HttpRequest.BodyPublishers.ofString(updatePatientData)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }

}
