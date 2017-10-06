package com.fpt.capstone.Services;

import com.fpt.capstone.Paypal.Utils.PaypalPaymentIntent;
import com.fpt.capstone.Paypal.Utils.PaypalPaymentMethod;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

public interface PaypalService {
    Payment createPayment(Double total, String currency, PaypalPaymentMethod method, PaypalPaymentIntent intent, String description, String cancelUrl, String successUrl) throws PayPalRESTException;
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
