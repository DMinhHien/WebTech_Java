package WebTech.WebTech.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private long id;
    private String productName;
    private double unitPrice;
    private String image;
    private int quantity;
    private String description;
    private long idShop;  // Changed from Shop to long
    private long categoryId;   
    private String status;
    private Double rating;
    private String categoryName;  
}
