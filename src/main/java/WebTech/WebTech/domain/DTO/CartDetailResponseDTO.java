package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDetailResponseDTO {
    private long cartId;
    private long userId;
    private String shopName;
    private String shopImage;
    private long cartDetailId;
    private long productId;
    private String productName;
    private double unitPrice;
    private int quantity;
}