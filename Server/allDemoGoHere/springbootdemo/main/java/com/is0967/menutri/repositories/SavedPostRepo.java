package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 20-03-17.
 */
@Repository
public interface SavedPostRepo extends JpaRepository<SavedPost, Long> {

   @Modifying
   @Query(value = "insert into saved_posts(user_id, post_id) values(?1, ?2)", nativeQuery = true)
   int insertSavedPost(Long userId, Long postId);

   SavedPost findByUserIdAndPostId(Long userId, Long postId);

   List<SavedPost> findByUserId(Long userId);
}
