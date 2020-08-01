package net.suyudi.retail_uma.service;

import org.springframework.data.domain.Pageable;

import net.suyudi.retail_uma.dto.request.RoleRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.model.Role;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface RoleService {

    public List<Role> findAll();

    public Role findById(Integer id);

    public Object menuById(Integer id);

    public Role findByLevel(Integer level);

    public Role create(RoleRequest role);

    public Role update(Integer id, RoleRequest role);

    public Object updateMenu(Integer id, Object menus);

    public Boolean delete(Integer id);

}
