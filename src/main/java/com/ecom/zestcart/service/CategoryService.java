package com.ecom.zestcart.service;

import com.ecom.zestcart.model.Category;
import com.ecom.zestcart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category, MultipartFile icon) {
        Category lastCategory = categoryRepository.findFirstByOrderByCategoryFileIdDesc();
        String newCategoryId;

        if (lastCategory != null && lastCategory.getCategoryFileId() != null) {
            int lastNum = Integer.parseInt(lastCategory.getCategoryFileId().replace("CAT", ""));
            newCategoryId = "CAT" + String.format("%03d", lastNum + 1);
        } else {
            newCategoryId = "CAT001";
        }

        category.setCategoryFileId(newCategoryId);
        category.setIcon(fileService.fileUpload(List.of(icon), newCategoryId).get(0).getDocumentPath());
        return categoryRepository.save(category);
    }

}
