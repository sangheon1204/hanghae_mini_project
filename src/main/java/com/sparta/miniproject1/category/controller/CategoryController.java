package com.sparta.miniproject1.category.controller;

import com.sparta.miniproject1.category.dto.RequestDto;
import com.sparta.miniproject1.category.dto.ResponseCategoryDto;
import com.sparta.miniproject1.category.dto.ResponseDto;
import com.sparta.miniproject1.category.service.CategoryService;
import com.sparta.miniproject1.user.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Category"})
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @ApiOperation(value = "카테고리 리턴", notes = "저장된 카테고리들을 리턴한다.")
    @GetMapping ("/categories")
    public ResponseCategoryDto get() {
        return categoryService.get();
    }

    @ApiOperation(value = "카테고리 추가", notes = "카테고리를 추가한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @PostMapping("/categories")
    public ResponseDto create(@RequestBody RequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.create(request, userDetails.getUser());
    }

    @ApiOperation(value = "카테고리 삭제", notes = "카테고리를 삭제한다.")
    @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header")
    @DeleteMapping("/categories")
    public ResponseDto delete(@RequestBody RequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return categoryService.delete(request, userDetails.getUser());
    }
}
