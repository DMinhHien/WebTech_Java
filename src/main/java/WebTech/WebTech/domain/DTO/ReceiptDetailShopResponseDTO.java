package WebTech.WebTech.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailShopResponseDTO {
    private long idProduct;
    private int quantity;
    private long idReceipt;
    private ReceiptInfoDTO receipt;
    private ProductInfoForReceiptDTO product;
}