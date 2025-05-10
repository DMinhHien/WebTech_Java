package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.ShopDTO;
import WebTech.WebTech.service.ShopService;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
public class ShopController {
    private final ShopService shopService;
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }
    @PostMapping("/Shop/create")
    public void createShop(@RequestBody Shop shop) {
        shopService.createShop(shop);
    } 
    @DeleteMapping("/Shop/delete/{id}")
    public void deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
    }
    @PostMapping("/Shop/edit")
    public void updateShop(@RequestBody ShopDTO shopDTO) {
        shopService.updateShop(shopDTO);
    }
    @GetMapping("/Shop/getListUse")
    public List<ShopDTO> getListUse() {
        return shopService.getAllShops();
    }
    @GetMapping("/Shop/getElementById/{id}")
    public ShopDTO getElementById(@PathVariable Long id) {
        return shopService.getShopById(id);
    }
    @GetMapping("/Shop/getElementByUserId/{userId}")
    public ShopDTO getElementByUserId(@PathVariable Long userId) {
        return shopService.getShopByUserId(userId);
    }
    @GetMapping("/Shop/getShopId/{userId}")
    public Long getShopId(@PathVariable Long userId) {
        return shopService.getShopId(userId);
    }

    
}
