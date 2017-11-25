package hackathon.fpt.ktk.repository;


import hackathon.fpt.ktk.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepos extends JpaRepository<Price, Integer> {

    @Query(value = "SELECT * FROM price WHERE stationId = :stationId " +
            "AND typeid = :typeid", nativeQuery = true)
    Price findByStationIdAndTypeId(@Param(value = "stationId") int stationId,
                                   @Param(value = "typeid") int typeid);

}