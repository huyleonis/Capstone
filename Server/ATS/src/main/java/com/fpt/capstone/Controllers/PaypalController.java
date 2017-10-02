package com.fpt.capstone.Controllers;

import com.fpt.capstone.Services.PaypalService;
import com.paypal.api.payments.Payment;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ATS/paypal")
public class PaypalController {

    @Autowired
    protected PaypalService paypalService;

    Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Payment createPayment(Payment payment) {
        Payment returnedPayment = paypalService.create(payment);
//        logger.info(returnedPayment);
        return returnedPayment;
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    @ResponseBody
    public Payment executePayment(@RequestBody Payment payment) {
        logger.info("EXECUTE" + payment);
        return paypalService.execute(payment);
    }
}
