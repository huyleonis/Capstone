package com.is0967.menutri.repositories;

import com.is0967.menutri.entities.MenuTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by an on 2/22/2017.
 */
@Repository
public interface MenuTagRepo extends JpaRepository<MenuTag, Long> {
   MenuTag findByName(String name);
}
