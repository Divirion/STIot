/**
 * *
 *  * @file      FctUsedFromSgaToSgi.java
 *  * @author    Arnaud B
 *  * @version   1.0
 *  * @date      19 April 2016
 *  * @brief
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

package com.example.prose.stiot.Network.Proxy;


/** @enum mapper::FctUsedFromSgaToSgi
 *  @author Arnaud B
 *  @brief is a typed enum class representing the function to send to Sgi is the first
 *  word in a message.
 */
public enum FctUsedFromSgaToSgi {
    NOTIFY_THRESHOLDS,           /**<  send to SGI a threshold was changed*/
    SET_TIMING_SCAN,             /**<  send to SGI A */
    ASK_CO_LIST,                 /**<  ask SGI to send the list Co*/
    ASK_ALL_DATA_OF_ONE_CO,      /**<  ask SGI to send all data of one Co specified period of time */
    ASK_LAST_MEASURES_OF_ALL_COS,/**<  ask SGI to send all last value of all Cos */
}
