package WebTech.WebTech.service;
import java.util.List;
import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Shop;
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

    public List<Shop> getAllShops() {
        return this.shopRepository.findAll();
    }

    public Shop getShopById(long id) {
        return this.shopRepository.findById(id);
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
    
}
