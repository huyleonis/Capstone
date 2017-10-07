package com.masasdani.paypal.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.masasdani.paypal.config.PaypalPaymentIntent;
import com.masasdani.paypal.config.PaypalPaymentMethod;
import com.masasdani.paypal.service.PaypalService;
import com.masasdani.paypal.util.URLUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class PaymentController {

    public static final String PAYPAL_SUCCESS_URL = "/pay/success";
    public static final String PAYPAL_CANCEL_URL = "/pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    @ResponseBody
    public Map<String, String> pay(HttpServletRequest req) {
        String cancelUrl = URLUtils.getBaseURl(req) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(req) + "/" + PAYPAL_SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(
                    1.99,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("paymentID", payment.getId());

                    return map;
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @RequestMapping(method = RequestMethod.POST, value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentID") String paymentId, @RequestParam("payerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

}
