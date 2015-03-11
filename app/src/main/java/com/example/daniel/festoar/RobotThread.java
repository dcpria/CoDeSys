package com.example.daniel.festoar;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


public class RobotThread extends AsyncTask<Object, Void, RobotData> { //<args, state(?), retval>

    /* CONSTANTS */
    public static final int maxTimeout = 1000; //milliseconds
    public static final int maxBytesRead = 10240+1; // 10kB
    private static final String tag = "DEBUG_ONLY";

    /* VARIABLES */
    MainActivity mParentActivity;

    protected RobotData doInBackground(Object... params) { //The value returned by doInBackground() is sent to onPostExecute()

        RobotData robData = new RobotData(); //declare as class private variable ?
        mParentActivity = (MainActivity) params[0];
        InputStream robInStream;
        byte[] data = new byte[maxBytesRead];
        int bRead = -1;

        /* Socket*/
        InetSocketAddress hostSock = (InetSocketAddress) params[1];
        Socket cliSock = new Socket(); //create socket
        if(!cliSock.isConnected()) {
            try {
                cliSock.connect(hostSock, maxTimeout); //connect to server
                robData.robOnline = true;
                robInStream = cliSock.getInputStream();
                bRead = robInStream.read(data); //read data
                Log.i(tag, "bytes read: "+bRead+"\n");
                if(bRead > 0){
                    /* Parse RobotData from XML String */
                    //try{
                        robData = (RobotData) new XMLParser().XMLStringToRobotData(new String(data, "ISO-8859-1")); //use .clone() ?
                        //Log.i(tag, robData.toString());
                    /*}catch(CloneNotSupportedException e){
                        //System.out.println("Exception thrown  :" + e);
                        Log.i(tag, e.toString());
                        robData.robOnline = false;
                    }*/
                    //robData.RobotName = new String(data, "ISO-8859-1").trim(); //working charsets: US-ASCII, ISO-8859-1, UTF-8
                    //robData.robOnline = true; //connection successful
                    //Log.i(tag, Integer.toString(robData.RobotName.length()));
                    //Log.i(tag, robData.RobotName);
                    /*Log.i(tag, new String(data, "US-ASCII"));
                    Log.i(tag, new String(data, "ISO-8859-1"));
                    Log.i(tag, new String(data, "UTF-8"));
                    Log.i(tag, new String(data, "UTF-16BE"));
                    Log.i(tag, new String(data, "UTF-16LE"));
                    Log.i(tag, new String(data, "UTF-16"));*/
                    /*Log.i(tag, "-----------------------------");
                    Log.i(tag, Integer.toString(bRead)+" bytes read: ");
                    String recvInts = new String();
                    Log.i(tag, new String(data, "ISO-8859-1").trim());*/
                    /*for(int x = 0; x < maxBytesRead; x++) {
                        recvInts+=Integer.toString(data[x]);
                    }
                    Log.i(tag, recvInts);*/
                    /*
                    Log.i(tag, robData.RobotName.getBytes("US-ASCII").toString());
                    Log.i(tag, robData.RobotName.getBytes("ISO-8859-1").toString());
                    Log.i(tag, robData.RobotName.getBytes("UTF-8").toString());
                    Log.i(tag, robData.RobotName.getBytes("UTF-16BE").toString());
                    Log.i(tag, robData.RobotName.getBytes("UTF-16LE").toString());
                    Log.i(tag, robData.RobotName.getBytes("UTF-16").toString());*/

                }
                cliSock.close();
            } catch (IOException e) {
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
                robData.robOnline = false;
            }
        }
        return robData;
    }

    protected void onPostExecute(RobotData newData) {
        mParentActivity.updateRobotData(newData);
        mParentActivity.updateDispData();
    }

}
