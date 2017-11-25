package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepos extends JpaRepository<Station, Integer> {
}

