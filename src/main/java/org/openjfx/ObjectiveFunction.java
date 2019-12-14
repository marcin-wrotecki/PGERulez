package org.openjfx;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ObjectiveFunction {

    ArrayList<ResultData> resultData = new ArrayList<>();

    public ObjectiveFunction(ArrayList<String>linesOfFile){

        String[] str = new String[8];
       for(int i =1 ; linesOfFile.size()>(i+1);i++){
            str=linesOfFile.get(i).split(";");
            resultData.add(new ResultData(str));


        }
        for(int i=0;i<resultData.size();i++) {
            ChooseRange(new BigDecimal(resultData.get(i).Ptz),i);
        }

    }
    public void ChooseRange(BigDecimal th_needed, int i )
    {
        int choose = 0;
        BigDecimal mean_demand24 = new BigDecimal("0");
        BigDecimal sum24 = new BigDecimal("0");
        for( int j = i; j < i+24; j++ )
        {
            sum24.add(new BigDecimal(resultData.get(j).Ptz));
        }
        mean_demand24.divide(sum24, 5, RoundingMode.HALF_UP);
        if( mean_demand24.compareTo(new BigDecimal("140")) == 1 )
            choose = 1;

        if( ( th_needed.compareTo(new BigDecimal("0")) == 1 ) && (th_needed.compareTo(new BigDecimal("140")) == -1 ) && (choose==0))
        {
            Optimize0To140( th_needed, i );
        }
        else if( (th_needed.compareTo(new BigDecimal("140")) >= 0 ) &&(th_needed.compareTo(new BigDecimal("220")) == -1) && (choose == 1))
        {
            Optimize140To220();
        }
        else if( (th_needed.compareTo(new BigDecimal("220")) >= 0 ) &&
                (th_needed.compareTo(new BigDecimal("280")) == -1)  )
        {
            Optimize220To280();
        }
        else if( (th_needed.compareTo(new BigDecimal("280")) >= 0) &&
                (th_needed.compareTo(new BigDecimal("440")) == -1)  )
        {
            Optimize280To440();
        }
        else if( (th_needed.compareTo(new BigDecimal("440")) >= 1  ) &&
                (th_needed.compareTo(new BigDecimal("490")) == -1)  )
        {
            Optimize440To490();
        }
        else if( (th_needed.compareTo(new BigDecimal("490")) >= 0 ) &&
                (th_needed.compareTo(new BigDecimal("662")) == -1)  )
        {
            Optimize490To662();
        }
        else
        {
            OptimizeOver662();
        }
    }

    public void Optimize0To140(  BigDecimal th_needed, int i )
    {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();
        PowerStorage ps = new PowerStorage();
        if(i==2361)
            System.out.println(i);
        if( resultData.get(i).DB.equals("1") )
        {
            b.Pt = th_needed;
            double max = 0.0;
            double Y = 0.0;
            double cu = 0.0;
            double Pe_new = 0.0;
            double cu_save = 0.0;

            for( double j = 72; j < 120; j+=0.1 )
            {
                cu = Double.parseDouble(b.CoalUse( new BigDecimal(j), th_needed ).toString());
                Y = Double.parseDouble(resultData.get(i).K)*j + 130*Double.parseDouble(b.Pt.toString()) - 20.8*cu;
                if( Y > max && Math.abs(cu) > 0.0001 )
                {
                    max = Y;
                    Pe_new = j;
                    cu_save = cu;
                }
            }
            b.Pe = new BigDecimal(Pe_new);
            b.Zw = new BigDecimal(cu_save);
            b.Y = new BigDecimal(max);
        }
        else {
            double max = 0.0;
            double Y = 0.0;
            double gu = 0.0;
            double pe = 0.0;
            double Pe_new = 0.0;
            double Pt_new = 0.0;
            double gu_save = 0.0;

            for( double j = 40; a.CheckBoundaries( new BigDecimal( resultData.get(i).t ), new BigDecimal(j)); j+=0.1 )
            {
                gu = Double.parseDouble( a.GasUse(new BigDecimal(j)).toString());
                pe = Double.parseDouble(a.ElectricPower(new BigDecimal(j)).toString());
                Y = Double.parseDouble( resultData.get(i).K)*Pe_new + 130*j - 32*gu;
                if( Y > max )
                {
                    max = Y;
                    Pt_new = j;
                    gu_save = gu;
                    Pe_new = pe;
                }
            }
            a.Pe = new BigDecimal(Pe_new);
            a.Pt = new BigDecimal(Pt_new);
            a.Zg = new BigDecimal(gu_save);
            a.Y = new BigDecimal(max);
        }

        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);
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
