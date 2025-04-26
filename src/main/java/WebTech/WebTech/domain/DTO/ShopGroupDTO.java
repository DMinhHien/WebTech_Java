package WebTech.WebTech.domain.DTO;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ShopGroupDTO {
    private String shopId;
    private ShopInfoDTO shopInfo;
    private List<CartProductDTO> products;
}