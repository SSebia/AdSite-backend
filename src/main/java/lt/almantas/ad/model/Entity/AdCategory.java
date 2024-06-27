package lt.almantas.ad.model.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.ad.model.dto.AdCategoryCreateDTO;

import java.util.List;

@Entity
@Table(name = "\"ad_category\"")
@Data
public class AdCategory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ad> ads;

    public AdCategory(AdCategoryCreateDTO adCategoryCreateDTO) {
        this.name = adCategoryCreateDTO.getName();
    }

    public AdCategory() {

    }
}