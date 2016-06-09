package com.example.prose.stiot;

import android.content.Context;

import com.example.prose.stiot.ItemHomeActivity.ItemOc;
import com.example.prose.stiot.ItemHomeActivity.ItemOcAdapterDetail;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by divi on 05/05/16.
 */
public class ItemOcTest {
    private ItemOc itemOc;
    private String titre = "OcTest";
    private String titreButon = "ButonTest";
    private ItemOcAdapterDetail adapterDetail;
    private Context context;

    @Before
    public void before() {

        itemOc=new ItemOc(titre, titreButon, adapterDetail, context);

    }
    @Test
    public void testGetTitle() throws Exception {

        assertThat(itemOc.getTitle(), is(titre));


    }

    @Test
    public void testGetTitleButon() throws Exception {

        assertThat(itemOc.getTitleButton(), is(titreButon));

    }

    @Test
    public void testGetAdapter() throws Exception {
        assertThat(itemOc.getAdapter(), is(adapterDetail));
    }

    @Test
    public void testGetC() throws Exception {
        assertThat(itemOc.getC(), is(context));

    }
}