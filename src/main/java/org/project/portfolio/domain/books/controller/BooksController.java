package org.project.portfolio.domain.books.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.books.dto.request.CreateBooksRequestDto;
import org.project.portfolio.domain.books.dto.response.BookResponse;
import org.project.portfolio.domain.books.service.BooksService;
import org.project.portfolio.domain.users.dto.response.MessageResponseDto;
import org.project.portfolio.global.exception.dto.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BooksController {
    private final BooksService booksService;

    @PostMapping("/create")
    public ResponseEntity<ApiSuccessResponse<MessageResponseDto>> createBook(
            @RequestBody CreateBooksRequestDto dto,
            HttpServletRequest servletRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servletRequest.getServletPath(),
                        booksService.createBook(dto)
                ));
    }



}
