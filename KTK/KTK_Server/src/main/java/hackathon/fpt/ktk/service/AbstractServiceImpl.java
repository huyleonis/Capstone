package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractServiceImpl {

    protected final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccountRepos accountRepos;

    @Autowired
    private BeaconRepos beaconRepos;

    @Autowired
    private LaneRepos laneRepos;

    @Autowired
    private PriceRepos priceRepos;

    @Autowired
    private StationRepos stationRepos;

    @Autowired
    private TransactionRepos transactionRepos;

    @Autowired
    private VehicleRepos vehicleRepos;

    @Autowired
    private VehicleTypeRepos vehicleTypeRepos;
}
