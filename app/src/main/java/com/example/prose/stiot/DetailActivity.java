/**
 * @author David COUET
 * @version 0.0
 * @file DetailActivity
 * @date 14/05/16
 * @brief
 * @copyright (The MIT Licence) Copyright (c) 2016 Prose2017-EquipeB1, ESEO, STMicroelectronics
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.prose.stiot;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.prose.stiot.Model.ConnectedObject;
import com.example.prose.stiot.ItemDetailOcActivity.DividerItemDecoration;
import com.example.prose.stiot.ItemDetailOcActivity.ItemSensorAdapter;
import com.example.prose.stiot.Model.Data;
import com.example.prose.stiot.Network.ApplicationConnection;

import java.util.Calendar;




/**
 * @fn DetailActivity
 * @brief This class handles the Connected object detail screen
 *
 * */
public class DetailActivity extends AppCompatActivity {


    static String mHour ="and Time";                                                              /**< Text of the DetailActivity */
    static String mDate = "Date";                                                             /**< Text of the DetailActivity */

    public static DetailActivity details = new DetailActivity();
    private ConnectedObject connectedObject;
    private RecyclerView rvMeasures;
    private Parcelable recyclerViewState;
    private RecyclerView.LayoutManager myLayoutManager;
    private ItemSensorAdapter mAdapter = new ItemSensorAdapter(MyService.mSensorList, details);
    private int counter = 0;
    public static MyService service;                                            /**< Declaration of the service to modify MyService' values */
    private Intent intent;


    final String CO_NUMBER_IN_LIST = "number in list"; // Pour passer des paramètres entre deux activités


    /**
     * @fn onCreate
     * @brief Function Called when the activity is accessed for the first time. it sets up
     * the recyclerView and its layout. It selects the right connected object based on which button
     * has been pressed and displays its sensors.
     *
     * @return void
     *
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailco);

        service = new MyService();
        rvMeasures = (RecyclerView) findViewById(R.id.rvMeasures);
        myLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvMeasures.setLayoutManager(myLayoutManager);
        rvMeasures.setItemAnimator(new DefaultItemAnimator());
        rvMeasures.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        rvMeasures.setAdapter(mAdapter);

        setConnectedObject(MyService.listConnectedObject.get(Integer.parseInt(getIntent().getStringExtra(CO_NUMBER_IN_LIST))));
        initDetailActivity();

    }


    public void setConnectedObject(ConnectedObject connectedObject) {
        this.connectedObject = connectedObject;
        Log.e("MYCOID",Integer.toString(this.connectedObject.getId()));
    }
    /**
     * @fn initDetailActivity
     * @brief This function fills the sensorList with the sensor to display for this specific Connected Object.
     * it also sets the date and time from whom we want to get the datas from the gateway.
     *
     *
     * @return void
     *
     * */
    public void initDetailActivity() {


        for (counter = 0; counter < connectedObject.getSensorList().size(); counter++) {
            MyService.mSensorList.add(connectedObject.sensorList.get(counter));
        }

        setOcName(connectedObject.getName());
        setDate();
        setTime();


    }
    /**
     * @fn  setOcName
     * @brief  Change the  "Oc name" textview with the desired name
     *
     * */
    public void setOcName(String nom) {
        TextView ocName = (TextView) findViewById(R.id.tvOcName);
        ocName.setText(nom);
    }

    /**
     * @fn  setDate
     * @brief  Change the  "Oc Date" textview with the desired date in the DD/MM/YYYY format
     *
     * */
    public void setDate() {
        TextView ocDate = (TextView) findViewById(R.id.tvDate);
        ocDate.setText(mDate);

    }

    /**
     * @fn  setTime
     * @brief  Change the  "Time" textview with the desired time in the HH:MM:SS format
     *
     * */
    public void setTime() {
        TextView ocDate = (TextView) findViewById(R.id.tvPeriod);
        ocDate.setText(mHour);

    }

