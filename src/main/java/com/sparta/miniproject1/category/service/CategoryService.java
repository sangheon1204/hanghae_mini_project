package com.sparta.miniproject1.category.service;

import com.sparta.miniproject1.category.dto.RequestDto;
import com.sparta.miniproject1.category.dto.ResponseCategoryDto;
import com.sparta.miniproject1.category.dto.ResponseDto;
import com.sparta.miniproject1.category.entity.Category;
import com.sparta.miniproject1.category.repository.CategoryRepository;
import com.sparta.miniproject1.user.entity.User;
import com.sparta.miniproject1.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ResponseCategoryDto get() {
        List<Category> categories = categoryRepository.findAll();
        List<String> category = new ArrayList<>();

        for(Category arr: categories) category.add(arr.getCategory());

        return new ResponseCategoryDto(category);
    }

    @Transactional
    public ResponseDto create(RequestDto request, User user) {
        if(user.getRole().equals(UserRoleEnum.USER)) {
            return new ResponseDto("관리자만 카테고리를 추가할 수 있습니다.");
        }
        if(categoryRepository.findByCategory(request.getCategory()).isPresent()) {
            return new ResponseDto("이미 존재하는 카테고리 입니다.");
        }
        categoryRepository.save(new Category(request));
        return new ResponseDto("카테고리 등록에 성공하셨습니다.");
    }

    @Transactional
    public ResponseDto delete(RequestDto request, User user) {
        if(user.getRole().equals(UserRoleEnum.USER)) {
            return new ResponseDto("관리자만 카테고리를 삭제할 수 있습니다.");
        }
        if(categoryRepository.findByCategory(request.getCategory()).isEmpty()) {
            return new ResponseDto("존재하지 않는 카테고리 입니다.");
        }
        categoryRepository.deleteById(categoryRepository.findByCategory(request.getCategory()).get().getId());
        return new ResponseDto("카테고리 삭제에 성공하셨습니다.");
    }
}
