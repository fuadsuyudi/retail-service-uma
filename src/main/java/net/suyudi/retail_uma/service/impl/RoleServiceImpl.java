package net.suyudi.retail_uma.service.impl;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.suyudi.retail_uma.dto.request.RoleRequest;
import net.suyudi.retail_uma.exception.InternalServerErrorException;
import net.suyudi.retail_uma.exception.UnprocessableEntityException;
import net.suyudi.retail_uma.model.Role;
import net.suyudi.retail_uma.repository.RoleRepository;
import net.suyudi.retail_uma.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public Role findByLevel(Integer level) {
        return roleRepository.getRoleByLevel(level);
    }

    @Override
    public Role create(RoleRequest data) {
        Role role = new Role();

        role.setName(data.getName());
        role.setDescription(data.getDescription());
        role.setLevel(data.getLevel());
        role.setCreatedAt(new Date());
        role.setUpdatedAt(new Date());
        role.setIsDelete(0);

        return roleRepository.save(role);
    }

    @Override
    public Role update(Integer id, RoleRequest data) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());

        role.setName(data.getName());
        role.setDescription(data.getDescription());
        role.setLevel(data.getLevel());
        role.setUpdatedAt(new Date());

        return roleRepository.save(role);
    }

    @Override
    public Boolean delete(Integer id) {
        Role role = roleRepository.getOne(id);

        if (role != null) {
            
            role.setDeletedAt(new Date());
            role.setIsDelete(1);

            roleRepository.save(role);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object menuById(Integer id) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Role role = roleRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());

            if(role.getMenus() != null) {
                return mapper.readTree(role.getMenus());
            }

            return role.getMenus();
        } catch (Exception e) {
            e.getMessage();
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Object updateMenu(Integer id, Object menus) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Role role = roleRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());

            role.setMenus(mapper.writeValueAsString(menus));
            role.setUpdatedAt(new Date());

            roleRepository.save(role);

            return menus;
        } catch (Exception e) {
            e.getMessage();
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    
}