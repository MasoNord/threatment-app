package org.project.patients;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteTest {
    private final String testId = "801d0740-c50a-4d1b-82e5-be1baa084926";
    private final String dummyId = "bf055762-5c78-411b-9087-45fbd422fbb0";
    private final String wrongId = "1";
    private final String url = "http://localhost:4000/api/patient";

    @Test
    @DisplayName("Should correctly perform deleting of patient by id")
    public void deletePatientTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.url + "/" + this.testId))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(204, response.statusCode());
    }
    @Test
    @DisplayName("Ensures that the status code will be 400 when was received wrong type of id")
    public void getBadRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + wrongId)).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(400, response.statusCode());
    }

    @Test
    @DisplayName("Ensures that the status code will be 404 when was received not existing patient's id")
    public void getNotFountRequestTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(this.url + "/" + dummyId)).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
    }


}

