package WebTech.WebTech.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoForReceiptDTO {
    private long id;
    private String productName;
    private double unitPrice;
    private double totalPrice; // Calculated field: quantity * unitPrice
}