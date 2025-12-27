package com.bookmark.controller;

import com.bookmark.model.Bookmark;
import com.bookmark.service.BookmarkService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@CrossOrigin(origins = "*")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping
    public ResponseEntity<List<Bookmark>> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkService.getAllBookmarks();
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> getBookmarkById(@PathVariable Long id) {
        return bookmarkService.getBookmarkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(@Valid @RequestBody Bookmark bookmark) {
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookmark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> updateBookmark(@PathVariable Long id, @Valid @RequestBody Bookmark bookmark) {
        try {
            Bookmark updatedBookmark = bookmarkService.updateBookmark(id, bookmark);
            return ResponseEntity.ok(updatedBookmark);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long id) {
        try {
            bookmarkService.deleteBookmark(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Bookmark>> searchBookmarks(@RequestParam String q) {
        List<Bookmark> bookmarks = bookmarkService.searchBookmarks(q);
        return ResponseEntity.ok(bookmarks);
    }
}
