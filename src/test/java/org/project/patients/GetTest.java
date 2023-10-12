package org.project.patients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetTest {
    private final String url = "http://localhost:4000/api/patient";
    private final String testId = "fd8771d2-4ebe-43cc-b029-287aaf170e83";
    private final String dummyId = "bf055762-5c78-411b-9087-45fbd422fbb0";
    private final String wrongId = "1";
//    private final String dummyJSON = "{\"name\" : \"Denial\", \"sex\" : \"F\", \"dob\" : \"12.03.1980\", \"hp\" : \"{ \"name\" : \"Dementia\", \"degree\" : \"1\"}\"}";

    @Test
    @DisplayName("Ensures that the status code is 200 OK")
    public void GetAllTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals (200, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the contents type is application/json")
    public void checkContentType() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Optional<String> contentType = response.headers().firstValue("Content-Type");

        contentType.ifPresent(s -> assertEquals("application/json", s));
    }

    @Test
    @DisplayName("Ensures that body is not empty")
    public void bodyIsNotEmptyTest()throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertFalse(response.body().isEmpty());
    }

    @Test
    @DisplayName("Ensures that the status code will be 200 ")
    public void getByIdTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + this.testId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that body contains id field")
    public void bodyContainsIdFieldTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + this.testId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertTrue(response.body().contains("\"id\":" + "\"" + this.testId + "\""));
    }

    @Test
    @DisplayName("Ensures that the status code will be 400 when was received wrong type of id")
    public void getBadRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + wrongId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 404 when was received not existing patient's id")
    public void getNotFountRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + dummyId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }
}
