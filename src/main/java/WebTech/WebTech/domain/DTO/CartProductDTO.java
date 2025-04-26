package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartProductDTO {
    private String id;
    private String idProduct;
    private int quantity;
    private ProductInfoDTO productInfo;
}