package WebTech.WebTech.domain.DTO;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptResponseDTO {
    private long id;
    private Instant date;
    private double totalAmount; 
    // private String shopName; 
}
