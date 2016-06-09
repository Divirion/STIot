package com.example.prose.stiot;

//import com.example.prose.stiot.Network.Protocol;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lucas-pc on 18/05/16.
 */
public class ProtocolUnitTest {

    private int i = 0;
    ///private Protocol protocol = new Protocol();
    private int nb=3;

    @Before
    public void protocol_test_init() throws Exception {

    }

    @Test
    public void appendCoInList_test() throws Exception {
        for(i=0; i<nb; i++){
        //    protocol.appendCoInList(""+i,"coName"+i, "censorName"+i, ""+i, "unit");
        }
        
        assertEquals(nb,MyService.listConnectedObject.size());
        assertEquals(MyService.listConnectedObject.get(1).getName(), "coName1");
        assertEquals(MyService.listConnectedObject.get(1).getSensorList().size(), 1);
        assertEquals(MyService.listConnectedObject.get(1).getId(), 1);
    }
}

