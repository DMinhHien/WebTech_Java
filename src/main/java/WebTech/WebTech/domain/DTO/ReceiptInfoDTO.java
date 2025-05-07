package WebTech.WebTech.domain.DTO;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptInfoDTO {
    private long userId;
    private Instant date;
    private String accountName;
}
