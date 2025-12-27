package com.bookmark.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO for creating or updating a bookmark")
public class BookmarkRequestDTO {

    @NotBlank(message = "Title is required")
    @Schema(description = "Title of the bookmark", example = "Spring Boot Documentation", required = true)
    private String title;

    @NotBlank(message = "URL is required")
    @Schema(description = "URL of the bookmark", example = "https://spring.io/projects/spring-boot", required = true)
    private String url;

    @Schema(description = "Description of the bookmark", example = "Official Spring Boot documentation")
    private String description;

    // Getters and Setters
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
}
