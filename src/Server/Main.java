package Server;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import Module.Task;
import Module.SubTask;
import Module.Epic;
import Module.TaskStatus;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        new KVServer().start();
        new HttpTaskServer(8088);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        URI url1 = URI.create("http://localhost:8080/tasks/task/");
        String json = gson.toJson(new Task(1, "Title", "Description", TaskStatus.NEW,
                 LocalDateTime.now(), Duration.ofMinutes(60)));
        final HttpRequest.BodyPublisher body1 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request1 = HttpRequest.newBuilder().headers("Content-Type", "application/json").version(HttpClient.Version.HTTP_2)
                .uri(url1).POST(body1).build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url2 = URI.create("http://localhost:8080/tasks/task/");
        json = gson.toJson(new Task(2, "Title", "Description", TaskStatus.NEW,
                LocalDateTime.now(), Duration.ofMinutes(60)));
        final HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(body2).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url3 = URI.create("http://localhost:8080/tasks/epic/");
        json = gson.toJson(new Epic(2, "Title", "Description"));
        final HttpRequest.BodyPublisher body3 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request3 = HttpRequest.newBuilder().uri(url3).POST(body3).build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url4 = URI.create("http://localhost:8080/tasks/epic/");
        json = gson.toJson(new Epic(4, "Title", "Description"));
        final HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request4 = HttpRequest.newBuilder().uri(url4).POST(body4).build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        URI url5 = URI.create("http://localhost:8080/tasks/subtask/");
        json = gson.toJson(new SubTask(5, "Title", "Description", TaskStatus.NEW, 4));
        final HttpRequest.BodyPublisher body5 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request5 = HttpRequest.newBuilder().uri(url5).POST(body5).build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());

        URI url6 = URI.create("http://localhost:8080/tasks/subtask/");
        json = gson.toJson(new SubTask(new SubTask(6, "Title", "Description", TaskStatus.NEW, 4)));
        final HttpRequest.BodyPublisher body6 = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request6 = HttpRequest.newBuilder().uri(url6).POST(body6).build();
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());


        URI url7 = URI.create("http://localhost:8080/tasks/epic/");
        HttpRequest request7 = HttpRequest.newBuilder().uri(url1).GET().build();
        HttpResponse<String> response7 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        URI url8 = URI.create("http://localhost:8080/tasks/epic/?id=2");
        HttpRequest request8 = HttpRequest.newBuilder().uri(url2).GET().build();
        HttpResponse<String> response8 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        URI url9 = URI.create("http://localhost:8080/tasks/subtask/epic/?id=2");
        HttpRequest request9 = HttpRequest.newBuilder().uri(url3).GET().build();
        HttpResponse<String> response9 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url10 = URI.create("http://localhost:8080/tasks/");
        HttpRequest request10 = HttpRequest.newBuilder().uri(url7).GET().build();
        HttpResponse<String> response10 = client.send(request7, HttpResponse.BodyHandlers.ofString());

        System.out.println(response6.body());
        System.out.println(response1.body());
        System.out.println(response2.body());
        System.out.println(response3.body());
        System.out.println(response7.body());


    }

    }
