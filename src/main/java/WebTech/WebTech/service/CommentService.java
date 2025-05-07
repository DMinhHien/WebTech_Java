package WebTech.WebTech.service;
import java.util.List;

import WebTech.WebTech.domain.User;
import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Comment;
import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.domain.DTO.CommentDTO;
import WebTech.WebTech.repository.CommentRepository;
import WebTech.WebTech.repository.ProductRepository;
import WebTech.WebTech.repository.ShopRepository;
import WebTech.WebTech.repository.UserRepository;
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository; // Assuming you have a UserRepository

    public CommentService(CommentRepository commentRepository,
                          ProductRepository productRepository,
                          ShopRepository shopRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository; // Initialize the UserRepository
    }

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    public Comment getCommentById(long id) {
        return this.commentRepository.findById(id).orElse(null);
    }
    public List<CommentDTO> getCommentsByProductId(long productId) {
        List<Comment> Comments=this.commentRepository.findByProduct_Id(productId);
        return Comments.stream()
                .map(comment -> CommentDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .rating(comment.getRating())
                        .date(comment.getDate())
                        .productId(comment.getProduct().getId())
                        .userId(comment.getUser().getId())
                        .build())
                .toList();
    }

    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment= convertToComment(commentDTO);
        return this.commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        Comment updatedComment = this.commentRepository.save(comment);
        updateProductAndShopRating(String.valueOf(comment.getProduct().getId()));
        return updatedComment;
    }

    public void deleteComment(long id) {
        Comment comment = this.commentRepository.findById(id).orElse(null);
        if (comment != null) {
            this.commentRepository.deleteById(id);
            updateProductAndShopRating(String.valueOf(comment.getProduct().getId()));
        }
    }
    public void updateProductAndShopRating(String productIdStr) {
        // Assuming productId is stored as a long
        long productId = Long.parseLong(productIdStr);
        // Get the product for the given productId.
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return;
        }
        // Get all comments for the product that have a non-zero rating.
        List<Comment> productComments = commentRepository.findByProduct_IdAndRatingNot(productId, 0);
        double average = 0;
        if (!productComments.isEmpty()) {
            average = productComments.stream().mapToDouble(Comment::getRating).average().orElse(0);
        }
        // Update product rating.
        product.setRating(average);
        productRepository.save(product);

        // Update shop rating: find the shop associated with the product.
        Shop shop = shopRepository.findById(product.getShop().getId());
        if (shop == null) {
            return;
        }
        // Get all products for this shop that have a non-zero rating.
        List<Product> shopProducts = productRepository.findByShop_IdAndRatingNot(shop.getId(), 0);
        double shopAverage = 0;
        if (!shopProducts.isEmpty()) {
            shopAverage = shopProducts.stream().mapToDouble(Product::getRating).average().orElse(0);
        }
        shop.setRating(shopAverage);
        shopRepository.save(shop);
    }
    public CommentDTO convertToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .rating(comment.getRating())
                .date(comment.getDate())
                .productId(comment.getProduct().getId())
                .userId(comment.getUser().getId())
                .build();
    }
    public Comment convertToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setRating(commentDTO.getRating());
        comment.setDate(commentDTO.getDate());
        // Assuming you have methods to get Product and User by their IDs
        Product product = productRepository.findById(commentDTO.getProductId()).orElse(null);
        comment.setProduct(product);
        // Assuming you have a method to get User by ID
        User user = userRepository.findById(commentDTO.getUserId()).orElse(null);
        comment.setUser(user);
        return comment;
    }
    
}
