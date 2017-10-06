package com.fpt.capstone.Services;


import static com.fpt.capstone.Paypal.Utils.PaypalConstant.clientID;
import static com.fpt.capstone.Paypal.Utils.PaypalConstant.clientSecret;
import static com.fpt.capstone.Paypal.Utils.PaypalConstant.mode;


import com.fpt.capstone.Paypal.Utils.PaypalPaymentIntent;
import com.fpt.capstone.Paypal.Utils.PaypalPaymentMethod;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaypalServiceImpl implements PaypalService {

    APIContext apiContext = new APIContext(clientID, clientSecret, mode);

    @Override
    public Payment createPayment(Double total, String currency,
                                 PaypalPaymentMethod method, PaypalPaymentIntent intent,
                                 String description, String cancelUrl, String successUrl) throws PayPalRESTException{

        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {

        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
