package WebTech.WebTech.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/Receipts/create/{userId}")
    public void createReceipt(@RequestBody List<ReceiptDetail> receipt, @PathVariable Long userId) {
        receiptService.createReceipt(receipt,userId);
    }
    @PostMapping("/Receipts/getListUse/{userId}")
    public List<ReceiptResponseDTO> getReceiptsByUserId(@PathVariable Long userId) {
        return receiptService.getReceiptsByUserId(userId);
    }
    @PostMapping("/Receipts/getListUseShop/{id}")
    public List<ReceiptDetailShopResponseDTO> getReceiptsByShopId(@PathVariable Long id) {
        return receiptService.getListUseShop(id);
    }
    @PostMapping("/Receipts/getReceiptDetail/{id}")
    public List<ReceiptDetailDTO> getReceiptById(@PathVariable Long id) {
        return receiptService.getReceiptDetail(id);
    }
    
}
