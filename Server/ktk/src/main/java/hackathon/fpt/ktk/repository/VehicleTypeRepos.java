package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepos extends JpaRepository<VehicleType, Integer> {

}
