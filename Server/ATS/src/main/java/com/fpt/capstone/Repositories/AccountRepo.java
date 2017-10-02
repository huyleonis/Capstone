package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, String> {

//    @Query(value = "SELECT * FROM account WHERE Username LIKE ?1 AND Password LIKE ?2", nativeQuery = true)
    AccountEntity findByUsernameAndPassword(String Username, String Password);
}