    /**
     * @fn  buttonGetDateOnClick
     * @brief When the button "bnSetDate" is pressed, clears the data list and ask the gateway for the datas from the date picked by the user.
     * It also stops asking
     * */
    public void buttonGetDataOnClick(View v){


        for (counter=0; counter< connectedObject.sensorList.size(); counter++) {
            Data temp = connectedObject.sensorList.get(counter).getData().get(connectedObject.sensorList.get(counter).getData().size()-1);
            connectedObject.sensorList.get(counter).getData().clear();
            connectedObject.sensorList.get(counter).getData().add(temp);
        }
        mAdapter.notifyDataSetChanged();


        Message message = service.handlerFromDetail.obtainMessage();
        message.arg1=this.connectedObject.getId();
        message.obj = mDate + "@" + mHour;
        service.handlerFromDetail.sendMessage(message);

        Log.e("getData","askAllDataOfOneCo OK Coid = " +connectedObject.getId()+ " handlerDetail object passed :  "+message.obj);

        MyService.newDate=true;

    }

    /**
     * @fn  buttonbpBackOnClick
     * @brief  when the button "bpBack" is pressed,Clears the sensor list and kills the activity to go back to home.
     *
     * */
    public void buttonbpBackOnClick(View v) {
        MyService.flagDisableActualizing=false;
        MyService.mSensorList.clear();
        finish();

    }
    /**
     * @fn  buttonbpDateClick
     * @brief  when the button "bpTime" is pressed,opens a timePicker for the user to set the desired time.
     *
     * */
    public void buttonbpTimeClick(View v) {
        DialogFragment newFragment = new TimePickerFragment();

        newFragment.show(getFragmentManager(), "timePicker");


    }
    /**
     * @fn  buttonbpDateClick
     * @brief  when the button "tvDate" is pressed,opens a datePicker for the user to set the desired date.
     *
     * */
    public void tvDateOnClick(View v){
        DialogFragment newFragment = new DatePickerFragment();

        newFragment.show(getFragmentManager(), "DatePicker");
    }


    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return true;
    }

    /**
     * @fn  onSaveInstanceState
     * @brief saves the instance state and finish the activity.
     *
     * */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyService.flagDisableActualizing=false;
        MyService.mSensorList.clear();
        finish();


    }


    /**
     * @fn  onPause
     * @brief
     *
     * */
    @Override
    protected void onPause() {

        super.onPause();
        MyService.flagDisableActualizing=false;
        recyclerViewState = rvMeasures.getLayoutManager().onSaveInstanceState();//save
        MyService.mSensorList.clear();
        finish();


    }

    /**
     * @fn class TimePickerFragment
     * @brief Used to display a Time picker to the user via a Dialog
     * After the user has set the time,Formats it to HH:MM, displays it and stores it in our variable.
     *
     *
     * @return void
     **/
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String mTime =Integer.toString(minute);;
            String hTime = Integer.toString(hourOfDay);
            if (minute <10) {
                mTime = "0"+ Integer.toString(minute);
            }

            if (hourOfDay<10){
                hTime = "0"+ Integer.toString(hourOfDay);
            }
            mHour = hTime + ":" + mTime;

            TextView ocPeriodTime =(TextView) this.getActivity().findViewById(R.id.tvPeriod);

            ocPeriodTime.setText(mHour);




        }


    }
    /**
     * @fn class DatePickerFragment
     * @brief Used to display a Date picker to the user via a Dialog
     * After the user has set the date,Formats it to DD/MM/YYYY, displays it and stores it in our variable.
     *
     *
     * @return void
     **/
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String mDay = Integer.toString(day);
            String mMonth = Integer.toString(month+1);
            if (month+1 <10) {
                mMonth = "0"+ Integer.toString(month+1);
            }

            if (day<10){
                mDay = "0"+ Integer.toString(day);
            }

            mDate =Integer.toString(year)+"/" +mMonth +"/" + mDay;
            TextView ocPeriodDate = (TextView) this.getActivity().findViewById(R.id.tvDate);
            ocPeriodDate.setText(mDate);
            // Do something with the date chosen by the user
        }
    }
}

