package com.fpt.capstone.Repositories;

import com.fpt.capstone.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

    @Query(value = "select license_plate from vehicle where id = " +
            "(select vehicle_id from account where username = ?1)", nativeQuery = true)
    String getLicensePlateOfAccount(String username);
}
