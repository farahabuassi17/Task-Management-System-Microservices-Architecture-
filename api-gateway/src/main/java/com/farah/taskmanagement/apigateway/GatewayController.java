package com.farah.taskmanagement.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final RestClient restClient;
    private final String userServiceUrl;
    private final String taskServiceUrl;
    private final String notificationServiceUrl;
    private final String boardServiceUrl;

    public GatewayController(
            RestClient restClient,
            @Value("${gateway.user-service-url}") String userServiceUrl,
            @Value("${gateway.task-service-url}") String taskServiceUrl,
            @Value("${gateway.notification-service-url}") String notificationServiceUrl,
            @Value("${gateway.board-service-url}") String boardServiceUrl
    ) {
        this.restClient = restClient;
        this.userServiceUrl = userServiceUrl;
        this.taskServiceUrl = taskServiceUrl;
        this.notificationServiceUrl = notificationServiceUrl;
        this.boardServiceUrl = boardServiceUrl;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<String> getUser(@PathVariable String id) {
        return get(userServiceUrl + "/api/users/" + id);
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody Map<String, Object> body) {
        return post(taskServiceUrl + "/api/tasks", body);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<String> getTask(@PathVariable Long id) {
        return get(taskServiceUrl + "/api/tasks/" + id);
    }

    @PatchMapping("/tasks/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        return patch(taskServiceUrl + "/api/tasks/" + id + "/status", body);
    }

    @GetMapping("/notifications")
    public ResponseEntity<String> getNotifications() {
        return get(notificationServiceUrl + "/api/notifications");
    }

    @PostMapping("/boards/{boardId}/summary-requests")
    public ResponseEntity<String> requestBoardSummary(@PathVariable String boardId, @RequestBody Map<String, Object> body) {
        return post(taskServiceUrl + "/api/boards/" + boardId + "/summary-requests", body);
    }

    @GetMapping("/board-summaries")
    public ResponseEntity<String> getBoardSummaries() {
        return get(boardServiceUrl + "/api/board-summaries");
    }

    private ResponseEntity<String> get(String url) {
        return restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);
    }

    private ResponseEntity<String> post(String url, Map<String, Object> body) {
        return restClient.post()
                .uri(url)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }

    private ResponseEntity<String> patch(String url, Map<String, Object> body) {
        return restClient.patch()
                .uri(url)
                .body(body)
                .retrieve()
                .toEntity(String.class);
    }
}
