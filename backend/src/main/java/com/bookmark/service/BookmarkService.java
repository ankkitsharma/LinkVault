package com.bookmark.service;

import com.bookmark.model.Bookmark;
import com.bookmark.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Optional<Bookmark> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id);
    }

    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark updateBookmark(Long id, Bookmark bookmarkDetails) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark not found with id: " + id));

        bookmark.setTitle(bookmarkDetails.getTitle());
        bookmark.setUrl(bookmarkDetails.getUrl());
        bookmark.setDescription(bookmarkDetails.getDescription());

        return bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(Long id) {
        if (!bookmarkRepository.existsById(id)) {
            throw new RuntimeException("Bookmark not found with id: " + id);
        }
        bookmarkRepository.deleteById(id);
    }

    public List<Bookmark> searchBookmarks(String query) {
        return bookmarkRepository.findByTitleContainingIgnoreCase(query);
    }
}
