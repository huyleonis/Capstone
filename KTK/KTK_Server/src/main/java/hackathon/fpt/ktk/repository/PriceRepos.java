package hackathon.fpt.ktk.repository;


import hackathon.fpt.ktk.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepos extends JpaRepository<Price, Integer> {

}