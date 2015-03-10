package com.example.daniel.festoar;

import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import java.io.File;


public class XMLParser {
    private String tag = "DEBUG_ONLY";

    public RobotData XMLStringToRobotData() { //TODO: input xml string
        Serializer serializer = new Persister();
        XMLString XMLString = new XMLString(); //TODO: replace with real data
        RobotData robData = new RobotData();

        try{
            robData = serializer.read(RobotData.class, XMLString.createTestXMLString());
        }catch(Exception e){
            //System.out.println("Exception thrown  :" + e);
            Log.i(tag, e.toString());
            //Log.i(tag, "failed at serialzier.read");
        }

        return robData;
    }

}
