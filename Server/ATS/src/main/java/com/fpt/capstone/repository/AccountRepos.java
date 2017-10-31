package com.fpt.capstone.repository;

<<<<<<< HEAD:Server/ATS/src/main/java/com/fpt/capstone/Repositories/AccountRepos.java
import com.fpt.capstone.Entities.Account;
import javax.transaction.Transactional;
=======
import com.fpt.capstone.entity.Account;
>>>>>>> origin/WebAdmin:Server/ATS/src/main/java/com/fpt/capstone/repository/AccountRepos.java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

    @Query(value = "select license_plate from vehicle where id = " +
            "(select vehicle_id from account where username = ?1)", nativeQuery = true)
    String getLicensePlateOfAccount(String username);
    
    @Query(value = "SELECT * FROM account a WHERE a.username = :username", nativeQuery = true)
    Account getAccount(@Param("username") String username);
    
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update account set balance = :newBalance where id = :id", nativeQuery = true)
    int updateBalance(@Param("id") int id, @Param("newBalance") double newBalance);

<<<<<<< HEAD:Server/ATS/src/main/java/com/fpt/capstone/Repositories/AccountRepos.java
    @Query(value = "SELECT * FROM account WHERE id = ?1", nativeQuery = true)
    Account findAccountById(Integer id);
    
    @Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
    Account checkLoginFromDesktopApp(String username, String password);
      
=======
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET balance = ?1  WHERE id =?2", nativeQuery = true)
    Integer updateAccountBalance(double newBalance, Integer id);

    @Query(value = "SELECT * FROM account WHERE username = ?1 AND  password = ?2", nativeQuery = true)
    Account findByUsernameAndPassword(String username, String password);
>>>>>>> origin/WebAdmin:Server/ATS/src/main/java/com/fpt/capstone/repository/AccountRepos.java
}
