package WebTech.WebTech.service;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Receipt;
import WebTech.WebTech.domain.ReceiptDetail;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.ProductInfoForReceiptDTO;
import WebTech.WebTech.domain.DTO.ReceiptDetailDTO;
import WebTech.WebTech.domain.DTO.ReceiptDetailShopResponseDTO;
import WebTech.WebTech.domain.DTO.ReceiptInfoDTO;
import WebTech.WebTech.domain.DTO.ReceiptResponseDTO;
import WebTech.WebTech.repository.ProductRepository;
import WebTech.WebTech.repository.ReceiptDetailRepository;
import WebTech.WebTech.repository.ReceiptRepository;
@Service
public class ReceiptService {
     private final ReceiptRepository receiptRepository;
    private final ReceiptDetailRepository receiptDetailRepository;
    private final ProductRepository productRepository;

    public ReceiptService(ReceiptRepository receiptRepository, 
                          ReceiptDetailRepository receiptDetailRepository,
                          ProductRepository productRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptDetailRepository = receiptDetailRepository;
        this.productRepository = productRepository;
    }

    public List<Receipt> getAllReceipts() {
        return this.receiptRepository.findAll();
    }

    public Receipt getReceiptById(long id) {
        return this.receiptRepository.findById(id).orElse(null);
    }

    public Receipt createReceipt(List<ReceiptDetail> receiptDetails, long userId) {
        Receipt receipt = new Receipt();
        String receiptId = UUID.randomUUID().toString().substring(0, 10);
        receipt.setId(Long.parseLong(receiptId));
        receipt.setDate(LocalDateTime.now().toInstant(java.time.ZoneOffset.UTC));
        receiptRepository.save(receipt);
        for (ReceiptDetail detail : receiptDetails) {
            // Retrieve the product and update its quantity.
            Product product = productRepository.findById(detail.getProduct().getId()).orElse(null);
            if (product != null) {
                product.setQuantity(product.getQuantity() - detail.getQuantity());
                productRepository.save(product);
            }
            
            // Generate a new id for the receipt detail and set its receipt id.
            detail.setId(Long.parseLong(UUID.randomUUID().toString().substring(0, 10)));
            detail.getReceipt().setId(Long.parseLong(receiptId));
            receiptDetailRepository.save(detail);
        }
        return receipt;
    }

    public Receipt updateReceipt(Receipt receipt) {
        return this.receiptRepository.save(receipt);
    }

    public void deleteReceipt(long id) {
        this.receiptRepository.deleteById(id);
    }
     public List<ReceiptResponseDTO> getReceiptsByUserId(Long userId) {
        // Fetch receipts for the given user; ensure your ReceiptRepository has this method
        List<Receipt> receipts = receiptRepository.findByUser_Id(userId);
        List<ReceiptResponseDTO> result = new ArrayList<>();

        for (Receipt receipt : receipts) {
            // Get all ReceiptDetails for this receipt
            List<ReceiptDetail> details = receiptDetailRepository.findByReceipt_Id(receipt.getId());

            // Calculate total amount: sum(quantity * unitPrice) for each detail record
            double totalAmount = details.stream().mapToDouble(rd -> {
                Product product = productRepository.findById(rd.getProduct().getId()).orElse(null);
                return product != null ? rd.getQuantity() * product.getUnitPrice() : 0;
            }).sum();

            // For ShopName, we fetch the shop name from the first receipt detail's product.
            String shopName = "";
            if (!details.isEmpty()) {
                ReceiptDetail firstDetail = details.get(0);
                Product product = productRepository.findById(firstDetail.getProduct().getId()).orElse(null);
                if (product != null && product.getShop() != null) {
                    Shop shop = product.getShop();
                    shopName = shop.getName();
                }
            }

            ReceiptResponseDTO dto = ReceiptResponseDTO.builder()
                    .id(receipt.getId())
                    .date(receipt.getDate())
                    .totalAmount(totalAmount)
                    .shopName(shopName)
                    .build();

            result.add(dto);
        }
        return result;
    }
    public List<ReceiptDetailShopResponseDTO> getListUseShop(Long shopId) {
        // Here we assume shopId is convertible to long; adjust as needed.
        List<ReceiptDetail> allDetails = receiptDetailRepository.findAll();
        List<ReceiptDetailShopResponseDTO> result = new ArrayList<>();

        // Filter receipt details whose product belongs to the given shopId.
        allDetails.stream()
            .filter(rd -> rd.getProduct() != null 
                    && rd.getProduct().getShop() != null 
                    && rd.getProduct().getShop().getId() == shopId)
            .forEach(rd -> {
                // Get product info and compute total price.
                Product product = rd.getProduct();
                double totalPrice = rd.getQuantity() * product.getUnitPrice();

                ProductInfoForReceiptDTO productDTO = ProductInfoForReceiptDTO.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .unitPrice(product.getUnitPrice())
                        .totalPrice(totalPrice)
                        .build();

                // Get receipt info (including AccountName from user, assuming Receipt has a User field)
                Receipt receipt = receiptRepository.findById(rd.getReceipt().getId()).orElse(null);
                String accountName = "";
                long userId = 0;
                if (receipt != null && receipt.getUser() != null) {
                    userId = receipt.getUser().getId();
                    accountName = receipt.getUser().getEmail();
                }
                ReceiptInfoDTO receiptInfo = ReceiptInfoDTO.builder()
                        .userId(userId)
                        .date(receipt != null ? receipt.getDate() : null)
                        .accountName(accountName)
                        .build();

                // Build result DTO for each receipt detail.
                ReceiptDetailShopResponseDTO dto = ReceiptDetailShopResponseDTO.builder()
                        .idProduct(product.getId())
                        .quantity(rd.getQuantity())
                        .idReceipt(rd.getReceipt().getId())
                        .receipt(receiptInfo)
                        .product(productDTO)
                        .build();

                result.add(dto);
            });
        return result;
    }
    public List<ReceiptDetailDTO> getReceiptDetail(Long receiptId) {
        List<ReceiptDetail> details = receiptDetailRepository.findByReceipt_Id(receiptId);
        List<ReceiptDetailDTO> result = new ArrayList<>();

        for (ReceiptDetail rd : details) {
            Product product = productRepository.findById(rd.getProduct().getId()).orElse(null);
            String image = product != null ? product.getImage() : "";
            double unitPrice = product != null ? product.getUnitPrice() : 0;
            String productName = product != null ? product.getProductName() : "";
        
            ReceiptDetailDTO dto = ReceiptDetailDTO.builder()
                    .id(rd.getId())
                    .productId(rd.getProduct().getId())
                    .quantity(rd.getQuantity())
                    .Image(image)
                    .UnitPrice(unitPrice)
                    .ProductName(productName)
                    .build();
            result.add(dto);
        }
        return result;
    }
}
