package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by NBL.Huyen on 22-02-17.
 */
@Repository
public interface FollowRepo extends JpaRepository<Follow, Long> {
   @Query(value = "insert into follows(" +
           "user_id, followed_user_id, create_date) values (?1, ?2, ?3)", nativeQuery = true)
   Follow insertFollow(Long userId, Long followedUserId, Long createDateInMilis);

   @Query(value = "select followed_user_id from follows where user_id = ?1", nativeQuery = true)
   List<BigInteger> findFollowedIds(Long userId);

   List<Follow> findByFollowedUserId(Long followedUserId);

   List<Follow> findByUserIdAndCreateDateGreaterThan(Long userId, Long lastCheckInMilis);

   Follow findByUserIdAndFollowedUserId(Long userId, Long followedUserId);

}
