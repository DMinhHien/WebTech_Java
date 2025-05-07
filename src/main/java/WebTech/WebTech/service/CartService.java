package WebTech.WebTech.service;

import WebTech.WebTech.domain.Cart;
import WebTech.WebTech.domain.CartDetail;
import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.CartDTO;
import WebTech.WebTech.domain.DTO.CartDetailDTO;
import WebTech.WebTech.domain.DTO.CartProductDTO;
import WebTech.WebTech.domain.DTO.CartResponseDTO;
import WebTech.WebTech.domain.DTO.ProductInfoDTO;
import WebTech.WebTech.domain.DTO.ShopGroupDTO;
import WebTech.WebTech.domain.DTO.ShopInfoDTO;
import WebTech.WebTech.repository.CartDetailRepository;
import WebTech.WebTech.repository.CartRepository;
import WebTech.WebTech.repository.ProductRepository;
import WebTech.WebTech.repository.UserRepository;

import WebTech.WebTech.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CartService {  
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public CartService(ProductRepository productRepository, 
                       CartRepository cartRepository, 
                       CartDetailRepository cartDetailRepository,
                       UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userRepository = userRepository;
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    public CartDTO convertDetailToDTO(Cart cart) {
        if (cart == null || cart.getUser() == null) {
            return null;
        } 
        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .build();
    }
    public Cart createCartfromDTO(CartDTO cartdto) {
        Cart cart = new Cart();
        cart.setId(cartdto.getId());
        User user = userRepository.findById(cartdto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + cartdto.getUserId()));
        cart.setUser(user);
        return cart;
    }

    public Cart getCartById(long id) {
        return cartRepository.findById(id);
    }

    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void deleteCart(long id) {
        cartRepository.deleteById(id);
    }
    public Cart findByUserId(long userId) {
        return cartRepository.findByUserId(userId);
    }
    public void deleteCartDetail(long id) {
        cartDetailRepository.deleteById(id);
    }
    public CartDTO convertToDTO(Cart cart) {
        if (cart == null || cart.getUser() == null) {
            return null;
        }
        
        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .build();
    }
    public CartResponseDTO getCartWithShopsByUserId(Long userIdStr) {
        long userId = userIdStr;
    
        Cart cart = findByUserId(userId);
        if (cart == null) {
            return null;
        }
    
        List<CartDetail> details = cartDetailRepository.findByCart_Id(cart.getId());
        Map<Long, List<CartDetail>> groupedDetails = details.stream()
            .filter(d -> d.getProduct() != null && d.getProduct().getShop() != null)
            .collect(Collectors.groupingBy(d -> d.getProduct().getShop().getId()));
         
        List<ShopGroupDTO> shopGroups = new ArrayList<>();
    
        for (Map.Entry<Long, List<CartDetail>> entry : groupedDetails.entrySet()) {
            Long shopId = entry.getKey();
            List<CartDetail> shopDetails = entry.getValue();
            Shop shop = shopDetails.get(0).getProduct().getShop();
            ShopInfoDTO shopInfo = ShopInfoDTO.builder()
                .name(shop.getName())
                .image(shop.getImage())
                .build();
            List<CartProductDTO> products = shopDetails.stream().map(detail -> {
                Product product = detail.getProduct();
                ProductInfoDTO productInfo = ProductInfoDTO.builder()
                    .productName(product.getProductName())
                    .unitPrice(product.getUnitPrice())
                    .image(product.getImage())
                    .quantity(product.getQuantity())
                    .build();
                return CartProductDTO.builder()
                    .id(String.valueOf(detail.getId()))
                    .idProduct(String.valueOf(product.getId()))
                    .quantity(detail.getQuantity())
                    .productInfo(productInfo)
                    .build();
            }).collect(Collectors.toList());
         
            ShopGroupDTO shopGroup = ShopGroupDTO.builder()
                .shopId(String.valueOf(shopId))
                .shopInfo(shopInfo)
                .products(products)
                .build();
         
             shopGroups.add(shopGroup);
        }
    
        return CartResponseDTO.builder()
            .id(String.valueOf(cart.getId()))
            .userId(String.valueOf(cart.getUser().getId()))
            .shops(shopGroups)
            .build();
    }
    public CartDetail addCartProduct(CartDetailDTO dto) {
        // Fetch the product entity
        Product product = productRepository.findById(dto.getIdProduct())
               .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getIdProduct()));
        
        // Fetch the cart entity (ensure cart is not null)
        Cart cart = cartRepository.findById(dto.getIdCart());
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + dto.getIdCart());
        }
        CartDetail existingDetail = cartDetailRepository.findByCart_IdAndProduct_Id(cart.getId(), product.getId());
        
        if (existingDetail != null) {

            existingDetail.setQuantity(existingDetail.getQuantity() + dto.getQuantity());
            return cartDetailRepository.save(existingDetail);
        } else {
            CartDetail newCartDetail = new CartDetail();
            newCartDetail.setProduct(product);
            newCartDetail.setQuantity(dto.getQuantity());
            newCartDetail.setCart(cart);
            return cartDetailRepository.save(newCartDetail);
        }
    }
    public CartDetail editCartDetail (CartDetailDTO cartDetailDTO) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailDTO.getId())
                .orElseThrow(() -> new RuntimeException("CartDetail not found with id: " + cartDetailDTO.getId()));
        cartDetail.setQuantity(cartDetailDTO.getQuantity());
        return cartDetailRepository.save(cartDetail);
    }
    public CartDetail deleteCartDetail (CartDetailDTO cartDetailDTO) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailDTO.getId())
                .orElseThrow(() -> new RuntimeException("CartDetail not found with id: " + cartDetailDTO.getId()));
       
        cartDetailRepository.delete(cartDetail);
        return cartDetail;
    }
    public String getCartId(long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            return String.valueOf(cart.getId());
        } else {
            return null;
        }
    }
}
