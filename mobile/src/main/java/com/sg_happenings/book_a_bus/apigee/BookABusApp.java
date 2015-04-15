package com.sg_happenings.book_a_bus.apigee;

import android.app.Application;

import com.apigee.sdk.ApigeeClient;
/**
 * Created by jess on 15-Apr-15.
 */
public class BookABusApp extends Application
{

    private ApigeeClient apigeeClient;

    public BookABusApp()
    {
        this.apigeeClient = null;
    }

    public ApigeeClient getApigeeClient()
    {
        return this.apigeeClient;
    }

    public void setApigeeClient(ApigeeClient apigeeClient)
    {
        this.apigeeClient = apigeeClient;
    }
}
