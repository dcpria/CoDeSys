package com.example.daniel.festoar;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.File;


public class XMLParser {
    private String tag = "DEBUG_ONLY";

    /* Methods */

    public RobotData XMLStringToRobotData(String xml) { //XML String to RobotData
        Serializer serializer = new Persister();
        RobotData robData = new RobotData();
        Log.i(tag, xml.replace("\n","").replace("\r", "").replace("\t", "").replace("\b", "").replace("\f", "").trim());
        try{
            robData = serializer.read(RobotData.class, xml.replace("\n","").replace("\r", "").replace("\t", "").replace("\b", "").replace("\f", "").trim()); //remove potential harming characters (\n!)
        }catch(Exception e){
            //System.out.println("Exception thrown  :" + e);
            //Log.i(tag, e.toString());
        }

        return robData;
    }

    /* Debug */

    public RobotData debugXMLStringToRobotData() { //debug
        Serializer serializer = new Persister();
        XMLString XMLString = new XMLString();
        RobotData robData = new RobotData();

        try{
            String xml = new String();
            xml = XMLString.createTestXMLString2(); //assign received xml string
            robData = serializer.read(RobotData.class, xml.replace("\n","").replace("\r", "").replace("\t", "").replace("\b", "").replace("\f", "")); //remove potential harming characters (\n!)
        }catch(Exception e){
            //System.out.println("Exception thrown  :" + e);
            //Log.i(tag, e.toString());
        }

        return robData;
    }

}
