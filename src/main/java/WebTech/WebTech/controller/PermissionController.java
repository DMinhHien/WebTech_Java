package WebTech.WebTech.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import WebTech.WebTech.domain.Permission;
import WebTech.WebTech.domain.DTO.ResultPaginationDTO;
import WebTech.WebTech.service.PermissionService;
import WebTech.WebTech.util.error.IdInvalidException;
import jakarta.validation.Valid;

@RestController
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> createNewPermission(@Valid @RequestBody Permission permission)
            throws IdInvalidException {
        boolean existPermission = this.permissionService.isPermissionExist(permission);
        if (!existPermission) {
            return ResponseEntity.ok().body(this.permissionService.handleCreatePermission(permission));
        } else {
            throw new IdInvalidException("Permission da ton tai");
        }
    }

    @PutMapping("/permissions")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission)
            throws IdInvalidException {
        boolean isExist = this.permissionService.existsById(permission.getId());
        boolean existPermission = this.permissionService.isPermissionExist(permission);
        if (!isExist) {
            throw new IdInvalidException("Permission voi id = " + permission.getId() + " khong ton tai");
        }  
        if (existPermission) {
            if (this.permissionService.isSameName(permission)) {
                throw new IdInvalidException("Permission đã tồn tại.");
            }
        } 
        return ResponseEntity.ok().body(this.permissionService.handleUpdatePermission(permission));
        
    }

    @GetMapping("/permissions")
    public ResponseEntity<ResultPaginationDTO> getAllPermissions(@Filter Specification<Permission> spec,
            Pageable pageable) {
        ResultPaginationDTO resultPaginationDTO = this.permissionService.fetchAllPermissions(spec, pageable);
        return ResponseEntity.ok().body(resultPaginationDTO);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") long id) throws IdInvalidException {
        boolean isExist = this.permissionService.existsById(id);
        if (isExist) {
            this.permissionService.delete(id);
            return ResponseEntity.ok().body(null);
        } else {
            throw new IdInvalidException("Permission voi id = " + id + " khong ton tai");
        }
    }

    @GetMapping("/permissions/restore/{id}")
    public ResponseEntity<Void> restorePermission(@PathVariable("id") long id) throws IdInvalidException {
        boolean isExist = this.permissionService.existsById(id);
        if (isExist) {
            this.permissionService.restore(id);
            return ResponseEntity.ok().body(null);
        } else {
            throw new IdInvalidException("Permission voi id = " + id + " khong ton tai");
        }
    }
}