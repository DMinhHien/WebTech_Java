package WebTech.WebTech.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turkraft.springfilter.boot.Filter;

import WebTech.WebTech.domain.User;
import WebTech.WebTech.domain.DTO.ResCreateUserDTO;
import WebTech.WebTech.domain.DTO.ResultPaginationDTO;
import WebTech.WebTech.domain.DTO.UserDTO;
import WebTech.WebTech.service.UserService;
import WebTech.WebTech.util.error.IdInvalidException;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/User")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@RequestBody User postUser){
        String hashPassword = this.passwordEncoder.encode(postUser.getPassword());
        postUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(postUser);
        ResCreateUserDTO resCreateUserDTO = this.userService.convertToResCreateUserDTO(newUser);
        return ResponseEntity.ok().body(resCreateUserDTO);
    }

    @PostMapping("/User/CreateUserAdmin")
    public ResponseEntity<ResCreateUserDTO> createNewUserAdmin(@RequestBody User user) throws IdInvalidException{
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUserAdmin(user);
        if(newUser == null){
            throw new IdInvalidException("Email da ton tai");
        }
        ResCreateUserDTO resCreateUserDTO = this.userService.convertToResCreateUserDTO(newUser);
        return ResponseEntity.ok().body(resCreateUserDTO);
    }

    @DeleteMapping("/User/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws IdInvalidException{
        User user = this.userService.fetchUserById(id);
        if(user == null){
            throw new IdInvalidException("User not found");
        }
        this.userService.handleDeleteUser(user);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/User/getElementById/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id) throws IdInvalidException{
        UserDTO user = this.userService.fetchUserDTOById(id);
        if(user == null){
            throw new IdInvalidException("User not found");
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/User")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User currentUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok().body(currentUser);
    }

    @PutMapping("/User/UpdateClient")
    public ResponseEntity<User> updateUserClient(@RequestParam(value = "email") String email, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "birthDay", required = false) Instant birthDay, @RequestParam(value = "phoneNumber", required = false) String phoneNumber, @RequestParam(value = "address", required = false) String address, @RequestParam(value = "userImage", required = false) MultipartFile userImage) throws IOException{
        return ResponseEntity.ok().body(this.userService.handleUpdateUserClient(email, name, birthDay, phoneNumber, address, userImage));
    }
    
    @GetMapping("/User")
    public ResponseEntity<ResultPaginationDTO> getAllUsers (@Filter Specification<User> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.userService.fetchAllUsers(spec, pageable));
    }

    @PostMapping("/User/ChangePassword")
    public ResponseEntity<User> changePassword(@RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("password") String password) throws IdInvalidException{
        if(this.userService.changePassword(email, oldPassword, password) == null){
            throw new IdInvalidException("Mat khau hien tai khong dung");
        }
        return ResponseEntity.ok().body(this.userService.changePassword(email, oldPassword, password));
    }
    @PutMapping("/User/EditUser")
    public ResponseEntity<User> updateUserEdit(@RequestBody UserDTO dto) throws IdInvalidException {
        // In the service, update only the fields from dto.
        User updatedUser = userService.handleUpdateUserEdit(dto);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/User/getListUse")
    public ResponseEntity<List<UserDTO>> getListUse() {
        return ResponseEntity.ok(this.userService.getListUse());
    }
}
