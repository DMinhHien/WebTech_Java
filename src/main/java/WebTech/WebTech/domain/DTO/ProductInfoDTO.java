package WebTech.WebTech.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDTO {
    private String productName;
    private double unitPrice;
    private String image;
    private int quantity;
}