package WebTech.WebTech.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import WebTech.WebTech.domain.Role;

import WebTech.WebTech.domain.User;
import WebTech.WebTech.domain.DTO.ResCreateUserDTO;
import WebTech.WebTech.domain.DTO.ResultPaginationDTO;
import WebTech.WebTech.domain.DTO.UserDTO;
import WebTech.WebTech.repository.RoleRepository;
import WebTech.WebTech.repository.UserRepository;
import WebTech.WebTech.util.error.IdInvalidException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String uploadDir = "src/main/resources/static/uploads/";
    public UserService(UserRepository userRepository, RoleService roleService, 
    PasswordEncoder passwordEncoder,   RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User handleCreateUser(User user){
        if(this.userRepository.existsByEmail(user.getEmail()) == false){
            User newUser = this.getUserByName(user.getName());
            if(newUser != null){
                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());
                return this.userRepository.save(newUser);
            }
            else{
                Role role = this.roleService.getRoleByName("User");
                user.setRole(role);
                this.userRepository.save(user);
                return user;
            }
        }
        return null;
    }

    public User handleCreateUserAdmin(User user){
        if(this.userRepository.existsByEmail(user.getEmail()) == false){
            Role role = this.roleService.getRoleByName(user.getRole().getName());
            user.setRole(role);
            this.userRepository.save(user);
            return user;
        }
        return null;
    }

    public ResCreateUserDTO convertToResCreateUserDTO(User user){
        ResCreateUserDTO resCreateUserDTO = new ResCreateUserDTO();
        resCreateUserDTO.setId(user.getId());
        resCreateUserDTO.setName(user.getName());
        resCreateUserDTO.setGender(user.getGender());
        resCreateUserDTO.setEmail(user.getEmail());
        resCreateUserDTO.setCreatedAt(user.getCreatedAt());
        resCreateUserDTO.setBirthDay(user.getBirthDay());
        resCreateUserDTO.setAddress(user.getAddress());
        resCreateUserDTO.setPhoneNumber(user.getPhoneNumber());
        return resCreateUserDTO;
    }

    public User fetchUserById(long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
    }
    public void handleDeleteUser(User user){
        this.userRepository.delete(user);
    }
    public User handleUpdateUser(User user){
        User dbUser = this.fetchUserById(user.getId());
        Role role = this.roleService.getRoleByName(user.getRole().getName());
        dbUser.setRole(role);
        dbUser.setEmail(user.getEmail());
        dbUser.setName(user.getName());
        User currentUser = this.userRepository.save(dbUser);
        return currentUser;
    }

    public User handleGetUserByUsername(String email){
        return this.userRepository.findByEmail(email);
    }
    public void updateUserToken(String token, String email) {
        User currentUser = this.handleGetUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }
    public User getUserByRefreshTokenAndEmail(String token, String email) {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    public User getUserByName(String name){
        return this.userRepository.findByName(name);
    }

    public User handleCreateUserBase(User user){
        return this.userRepository.save(user);
    }

    public User handleUpdateUserClient(String email, String name, Instant birthDay, String phoneNumber, String address, MultipartFile multipartFile) throws IOException{
        User dbUser = this.handleGetUserByUsername(email);
        if(name != null){
            dbUser.setName(name);
        }
        if(phoneNumber != null){
            dbUser.setPhoneNumber(phoneNumber);
        }
        if(birthDay != null){
            dbUser.setBirthDay(birthDay);
        }
        if(address != null){
            dbUser.setAddress(address);
        }
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String imagePath = saveImage(multipartFile);
            dbUser.setUserImage(imagePath);
        }
        return this.userRepository.save(dbUser);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // Hoặc trả về giá trị mặc định nếu không có ảnh mới
        }
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, imageFile.getBytes());
    
        return "/uploads/" + fileName;
    }

    public ResultPaginationDTO fetchAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> page = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(page.getContent());
        return resultPaginationDTO;
    }

    public String forgotPassword(String email){
        User user = this.handleGetUserByUsername(email);
        String newPassword = this.randomPassword();
        String hashPassword = this.passwordEncoder.encode(newPassword);
        user.setPassword(hashPassword);
        user = this.userRepository.save(user);
        return newPassword;
    }

    public String randomPassword(){
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            result.append(digit);
        }
        return result.toString();
    }

    public User changePassword(String email, String oldPassword, String password){
        User user = this.handleGetUserByUsername(email);
        if(!this.passwordEncoder.matches(oldPassword, user.getPassword())){
            return null;
        }
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);
        return this.userRepository.save(user);
    }
    public UserDTO fetchUserDTOById(long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birthDay(user.getBirthDay())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .phoneNumber(user.getPhoneNumber())
                    .userImage(user.getUserImage())
                    .role(user.getRole() != null ? user.getRole().getName() : null) // Extract role name
                    .build();
        }
        return null;
    }
    public User handleUpdateUserEdit(UserDTO dto) throws IdInvalidException {
        User user = fetchUserById(dto.getId());
        if(user == null){
            throw new IdInvalidException("User not found");
        }
        // Update the fields as needed
        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
        user.setEmail(dto.getEmail());
        user.setBirthDay(dto.getBirthDay());
        user.setPhoneNumber(dto.getPhoneNumber());
        return this.userRepository.save(user);
    }
    public List<UserDTO> getListUse() {
        List<User> users = this.userRepository.findAll();
        return users.stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .birthDay(user.getBirthDay())
                        .phoneNumber(user.getPhoneNumber())
                        .address(user.getAddress())
                        .role(user.getRole() != null ? user.getRole().getName() : null) // Extract role name
                        .build())
                .toList();
    }
    public User updateUserRole(long userId, long roleId) {
        // Fetch the user
        User user = fetchUserById(userId);
        if(user == null){
            throw new RuntimeException("User not found with id: " + userId);
        }
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        if(!roleOptional.isPresent()){
            throw new RuntimeException("Role not found with id: " + roleId);
        }
        Role role = roleOptional.get();
        user.setRole(role);
        return userRepository.save(user);
    }
    public User addRoleToUser(long userId, String roleName) throws IdInvalidException {
        User user = fetchUserById(userId);
        if(user == null) {
            throw new IdInvalidException("User not found with id: " + userId);
        }
        Role role = roleRepository.findByName(roleName);
        if(role == null) {
            throw new RuntimeException("Role not found with name: " + roleName);
        }
        user.setRole(role);
        return userRepository.save(user);
    }
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
