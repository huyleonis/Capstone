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
public enum TransactionStatus {
            
    TRANS_INITIAL("Initial", "Khởi tạo"),
    TRANS_NOTPAY("Not pay", "Chưa thanh toán"),
    TRANS_SUCCESS("Success", "Thanh toán thành công"),
    TRANS_FAILED("Failed", "Thanh toán thất bại"),
    TRANS_FAILED_PASSED("Failed Passed", "Thanh toán thất bại - Đã qua trạm"),
    TRANS_FINISH("Finish" , "Hoàn thành giao dịch"),
    TRANS_PENDING("Pending", "Đang xử lý"),
    TRANS_ERROR("Error" , "Giao dịch sai");
    
    private final String name;
    private final String display;

    private TransactionStatus(String name, String display) {
        this.name = name;
        this.display = display;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String display() {
        return this.display;
    }

    @Override
    public String toString() {
        return name;
    }    
    
}
