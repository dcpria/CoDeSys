package com.example.daniel.festoar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.content.Intent;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class MainActivity extends ActionBarActivity {

    /* CONSTANTS */
    public static final String Host = "10.50.10.100";
    public int Port = 4510;

    /* VARIABLES */
    private TextView tvConState;
    private TextView tvRobData;
    private TextView tvAddress;
    private EditText etPort;
    private String tag = "DEBUG_ONLY";
    MainActivity arg = this;
    RobotData robData = new RobotData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* ########## Text Fields ########## */
        tvConState=(TextView)findViewById(R.id.tvConState);
        tvRobData=(TextView)findViewById(R.id.tvRobData);
        tvAddress=(TextView)findViewById(R.id.tvAddress);

        tvAddress.setText(Host+":"+Port);
        /* ########## EditText ########## */
        etPort =(EditText)findViewById(R.id.etPort);

         /* ########## Buttons ########## */

        /* Connect */
        Button ButtConnect = (Button) findViewById(R.id.buttConnect); // Connect to Robot and pull Data
        ButtConnect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RobotThread robotTask = new RobotThread();
                Port = Integer.parseInt(etPort.getText().toString());
                tvAddress.setText(Host+":"+Port);
                robotTask.execute(arg, new InetSocketAddress(Host, Port));
            }

        });

        /* Test Function */
        Button ButtTestFunction = (Button) findViewById(R.id.buttTestFunction); // Button to test functions
        ButtTestFunction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RobotData RobotDataTest = new XMLParser().debugXMLStringToRobotData(); //use for dummy RobotData
                //tvRobData.setText(RobotDataTest.RobotName + " " + RobotDataTest.RobotActive);
                //tvRobData.setText(RobotDataTest.toString());
                try{
                    robData = (RobotData) RobotDataTest.clone(); //copy debug data to actual robotdata variable
                    tvRobData.setText(robData.toString());//TODO: debug only -> show actual visualization of data
                }catch(CloneNotSupportedException e){
                    //System.out.println("Exception thrown  :" + e);
                    Log.i(tag, e.toString());
                    robData.robOnline = false;
                }
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Methods */

    public void setConState(boolean state){
        if(state){
            tvConState.setText("online");
        }else{
            tvConState.setText("offline");
        }
    }

    public void updateRobotData(RobotData newData){
         //this.robData.copyFrom(newData);
        try {
            this.robData = (RobotData) newData.clone();
        }catch(CloneNotSupportedException e){
            //System.out.println("Exception thrown  :" + e);
            Log.i(tag, e.toString());
            robData.robOnline = false;
        }
    }

    public void updateDispData(){ //nur aufrufen wenn RobotThread.doInBackground() fertig ist
        this.setConState(robData.robOnline);
        /* Debug */
        tvRobData.setText(robData.toString());//TODO: debug only -> show actual visualization of data
    }

}
