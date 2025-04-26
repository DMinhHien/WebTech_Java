package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptDetailShopResponseDTO {
    private long idProduct;
    private int quantity;
    private long idReceipt;
    private ReceiptInfoDTO receipt;
    private ProductInfoForReceiptDTO product;
}