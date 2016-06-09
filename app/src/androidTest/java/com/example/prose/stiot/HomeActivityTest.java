package com.example.prose.stiot;


import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by divi on 07/05/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {


    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<>(HomeActivity.class);




    @Test
    public void changeScreenToConnection(){
        onView(withId(R.id.imageButtonWifi)).perform(click());
        onView(withId(R.id.imageButtonWifi)).check(matches(withText("@+id/textView6")));


    }

//TO DO
/*
    A faire pour chaque OC?
    @Test
    public void changeScreenToItem(){
        onView(withId(R.id.BOUTON_CORRESPONDANT)).perform(click());
        onView(withId(R.id.TEXTEVIEW_SUR_ECRANITEM)).check(matches(withText("ID DU TEXTE VIEW ATTENDU")));


    }
*/
    /*
    TO DO
@Test
    public void changeScreenToParameter(){
        onView(withId(R.id.idParametre)).perform(click());
        onView(withId(R.id.TEXTEVIEW_SUR_ECRANPARAM)).check(matches(withText("ID DU TEXTEVIEW ATTENDU")));


}
*/
}

