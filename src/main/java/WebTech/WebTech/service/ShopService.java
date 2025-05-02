package WebTech.WebTech.service;
import java.util.List;
import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.ShopDTO;
import WebTech.WebTech.repository.ShopRepository;
@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public boolean existsByName(String name) {
        return this.shopRepository.existsByName(name);
    }

    public List<ShopDTO> getAllShops() {
        List<Shop> shops = this.shopRepository.findAll();
        return shops.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ShopDTO getShopById(long id) {
        return convertToDTO(this.shopRepository.findById(id));
    }

    public Shop createShop(Shop shop) {
        return this.shopRepository.save(shop);
    }

    public Shop updateShop(Shop shop) {
        return this.shopRepository.save(shop);
    }

    public void deleteShop(long id) {
        this.shopRepository.deleteById(id);
    }
    public List<Shop> searchShops(String name) {
        return this.shopRepository.findByName(name);
    }
    public ShopDTO convertToDTO(Shop shop) {
        return ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .address(shop.getAddress())
                .image(shop.getImage())
                .userName(shop.getUser().getEmail())
                .rating(shop.getRating())
                .build();
    }
    public ShopDTO getShopByUserId (long userId) {
        return convertToDTO(this.shopRepository.findByUser_Id(userId));
    } 
    public Long getShopId (long userId) {
        return this.shopRepository.findByUser_Id(userId).getId();
    }
}
