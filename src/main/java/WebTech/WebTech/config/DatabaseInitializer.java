package WebTech.WebTech.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import WebTech.WebTech.domain.Permission;
import WebTech.WebTech.domain.Role;
import WebTech.WebTech.domain.User;
import WebTech.WebTech.repository.PermissionRepository;
import WebTech.WebTech.repository.RoleRepository;
import WebTech.WebTech.repository.UserRepository;


public class DatabaseInitializer implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            PermissionRepository permissionRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countPermissions = this.permissionRepository.count();
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();

        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(new Permission("Create a product", "/Product", "POST", "PRODUCT"));
            arr.add(new Permission("Update a product", "/Product", "PUT", "PRODUCT"));
            arr.add(new Permission("Delete a product", "/Product/{id}", "DELETE", "PRODUCT"));
            arr.add(new Permission("Get a product by id", "/Product/{id}", "GET", "PRODUCT"));
            arr.add(new Permission("Get all product", "/Product", "GET", "PRODUCT"));
            arr.add(new Permission("Restore a product", "/Product/Back/{id}", "GET", "PRODUCT"));

            arr.add(new Permission("Create a category", "/Category", "POST", "CATEGORY"));
            arr.add(new Permission("Update a category", "/Category", "PUT", "CATEGORY"));
            arr.add(new Permission("Delete a category", "/Category/{id}", "DELETE", "CATEGORY"));
            arr.add(new Permission("Get a category by id", "/Category/{id}", "GET", "CATEGORY"));
            arr.add(new Permission("Get all category", "/Category", "GET", "CATEGORY"));
            arr.add(new Permission("Restore a category", "/Category/Back/{id}", "GET", "CATEGORY"));

            arr.add(new Permission("Get cart", "/Cart/GetCart", "GET", "CART"));
            arr.add(new Permission("Add product to cart", "/Cart/AddProductToCart", "POST", "CART"));
            arr.add(new Permission("Delete product from cart", "/Cart/DeleteProductFromCart", "POST", "CART"));

            arr.add(new Permission("Create new import ticket", "/ImportTicket/Create", "POST", "IMPORT_TICKET"));
            arr.add(new Permission("Get all import tickets", "/ImportTickets", "GET", "IMPORT_TICKET"));
            arr.add(new Permission("Complete import tickets", "/ImportTicket/CompleteTicket", "POST", "IMPORT_TICKET"));
            arr.add(new Permission("Delete import tickets", "/ImportTicket/{id}", "DELETE", "IMPORT_TICKET"));
            arr.add(new Permission("Get detail import tickets", "/ImportTicket/Detail/{id}", "GET", "IMPORT_TICKET"));

            arr.add(new Permission("Create a permission", "/permissions", "POST", "PERMISSION"));
            arr.add(new Permission("Update a permission", "/permissions", "PUT", "PERMISSION"));
            arr.add(new Permission("Get all permissions", "/permissions", "GET", "PERMISSION"));
            arr.add(new Permission("Delete permissions by id", "/permissions/{id}", "DELETE", "PERMISSION"));
            arr.add(new Permission("Restore permissions by id", "/permissions/restore/{id}", "GET", "PERMISSION"));

            arr.add(new Permission("Create a product comment", "/ProductReview/CreateComment", "POST",
                    "PRODUCT_REVIEW"));
            arr.add(new Permission("Get all product comment", "/ProductReviews", "GET", "PRODUCT_REVIEW"));
            arr.add(new Permission("Delete product comment by id", "/ProductReviews/{id}", "DELETE", "PRODUCT_REVIEW"));
            arr.add(new Permission("Update product comment", "/ProductReviews", "PUT", "PRODUCT_REVIEW"));

            arr.add(new Permission("Create a role", "/roles", "POST", "ROLE"));
            arr.add(new Permission("Update a role", "/roles", "PUT", "ROLE"));
            arr.add(new Permission("Get all roles", "/roles", "GET", "ROLE"));
            arr.add(new Permission("Get role by id", "/roles/{id}", "GET", "ROLE"));

            arr.add(new Permission("Get service by id", "/services/{id}", "GET", "SERVICE"));
            arr.add(new Permission("Create a service", "/Service", "POST", "SERVICE"));
            arr.add(new Permission("Get all service", "/Service", "GET", "SERVICE"));
            arr.add(new Permission("Delete service by id", "/Service/{id}", "DELETE", "SERVICE"));
            arr.add(new Permission("Restore service by id", "/Service/Back/{id}", "GET", "SERVICE"));
            arr.add(new Permission("Update service", "/Service", "PUT", "SERVICE"));

            arr.add(new Permission("Get all service tickets", "/ServiceTickets", "GET", "SERVICE_TICKET"));
            arr.add(new Permission("Booking service (client)", "/ServiceTickets/Client", "POST", "SERVICE_TICKET"));
            arr.add(new Permission("Get service ticket by id", "/ServiceTickets/Detail/{id}", "GET", "SERVICE_TICKET"));
            arr.add(new Permission("Confirm service ticket", "/ServiceTickets/confirmServiceTicket", "POST",
                    "SERVICE_TICKET"));
            arr.add(new Permission("Confirm checkout", "/ServiceTickets/confirmCheckout", "POST", "SERVICE_TICKET"));
            arr.add(new Permission("Booking service (admin)", "/ServiceTickets/Admin", "POST", "SERVICE_TICKET"));

            arr.add(new Permission("Create store comment", "/StoreReviews", "POST", "STORE_REVIEW"));
            arr.add(new Permission("Get all store comments", "/StoreReviews", "GET", "STORE_REVIEW"));
            arr.add(new Permission("Delete store comment", "/StoreReviews/{id}", "DELETE", "STORE_REVIEW"));
            arr.add(new Permission("Update store comment", "/StoreReviews", "PUT", "STORE_REVIEW"));
            arr.add(new Permission("Response store review", "/StoreReviews/Response", "PUT", "STORE_REVIEW"));

            arr.add(new Permission("Create a supplier", "/Supplier", "POST", "SUPPLIER"));
            arr.add(new Permission("Get all supplier", "/Suppliers", "GET", "SUPPLIER"));
            arr.add(new Permission("Update supplier", "/Supplier", "PUT", "SUPPLIER"));
            arr.add(new Permission("Delete supplier by id", "/Supplier/{id}", "DELETE", "SUPPLIER"));
            arr.add(new Permission("Get supplier by id", "/Supplier/{id}", "GET", "SUPPLIER"));
            arr.add(new Permission("Restore supplier by id", "/Supplier/Back/{id}", "GET", "SUPPLIER"));

            arr.add(new Permission("Create a user admin", "/Users/CreateUserAdmin", "POST", "USER"));
            arr.add(new Permission("Update a user admin", "/Users", "PUT", "USER"));
            arr.add(new Permission("Get all user", "/Users", "GET", "USER"));
            arr.add(new Permission("Update user (client)", "/Users/UpdateClient", "PUT", "USER"));

            arr.add(new Permission("Get voucher by id", "/Voucher/{id}", "GET", "VOUCHER"));
            arr.add(new Permission("Create a voucher", "/Voucher", "POST", "VOUCHER"));
            arr.add(new Permission("Update voucher", "/Voucher", "PUT", "VOUCHER"));
            arr.add(new Permission("Delete a voucher", "/Voucher/{id}", "DELETE", "VOUCHER"));
            arr.add(new Permission("Restore a voucher", "/Voucher/Back/{id}", "GET", "VOUCHER"));
            arr.add(new Permission("Get all vouchers", "/Voucher", "GET", "VOUCHER"));

            arr.add(new Permission("Create order ticket client", "/SaleTickets/Client", "POST", "ORDER_TICKET"));
            arr.add(new Permission("Create order ticket admin", "/SaleTickets/CreateTicketAdmin", "POST", "ORDER_TICKET"));
            arr.add(new Permission("Get all order ticket client", "/SaleTickets/Client/GetAllSaleTickets", "GET", "ORDER_TICKET"));
            arr.add(new Permission("Get detail order ticket client", "/SaleTickets/Clients/GetDetail/{id}", "GET", "ORDER_TICKET"));
            arr.add(new Permission("Confirm complete order ticket", "/SaleTickets/ConfirmComplete", "POST", "ORDER_TICKET"));
            arr.add(new Permission("Confirm delivery order ticket", "/SaleTickets/ConfirmDelivery", "POST", "ORDER_TICKET"));
            arr.add(new Permission("Get all order tickets", "/SaleTickets", "GET", "ORDER_TICKET"));
            arr.add(new Permission("Delete order ticket", "/SaleTickets/{id}", "DELETE", "ORDER_TICKET"));

            arr.add(new Permission("Get inventory", "/Inventory/Save", "POST", "INVENTORY"));
            arr.add(new Permission("Get inventory detail", "/Inventory/Detail", "GET", "INVENTORY"));

            this.permissionRepository.saveAll(arr);
        }

        if (countRoles == 0) {
            List<Permission> allPermissions = this.permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Full permission");
            adminRole.setActive(true);
            adminRole.setPermissions(allPermissions);

            this.roleRepository.save(adminRole);

            Role customerRole = new Role();
            customerRole.setName("CUSTOMER");
            customerRole.setDescription("Customer role");
            customerRole.setActive(true);
            List<Permission> customerPermissions = new ArrayList<>();
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Product/{id}", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Product", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Category", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Cart/GetCart", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Cart/AddProductToCart", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Cart/DeleteProductFromCart", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/ProductReview/CreateComment", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/ProductReviews", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/ProductReviews/{id}", "DELETE"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/ProductReviews", "PUT"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/services/{id}", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Service", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/roles/{id}", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/ServiceTickets/Client", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/StoreReviews", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/StoreReviews", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/StoreReviews/{id}", "DELETE"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/StoreReviews", "PUT"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/Users/UpdateClient", "PUT"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/SaleTickets/Client", "POST"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/SaleTickets/Client/GetAllSaleTickets", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/SaleTickets/Clients/GetDetail/{id}", "GET"));
            customerPermissions.add(this.permissionRepository.findByApiPathAndMethod("/SaleTickets/ConfirmComplete", "POST"));
            customerRole.setPermissions(customerPermissions);
            this.roleRepository.save(customerRole);
        }

        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setEmail("admin@gmail.com");
            adminUser.setAddress("kt");
            adminUser.setName("Admin has full permission");
            adminUser.setPassword(this.passwordEncoder.encode("123456"));

            Role adminRole = this.roleRepository.findByName("ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }
            this.userRepository.save(adminUser);
        }

        if (countPermissions > 0 && countRoles > 0 && countUsers > 0) {
            System.out.println(">>> SKIP INIT DATABASE ~ ALREADY HAVE DATA...");
        } else
            System.out.println(">>> END INIT DATABASE");
    }
}
