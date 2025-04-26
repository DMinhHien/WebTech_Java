package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptDetailDTO {
    private long id;
    private long productId;
    private int quantity;
    private String Image;
    private String ProductName;
    private double UnitPrice;
}
