package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfoForReceiptDTO {
    private long id;
    private String productName;
    private double unitPrice;
    private double totalPrice; // Calculated field: quantity * unitPrice
}