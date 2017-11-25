package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepos extends JpaRepository<Transaction, Integer> {
}
