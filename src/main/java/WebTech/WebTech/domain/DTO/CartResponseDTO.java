package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CartResponseDTO {
    private String id;
    private String userId;
    private List<ShopGroupDTO> shops;
}