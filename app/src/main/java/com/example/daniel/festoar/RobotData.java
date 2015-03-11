package com.example.daniel.festoar;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Transient;

import java.util.Arrays;

import Classes.TAxisDyn;
import Classes.TAxisPos;
import Classes.TCartDyn;
import Classes.TCartPos;
import Classes.TPathDyn;
import Classes.TTool;

@Root(strict=false) //false = not all fields from XML need to be assigned to RobotData
@Default(DefaultType.FIELD) //match all variables by field (?)
public class RobotData implements Cloneable{

    /* CONSTANTS */

    /* VARIABLES */
    @Transient //prevents XMLParser from using this variable
    public boolean robOnline = false;

    /* RcIfRobotData */
    //Ref: http://de.wikipedia.org/wiki/Java-Syntax#Primitive_Datentypen


    public String RobotName = "unknown_Robot";
    public boolean RobotActive = false;
    public boolean RobotReferenced = false;
    public boolean RobotError = false;
    public short RobotOverride = 0;
    public String RefSysName = "unknown_RefSys";
    public TTool Tool = new TTool();
    public String ToolName = "unknown_Tool";
    public int ToolNumber = 0;
    public int AxisCountMain = 0;
    public int AxisCountWrist = 0;
    public int AxisCountAux = 0;
    public byte[] AxisSimulated = new byte[2];
    public byte[] AxisReferenced = new byte[2];
    public byte[] AxisLSN = new byte[2];
    public byte[] AxisLSP = new byte[2];
    public TAxisPos AxisPos = new TAxisPos();
    public TCartPos CartPosWorld = new TCartPos();
    public TCartPos CartPosRefSys = new TCartPos();
    public TAxisDyn AxisDyn = new TAxisDyn();
    public TPathDyn PathDyn = new TPathDyn();
    public TCartDyn CartDyn = new TCartDyn();
    public boolean Error = false;
    public int ErrorId = 0; //Achtung!!! TRCIFERRORID oder TRCLFERRORID -> I/L <> I/l; war ursprünglich typ TRcIfErrorID

    /* METHODS */

    public RobotData() {
        Arrays.fill(this.AxisSimulated, (byte) 0);
        Arrays.fill(this.AxisReferenced, (byte) 0);
        Arrays.fill(this.AxisLSN, (byte) 0);
        Arrays.fill(this.AxisLSP, (byte) 0);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString(){
        String DataString = new String();
        DataString =
                "RobotName: "+RobotName+"\n"+
                "RobotActive: "+RobotActive+"\n"+
                "RobotReferenced: "+RobotReferenced+"\n"+
                "RobotError: "+RobotError+"\n"+
                "RobotOverride: "+RobotOverride+"\n"+
                "RefSysName: "+RefSysName+"\n"+
                "Tool: "+Tool.x+", "+Tool.y+", "+Tool.z+", "+Tool.a+", "+Tool.b+", "+Tool.c+"\n"+
                "ToolName: "+ToolName+"\n"+
                "ToolNumber: "+ToolNumber+"\n"+
                "AxisCountMain: "+AxisCountMain+"\n"+
                "AxisCountWrist: "+AxisCountWrist+"\n"+
                "AxisCountAux: "+AxisCountAux+"\n"+
                "AxisSimulated: "+String.format("%8s", Integer.toBinaryString(AxisSimulated[0] & 0xFF)).replace(' ', '0')+"\n"+
                "AxisReferenced: "+String.format("%8s", Integer.toBinaryString(AxisReferenced[0] & 0xFF)).replace(' ', '0')+"\n"+
                "AxisLSN: "+String.format("%8s", Integer.toBinaryString(AxisLSN[0] & 0xFF)).replace(' ', '0')+"\n"+
                "AxisLSP: "+String.format("%8s", Integer.toBinaryString(AxisLSP[0] & 0xFF)).replace(' ', '0')+"\n"+
                "AxisPos: "+AxisPos.a1+", "+AxisPos.a2+", "+AxisPos.a3+", "+AxisPos.a4+", "+AxisPos.a5+", "+AxisPos.a6+", "+AxisPos.aux1+", "+AxisPos.aux2+", "+AxisPos.aux3+"\n"+
                "CartPosWorld: "+CartPosWorld.x+", "+CartPosWorld.y+", "+CartPosWorld.z+", "+CartPosWorld.a+", "+CartPosWorld.b+", "+CartPosWorld.c+", "+CartPosWorld.aux1+", "+CartPosWorld.aux2+", "+CartPosWorld.aux3+"\n"+
                "CartPosRefSys: "+CartPosRefSys.x+", "+CartPosRefSys.y+", "+CartPosRefSys.z+", "+CartPosRefSys.a+", "+CartPosRefSys.b+", "+CartPosRefSys.c+", "+CartPosRefSys.aux1+", "+CartPosRefSys.aux2+", "+CartPosRefSys.aux3+"\n"+
                "AxisDyn: "
                        +AxisDyn.a1.vel+", "+AxisDyn.a1.acc+", "+AxisDyn.a1.jerk+", "+AxisDyn.a2.vel+", "+AxisDyn.a2.acc+", "+AxisDyn.a2.jerk+", "
                        +AxisDyn.a3.vel+", "+AxisDyn.a3.acc+", "+AxisDyn.a3.jerk+", "+AxisDyn.a4.vel+", "+AxisDyn.a4.acc+", "+AxisDyn.a4.jerk+", "
                        +AxisDyn.a5.vel+", "+AxisDyn.a5.acc+", "+AxisDyn.a5.jerk+", "+AxisDyn.a6.vel+", "+AxisDyn.a6.acc+", "+AxisDyn.a6.jerk+", "
                        +AxisDyn.aux1.vel+", "+AxisDyn.aux1.acc+", "+AxisDyn.aux1.jerk+", "
                        +AxisDyn.aux2.vel+", "+AxisDyn.aux2.acc+", "+AxisDyn.aux2.jerk+", "
                        +AxisDyn.aux3.vel+", "+AxisDyn.aux3.acc+", "+AxisDyn.aux3.jerk+"\n"+
                "PathDyn: "+PathDyn.Path.vel+", "+PathDyn.Path.acc+", "+PathDyn.Path.jerk+", "+PathDyn.Ori.vel+", "+PathDyn.Ori.acc+", "+PathDyn.Ori.jerk+"\n"+
                "CartDyn: "
                        +CartDyn.x.vel+", "+CartDyn.x.acc+", "+CartDyn.x.jerk+", "
                        +CartDyn.y.vel+", "+CartDyn.y.acc+", "+CartDyn.y.jerk+", "
                        +CartDyn.z.vel+", "+CartDyn.z.acc+", "+CartDyn.z.jerk+", "
                        +CartDyn.aux1.vel+", "+CartDyn.aux1.acc+", "+CartDyn.aux1.jerk+", "
                        +CartDyn.aux2.vel+", "+CartDyn.aux2.acc+", "+CartDyn.aux2.jerk+", "
                        +CartDyn.aux3.vel+", "+CartDyn.aux3.acc+", "+CartDyn.aux3.jerk+", "+"\n"+
                "Error: "+Error+"\n"+
                "ErrorId: "+ErrorId+"\n";
        return DataString;
    }

    /* DEBUG ONLY */
}
