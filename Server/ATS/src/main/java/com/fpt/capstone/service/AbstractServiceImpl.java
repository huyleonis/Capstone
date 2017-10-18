package com.fpt.capstone.service;

import com.fpt.capstone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractServiceImpl {

    /**
     * @author dovie
     * Class này để tập hợp các injection từ repository nhằm giảm thiểu code
     */

    @Autowired
    protected AccountRepos accountRepos;

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
    protected BeaconRepos beaconRepos;
}
