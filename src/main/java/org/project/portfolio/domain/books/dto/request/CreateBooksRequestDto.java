package org.project.portfolio.domain.books.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.project.portfolio.domain.books.domain.Books;

import java.time.LocalDate;

@Getter
@Builder
public class CreateBooksRequestDto {
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishedDate;

    public static Books toEntity(CreateBooksRequestDto dto) {
        return Books.builder()
                .title(dto.title)
                .author(dto.author)
                .publisher(dto.publisher)
                .publishDate(dto.publishedDate)
                .build();
    }
}
