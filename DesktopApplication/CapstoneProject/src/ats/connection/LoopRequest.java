/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.connection;

import ats.dtos.VehiclePayment;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chi Hieu
 */
public class LoopRequest {

    Timer timer;
    //update List each 1 second

//    public Queue<VehiclePayment> getListTransaction(int seconds) {
//        timer = new Timer();
//        Queue<VehiclePayment> qe = new PriorityQueue<>();
//        timer.scheduleAtFixedRate(new AutoPaymentRequest() {
//            @Override
//            public void run() {
//                try {
//                    Queue<VehiclePayment> qe = null;
//                    qe = getAutoTrans(1);
//                } catch (Exception ex) {
//                    Logger.getLogger(LoopRequest.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }, 0, seconds * 1000);
//        //AutoPaymentRequest apr = new AutoPaymentRequest();
//        try {
//            //qe = ;
//        } catch (Exception ex) {
//            Logger.getLogger(LoopRequest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return qe;
//    }
//
//    public Queue<VehiclePayment> list() {
//
//        Queue<VehiclePayment> qe = new PriorityQueue<>();
//        VehiclePayment current = new VehiclePayment("1", "11A-1111", "Ô tô con", "Thanh toán thành công", 10000);
//        VehiclePayment current1 = new VehiclePayment("2", "22B-2222", "Xe tải", "Thanh toán thành công", 15000);
//        VehiclePayment current2 = new VehiclePayment("3", "33C-3333", "Xe tải", "Thanh toán thành công", 15000);
//        qe.offer(current);
//        qe.offer(current1);
//        qe.offer(current2);
//        return qe;
//    }
}
