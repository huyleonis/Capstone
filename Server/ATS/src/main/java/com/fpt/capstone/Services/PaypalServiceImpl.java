package com.fpt.capstone.Services;

import com.paypal.api.payments.*;
import com.paypal.base.ClientCredentials;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.fpt.capstone.Paypal.Utils.ApiConstants.clientID;
import static com.fpt.capstone.Paypal.Utils.ApiConstants.clientSecret;
import static com.fpt.capstone.Paypal.Utils.ApiConstants.mode;


@Service
public class PaypalServiceImpl implements PaypalService {

    private static final Logger LOGGER = Logger.getLogger(PaypalServiceImpl.class);

    @Override
    public Payment create(Payment payment) {

        APIContext apiContext = new APIContext(clientID, clientSecret, mode);
        Payment createdPayment = null;

        // Set payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        // Set redirect URLs
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/ATS/paypal/cancel");
        redirectUrls.setReturnUrl("http://localhost:8080/ATS/paypal/process");

        // Set payment details
        Details details = new Details();
        details.setShipping("1");
        details.setSubtotal("5");
        details.setTax("1");

        // Payment amount
        Amount amount = new Amount();
        amount.setCurrency("USD");
        // Total must be equal to sum of shipping, tax and subtotal.
        amount.setTotal("7");
        amount.setDetails(details);

        // Transaction information
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction
                .setDescription("This is the payment transaction description.");

        // Add transaction to a list
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        // Add payment details
        payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);

        try {
            createdPayment = payment.create(apiContext);
            LOGGER.info("PaymentID=" + createdPayment.getId() + " and status=" + createdPayment.getState());

            List<Links> links = createdPayment.getLinks();
            for (Links link : links) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
//                    String url = link.getHref().replace("\u003d", "=");

                    redirectUrls.setReturnUrl(link.getHref());

                    System.out.println(redirectUrls.getReturnUrl());
                }
            }
            createdPayment.setRedirectUrls(redirectUrls);

            HashMap<String, String> map = new HashMap<>();
            String paymentID = "";
            map.put(paymentID, createdPayment.getId());

        } catch (PayPalRESTException e) {
            e.printStackTrace();
        } finally {
            return createdPayment;
        }
    }

    @Override
    public Payment execute(Payment payment) {
        Payment createdPayment = null;

        APIContext apiContext = new APIContext(clientID, clientSecret, mode);

        PaymentExecution paymentExecution = new PaymentExecution();
        try {
            createdPayment = payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        } finally {
            return createdPayment;
        }

    }
}
