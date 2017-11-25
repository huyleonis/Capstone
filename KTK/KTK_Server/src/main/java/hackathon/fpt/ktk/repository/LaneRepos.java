package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Lane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaneRepos extends JpaRepository<Lane, Integer> {

}
