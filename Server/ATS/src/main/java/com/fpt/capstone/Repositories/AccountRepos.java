package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

    @Query(value = "select license_plate from vehicle where id = " +
            "(select vehicle_id from account where username = ?1)", nativeQuery = true)
    String getLicensePlateOfAccount(String username);

    @Query(value = "SELECT * FROM account WHERE id = ?1", nativeQuery = true)
    Account findAccountById(Integer id);
    
    @Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Account checkLoginFromDesktopApp(String username, String password);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET balance = ?1  WHERE id =?2", nativeQuery = true)
    Integer updateAccountBalance(double newBalance, Integer id);
   
}
