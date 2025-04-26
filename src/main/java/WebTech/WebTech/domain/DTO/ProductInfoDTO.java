package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfoDTO {
    private String productName;
    private double unitPrice;
    private String image;
    private int quantity;
}