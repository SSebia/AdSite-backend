package lt.almantas.ad.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.ad.model.Entity.AdCategory;
import lt.almantas.ad.model.dto.AdCategoryCreateDTO;
import lt.almantas.ad.model.dto.AdCategoryEditDTO;
import lt.almantas.ad.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Iterable<AdCategory> getCategories() {
        return categoryRepository.findAll();
    }

    public AdCategory createCategory(AdCategoryCreateDTO adCategoryCreateDTO) {
        AdCategory adCategory = new AdCategory(adCategoryCreateDTO);
        return categoryRepository.save(adCategory);
    }

    public AdCategory editCategory(Long id, AdCategoryEditDTO adCategoryEditDTO) {
        Optional<AdCategory> findCategory = categoryRepository.findById(id);
        if (findCategory.isEmpty()) {
            return null;
        }
        AdCategory currentAdCategory = findCategory.get();
        currentAdCategory.setName(adCategoryEditDTO.getName());
        return categoryRepository.save(currentAdCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
