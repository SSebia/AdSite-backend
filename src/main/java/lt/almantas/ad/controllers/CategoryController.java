package lt.almantas.ad.controllers;

import lt.almantas.ad.model.Entity.AdCategory;
import lt.almantas.ad.model.dto.AdCategoryCreateDTO;
import lt.almantas.ad.model.dto.AdCategoryEditDTO;
import lt.almantas.ad.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AdCategory>> getCategories() {
        Iterable<AdCategory> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AdCategory> createCategory(@RequestBody AdCategoryCreateDTO adCategoryCreateDTO) {
        AdCategory createdAdCategory = categoryService.createCategory(adCategoryCreateDTO);
        return new ResponseEntity<>(createdAdCategory, HttpStatus.CREATED);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<AdCategory> editCategory(@PathVariable Long id, @RequestBody AdCategoryEditDTO adCategoryEditDTO) {
        AdCategory editedAdCategory = categoryService.editCategory(id, adCategoryEditDTO);
        return (editedAdCategory != null ? new ResponseEntity<>(editedAdCategory, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
