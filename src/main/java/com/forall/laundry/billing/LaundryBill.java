/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.forall.laundry.billing;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author jd
 */
public class LaundryBill {
    
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, 23);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    }
}