package org.openjfx;


import java.math.BigDecimal;
import java.util.ArrayList;

public class ObjectiveFunction {

    ArrayList<ResultData> resultData = new ArrayList<>();

    public ObjectiveFunction(ArrayList<String>linesOfFile){
        int i = 1;
        String[] str = new String[8];
        while(linesOfFile.size()>(i-1)){
            str=linesOfFile.get(i).split(";");
            resultData.add(new ResultData(str));
            i++;
        }


    }
    public void ChooseRange(BigDecimal th_needed)
    {

        if( ( th_needed.compareTo(new BigDecimal("0")) == 1 ) && (th_needed.compareTo(new BigDecimal("140")) == -1 ))
        {
            //Optimize0To140();
        }
        else if( (th_needed.compareTo(new BigDecimal("140")) == 1 || th_needed.compareTo(new BigDecimal("140")) == 0 ) &&
                (th_needed.compareTo(new BigDecimal("220")) == -1) )
        {
            Optimize140To220();
        }
        else if( (th_needed.compareTo(new BigDecimal("220")) == 1 || th_needed.compareTo(new BigDecimal("220")) == 0 ) &&
                (th_needed.compareTo(new BigDecimal("280")) == -1)  )
        {
            Optimize220To280();
        }
        else if( (th_needed.compareTo(new BigDecimal("280")) == 1 || th_needed.compareTo(new BigDecimal("280")) == 0 ) &&
                (th_needed.compareTo(new BigDecimal("440")) == -1)  )
        {
            Optimize280To440();
        }
        else if( (th_needed.compareTo(new BigDecimal("440")) == 1 || th_needed.compareTo(new BigDecimal("440")) == 0 ) &&
                (th_needed.compareTo(new BigDecimal("490")) == -1)  )
        {
            Optimize440To490();
        }
        else if( (th_needed.compareTo(new BigDecimal("490")) == 1 || th_needed.compareTo(new BigDecimal("490")) == 0 ) &&
                (th_needed.compareTo(new BigDecimal("662")) == -1)  )
        {
            Optimize490To662();
        }
        else
        {
            OptimizeOver662();
        }
    }

    public void Optimize0To140(  BigDecimal th_needed )
    {
        UnitB b = new UnitB();
        if( b.active == true )
        {
            b.Pt = th_needed;

        }
    }

    public void Optimize140To220()
    {

    }

    public void Optimize220To280()
    {

    }

    public void Optimize280To440()
    {

    }

    public void Optimize440To490()
    {

    }

    public void Optimize490To662()
    {

    }

    public void OptimizeOver662()
    {

    }



}
