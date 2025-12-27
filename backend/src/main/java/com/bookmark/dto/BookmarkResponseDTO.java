package com.bookmark.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Response DTO for bookmark data")
public class BookmarkResponseDTO {

    @Schema(description = "Unique identifier of the bookmark", example = "1")
    private Long id;

    @Schema(description = "Title of the bookmark", example = "Spring Boot Documentation")
    private String title;

    @Schema(description = "URL of the bookmark", example = "https://spring.io/projects/spring-boot")
    private String url;

    @Schema(description = "Description of the bookmark", example = "Official Spring Boot documentation")
    private String description;

    @Schema(description = "Timestamp when the bookmark was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the bookmark was last updated", example = "2024-01-15T10:30:00")
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
