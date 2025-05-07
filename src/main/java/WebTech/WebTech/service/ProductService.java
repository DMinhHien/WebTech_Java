package WebTech.WebTech.service;

import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.Category;
import WebTech.WebTech.domain.DTO.ProductDTO;
import WebTech.WebTech.repository.CategoryRepository;
import WebTech.WebTech.repository.ProductRepository;
import WebTech.WebTech.repository.ShopRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;
    public ProductService(ProductRepository productRepository ,
                          ShopRepository shopRepository,
                          CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
    }

    public ProductDTO getProductById(long id) {
        return productRepository.findById(id)
            .map(this::convertToDTO)
            .orElse(null);
    }
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
    public Product createProductFromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setImage(productDTO.getImage());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setStatus(productDTO.getStatus());
        product.setRating(productDTO.getRating());
        
        // Get Shop by ID
        Shop shop = shopRepository.findById(productDTO.getIdShop());
        product.setShop(shop);
        
        // Get Category by ID
        Category category = categoryRepository.findById(productDTO.getCategoryId());
        product.setCategory(category);
        return product;
    }
    public Product updateProduct(ProductDTO productDTO) {
        Product product=convertToProduct(productDTO);
        return productRepository.save(product);
    }
    public ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .productName(product.getProductName())
            .unitPrice(product.getUnitPrice())
            .image(product.getImage())
            .quantity(product.getQuantity())
            .description(product.getDescription())
            .idShop(product.getShop() != null ? product.getShop().getId() : 0)
            .categoryId(product.getCategory() != null ? product.getCategory().getId() : 0)
            .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
            .status(product.getStatus())
            .rating(product.getRating())
            .build();
    }
    public Product convertToProduct (ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setUnitPrice(productDTO.getUnitPrice());
        product.setImage(productDTO.getImage());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setStatus(productDTO.getStatus());
        product.setRating(productDTO.getRating());

        // Get Shop by ID
        Shop shop = shopRepository.findById(productDTO.getIdShop());
        if (shop == null) {
            throw new EntityNotFoundException("Shop not found with id: " + productDTO.getIdShop());
        }
        product.setShop(shop);

        // Get Category by ID
        Category category = categoryRepository.findById(productDTO.getCategoryId());
        if (category == null) {
            throw new EntityNotFoundException("Category not found with id: " + productDTO.getCategoryId());
        }
        product.setCategory(category);

        return product; 
    }
    public List<ProductDTO> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}
    public List<ProductDTO> getProductsByShopId(long shopId) {
        List<Product> products = productRepository.findByShopId(shopId);
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    public List<ProductDTO> getProductsByCategoryId(long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
}
