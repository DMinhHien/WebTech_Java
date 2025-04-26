package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptInfoDTO {
    private long userId;
    private String date;
    private String accountName;
}
