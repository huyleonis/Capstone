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

	@Query(value = "select license_plate from vehicle where id = "
			+ "(select vehicle_id from account where username = ?1)", nativeQuery = true)
	String getLicensePlateOfAccount(String username);

	@Query(value = "SELECT * FROM account a WHERE a.username = :username", nativeQuery = true)
	Account findByUsername(@Param("username") String username);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update account set balance = :newBalance where id = :id", nativeQuery = true)
	int updateBalance(@Param("id") int id, @Param("newBalance") double newBalance);

	@Query(value = "SELECT * FROM account WHERE username = ?1 AND password = ?2", nativeQuery = true)
	Account checkLoginFromDesktopApp(String username, String password);
	
	@Query(value = "SELECT * FROM account a WHERE a.vehicle_id = :vehicleId", nativeQuery = true)
	Account findByVehicleId(@Param("vehicleId") int vehicleId);

}
