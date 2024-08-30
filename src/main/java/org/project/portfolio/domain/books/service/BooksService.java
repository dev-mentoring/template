package org.project.portfolio.domain.books.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.books.domain.Books;
import org.project.portfolio.domain.books.dto.request.CreateBooksRequestDto;
import org.project.portfolio.domain.books.dto.response.BookResponse;
import org.project.portfolio.domain.books.exception.BookNotFoundException;
import org.project.portfolio.domain.books.repository.BooksRepository;
import org.project.portfolio.domain.users.dto.response.MessageResponseDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {
    private final BooksRepository booksRepository;

    public MessageResponseDto createBook(CreateBooksRequestDto dto) {
        booksRepository.save(Books.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .publishDate(dto.getPublishedDate())
                .status(true)
                .build());

        return new MessageResponseDto("도서 등록이 완료되었습니다.");
    }
}