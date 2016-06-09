package com.example.prose.stiot.Model;

import java.util.ArrayList;

/**
 * @file      Sensor.java
 * @author    Arnaud B
 * @version   1.0
 * @date      26 April 2016
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
public class Sensor {
    private String name;                    /**<Name Sensor use only display*/
    private String unit;                    /**<Unit measure use only display*/
    public ArrayList<Data>  listData ;     /**<listData content all data */
    private Data min;                       /**<min the min value in listData send by Sgi*/
    private Data max;                       /**<max the max value in listData send by Sgi*/
    private String thresholdState;          /**<TODO*/
    private float thresholdValue;           /**<the critical value the user can change this value */
    public static  Boolean param;



    /**
     * @fn Sensor
     * @brief Constructor of sensor One sensor must have a name,
     * a unit and a threashold value
     * @param name sensor name, it's the name Sensor display on the screen
     * @param unit unit,it's the unit display on the screen
     * @param thresholdValue the threshold value display on the screen, the user can change this value
     * */
    public Sensor(String name, String unit, float thresholdValue) {
        this.name = name;
        this.unit = unit;
        this.listData = new ArrayList<>();
        listData.add(new Data("End of list",0));     // Default value
        this.thresholdState="0";
        this.thresholdValue=thresholdValue;
        this.param=true;
    }
    public Sensor(String name, String unit, float thresholdValue, ArrayList<Data> pData) {
        this.name = name;
        this.unit = unit;
        this.listData = pData;
        //data.add(new Data("25/2/2015",0));     /** Default value  */
        this.thresholdState = "0";
        this.thresholdValue = thresholdValue;
        this.param=true;
    }
    /**
     * @fn  float getLastData
     * @brief    Return the most recent data
     * @return     Return the last Value of the ArrayList<Data> data
     */
    public  float getLastData(){
        return this.listData.get(0).getValue();
    }

    /**
     * @fn public void insertNewData()
     * @brief    Insert a New Data with the date in the list Data at index 0
     * @param    date    date of data
     * @param    value   value of data
     */
    public void insertNewData(String date, int  value){
        this.listData.add(0,new Data(date,value));
    }

    //Getter and Setter Attribute

    public ArrayList<Data> getData() {
        return listData;
    }

    public void setData(ArrayList<Data> data) {
        data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        unit = unit;
    }

    public Data getMin() {
        return min;
    }

    public void setMin(Data min) {
        this.min = min;
    }

    public Data getMax() {
        return max;
    }

    public void setMax(Data max) {
        this.max = max;
    }

    public String getThresholdState() {
        return thresholdState;
    }

    public void setThresholdState(String thresholdState) {
        this.thresholdState = thresholdState;
    }
    public float getThresholdValue() {
        return thresholdValue;
    }
}
