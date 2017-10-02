package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by NBL.Huyen on 22-02-17.
 */
@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {
   Like findByUserIdAndPostId(Long userid, Long postid);

   @Modifying
   @Query(value = "delete from likes where post_id = ?1", nativeQuery = true)
   void deleteByPostid(Long postId);

   List<Like> findByUserIdAndCreateDateGreaterThan(Long userId, Long lastCheckInMilis);

   List<Like> findByPostIdIn(List<Long> postIds);

   @Query(value = "select count (id) from likes where post_id = ?1", nativeQuery = true)
   Integer findNumOfLikeOfPost(Long postId);
}
