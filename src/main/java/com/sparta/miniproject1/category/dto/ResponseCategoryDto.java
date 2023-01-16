package com.sparta.miniproject1.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseCategoryDto {
    List<String> categories = new ArrayList<>();

    public ResponseCategoryDto(List<String> categories) {
        this.categories = categories;
    }
}
