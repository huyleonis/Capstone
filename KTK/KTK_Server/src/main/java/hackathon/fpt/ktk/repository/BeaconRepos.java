package hackathon.fpt.ktk.repository;

import hackathon.fpt.ktk.entity.Beacon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BeaconRepos extends JpaRepository<Beacon, Integer>{

    @Query(value = "SELECT * FROM beacon WHERE uuid = :uuid " +
            "AND major = :major AND minor = :minor", nativeQuery = true)
    Beacon findByUuidAndMajorAndMinor(@Param(value = "uuid") String uuid,
                                      @Param(value = "major") int major,
                                      @Param(value = "minor") int minor);
}
