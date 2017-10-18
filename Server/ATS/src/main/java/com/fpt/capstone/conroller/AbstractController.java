package com.fpt.capstone.conroller;

import com.fpt.capstone.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {

    /**
     * @author dovie
     * Class này dùng để tập hợp inject các service nhằm giảm thiểu code
     */

    protected  final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected LaneService laneService;

    @Autowired
    protected PriceService priceService;

    @Autowired
    protected StationService stationService;

    @Autowired
    protected TransactionDetailService transactionDetailService;

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected VehicleService vehicleService;

    @Autowired
    protected BeaconService beaconService;
}
