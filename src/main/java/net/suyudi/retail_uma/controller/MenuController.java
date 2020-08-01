package net.suyudi.retail_uma.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.suyudi.retail_uma.dto.request.MenuRequest;
import net.suyudi.retail_uma.dto.response.BaseResponse;
import net.suyudi.retail_uma.service.MenusService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenusService menusService;

    @GetMapping()
    public BaseResponse getAllMenu() {
        return BaseResponse.ok(menusService.findAll());
    }

    @GetMapping("/{id}")
    public BaseResponse getMenu(@PathVariable Integer id) {
        return BaseResponse.ok(menusService.findById(id));
    }

    @GetMapping("/map")
    public BaseResponse getAllMenuMap() {
        return BaseResponse.ok(menusService.findMap());
    }

    @GetMapping("/{id}/map")
    public BaseResponse getMenuMap(@PathVariable Integer id) {
        return BaseResponse.ok(menusService.findMapById(id));
    }
    
    @PostMapping()
    public ResponseEntity<BaseResponse> postMethodName(@Valid @RequestBody MenuRequest menu) {
        return new ResponseEntity<>(BaseResponse.created(menusService.create(menu)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BaseResponse putMenu(@PathVariable Integer id, @Valid @RequestBody MenuRequest menu) {
        return BaseResponse.ok(menusService.update(id, menu));
    }

    @DeleteMapping()
    public BaseResponse deleteMenu(@RequestParam("id") Integer id) {
        return BaseResponse.ok(menusService.delete(id));
    }

}