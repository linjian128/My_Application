package com.example.jerolin.sinchcall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Sinch_Phone_Call extends AppCompatActivity {
    private Button bt;
    private EditText et;
    TelephonyManager telephonyManager;

    private int dial_cnt;
    private int ans_cnt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinch__phone__call);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener listener = new PhoneStateListener()
        {
            public void onCallStateChanged ( int state, String incomingNumber)
            {
                ans_cnt++;

                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:   //来电

                        //writeFileToSD("Test String\n");
                        //Log.e("Ring", "ANS =" + ans_cnt);
                        //getTime("[Ring]", "Coming", "Local");
                        Log.e("[Ring]","C "+ ans_cnt + " == " + new String(getTime()));
                        writeFileToSD("[Ring] "+" C"+ ans_cnt + " == " + new String(getTime()) + "\n");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:   //接通电话
                        //getTime("[OFFHOOK]", "Null", "Null");
                        Log.e("[OFFHOOK]","C "+ ans_cnt + " == " + new String(getTime()));
                        writeFileToSD("[OFFHOOK] " + " C" + ans_cnt + " == " + new String(getTime()) + "\n");
                        // Log.e("hg", "电话状态……OFFHOOK");
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:  //挂掉电话
                        //getTime("[IDLE]", "Null", "Null");
                        Log.e("[IDLE]","C "+ ans_cnt + " == " + new String(getTime()));
                        writeFileToSD("[IDLE] " + " C" + ans_cnt + " == " + new String(getTime()) + "\n");
                        //Log.e("hg", "电话状态……IDLE");
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
//增加事件响应

        bt.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                dial_cnt++;
                String inputStr = et.getText().toString();
//取得输入的电话号码串

                //getTime("[Start]", "Local", inputStr);

                Log.e("[Start]", "S" + dial_cnt + " == From Local to " + inputStr + " " + new String(getTime()));
                writeFileToSD("[Start] " + " S" + dial_cnt + " == From Local to " + inputStr + " " + new String(getTime()) + "\n");
//如果输入不为空创建打电话的Intent
                if (inputStr.trim().length() != 0) {
                    Intent phoneIntent = new Intent("android.intent.action.CALL",
                            Uri.parse("tel:" + inputStr));
//启动
                    startActivity(phoneIntent);
                }
//否则Toast提示一下
                else {
                    Toast.makeText(Sinch_Phone_Call.this, "不能输入为空", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String getTime(){
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间。
        //String time=t.year+"年 "+(t.month+1)+"月 "+t.monthDay+"日 "+t.hour+"h "+t.minute+"m "+t.second;
        String time=t.year+"-"+(t.month+1)+"-"+t.monthDay+" "+t.hour+":"+t.minute+":"+t.second;
        return time;
    }

    private void writeFileToSD(String content) {
        String sdStatus = Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            String pathName="/sdcard/AASinch/";
            String fileName="file.txt";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if( !path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }
            if( !file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            //String content = "this is a test string writing to file.\n";
            /*FileOutputStream stream = new FileOutputStream(file);
            String s = "this is a test string writing to file.";
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();*/

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathName + fileName, true)));
                out.write(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(out != null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch(Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }
}
