package WebTech.WebTech.domain.DTO;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptInfoDTO {
    private long userId;
    private Instant date;
    private String accountName;
}
