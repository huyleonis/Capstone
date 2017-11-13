/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.utils;

/**
 *
 * @author hp
 */
public enum TransactionType {
    UNKNOWN(0, "Chưa biết"),
    AUTOMATION(1, "Tự động"),
    MANUAL(2, "Thủ công");
    
    private int type;
    private String display;

    private TransactionType(int type, String display) {
        this.type = type;
        this.display = display;
    }

    public int getType() {
        return type;
    }

    public String display() {
        return display;
    }

    @Override
    public String toString() {
        return display;
    }
    
    public TransactionType getType(int type) {
        switch(type) {
            case 0:
                return UNKNOWN;
            case 1:
                return AUTOMATION;
            case 2: 
                return MANUAL;
            default:
                    return UNKNOWN;
        }
    }

}
