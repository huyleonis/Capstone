/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.capstone.PaymentModule;

/**
 *
 * @author hp
 */
public class NganLuongResult {

    private String errorCode;
    private String merchantId;
    private String merchantAccount;
    private String pinCard;
    private String cardSerial;
    private String typeCard;
    private String orderId;
    private String clientFullname;
    private String clientEmail;
    private String clientMobile;
    private String cardAmount;
    private String amount;
    private String transactionId;
    private String errorMessage;

    public NganLuongResult() {
    }

    public NganLuongResult(String errorCode, String merchantId, String merchantAccount, String pinCard, String cardSerial, String typeCard, String orderId, String clientFullname, String clientEmail, String clientMobile, String cardAmount, String amount, String transactionId, String errorMessage) {
        this.errorCode = errorCode;
        this.merchantId = merchantId;
        this.merchantAccount = merchantAccount;
        this.pinCard = pinCard;
        this.cardSerial = cardSerial;
        this.typeCard = typeCard;
        this.orderId = orderId;
        this.clientFullname = clientFullname;
        this.clientEmail = clientEmail;
        this.clientMobile = clientMobile;
        this.cardAmount = cardAmount;
        this.amount = amount;
        this.transactionId = transactionId;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getPinCard() {
        return pinCard;
    }

    public void setPinCard(String pinCard) {
        this.pinCard = pinCard;
    }

    public String getCardSerial() {
        return cardSerial;
    }

    public void setCardSerial(String cardSerial) {
        this.cardSerial = cardSerial;
    }

    public String getTypeCard() {
        return typeCard;
    }

    public void setTypeCard(String typeCard) {
        this.typeCard = typeCard;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getClientFullname() {
        return clientFullname;
    }

    public void setClientFullname(String clientFullname) {
        this.clientFullname = clientFullname;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(String clientMobile) {
        this.clientMobile = clientMobile;
    }

    public String getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(String cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    
}
