package lt.almantas.ad.repositories;

import lt.almantas.ad.model.Entity.AdCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<AdCategory, Long> {
}
