/**
 * *
 *  * @file      HomeActivity.java
 *  * @author    Arnaud B
 *  * @edited by    Lucas B
 *  * @version   1.0
 *  * @date      19 April 2016
 *  * @copyright (The MIT Licence) Copyright (c) 2016 Prose2017-EquipeB1, ESEO, STMicroelectronics
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
 *  * (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
 *  * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 *  * subject to the following conditions:
 *  * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 *  * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 *  * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.example.prose.stiot;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.prose.stiot.ItemDetailOcActivity.ItemSensorAdapter;
import com.example.prose.stiot.ItemHomeActivity.ItemOc;
import com.example.prose.stiot.ItemHomeActivity.ItemOcAdapter;
import com.example.prose.stiot.ItemHomeActivity.ItemOcDetail;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    public static Context context;
    public static HomeActivity home = new HomeActivity();                                   /**< Declaration of the home activity to tell it in an another thread */

    public static RecyclerView recylist;                                                    /**< Declaration of the recyclerView  */
    public static ArrayList<ItemOc> tableauItemOc = new ArrayList<>();                      /**< List of ItemOc used by the recyclerView */
    public static ItemOcAdapter adapterItemOc = new ItemOcAdapter(tableauItemOc, home);     /**< Contain the contain of OCs, used by the recyclerView */

    int counter = 0;                                                                        /**< ... */
    public Intent intent;                                                                   /**< Creation of the intent to launch the service */

    public static LinearLayoutManager llm;                                                  /**< Linear Layout Manager */


    /**
     * @fn HomeActivity
     * @brief Constructor of the HomeActivity
     */
    public HomeActivity() {
    }


    /**
     * @fn handleHome
     * @brief Handler told by the service's thread
     * @param msg The message received from Service
     */
    public Handler handlerHome = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.e("Handler : ", "Arg1: " + msg.arg1 + "\targ2: " + msg.arg2 + "\tWhat: " + msg.what);
            try {
                initView();        /***/
                if (msg.arg1 == -1) {
                    Toast.makeText(getAppContext(), "google", Toast.LENGTH_LONG).show();
                    // showAlertConnection(getAppContext());
                }
                Log.e("Handler", "View initialized");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Handler", "Probleme View Initializer !!!"+e);
            }
            try {
                updateData(msg.arg1, msg.what, msg.arg2);
                Log.e("Handler :", "Data Sent");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Handler : ", "Probleme Data Sent !!!");
            }
        }
    };

    public static Context getAppContext() {
        return HomeActivity.home.context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Launch of the service
        intent = new Intent(this, MyService.class);
        this.startService(intent);

        Log.e("HomeActivity", "Yo !!! ");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recylist = (RecyclerView) findViewById(R.id.recylist);

        recylist.setAdapter(adapterItemOc);
        recylist.setHasFixedSize(true);
        llm = new LinearLayoutManager(home);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recylist.setLayoutManager(llm);
    }

    /**
     * @fn initView
     * @brief Insertion of the COs in view
     */
    public void initView() {
        int i = 0;
        for (i = 0; i < MyService.listConnectedObject.size(); i++) {
            if (!(MyService.listConnectedObject.get(i).getInView())) {
                // Insertion of OC in the COs list
                Log.d("InitView liste -->", "" + MyService.listConnectedObject.size());
                Log.d("InitView ligne -->", "" + MyService.listConnectedObject.get(0).sensorList.size());

                tableauItemOc.add(new ItemOc(MyService.listConnectedObject.get(i).getName(),
                        MyService.listConnectedObject.get(i).getName() + " detail", MyService.listConnectedObject.get(i).adapterLastMeasure, home));

                Log.d("InitView -->", "OC integre");
                MyService.listConnectedObject.get(i).setInView();
                adapterItemOc.notifyDataSetChanged();


            }
        }
    }


    /**
     * @fn updateData
     * @brief Insertion of the new data at the good position in the recyclerView
     * @param val Value of the sensor concerned in the good Co.
     * @param OCposition Position of the Co concerned (idCo).
     * @param valPosition Position of the sensor Concerned in the Co.
     */
    public void updateData(int val, int OCposition, int valPosition) {
        Log.e("InsertSensorInCo", "In updateData at " + valPosition);
        //MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).insertNewData("25/25/2015", val);

        if (!(MyService.listConnectedObject.get(OCposition).listItemOcDetail.size() < MyService.listConnectedObject.get(OCposition).getSensorNumber())) {
            // Delete the old line if the list is full
            MyService.listConnectedObject.get(OCposition).listItemOcDetail.remove(valPosition);
        }
        // And after insertion of the new line
        MyService.listConnectedObject.get(OCposition).listItemOcDetail.add(valPosition, new ItemOcDetail(MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).getName(),
                String.valueOf(MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).getLastData()),
                MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).getUnit()));

        adapterItemOc.notifyDataSetChanged();

        int size = ItemSensorAdapter.rvDatasList.size();
        for (counter = 0; counter < size; counter++) {
            ItemSensorAdapter.rvDatasList.get(counter).getAdapter().notifyDataSetChanged();  //

        }
    }

   /* public void updateDetail(){
        int size = ItemSensorAdapter.rvDatasList.size();

        for (int counter = 0; counter < size; counter++) {
            ItemSensorAdapter.rvDatasList.get(counter).getAdapter().notifyDataSetChanged();  //

        }
    }*/


    /**
     * @fn buttonWifiOnClick
     * @brief Listenner of Connection View Button and launch of this view if this button is clicked
     * @param v View HomeActivity
     */
    public void buttonWifiOnClick(View v) {
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivity(intent);

    }


    /**
     * @fn buttonParametersOnClick
     * @brief Listenner of Parameter View Button and launch of this view if this button is clicked
     * @param v View HomeActivity
     */
    public void buttonParametersOnClick(View v) {
        Intent intentParameters = new Intent(this, ParameterActivity.class);
        startActivity(intentParameters);
    }


    ///////////////////////////////////////////////////////////////////

    /**
     * @fn showAlertConnection
     * @bief WARNING don't use this method
     *
     * */


    public void showAlertConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Error Connection")
                .setPositiveButton("to Connect", null)
                .setPositiveButton("to Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}



