/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ats.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chi Hieu
 */
public class CameraUtil {
    private List<String> listCarNumber = new ArrayList<>();
    int i = 0;
    public CameraUtil() {
        listCarNumber.add("123456");
        listCarNumber.add("135790");
        listCarNumber.add("523532");
        listCarNumber.add("123451");
        listCarNumber.add("123453");
        listCarNumber.add("123435");
    }
    public String getNumber(){
        return listCarNumber.get((++i)%listCarNumber.size());
    }
   
}