package com.johnray100.io.brandservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BrandController {

    /**
     * 🚦 Health check endpoint for the Brand Service.
     * This endpoint is used by monitoring tools to verify if the service is running.
     *
     * ✅ Returns HTTP 200 OK with body "OK" if service is healthy.
     * ⚠️ Returns HTTP 500 Internal Server Error with body "Unhealthy" if an error occurs.
     *
     * @return ResponseEntity<String> - HTTP response indicating the health status.
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            // 📝 Log the successful health check
            log.info("Health check OK for Brand Service");

            // 👍 Return 200 OK with "OK" if healthy
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            // ❌ Log error if health check fails
            log.error("Health check failed!", e);

            // 🚨 Return 500 Internal Server Error with "Unhealthy"
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unhealthy");
        }
    }
}