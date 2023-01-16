package com.sparta.miniproject1.category.controller;

import com.sparta.miniproject1.category.dto.RequestDto;
import com.sparta.miniproject1.category.dto.ResponseCategoryDto;
import com.sparta.miniproject1.category.dto.ResponseDto;
import com.sparta.miniproject1.category.service.CategoryService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping ("/categories")
    public ResponseCategoryDto get() {
        return categoryService.get();
    }

    @PostMapping("/categories")
    public ResponseDto create(@RequestBody RequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.create(request, userDetails.getUser());
    }

    @DeleteMapping("/categories")
    public ResponseDto delete(@RequestBody RequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.delete(request, userDetails.getUser());
    }
}
