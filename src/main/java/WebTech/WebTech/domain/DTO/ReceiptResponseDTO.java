package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptResponseDTO {
    private long id;
    private String date;
    private double totalAmount; 
    private String shopName; 
}
