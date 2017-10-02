package com.fpt.capstone.Paypal;

import com.paypal.api.payments.CreditCard;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class JavaSampleOne {
    public static void main(String[] args) {
        // Replace these values with your clientId and secret. You can use these to get started right now.
        String clientId = "Aavu1mJiJ_f2wn8NtXsdwYhreXgNfRwTI1LnKUNuQpwbkiJDP-MBaoKJRzvFsmsu2jKEb1pER6BqUKD5";
        String clientSecret = "EDmPbEz-kfwQMKQNHRwUYyOVs6Qf7gkEvH5CYeBAnJAASv8_tszLvyZ0N0jFU99EQzRF_2ehwhQI5jve";

        // Create a Credit Card
        CreditCard card = new CreditCard()
                .setType("visa")
                .setNumber("4417119669820331")
                .setExpireMonth(11)
                .setExpireYear(2019)
                .setCvv2(012)
                .setFirstName("Joe")
                .setLastName("Shopper");

        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            card.create(context);
            System.out.println(card);
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
    }
}
