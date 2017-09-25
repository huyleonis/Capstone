package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.ReportedPost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by NBL.Huyen on 27-03-17.
 */
public interface ReportedPostRepo extends JpaRepository<ReportedPost, Long> {
   ReportedPost findByUserIdAndPostId(Long userId, Long postId);
}
