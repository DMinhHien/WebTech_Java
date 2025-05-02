package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.ShopDTO;
import WebTech.WebTech.service.ShopService;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
public class ShopController {
    private final ShopService shopService;
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }
    @PostMapping("/Shops/create")
    public void createShop(@RequestBody Shop shop) {
        shopService.createShop(shop);
    } 
    @PostMapping("/Shops/delete")
    public void deleteShop(@RequestBody Long id) {
        shopService.deleteShop(id);
    }
    @PostMapping("/Shops/edit")
    public void updateShop(@RequestBody Shop shop) {
        shopService.updateShop(shop);
    }
    @PostMapping("/Shops/getListUse")
    public List<ShopDTO> getListUse() {
        return shopService.getAllShops();
    }
    @PostMapping("/Shops/getElementById/{id}")
    public ShopDTO getElementById(@PathVariable Long id) {
        return shopService.getShopById(id);
    }
    @PostMapping("/Shops/getElementByUserId/{userOd}")
    public ShopDTO getElementByUserId(@PathVariable Long userId) {
        return shopService.getShopByUserId(userId);
    }
    @PostMapping("/Shops/getShopId/{userId}")
    public Long getShopId(@PathVariable Long userId) {
        return shopService.getShopId(userId);
    }

    
}
