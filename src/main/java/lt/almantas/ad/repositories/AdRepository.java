package lt.almantas.ad.repositories;

import lt.almantas.ad.model.Entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByTitleContainingIgnoreCase(String search);

    List<Ad> findAllByCategory_Id(Integer catID);
}