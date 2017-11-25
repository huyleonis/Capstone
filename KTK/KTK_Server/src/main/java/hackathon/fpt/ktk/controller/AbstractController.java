package hackathon.fpt.ktk.controller;

import hackathon.fpt.ktk.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractController {

    protected final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private LaneService laneService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleTypeService vehicleTypeService;
}
