package com.siziksu.ru;

import android.support.test.runner.AndroidJUnit4;

import com.siziksu.ru.common.DatesManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatesTest {

    @Test
    public void year2017_isCorrect() throws Exception {
        String date = "2017-02-16";
        assertEquals("2017", DatesManager.getYear(date));
    }

    @Test
    public void yearNotKnown_isCorrect() throws Exception {
        String date = "";
        assertEquals("N/A", DatesManager.getYear(date));
    }

    @Test
    public void registerDate_isCorrect() throws Exception {
        String date = "2015-12-04 00:10:11";
        assertEquals("04/12/2015", DatesManager.getRegisteredDate(date));
    }
}
