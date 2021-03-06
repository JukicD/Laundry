package com.forall.laundry.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by jd on 8/17/15.
 */
public class LaundryUtil {


    public static boolean isToday(Date date){
        Date today = new Date();

        return today.getTime() - date.getTime() > 60 * 60 * 1000;
    }
    public static List<Date> getDaysOfThisWeek(){
        Date today = new Date();
        List<Date> daysOfWeek = new ArrayList<>();
        IntStream
                .iterate(2, n -> n + 1)
                .limit(7)
                .forEach(n ->
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setFirstDayOfWeek(Calendar.MONDAY);
                    cal.setTime(today);


                    if(n != 8){
                        cal.set(Calendar.DAY_OF_WEEK, n);
                    }else{
                        cal.set(Calendar.DAY_OF_WEEK, 1);
                    }
                    daysOfWeek.add(cal.getTime());
                });
        return daysOfWeek;
    }

    public Date getTrimmedDate(final Date date){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static void sendMessage(FacesMessage.Severity s, final String header, final String detail) {

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s, header, detail));
    }
}
