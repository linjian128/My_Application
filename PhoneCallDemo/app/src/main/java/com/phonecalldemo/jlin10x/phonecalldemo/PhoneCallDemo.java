package com.phonecalldemo.jlin10x.phonecalldemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneCallDemo extends Activity {
    private Button bt;
    private EditText et;
    TelephonyManager telephonyManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call_demo);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener listener = new PhoneStateListener()
        {
            public void onCallStateChanged ( int state, String incomingNumber)
            {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:   //来电
                        Log.e("hg", "电话状态……RINGING");
                        Log.i("TelephoneState555555", "RINGING");

                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:   //接通电话
                        Log.e("hg", "电话状态……OFFHOOK");

                        break;

                    case TelephonyManager.CALL_STATE_IDLE:  //挂掉电话
                        Log.e("hg", "电话状态……IDLE");

                        break;
                    default:
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        telephonyManager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);



//取得资源
        bt = (Button) findViewById(R.id.bt1);
        et = (EditText) findViewById(R.id.et1);

        /*
        boolean got = false;

        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        //String time=t.year+"年 "+(t.month+1)+"月 "+t.monthDay+"日 "+t.hour+"h "+t.minute+"m "+t.second;

        while(true) {
            t.setToNow();
            if (t.minute == 44 && t.second == 30) {
                String time=t.year+"年 "+(t.month+1)+"月 "+t.monthDay+"日 "+t.hour+"h "+t.minute+"m "+t.second;
                Log.e("msg", time);

                Intent phoneIntent = new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + "18962284493"));
                startActivity(phoneIntent);
            }

            if (got)
                break;
        }*/


//增加事件响应
        bt.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime4();
//取得输入的电话号码串
                String inputStr = et.getText().toString();
//如果输入不为空创建打电话的Intent
                if (inputStr.trim().length() != 0) {
                    Intent phoneIntent = new Intent("android.intent.action.CALL",
                            Uri.parse("tel:" + inputStr));
//启动
                    startActivity(phoneIntent);
                }
//否则Toast提示一下
                else {
                    Toast.makeText(PhoneCallDemo.this, "不能输入为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getTime4(){
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        String time=t.year+"年 "+(t.month+1)+"月 "+t.monthDay+"日 "+t.hour+"h "+t.minute+"m "+t.second;
        Log.e("msg", time);
    }


}

