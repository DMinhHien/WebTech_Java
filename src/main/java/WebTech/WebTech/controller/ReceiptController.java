package WebTech.WebTech.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Receipt;
import WebTech.WebTech.domain.ReceiptDetail;
import WebTech.WebTech.domain.DTO.ReceiptDetailDTO;
import WebTech.WebTech.domain.DTO.ReceiptDetailShopResponseDTO;
import WebTech.WebTech.domain.DTO.ReceiptResponseDTO;
import WebTech.WebTech.service.ReceiptService;
@RestController
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/Receipt/create/{userId}")
    public ResponseEntity<Receipt> createReceipt(@RequestBody List<ReceiptDetailDTO> receiptDTOs, @PathVariable Long userId) {
        List<ReceiptDetail> receiptDetails = receiptDTOs.stream().map(dto -> {
        ReceiptDetail detail = new ReceiptDetail();
        // Create a Product object with id populated.
        Product product = new Product();
        product.setId(dto.getIdProduct()); // convert idProduct to number if needed
        detail.setProduct(product);
        detail.setQuantity(dto.getQuantity());
        return detail;
    }).collect(Collectors.toList());
    
        return ResponseEntity.ok(receiptService.createReceipt(receiptDetails, userId));
    }
    @GetMapping("/Receipt/getListUse/{userId}")
    public List<ReceiptResponseDTO> getReceiptsByUserId(@PathVariable Long userId) {
        return receiptService.getReceiptsByUserId(userId);
    }
    @GetMapping("/Receipt/getListUseShop/{id}")
    public List<ReceiptDetailShopResponseDTO> getReceiptsByShopId(@PathVariable Long id) {
        return receiptService.getListUseShop(id);
    }
    @GetMapping("/Receipt/getReceiptDetail/{id}")
    public List<ReceiptDetailDTO> getReceiptById(@PathVariable Long id) {
        return receiptService.getReceiptDetail(id);
    }
    
}
