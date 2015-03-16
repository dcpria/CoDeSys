package com.example.daniel.festoar;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;


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
        byte[] data = new byte[maxBytesRead];

        /* Read TCP Data */
        //data = readTCPSocketByteArray(params);
        data = readTCPSocketByByte(params);

        /* Parse XML String to RobotData */
        try {
            robData = (RobotData) new XMLParser().XMLStringToRobotData(new String(data, "ISO-8859-1")); //use .clone() ?
        } catch (UnsupportedEncodingException e){
            //System.out.println("Exception thrown  :" + e);
            Log.i(tag, e.toString());
        }

        return robData;
    }

    protected void onPostExecute(RobotData newData) {
        mParentActivity.updateRobotData(newData);
        mParentActivity.updateDispData();
    }

    public byte[] readTCPSocketByteArray(Object... params){

        InputStream robInStream;
        byte[] data = new byte[maxBytesRead];
        int bRead = -1;

        /* Socket*/
        InetSocketAddress hostSock = (InetSocketAddress) params[1];
        Socket cliSock = new Socket(); //create socket
        if(!cliSock.isConnected()) {
            try {
                cliSock.connect(hostSock, maxTimeout); //connect to server
                robInStream = cliSock.getInputStream();
                /* Read Socket */
                bRead = robInStream.read(data); //read data
                //debug
                Log.i(tag, "bytes read: "+bRead+"\n");
                debug_printAllCharSets(data, false);
                cliSock.close();
            } catch (IOException e) {
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
                return null;
            } catch (NullPointerException e){
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
                return null;
            }
        }
        if(bRead <= 0) return null;
        return data;
    }

    public byte[] readTCPSocketByByte(Object... params){

        InputStream robInStream;
        byte[] data = new byte[maxBytesRead];
        int i = 0;
        int bRead = -1;

        /* Socket*/
        InetSocketAddress hostSock = (InetSocketAddress) params[1];
        Socket cliSock = new Socket(); //create socket
        if(!cliSock.isConnected()) {
            try {
                cliSock.connect(hostSock, maxTimeout); //connect to server
                robInStream = cliSock.getInputStream();
                /* Read Socket */
                do{
                    bRead = robInStream.read(); //read data
                    data[i] = (byte) bRead;
                    i++;
                }while(bRead != -1);
                //debug
                Log.i(tag, "bytes read: "+bRead+"\n");
                debug_printAllCharSets(data, false);
                cliSock.close();
            } catch (IOException e) {
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
                return null;
            } catch (NullPointerException e){
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
                return null;
            }
        }
        if(bRead <= 0) return null;
        return data;
    }

    /* DEBUG */

    public void debug_printAllCharSets(byte[] data, boolean trimmed){ //working charsets: US-ASCII, ISO-8859-1, UTF-8
        if(trimmed){
            try{
                Log.i(tag, "Length: "+data.length+"\n");
                Log.i(tag, new String(data, "US-ASCII").trim());
                Log.i(tag, new String(data, "ISO-8859-1").trim());
                Log.i(tag, new String(data, "UTF-8").trim());
                Log.i(tag, new String(data, "UTF-16BE").trim());
                Log.i(tag, new String(data, "UTF-16LE").trim());
                Log.i(tag, new String(data, "UTF-16").trim());
            }catch(UnsupportedEncodingException e) {
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
            }
        }else{
            try{
                Log.i(tag, "Length: "+data.length+"\n");
                Log.i(tag, new String(data, "US-ASCII"));
                Log.i(tag, new String(data, "ISO-8859-1"));
                Log.i(tag, new String(data, "UTF-8"));
                Log.i(tag, new String(data, "UTF-16BE"));
                Log.i(tag, new String(data, "UTF-16LE"));
                Log.i(tag, new String(data, "UTF-16"));
            }catch(UnsupportedEncodingException e) {
                //System.out.println("Exception thrown  :" + e);
                Log.i(tag, e.toString());
            }
        }
    }

}
