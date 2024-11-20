package com.ecom.zestcart.controller;
import com.ecom.zestcart.model.Category;
import com.ecom.zestcart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/open/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping(value = "/categories/create",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Category createCategory(
            @RequestPart Category category,
            @RequestPart("icon") MultipartFile icon) throws IOException {

        Category saveCategory = categoryService.createCategory(category,icon);
        return saveCategory;
    }
}
