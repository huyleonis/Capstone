package com.fpt.capstone.Paypal.Servlets;

import com.fpt.capstone.Paypal.Utils.ResultPrinter;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;

import com.paypal.base.rest.PayPalRESTException;
import org.apache.log4j.Logger;

import static com.fpt.capstone.Paypal.Utils.ApiConstants.clientID;
import static com.fpt.capstone.Paypal.Utils.ApiConstants.clientSecret;
import static com.fpt.capstone.Paypal.Utils.ApiConstants.mode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class PaymentWithPaypalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(PaymentWithPaypalServlet.class);
    Map<String, String> map = new HashMap<String, String>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException{
        doPost(req, res);
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException{
        createPayment(req, res);
        req.getRequestDispatcher("response.jsp").forward(req, res);
    }

    public Payment createPayment(HttpServletRequest req, HttpServletResponse res){
        Payment createdPayment = null;

        APIContext apiContext = new APIContext(clientID, clientSecret, mode);
        if (req.getParameter("PayerID") != null){
            Payment payment = new Payment();
            if (req.getParameter("guid") != null){
                payment.setId(map.get(req.getParameter("guid")));
            }

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(req.getParameter("PayerID"));
            try {

                createdPayment = payment.execute(apiContext, paymentExecution);
                ResultPrinter.addResult(req, res, "Executed The Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
            } catch (PayPalRESTException e){
                ResultPrinter.addResult(req, res, "Executed The Payment", Payment.getLastRequest(), null, e.getMessage());
            }
        } else {

            // ###Details
            // Let's you specify details of a payment amount.
            Details details = new Details();
            details.setShipping("1");
            details.setSubtotal("5");
            details.setTax("1");

            // ###Amount
            // Let's you specify a payment amount.
            Amount amount = new Amount();
            amount.setCurrency("USD");
            // Total must be equal to sum of shipping, tax and subtotal.
            amount.setTotal("7");
            amount.setDetails(details);

            // ###Transaction
            // A transaction defines the contract of a
            // payment - what is the payment for and who
            // is fulfilling it. Transaction is created with
            // a `Payee` and `Amount` types
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction
                    .setDescription("This is the payment transaction description.");

            // ### Items
            Item item = new Item();
            item
                    .setName("Ground Coffee 40 oz")
                    .setQuantity("1")
                    .setCurrency("USD")
                    .setPrice("5");
            ItemList itemList = new ItemList();
            List<Item> items = new ArrayList<Item>();
            items.add(item);
            itemList.setItems(items);

            transaction.setItemList(itemList);


            // The Payment creation API requires a list of
            // Transaction; add the created `Transaction`
            // to a List
            List<Transaction> transactions = new ArrayList<Transaction>();
            transactions.add(transaction);

            // ###Payer
            // A resource representing a Payer that funds a payment
            // Payment Method
            // as 'paypal'
            Payer payer = new Payer();
            payer.setPaymentMethod("paypal");

            // ###Payment
            // A Payment Resource; create one using
            // the above types and intent as 'sale'
            Payment payment = new Payment();
            payment.setIntent("sale");
            payment.setPayer(payer);
            payment.setTransactions(transactions);

            // ###Redirect URLs
            RedirectUrls redirectUrls = new RedirectUrls();
            String guid = UUID.randomUUID().toString().replaceAll("-", "");
            redirectUrls.setCancelUrl(req.getScheme() + "://"
                    + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath() + "/paymentwithpaypal?guid=" + guid);
            redirectUrls.setReturnUrl(req.getScheme() + "://"
                    + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath() + "1" + guid);
            payment.setRedirectUrls(redirectUrls);

            // Create a payment by posting to the APIService
            // using a valid AccessToken
            // The return object contains the status;
            try {
                createdPayment = payment.create(apiContext);
                LOGGER.info("Created payment with id = "
                        + createdPayment.getId() + " and status = "
                        + createdPayment.getState());
                // ###Payment Approval Url
                Iterator<Links> links = createdPayment.getLinks().iterator();
                while (links.hasNext()) {
                    Links link = links.next();
                    if (link.getRel().equalsIgnoreCase("approval_url")) {
                        req.setAttribute("redirectURL", link.getHref());
                    }
                }
                ResultPrinter.addResult(req, res, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
                map.put(guid, createdPayment.getId());
            } catch (PayPalRESTException e) {
                ResultPrinter.addResult(req, res, "Payment with PayPal", Payment.getLastRequest(), null, e.getMessage());
            }
        }
        return createdPayment;
    }
}
