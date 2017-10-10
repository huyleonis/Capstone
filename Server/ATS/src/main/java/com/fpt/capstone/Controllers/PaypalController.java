package com.fpt.capstone.Controllers;

import com.fpt.capstone.Paypal.Utils.PaypalPaymentIntent;
import com.fpt.capstone.Paypal.Utils.PaypalPaymentMethod;
import com.fpt.capstone.Paypal.Utils.UrlUtils;
import com.fpt.capstone.Services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class PaypalController {

    @Autowired
    protected PaypalService paypalService;

    private Logger log = Logger.getLogger(this.getClass());

    public static final String PAYPAL_SUCCESS_URL = "/pay/success";
    public static final String PAYPAL_CANCEL_URL = "/pay/cancel";

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> createPayment(HttpServletRequest req) {

        String cancelUrl = UrlUtils.getBaseUrl(req) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = UrlUtils.getBaseUrl(req) + "/" + PAYPAL_SUCCESS_URL;

        Map<String, String> map = new HashMap<>();

        try {
            Payment payment = paypalService.createPayment(4.00, "USD",
                    PaypalPaymentMethod.paypal, PaypalPaymentIntent.sale,
                    "payment description", cancelUrl, successUrl) ;

            for (Links links : payment.getLinks()){
                if (links.getRel().equalsIgnoreCase("approval_url")) {

                    RedirectUrls redirectUrls = new RedirectUrls();
                    redirectUrls.setReturnUrl(links.getHref());
                    payment.setRedirectUrls(redirectUrls);

                    map.put("paymentID", payment.getId());
                    return map;
                }
            }
        } catch (PayPalRESTException e){
            log.error(e.getMessage());
        }

        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay() { return "cancel.html";}

    @RequestMapping(value = PAYPAL_SUCCESS_URL, method = RequestMethod.POST)
    public String executePayment(@RequestParam("paymentID") String paymentId, @RequestParam("payerID") String payerId) {

        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equalsIgnoreCase("approved")){
                return "success";
            }
        } catch (PayPalRESTException e){
            log.error(e.getMessage());
        }

        return "cancel.html";
    }
}
