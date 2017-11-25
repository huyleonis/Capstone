package hackathon.fpt.ktk.controller;

import hackathon.fpt.ktk.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {

    protected final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected BeaconService beaconService;

    @Autowired
    protected LaneService laneService;

    @Autowired
    protected PriceService priceService;

    @Autowired
    protected StationService stationService;

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected VehicleService vehicleService;

    @Autowired
    protected VehicleTypeService vehicleTypeService;
}
