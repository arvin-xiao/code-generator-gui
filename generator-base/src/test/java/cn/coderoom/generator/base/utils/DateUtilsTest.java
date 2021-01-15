package cn.coderoom.generator.base.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void getLastDayOfWeek() {

        String str = DateUtils.getLastDayOfWeek("2020","15");

        System.out.println(str);
    }

}