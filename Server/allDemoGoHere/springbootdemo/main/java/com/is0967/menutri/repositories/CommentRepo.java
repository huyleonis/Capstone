package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 22-02-17.
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

   @Modifying
   @Query(value = "insert into comments(" +
           "user_id, post_id, content, create_date) values(?1, ?2, ?3, ?4)", nativeQuery = true)
   int insertComment(Long userId, Long postId, String content, Long createDateInMilis);

   @Modifying
   @Query(value = "delete from comments where post_id = ?1", nativeQuery = true)
   void deleteByPostId(Long postId);

   List<Comment> findByUserIdAndCreateDateGreaterThan(Long userId, Long lastCheckInMilis);

   List<Comment> findByPostIdOrderByCreateDateAsc(Long postId);

   List<Comment> findByPostIdIn(List<Long> postIds);

   @Query(value = "select count (id) from comments where post_id = ?1", nativeQuery = true)
   Integer findNumOfCommentOfPost(Long postId);
}
