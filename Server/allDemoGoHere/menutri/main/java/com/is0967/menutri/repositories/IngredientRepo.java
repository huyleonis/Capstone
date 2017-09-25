package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by NBL.Huyen on 15-02-17.
 */

/**
 * @Repository để truy cập db, có thể viết query SQL bình thường hoặc query trực tiếp, chỉ cần khai báo hàm theo cú pháp của Spring JPA
 * Cú pháp tắt xem ở đây: http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html, bảng 2.2
 * Coi hàm ví dụ ở dưới
 * interface extend JpaRepository<Class Entity, Kiểu của primary key>
 */
@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long> {
   /**
    * HÀM VÍ DỤ
    * Spring sẽ tự chuyển đổi thành câu query "SELECT * FROM INGREDIENTS WHERE NAME LIKE '%searchInput%' rồi return ra list luôn
    * đúng cú pháp nó mới convert đc, ghi sai cú pháp BUILD LỖI
    *
    */
//   List<Ingredient> findBySearchStringContainingDeletedFalse(String searchInput);


   /**
    * HÀM VÍ DỤ DÙNG NATIVE QUERY
    * Mấy câu truy vấn phức tạp quá thì dùng native query
    * @Query(value = "câu query", nativeQuery = true)
    */
   @Query(value = "insert into ingredients (name, imageurl) values (?1, ?2)", nativeQuery = true)
   Ingredient insertNewIngredient(String name, String imageurl);


   List<Ingredient> findAllByDeletedFalseOrderByIdAsc();

   @Query(value = "select * from ingredients where deleted = false " +
           "order by last_edit desc limit 1", nativeQuery = true)
   Ingredient getLastEditIngredient();

}
