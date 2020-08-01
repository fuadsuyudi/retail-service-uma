package net.suyudi.retail_uma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.suyudi.retail_uma.model.User;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("FROM User WHERE id = :id AND isDelete = 0")
    User getUserById(@Param("id") Long id);

    @Query("FROM User WHERE username = :username")
    User getUserByUsername(@Param("username") String username);

    @Query("FROM User WHERE id =?1")
    User getByUserId(Long id);

    @Query("FROM User WHERE mobile = :mobile")
    User getUserByMobile(@Param("mobile") String mobile);
}
