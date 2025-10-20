package org.example.spring_data_query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CustomFilterResultDTO<T> {
    public List<T> content;
    public Long totalElements;
}

