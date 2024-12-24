package com.sparta.newsfeed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageQuery {

    private int page = 0;
    private int size = 10;
    private Sort.Direction direction = Sort.Direction.DESC;
    private List<String> sort = Collections.emptyList();

    public PageQuery() {}

    public Pageable toPageable() {
        if (sort.isEmpty()) {
            return PageRequest.of(page, size);
        } else {
            return PageRequest.of(page, size, direction, sort.toArray(new String[0]));
        }
    }
}