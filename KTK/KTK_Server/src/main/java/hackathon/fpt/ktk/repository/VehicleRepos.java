package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepos extends JpaRepository<Vehicle, Integer> {

    @Query(value = "SELECT * FROM vehicle WHERE licensePlate = :licensePlate", nativeQuery = true)
    Vehicle findByLicensePlate(@Param(value = "licensePlate") String licensePlate);
}
