package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WebTech.WebTech.domain.Cart;
import WebTech.WebTech.domain.CartDetail;
import WebTech.WebTech.domain.DTO.CartDTO;
import WebTech.WebTech.domain.DTO.CartDetailDTO;
import WebTech.WebTech.domain.DTO.CartResponseDTO;
import WebTech.WebTech.service.CartService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;


import java.util.Map;



@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/Cart/create")
    public ResponseEntity<Cart> createCart(@RequestBody Map<String, Object> payload) {
        Long userId = Long.parseLong(payload.get("userId").toString());
        return ResponseEntity.ok(cartService.createCartfromUserId(userId));
    } 
    @GetMapping("/Cart/getListUse/{userId}")
    public ResponseEntity<CartResponseDTO> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartWithShopsByUserId(userId));
    }
    @PostMapping("/Cart/addCartProduct")
    public ResponseEntity<CartDetail> addCartProduct(@RequestBody Map<String, Object> payload) {
        ObjectMapper mapper = new ObjectMapper();
        // Extract the 'data' node from the payload and convert it to a CartDetailDTO
        JsonNode dataNode = mapper.convertValue(payload.get("data"), JsonNode.class);
        CartDetailDTO dto = mapper.convertValue(dataNode, CartDetailDTO.class);
        return ResponseEntity.ok(cartService.addCartProduct(dto));
    }
    @GetMapping("/Cart/getCartId/{userId}")
    public String getCartId(@PathVariable String userId) {
        return cartService.getCartId(Long.parseLong(userId));
    }
    @PostMapping("/Cart/deleteCartDetail/{id}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable String id) {
        cartService.deleteCartDetail(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
   @PostMapping("/Cart/editCartDetail")
    public ResponseEntity<CartDetail> updateCartDetail(@RequestBody Map<String, Object> payload) {
        ObjectMapper mapper = new ObjectMapper();
        // Extract the "data" object and convert it to a CartDetailDTO
        JsonNode dataNode = mapper.convertValue(payload.get("data"), JsonNode.class);
        CartDetailDTO cartDetailDTO = mapper.convertValue(dataNode, CartDetailDTO.class);
        // Call service method to update the quantity
        CartDetail updated = cartService.editCartDetail(cartDetailDTO);
        return ResponseEntity.ok(updated);
    }
}
