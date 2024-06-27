package lt.almantas.ad.model.dto;

import lombok.Data;

@Data
public class AdCreateDTO {
    private String title;
    private String description;
    private int price;
    private String city;
    private long catID;
}