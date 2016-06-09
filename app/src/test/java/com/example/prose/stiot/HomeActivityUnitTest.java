package com.example.prose.stiot;

/**
 * Created by lucas-pc on 18/05/16.
 */

import android.os.Message;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class HomeActivityUnitTest {

    HomeActivity home = new HomeActivity();

    @Before
    public void handler_test_init() throws Exception {

        Message message = home.handlerHome.obtainMessage(8,5,3);
        home.handlerHome.sendMessage(message);

        //assertEquals(home.handlerHome.);
    }

    @Test
    public void handler_test() throws Exception {

    }
}