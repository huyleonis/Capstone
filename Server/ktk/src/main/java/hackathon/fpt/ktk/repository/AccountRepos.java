package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepos extends JpaRepository<Account, Integer> {
}
