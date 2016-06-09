/**
 * *
 *  * @file      ItemParam.java
 *  * @author    Lucas B
 *  * @version   1.0
 *  * @date      19 May 2016
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

package com.example.prose.stiot.ItemParameterActivity;


/**
 * Created by Lucas B on 19/05/16.
 */
public class ItemParam {

    private String sensorName;          /**< Name of the sensor concerned */
    private String coName;              /**< Name of the Co of the sensor concerned */
    private String value;               /**< Threshold value of sensor */
    private String unit;                /**< Unit of sensor value */
    private int idCo;                   /**< Id of the Co of the sensor concerned */
    private int idSensor;               /**< Id of the sensor concerned */


    /**
     * @fn ItemParam
     * @brief Constructor of ItemParam
     * @param coName Name of the Co concerned
     * @param idCo Id of the Co concerned
     * @param idSensor Id of the sensor concerned
     * @param sensorName Name of the sensor concerned
     * @param unit Unit of the sensor concerned
     */
    public ItemParam(String sensorName,String coName, String unit, int idCo, int idSensor) {
        this.sensorName = sensorName;
        this.coName = coName;
        this.unit = unit;
        this.idCo = idCo;
        this.idSensor = idSensor;
    }

    public String getCoName() {
        return coName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public int getIdCo() {
        return idCo;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setValue(String value){
        this.value = value;
    }

}

