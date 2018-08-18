package com.etc;


import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParseException {
        /*System.out.println( "Hello World!" );
        */
        String[] a = new String[5];
        a[0] = "yyyy-MM-dd HH:mm:ss";
        Date date = DateUtils.parseDate("2018-08-16 12:30:06", a);
        long time = date.getTime();
        System.out.println(time);
    }
}
