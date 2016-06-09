/**
 * *
 *  * @file      ConnectionActivity.java
 *  * @author    Arnaud B
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by billy on 01/05/16.
 */
public class ConnectionActivity extends AppCompatActivity {
    private EditText ipAdresse;
    private Button bpBackHome;
    private Button bpConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);
        bpBackHome=(Button)   findViewById(R.id.bpBackHome);
        bpConnect=(Button)  findViewById(R.id.bpConnect);
        ipAdresse= (EditText) findViewById(R.id.editIpAdresse);
        final Intent intent = new Intent();
        intent.setAction("myBroadcast");



        bpConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if (ipIsValid(ipAdresse.getText().toString())){
                   Log.e("Connection ip adresse","true IP Adresse");
                   ipAdresse.getText().toString();
                   sendBroadcast(intent);
                }else{
                    Log.e("Connection ip adresse","false IP Adresse");
                }
            }
        });

    }


    /**
     * @fn ipIsValid is correct
     * @brief This method verify that the pattern ip write by user
     * is correct
     * this \b method \b don't \b verify \b the \b connection
     * @param ipToCheck  the ip adresse we want verify
     * @return true if the correct pattern ip adresse else false
     *
     * */
    private Boolean ipIsValid(String ipToCheck) {
        Log.e("ipIsValid","->"+ipToCheck);
        Pattern ipPattern = Pattern
                .compile("^((25[0-5])|(2[0-4][0-9])|(1[0-9]{2})|([1-9][0-9])|([1-9]))\\.(((25[0-5])|(2[0-4][0-9])|(1[0-9]{2})|([1-9][0-9])|([0-9]))\\.){2}((25[0-5])|(2[0-4][0-9])|(1[0-9]{2})|([1-9][0-9])|([0-9]))$");
        Matcher m = ipPattern.matcher(ipToCheck);
        return m.matches();
    }

    public void buttonConnexionOnClick(View v){
        finish();
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

}

