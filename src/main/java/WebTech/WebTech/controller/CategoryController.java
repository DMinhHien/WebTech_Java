package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;

import WebTech.WebTech.domain.Category;
import WebTech.WebTech.domain.DTO.CategoryDTO;
import WebTech.WebTech.service.CategoryService;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/Categories/create")
    public void createCategory(@RequestBody CategoryDTO catDTO) {
        categoryService.createCategory(catDTO);
    }
    @PostMapping("/Categories/delete")
    public void deleteCategory(@RequestBody Map<String, Long> payload) {
        Long id = payload.get("id");
    categoryService.deleteCategory(id);
    }

    @GetMapping("/Categories/getListUse")
    public ResponseEntity<List<Category>> getListCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    
}
