package resorses;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class M {
    public static void main(String[] args) {
        BigDecimal g = BigDecimal.valueOf(10.9);
        BigDecimal h = new BigDecimal(11.11111111111);
        BigDecimal b = g.multiply(h, MathContext.DECIMAL32);
        System.out.println(b);
        int i = 0;
        System.out.println("FLGHT" + (i + 1));
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        System.out.println(calendar);
        Random random = new Random();
        Date date2 = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-YYYY-MM 'at' HH:mm");
        for(int in = 0; in < 10; in++){
            Date nd = new Date(date2.getTime()+random.nextInt(1000000000));
            System.out.println(format.format(nd));
        }
    }
}

