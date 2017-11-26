package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * FROM account WHERE username = :username", nativeQuery = true)
    Account findByUsername(@Param(value = "username") String username);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update account set balance = :newBalance where id = :id", nativeQuery = true)
    int updateBalance(@Param("id") int id, @Param("newBalance") double newBalance);
}
