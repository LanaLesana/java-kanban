package Server;
import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.io.IOException;



public class KVTaskClient {
    URI kvServerUri;
    String apiToken;
    HttpClient httpClient;
    Gson gson = new Gson();

    public KVTaskClient(URI uri) throws IOException, InterruptedException {
        httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri + "/register"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        apiToken = response.body();
        kvServerUri = uri;
    }
    public String load(String key) throws IOException, InterruptedException {
        HttpRequest requestLoad = HttpRequest.newBuilder()
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .uri(URI.create(kvServerUri + "/load/" + key + "?API_TOKEN="))
                .build();
        HttpResponse<String> responseLoad = httpClient.send(requestLoad, HttpResponse.BodyHandlers.ofString());
        return responseLoad.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {
        HttpRequest requestSave = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(URI.create(kvServerUri + "/save/" + key + "?API_TOKEN="))
                .header("Content-Type", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpResponse<String> responseLoad = httpClient.send(requestSave, HttpResponse.BodyHandlers.ofString());
    }
}
