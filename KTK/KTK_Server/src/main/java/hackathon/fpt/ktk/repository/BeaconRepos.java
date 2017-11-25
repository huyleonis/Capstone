package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepos extends JpaRepository<Beacon, Integer>{
}
