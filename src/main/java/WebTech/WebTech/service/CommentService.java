package WebTech.WebTech.service;
import java.util.List;
import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Comment;
import WebTech.WebTech.domain.Product;
import WebTech.WebTech.domain.Shop;
import WebTech.WebTech.repository.CommentRepository;
import WebTech.WebTech.repository.ProductRepository;
import WebTech.WebTech.repository.ShopRepository;
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final ShopRepository shopRepository;

    public CommentService(CommentRepository commentRepository,
                          ProductRepository productRepository,
                          ShopRepository shopRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    public Comment getCommentById(long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public Comment createComment(Comment comment) {
        Comment createdComment = this.commentRepository.save(comment);
        updateProductAndShopRating(String.valueOf(comment.getProduct().getId()));
        return createdComment;
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
    
}
