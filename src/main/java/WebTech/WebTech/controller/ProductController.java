package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.RestController;

import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.DTO.ProductDTO;
import WebTech.WebTech.service.ProductService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/Product/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProductFromDTO(productDTO));
    }

    @GetMapping("/Product/getElementById/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/Product/edit")
    public ResponseEntity<Product> updateProduct( @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO));
    }

    @PostMapping("/Product/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/Product/getListUse")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/Product/getListUseCategory/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }
    @GetMapping("/Product/getListUseShop/{shopId}")
    public ResponseEntity<List<ProductDTO>> getProductsByShop(@PathVariable Long shopId) {
        return ResponseEntity.ok(productService.getProductsByShopId(shopId));
    }
    
}
