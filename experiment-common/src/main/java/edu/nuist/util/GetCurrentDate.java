package edu.nuist.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetCurrentDate {

    public String getCurrentDate() {
        Date currentTime=new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(currentTime);
    }

}
