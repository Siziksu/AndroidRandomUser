package com.siziksu.ru;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.siziksu.ru.common.PreferencesManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PreferencesTest {

    private PreferencesManager preferencesManager;

    @Before
    public void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        preferencesManager = new PreferencesManager(PreferenceManager.getDefaultSharedPreferences(appContext));
    }

    @Test
    public void workingWithPreferences_isCorrect() throws Exception {
        preferencesManager.deleteKey("test");
        preferencesManager.setString("test", "this is a test");
        assertEquals("this is a test", preferencesManager.getString("test", ""));
        preferencesManager.deleteKey("test");
    }
}
