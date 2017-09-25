package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by NBL.Huyen on 21-02-17.
 */
@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

   List<Post> findByUserIdAndDeletedFalse(Long userId);

   Post findByIdAndDeletedFalse(Long postId);

   List<Post> findAllByDeletedFalse();

   List<Post> findAlLByFeaturedTrueAndDeletedFalse();


   @Query(value = "select id from posts where item_id = ?1 " +
           "and type = ?2 and deleted = false", nativeQuery = true)
   Long findPostId(Long itemId, Integer type);

   Post findByItemIdAndType(Long itemId, Integer type);

   @Query(value = "select id from posts where user_id = ?1", nativeQuery = true)
   List<BigInteger> getPostIdsOfUser(Long userId);
}
