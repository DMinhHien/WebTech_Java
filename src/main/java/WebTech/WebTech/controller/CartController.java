package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;

import WebTech.WebTech.domain.Cart;
import WebTech.WebTech.domain.DTO.CartDTO;
import WebTech.WebTech.domain.DTO.CartProductDTO;
import WebTech.WebTech.domain.DTO.CartResponseDTO;
import WebTech.WebTech.service.CartService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.http.HttpStatus;


@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/Cart/create")
    public ResponseEntity<Cart> createCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.createCartfromDTO(cartDTO));
    } 
    @GetMapping("/Cart/getListUse/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartWithShopsByUserId(userId));
    }
    
}
