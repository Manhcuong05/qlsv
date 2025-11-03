package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller to receive webhooks from Casdoor (e.g., user events like signup/login).
 * Expects POST with JSON payload containing event details.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")  // ⚠️ Chỉ nên dùng "*" trong môi trường dev
public class Webhook {

    private static final Logger logger = LoggerFactory.getLogger(Webhook.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/webhooks/casdoor")
    public ResponseEntity<String> handleCasdoorWebhook(@RequestBody String payload) {
        try {
            if (payload == null || payload.isEmpty()) {
                logger.warn("⚠️ Empty payload received");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("{\"status\":\"error\",\"message\":\"Empty payload\"}");
            }

            JsonNode jsonNode = objectMapper.readTree(payload);
            String event = jsonNode.path("action").asText();
            String userId = jsonNode.path("user").path("id").asText();
            String userName = jsonNode.path("user").path("name").asText();
            String email = jsonNode.path("user").path("email").asText();
            String organization = jsonNode.path("organization").asText("AdFlex");

            if (event == null || event.isEmpty()) {
                logger.warn("⚠️ Missing 'action' field");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("{\"status\":\"error\",\"message\":\"Missing 'action' field\"}");
            }

            logger.info("📨 Received Casdoor webhook: Event='{}', User='{}' ({}) from org='{}'",
                    event, userName, email, organization);

            switch (event) {
                case "PostRegister":
                    logger.info("👤 New user registered: {} <{}>", userName, email);
                    // Trả về 201 Created
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body("{\"status\":\"created\",\"event\":\"PostRegister\"}");

                case "PostLogin":
                    logger.info("🔐 User logged in: {} <{}>", userName, email);
                    // 200 OK
                    return ResponseEntity
                            .ok("{\"status\":\"ok\",\"event\":\"PostLogin\"}");

                case "PostUpdate":
                    logger.info("✏️ User updated: {} <{}>", userName, email);
                    // 200 OK
                    return ResponseEntity
                            .ok("{\"status\":\"ok\",\"event\":\"PostUpdate\"}");

                case "PostDelete":
                    logger.info("🗑️ User deleted: {}", userId);
                    // 200 OK hoặc 204 No Content
                    return ResponseEntity
                            .status(HttpStatus.NO_CONTENT)
                            .body("{\"status\":\"deleted\",\"userId\":\"" + userId + "\"}");

                default:
                    logger.warn("⚠️ Unknown event: {}", event);
                    // 405 Method Not Allowed (nếu event không được hỗ trợ)
                    return ResponseEntity
                            .status(HttpStatus.METHOD_NOT_ALLOWED)
                            .body("{\"status\":\"error\",\"message\":\"Unsupported event: " + event + "\"}");
            }

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("❌ Invalid JSON payload: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"status\":\"error\",\"message\":\"Invalid JSON format\"}");

        } catch (SecurityException e) {
            logger.error("🚫 Forbidden access: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("{\"status\":\"forbidden\",\"message\":\"Access denied\"}");

        } catch (IllegalStateException e) {
            logger.error("⚔️ Conflict: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("{\"status\":\"conflict\",\"message\":\"" + e.getMessage() + "\"}");

        } catch (Exception e) {
            logger.error("💥 Unexpected error: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    // Optional: Thêm GET để test webhook
    @GetMapping("/webhooks/test")
    public ResponseEntity<String> testWebhook() {
        return ResponseEntity.ok("{\"message\":\"Webhook endpoint is working!\"}");
    }
}
