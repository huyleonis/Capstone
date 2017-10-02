package com.fpt.capstone.Services;

import com.paypal.api.payments.Payment;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PaypalService {
    Payment create(Payment payment);
    Payment execute(Payment payment);
}
