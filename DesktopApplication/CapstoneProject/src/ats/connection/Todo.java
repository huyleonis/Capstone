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
public class Todo {

    Timer timer;

    public Queue<VehiclePayment> ToDo(int seconds) {
        timer = new Timer();
        Queue<VehiclePayment> qe = new PriorityQueue<>();
        //VehiclePayment v = new VehiclePayment();
        timer.scheduleAtFixedRate(new RequestServer() {
            @Override
            public void run() {
                try {                   
                    VehiclePayment v = new VehiclePayment();
                    v = sendGet();
                   for(VehiclePayment vp : qe){
                       if(!v.getLicensePlate().equals(vp.getLicensePlate())){
                           qe.offer(v);
                       }
                   }                                               
                } catch (Exception ex) {
                    Logger.getLogger(Todo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, seconds * 1000);
        
        return qe;
    }
    
        public Queue<VehiclePayment> list(){
        
        Queue<VehiclePayment> qe = new PriorityQueue<>();
        VehiclePayment current = new VehiclePayment("11A-1111", "Ô tô con", "Thanh toán thành công", 10000);
        VehiclePayment current1 = new VehiclePayment("22B-2222", "Xe tải", "Thanh toán thành công", 15000);
        VehiclePayment current2 = new VehiclePayment("33C-3333", "Xe tải", "Thanh toán thành công", 15000);
        qe.offer(current);
        qe.offer(current1);
        qe.offer(current2);
        return qe;
    }

}
