package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * FROM account WHERE username = :username", nativeQuery = true)
    Account findByUsername(@Param(value = "username") String username);

}
