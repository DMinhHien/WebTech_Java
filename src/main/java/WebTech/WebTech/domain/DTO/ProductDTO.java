package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private long id;
    private String productName;
    private double unitPrice;
    private String image;
    private int quantity;
    private String description;
    private long idShop;  // Changed from Shop to long
    private long idCategory;   
    private String status;
    private Double rating;
    private String categoryName;  
}
