package org.openjfx;

import java.math.RoundingMode;

public class ResultData {
    public String j;
    public String Ptz;
    public String t;
    public String K;
    public String DA;
    public String DB;
    public String DC;
    public String DD;

    public String Y;
    public String Pt;
    public String Pe;
    public String Zg;
    public String Zw;
    public String PtA;
    public String PtB;
    public String PtC;
    public String PtD;
    public String PtS;
    public String E;
    public String PeA;
    public String PeB;
    public String PeC;
    public String PeD;
    public String ZgA;
    public String ZwB;
    public String ZwC;
    public String ZwD;
    public String RA;
    public String RB;
    public String RC;
    public String RD;

    public ResultData(String j, String ptz, String t, String k, String DA, String DB, String DC, String DD) {
        this.j = j;
        Ptz = ptz;
        this.t = t;
        K = k;
        this.DA = DA;
        this.DB = DB;
        this.DC = DC;
        this.DD = DD;

        Y = "0";
        Pt ="0";
        Pe ="0";
        Zg ="0";
        Zw ="0";
        PtA="0";
        PtB="0";
        PtC="0";
        PtD="0";
        PtS="0";
        E ="0";
        PeA="0";
        PeB="0";
        PeC="0";
        PeD="0";
        ZgA="0";
        ZwB="0";
        ZwC="0";
        ZwD="0";
        this.RA ="0";
        this.RB ="0";
        this.RC ="0";
        this.RD ="0";
    }
    public ResultData(String[]str) {
        this.j = str[0];
        Ptz = str[1];
        this.t = str[2];
        K = str[3];
        this.DA =str[4];
        this.DB = str[5];
        this.DC = str[6];
        this.DD = str[7];

        Y = "";
        Pt ="";
        Pe ="";
        Zg ="";
        Zw ="";
        PtA="";
        PtB="";
        PtC="";
        PtD="";
        PtS="";
        E ="";
        PeA="";
        PeB="";
        PeC="";
        PeD="";
        ZgA="";
        ZwB="";
        ZwC="";
        ZwD="";
        this.RA ="";
        this.RB ="";
        this.RC ="";
        this.RD ="";
    }
    public String toString(){
        return this.j.replace('.',',') +";"+
                Ptz.replace('.',',') +";"+
                this.t.replace('.',',')+";"+
                K.replace('.',',') +";"+
                this.DA.replace('.',',') +";"+
                this.DB.replace('.',',')+";"+
                this.DC.replace('.',',')+";"+
                this.DD.replace('.',',')+";"+
                Y.replace('.',',')+";"+
                Pt.replace('.',',')+";"+
                Pe.replace('.',',')+";"+
                Zg.replace('.',',')+";"+
                Zw.replace('.',',')+";"+
                PtA.replace('.',',')+";"+
                PtB.replace('.',',')+";"+
                PtC.replace('.',',')+";"+
                PtD.replace('.',',')+";"+
                PtS.replace('.',',')+";"+
                E.replace('.',',')+";"+
                PeA.replace('.',',')+";"+
                PeB.replace('.',',')+";"+
                PeC.replace('.',',')+";"+
                PeD.replace('.',',')+";"+
                ZgA.replace('.',',')+";"+
                ZwB.replace('.',',')+";"+
                ZwC.replace('.',',')+";"+
                ZwD.replace('.',',')+";"+
                this.RA.replace('.',',')+";"+
                this.RB.replace('.',',')+";"+
                this.RC.replace('.',',')+";"+
                this.RD.replace('.',',');

    }
    
    public void saveDataFromUnits(UnitA a, UnitB b, UnitCD c, UnitCD d, PowerStorage ps )
    {
         Y = (a.Y.add(b.Y).add(c.Y).add(d.Y)).setScale(2, RoundingMode.HALF_UP).toString();

         Pt = a.Pt.add(b.Pt).add(c.Pt).add(d.Pt).add(ps.Pt).setScale(2, RoundingMode.HALF_UP).toString();
         Pe = a.Pe.add(b.Pe).add(c.Pe).add(d.Pe).setScale(2, RoundingMode.HALF_UP).toString();
         Zg = a.Zg.setScale(2, RoundingMode.HALF_UP).toString();
         Zw = b.Zw.add(c.Zw).add(d.Zw).setScale(2, RoundingMode.HALF_UP).toString();
         PtA = a.Pt.setScale(2, RoundingMode.HALF_UP).toString();
         PtB = b.Pt.setScale(2, RoundingMode.HALF_UP).toString();
         PtC = c.Pt.setScale(2, RoundingMode.HALF_UP).toString();
         PtD = d.Pt.setScale(2, RoundingMode.HALF_UP).toString();
         PtS = ps.Pt.setScale(2, RoundingMode.HALF_UP).toString();
         E = ps.storedPower.setScale(2, RoundingMode.HALF_UP).toString();
         PeA= a.Pe.setScale(2, RoundingMode.HALF_UP).toString();
         PeB= b.Pe.setScale(2, RoundingMode.HALF_UP).toString();
         PeC= c.Pe.setScale(2, RoundingMode.HALF_UP).toString();
         PeD= d.Pe.setScale(2, RoundingMode.HALF_UP).toString();
         ZgA = a.Zg.setScale(2, RoundingMode.HALF_UP).toString();
         ZwB = b.Zw.setScale(2, RoundingMode.HALF_UP).toString();
         ZwC = c.Zw.setScale(2, RoundingMode.HALF_UP).toString();
         ZwD = d.Zw.setScale(2, RoundingMode.HALF_UP).toString();
         RA= a.R.setScale(2, RoundingMode.HALF_UP).toString();
         RB= b.R.setScale(2, RoundingMode.HALF_UP).toString();
         RC= c.R.setScale(2, RoundingMode.HALF_UP).toString();
         RD= d.R.setScale(2, RoundingMode.HALF_UP).toString();
    }
}
