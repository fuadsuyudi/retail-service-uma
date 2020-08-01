package net.suyudi.retail_uma.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.suyudi.retail_uma.dto.request.MenuRequest;
import net.suyudi.retail_uma.exception.UnprocessableEntityException;
import net.suyudi.retail_uma.model.Menu;
import net.suyudi.retail_uma.model.MenuMap;
import net.suyudi.retail_uma.repository.MenuMapRepository;
import net.suyudi.retail_uma.repository.MenuRepository;
import net.suyudi.retail_uma.service.MenusService;

@Service
public class MenusServiceImpl implements MenusService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapRepository menuMapRepository;

    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(Integer id) {
        return menuRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());
    }

    @Override
    public Menu create(MenuRequest data) {
        Menu menu = new Menu();

        menu.setMenuId(data.getMenuId());
        menu.setLabel(data.getLabel());
        menu.setIcon(data.getIcon());
        menu.setType(data.getType());
        menu.setLink(data.getLink());
        menu.setCreatedAt(new Date());
        menu.setUpdatedAt(new Date());
        menu.setIsDelete(0);

        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Integer id, MenuRequest data) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new UnprocessableEntityException());

        menu.setMenuId(data.getMenuId());
        menu.setLabel(data.getLabel());
        menu.setIcon(data.getIcon());
        menu.setType(data.getType());
        menu.setLink(data.getLink());
        menu.setUpdatedAt(new Date());

        return menuRepository.save(menu);
    }

    @Override
    public Boolean delete(Integer id) {
        Menu menu = menuRepository.getOne(id);

        if (menu != null) {
            
            menu.setDeletedAt(new Date());
            menu.setIsDelete(1);

            menuRepository.save(menu);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<MenuMap> findMap() {
        return menuMapRepository.findAllMap();
    }

    @Override
    public MenuMap findMapById(Integer id) {
        return menuMapRepository.getOne(id);
    }

}