package com.bookmark.controller;

import com.bookmark.dto.BookmarkRequestDTO;
import com.bookmark.dto.BookmarkResponseDTO;
import com.bookmark.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = "*")
@Tag(name = "Bookmarks", description = "Bookmark management API")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    @Operation(summary = "Get all bookmarks", description = "Retrieve a list of all bookmarks")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of bookmarks",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookmarkResponseDTO.class)))
    public ResponseEntity<List<BookmarkResponseDTO>> getAllBookmarks() {
        List<BookmarkResponseDTO> bookmarks = bookmarkService.getAllBookmarks();
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bookmark by ID", description = "Retrieve a specific bookmark by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookmark found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookmarkResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bookmark not found")
    })
    public ResponseEntity<BookmarkResponseDTO> getBookmarkById(
            @Parameter(description = "ID of the bookmark to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return bookmarkService.getBookmarkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new bookmark", description = "Create a new bookmark with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bookmark created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookmarkResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BookmarkResponseDTO> createBookmark(
            @Parameter(description = "Bookmark details to create", required = true)
            @Valid @RequestBody BookmarkRequestDTO bookmarkRequest) {
        BookmarkResponseDTO createdBookmark = bookmarkService.createBookmark(bookmarkRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookmark);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a bookmark", description = "Update an existing bookmark by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookmark updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookmarkResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bookmark not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BookmarkResponseDTO> updateBookmark(
            @Parameter(description = "ID of the bookmark to update", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated bookmark details", required = true)
            @Valid @RequestBody BookmarkRequestDTO bookmarkRequest) {
        try {
            BookmarkResponseDTO updatedBookmark = bookmarkService.updateBookmark(id, bookmarkRequest);
            return ResponseEntity.ok(updatedBookmark);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bookmark", description = "Delete a bookmark by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bookmark deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bookmark not found")
    })
    public ResponseEntity<Void> deleteBookmark(
            @Parameter(description = "ID of the bookmark to delete", required = true, example = "1")
            @PathVariable Long id) {
        try {
            bookmarkService.deleteBookmark(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search bookmarks", description = "Search bookmarks by title (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching bookmarks",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookmarkResponseDTO.class)))
    public ResponseEntity<List<BookmarkResponseDTO>> searchBookmarks(
            @Parameter(description = "Search query string", required = true, example = "spring")
            @RequestParam String q) {
        List<BookmarkResponseDTO> bookmarks = bookmarkService.searchBookmarks(q);
        return ResponseEntity.ok(bookmarks);
    }
}
