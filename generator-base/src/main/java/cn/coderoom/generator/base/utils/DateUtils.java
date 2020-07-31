package cn.coderoom.generator.base.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils extends DateUtil {

    public static String getYesterday() {
        return formatDate(offsetDay(new DateTime(), -1));
    }

    /**
     * 周转日期
     *
     * @return
     */
    public static String getLastDayOfWeek(String year, String week) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.YEAR, Integer.valueOf(year));
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DATE, 1);

        Calendar cal = (GregorianCalendar) c.clone();
        cal.add(Calendar.DATE , (Integer.valueOf(week)-1) * 7);

        return getLastDayOfWeek(cal.getTime());
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return sdf.format(c.getTime());
    }

}
