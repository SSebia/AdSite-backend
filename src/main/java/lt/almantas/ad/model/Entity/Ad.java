package lt.almantas.ad.model.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lt.almantas.ad.model.dto.AdCreateDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"ad\"")
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private int price;
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private AdCategory category;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdComment> comments;

    public Ad(AdCreateDTO adCreateDTO) {
        this.title = adCreateDTO.getTitle();
        this.description = adCreateDTO.getDescription();
        this.price = adCreateDTO.getPrice();
        this.city = adCreateDTO.getCity();
        this.comments = new ArrayList<>();
    }

    public Ad() {

    }

    public void addComment(AdComment adComment) {
        comments.add(adComment);
    }
}
