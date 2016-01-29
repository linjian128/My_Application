package com.phonecalldemo.jlin10x.phonecalldemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jlin10x on 1/28/16.
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, PhoneService.class);
        context.startService(service);   //启动服务
    }

}
