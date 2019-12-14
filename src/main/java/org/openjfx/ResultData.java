package org.openjfx;

import java.math.BigDecimal;

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
                Ptz.replace('.',',')
                +";"+ this.t.replace('.',',')+";"+
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
                this.RD.replace('.',',')+"\n";

    }
}
