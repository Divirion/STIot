/**
 * *
 *  * @file      ParameterActivity.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      01 May 2016
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

// Imports
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prose.stiot.ItemParameterActivity.ItemParam;
import com.example.prose.stiot.ItemParameterActivity.ItemParamAdapter;

import java.util.ArrayList;


// Beginning of the class
public class ParameterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnClickListener{

    Spinner spinner;                                                            /**< Use for the Choice list of time range */
    ArrayAdapter<CharSequence> adapter;                                         /**< Adapter for this choice list */
    public static ArrayList<ItemParam> listItemParam = new ArrayList<>();       /**< List of ItemAdapter used by the recyclerView */
    public static ItemParamAdapter ipa = new ItemParamAdapter(listItemParam);   /**< Adapter of listItemIParam used by the recyclerView  */

    public static MyService service;                                            /**< Declaration of the service to modify MyService' values */

    public static RecyclerView recylistParam;                                   /**< Declaration of the recyclerView  */

    EditText edtT1 = null;                                                      /**< First EditText out of the recyclerView */
    EditText edtT2 = null;                                                      /**< Second EditText out of the recyclerView */

    private Button b1 = null;                                                   /**< Button Back to the HomeActivity */

    public static int nb1 = 0;                                                  /**< Integer to receive value from the first EditText */
    public static int nb2 = 0;                                                  /**< Integer to receive value from the second EditText */
    public static int tabCriticalValue[] = new int[50];                         /**< List of values which will contain the String of EditText in the recyclerView*/

    public static LinearLayoutManager llmParam;                                 /**< Linear Layout Manager */


    /**
     * @fn handleParam
     * @brief Handler told by the service's thread to send new COs and new sensors
     */
    public Handler handlerParam = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Log.e("Handler : ", "Arg1: " + msg.arg1 + "\targ2: " + msg.arg2 + "\tWhat: " + msg.what);
            try {
                includeParamInView(msg.arg1, msg.what, msg.arg2);        /***/
                Log.d("HandlerParam :", "View Param initialized");
            } catch (Exception e) {
                Log.d("HandlerParam : ", "Probleme View Param Initializer !!!");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameter);

        // Implementations
        service = new MyService();

        // References
        spinner = (Spinner)findViewById(R.id.spinner);
        edtT1 = (EditText)findViewById(R.id.et1);
        edtT2 = (EditText)findViewById(R.id.et2);
        recylistParam = (RecyclerView) findViewById(R.id.recylistSettings);
        b1 = (Button)findViewById(R.id.bpBackFromParam);

        // for dropdown list
        adapter = ArrayAdapter.createFromResource(this, R.array.time_scale, R.layout.myspinnerlayout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Button Back listener
        b1.setOnClickListener(this);

        // Recycler View
        recylistParam.setAdapter(ipa);
        recylistParam.setHasFixedSize(true);
        llmParam = new LinearLayoutManager(this);
        llmParam.setOrientation(LinearLayoutManager.VERTICAL);
        recylistParam.setLayoutManager(llmParam);

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
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        TextView text = (TextView)view;
        Toast.makeText(this, "You have chosen"+text.getText(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView){

    }


    /**
     * @fn readingParameters
     * @brief Creation of a thread to save and send to the Service the parameters entered by the user
     */
    Thread readingParameters = new Thread(new Runnable() {
        public void run() {
            try {
                //do {
                    Thread.sleep(1000);
                    Log.e("readingThread","     On est dans le readingThread");
                    saveParameters();
                    sendParamToService();
                //}while(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


    /**
     * @fn sendParamToService
     * @brief dispatch of the values treashold to the service
     */
    private void sendParamToService(){
        int i=0;
        try {
            Message messageTreashold;

            /** EditText not used for the moment */
            //messageThreasold = service.handlerService.obtainMessage(-1, 1, nb1);
            //service.handlerService.sendMessage(messageThreasold);
            //Thread.sleep(500);

            messageTreashold = service.handlerService.obtainMessage(-1, 0, nb2);
            service.handlerService.sendMessage(messageTreashold);

            for (i = 0; i < listItemParam.size(); i++) {
                Thread.sleep(500);
                messageTreashold = service.handlerService.obtainMessage(listItemParam.get(i).getIdCo(), listItemParam.get(i).getIdSensor(), tabCriticalValue[i]);
                service.handlerService.sendMessage(messageTreashold);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

        Log.d("ParamActivity:         ", "         editText1: " + nb1 +"           EditText2: "+ nb2);

        if(v.getId() == R.id.bpBackFromParam){
            readingParameters.start();
            Intent intent = new Intent(this, HomeActivity.class);
            this.startActivity(intent);
        }
    }


    /**
     * @fn saveParameters
     * @brief Reading of the values entered by the user on Parameters view
     *        This values are integrated in the integers nb1, nb2 and in the list of recyclerView EditText values
     */
    private void saveParameters(){
        int i=0;
        nb1 = Integer.parseInt(edtT1.getText().toString());
        nb2 = Integer.parseInt(edtT2.getText().toString())*getTime();

        for(i=0;i<listItemParam.size();i++){
                tabCriticalValue[i] = Integer.parseInt(ItemParamAdapter.listEdtTxt.get(i).getText().toString());
        }
    }


    /**
     * @fn includeParamInView
     * @brief Initialization of the recyclerView with the sensors presents (their name, their Co, their unit and an EditText to enter a treashold value
     * @param val Value of the last treasholds to init the EditText with it.
     * @param OCposition Position of the Co concerned (idCo).
     * @param valPosition Position of the sensor Concerned in the Co.
     */
    public void includeParamInView(int val, int OCposition, int valPosition){
        if(MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).param == true) {
            listItemParam.add(new ItemParam("Critical " + MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).getName(),
                    MyService.listConnectedObject.get(OCposition).getName(), MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).getUnit(), OCposition, valPosition));
            ipa.notifyDataSetChanged();
            MyService.listConnectedObject.get(OCposition).getSensorList().get(valPosition).param = false;
        }
        Log.d("IncludeParam :", "taille list param:" + listItemParam.size());
        Log.d("ParamActivity:         ", "         editText1: " + nb1 +"           EditText2: "+ nb2);
    }


    /**
     * @fn getTime
     * @brief Reading of the time scale of the second EditText out of recyclerView
     * @return the time scale in seconds
     */
	public int getTime(){
        switch(spinner.getSelectedItem().toString()){
            case "Seconds":
                return 1;
            case "Minutes":
                return 60;
            case "Hours":
                return 3600;
            case "Days":
                return 86400;
            default:
                return 1;
        }
    }
}
