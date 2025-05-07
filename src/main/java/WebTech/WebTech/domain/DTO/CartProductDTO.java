package WebTech.WebTech.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {
    private String id;
    private String idProduct;
    private int quantity;
    private ProductInfoDTO productInfo;
}