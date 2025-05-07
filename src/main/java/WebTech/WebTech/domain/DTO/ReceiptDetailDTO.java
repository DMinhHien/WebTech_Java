package WebTech.WebTech.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailDTO {
    private long id;
    private long idProduct;
    private int quantity;
    private String Image;
    private String ProductName;
    private double UnitPrice;
}
