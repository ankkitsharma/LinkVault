package com.bookmark.service;

import com.bookmark.dto.BookmarkRequestDTO;
import com.bookmark.dto.BookmarkResponseDTO;
import com.bookmark.model.Bookmark;
import com.bookmark.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<BookmarkResponseDTO> getAllBookmarks() {
        return bookmarkRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookmarkResponseDTO> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id)
                .map(this::toResponseDTO);
    }

    public BookmarkResponseDTO createBookmark(BookmarkRequestDTO requestDTO) {
        Bookmark bookmark = toEntity(requestDTO);
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return toResponseDTO(savedBookmark);
    }

    public BookmarkResponseDTO updateBookmark(Long id, BookmarkRequestDTO requestDTO) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark not found with id: " + id));

        bookmark.setTitle(requestDTO.getTitle());
        bookmark.setUrl(requestDTO.getUrl());
        bookmark.setDescription(requestDTO.getDescription());

        Bookmark updatedBookmark = bookmarkRepository.save(bookmark);
        return toResponseDTO(updatedBookmark);
    }

    public void deleteBookmark(Long id) {
        if (!bookmarkRepository.existsById(id)) {
            throw new RuntimeException("Bookmark not found with id: " + id);
        }
        bookmarkRepository.deleteById(id);
    }

    public List<BookmarkResponseDTO> searchBookmarks(String query) {
        return bookmarkRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Mapper methods
    private Bookmark toEntity(BookmarkRequestDTO dto) {
        Bookmark bookmark = new Bookmark();
        bookmark.setTitle(dto.getTitle());
        bookmark.setUrl(dto.getUrl());
        bookmark.setDescription(dto.getDescription());
        return bookmark;
    }

    private BookmarkResponseDTO toResponseDTO(Bookmark bookmark) {
        BookmarkResponseDTO dto = new BookmarkResponseDTO();
        dto.setId(bookmark.getId());
        dto.setTitle(bookmark.getTitle());
        dto.setUrl(bookmark.getUrl());
        dto.setDescription(bookmark.getDescription());
        dto.setCreatedAt(bookmark.getCreatedAt());
        dto.setUpdatedAt(bookmark.getUpdatedAt());
        return dto;
    }
}
