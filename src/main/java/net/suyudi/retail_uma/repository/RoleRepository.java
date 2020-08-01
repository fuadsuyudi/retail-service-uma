package net.suyudi.retail_uma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.suyudi.retail_uma.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("FROM Role WHERE id = :id")
    Role getRoleById(@Param("id") Integer id);

    @Query("FROM Role WHERE level = :level")
    Role getRoleByLevel(@Param("level") Integer level);

    @Query("FROM Role WHERE id =?1")
    Role getByIdRole(Integer id);

}
