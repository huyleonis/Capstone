package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Account;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

	@Query(value = "select licensePlate from vehicle where id = "
			+ "(select vehicleId from account where username = ?1)", nativeQuery = true)
	String getLicensePlateOfAccount(String username);

	@Query(value = "SELECT * FROM account a WHERE a.username = :username", nativeQuery = true)
	Account findByUsername(@Param("username") String username);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update account set balance = :newBalance where id = :id", nativeQuery = true)
	int updateBalance(@Param("id") int id, @Param("newBalance") double newBalance);

	@Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
	Account checkLoginFromDesktopApp(String username, String password);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update account set token = :token where username = :username", nativeQuery = true)
	int updateToken(@Param("username") String username, @Param("token") String token);

	@Query(value = "SELECT a.token FROM account a WHERE a.username = :username", nativeQuery = true)
	String getTokenOfAccount(String username);


}
