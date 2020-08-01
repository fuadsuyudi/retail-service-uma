package net.suyudi.retail_uma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.suyudi.retail_uma.dto.request.RoleRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.service.RoleService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public BaseResponse getRoleList() {
        return BaseResponse.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    public BaseResponse getRoleById(@PathVariable Integer id) {
        return BaseResponse.ok(roleService.findById(id));
    }

    @GetMapping("/{id}/menus")
    public BaseResponse getRoleMenu(@PathVariable Integer id) {
        return BaseResponse.ok(roleService.menuById(id));
    }
    
    @PostMapping()
    public ResponseEntity<BaseResponse> postRole(@RequestBody RoleRequest role) {
        return new ResponseEntity<>(BaseResponse.created(roleService.create(role)), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public BaseResponse putRole(@PathVariable Integer id, @RequestBody RoleRequest role) {
        return BaseResponse.ok(roleService.update(id, role));
    }

    @PutMapping("/{id}/menus")
    public BaseResponse putRole(@PathVariable Integer id, @RequestBody Object menus) {
        return BaseResponse.ok(roleService.updateMenu(id, menus));
    }

    @DeleteMapping()
    public BaseResponse deleteRole(@RequestParam("id") Integer id) {
        return BaseResponse.ok(roleService.delete(id));
    }

}