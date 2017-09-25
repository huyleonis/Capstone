package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 17-02-17.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   @Query(value = "select * from users where username ilike ?1 and password ilike ?2", nativeQuery = true)
   User findByUsernameAndPassword(String username, String password);

   @Query(value = "select * from users where username ilike '?1'", nativeQuery = true)
   User findByUsername(String username);

   @Query(value = "select * from users where " +
           "last_name ilike %?1% " +
           "or first_name ilike %?1% " +
           "or username ilike %?1%", nativeQuery = true)
   List<User> search(String value);

   List<User> findByFeaturedTrue();
}
