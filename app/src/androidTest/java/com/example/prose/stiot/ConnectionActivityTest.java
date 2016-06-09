package com.example.prose.stiot;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by divi on 08/05/16.
 */
public class ConnectionActivityTest {

    @Rule
    public ActivityTestRule<ConnectionActivity> connectionActivityTestRule = new ActivityTestRule<>(ConnectionActivity.class);


    @Test
    public void setIp() {
        onView(withId(R.id.editIpAdresse)).perform(typeText("127"), closeSoftKeyboard());
        onView(withId(R.id.editIpAdresse)).check(matches(withText("127")));
    }

    @Test
    public void changeScreenToHome() {
        onView(withId(R.id.bpBackHome)).perform(click());
        onView(withId(R.id.bpConnect)).check(matches(withText("@+id/bpConnection")));


    }
}



