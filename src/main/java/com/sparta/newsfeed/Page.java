package com.sparta.newsfeed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Page<T> {
    private List<T> content;
    private int currentPage;
    private int totalPage;
    private int totalCount;

    public static <T> Page<T> from(org.springframework.data.domain.Page<T> page){
        return new Page<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                (int)page.getTotalElements()
        );
    }
}