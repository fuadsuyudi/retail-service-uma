package net.suyudi.retail_uma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.suyudi.retail_uma.model.MenuMap;

public interface MenuMapRepository extends JpaRepository<MenuMap, Integer> {

    @Query("FROM MenuMap WHERE menu_id = null AND is_delete = 0")
    List<MenuMap> findAllMap();

}