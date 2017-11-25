package hackathon.fpt.ktk.service;

import hackathon.fpt.ktk.repository.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractServiceImpl {

    protected final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    protected AccountRepos accountRepos;

    @Autowired
    protected BeaconRepos beaconRepos;

    @Autowired
    protected LaneRepos laneRepos;

    @Autowired
    protected PriceRepos priceRepos;

    @Autowired
    protected StationRepos stationRepos;

    @Autowired
    protected TransactionRepos transactionRepos;

    @Autowired
    protected VehicleRepos vehicleRepos;

    @Autowired
    protected VehicleTypeRepos vehicleTypeRepos;
}
