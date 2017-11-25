package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepos extends JpaRepository<Vehicle, Integer> {

}
