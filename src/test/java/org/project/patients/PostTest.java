package org.project.patients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {
    private final String url = "http://localhost:4000/api/patient";

    private final String testJSON = "{\n" +
            "    \"name\": \"Denial\",\n" +
            "    \"sex\": \"M\",\n" +
            "    \"dob\": \"12.01.2001\",\n" +
            "    \"hp\": {\n" +
            "        \"name\": \"Diabetes\",\n" +
            "        \"degree\": 6\n" +
            "    }\n" +
            "}";
    private final String wrongJSON = "{\n" +
            "    \"sex\": \"M\",\n" +
            "    \"dob\": \"12.01.2003\",\n" +
            "    \"hp\": {\n" +
            "        \"name\": \"Dementia\",\n" +
            "        \"degree\": 1\n" +
            "    }\n" +
            "}";

    private final String wrongDataTypeJSON = "{\n" +
            "    \"name\": \"Denial\",\n" +
            "    \"sex\": \"G\",\n" +
            "    \"dob\": \"1.0101.403\",\n" +
            "    \"hp\": {\n" +
            "        \"name\": \"Diabetes\",\n" +
            "        \"degree\": 111\n" +
            "    }\n" +
            "}";

    @Test
    @DisplayName("Ensures that the patient will be successfully created")
    public void createPatientTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .POST(HttpRequest.BodyPublishers.ofString(this.testJSON))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 400 in case if was received not all fields")
    public void notAllFieldsWasReceived() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .POST(HttpRequest.BodyPublishers.ofString(this.wrongJSON))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 400 in case if was received wrong data type")
    public void wrongDataTypeWasReceived() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url))
                .POST(HttpRequest.BodyPublishers.ofString(this.wrongDataTypeJSON))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }
}
