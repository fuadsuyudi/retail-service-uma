package net.suyudi.retail_uma.service;

import java.util.List;

import net.suyudi.retail_uma.dto.request.MenuRequest;
import net.suyudi.retail_uma.model.Menu;
import net.suyudi.retail_uma.model.MenuMap;

public interface MenusService {

    public List<Menu> findAll();

    public Menu findById(Integer id);

    public Menu create(MenuRequest menu);

    public Menu update(Integer id, MenuRequest menu);

    public Boolean delete(Integer id);

    public List<MenuMap> findMap();

    public MenuMap findMapById(Integer id);
    
}