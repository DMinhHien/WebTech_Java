package WebTech.WebTech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import WebTech.WebTech.domain.Permission;
import WebTech.WebTech.domain.Role;
import WebTech.WebTech.domain.DTO.ResultPaginationDTO;
import WebTech.WebTech.domain.DTO.RoleDTO;
import WebTech.WebTech.repository.PermissionRepository;
import WebTech.WebTech.repository.RoleRepository;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean existsByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role handleCreateRole(Role role) {
        if (role.getPermissions() != null) {
            List<Long> reqPermissions = role.getPermissions().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(dbPermissions);
        }
        return this.roleRepository.save(role);
    }

    public Role handleUpdateRole(Role role) {
        Optional<Role> currentRole = this.roleRepository.findById(role.getId());
        if (currentRole.isPresent()) {
            Role saveRole = currentRole.get();
            saveRole.setName(role.getName());
            saveRole.setDescription(role.getDescription());
            saveRole.setActive(role.isActive());
            if (role.getPermissions() != null) {
                List<Long> reqPermissions = role.getPermissions().stream().map(x -> x.getId())
                        .collect(Collectors.toList());
                List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
                saveRole.setPermissions(dbPermissions);
            }
            return this.roleRepository.save(saveRole);
        }
        return null;
    }

    public ResultPaginationDTO fetchAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> page = this.roleRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        List<Role> list = new ArrayList<>();
        for (Role role : page.getContent()) {
            list.add(role);
        }
        resultPaginationDTO.setResult(list);
        return resultPaginationDTO;
    }

    public boolean isExistById(long id) {
        return this.roleRepository.existsById(id);
    }

    public void handleDeleteRole(long id) {
        this.roleRepository.deleteById(id);
    }

    public Role fetchById(long id) {
        Optional<Role> role = this.roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else
            return null;
    }

    public Role getRoleByName(String name){
        return this.roleRepository.findByName(name);
    }

    public Role disableRole(long id){
        Role role = this.fetchById(id);
        role.setActive(false);
        return this.roleRepository.save(role);
    }

    public Role activeRole(long id) {
        Role role = this.fetchById(id);
        role.setActive(true);
        return this.roleRepository.save(role);
    }
    public List<RoleDTO> getListRole() {
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDTO> roleDTOs = new ArrayList<>();
        for (Role role : roles) {
            RoleDTO roleDTO = RoleDTO.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .build();
            roleDTOs.add(roleDTO);
        }
        return roleDTOs;
    }
}
