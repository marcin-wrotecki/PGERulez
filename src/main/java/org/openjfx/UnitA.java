package org.openjfx;


import java.math.BigDecimal;

public class UnitA {
    public BigDecimal Pe;
    public BigDecimal Pt;
    public boolean active;
    public BigDecimal R;
    public boolean startWork;
    public BigDecimal Zg;
    public BigDecimal temperature;
    public BigDecimal Y;

    /*public UnitA(String Pe, String Pt, boolean active, String R, boolean startWork, String Zg, String temperature)
    {
        this.Pe = new BigDecimal(Pe);
        this.Pt = new BigDecimal(Pt);
        this.active = active;
        this.R = new BigDecimal(R);
        this.startWork = startWork;
        this.Zg = new BigDecimal(Zg);
        this.temperature = new BigDecimal(temperature);
    }*/

    public UnitA() {
        Pe=new BigDecimal("0");
        Pt=new BigDecimal("0");
        active=false;
        R=new BigDecimal("0");
        startWork=false;
        Zg=new BigDecimal("0");
        temperature=new BigDecimal("0");
        Y= new BigDecimal("0");
    }

    public boolean CheckBoundaries(BigDecimal t, BigDecimal thermal_power )   //sprawdzenie warunkow brzegowych
    {
        BigDecimal temp = t.multiply( new BigDecimal("-0.23" ));
        temp = temp.add(new BigDecimal("52"));
        if( t.compareTo( new BigDecimal("-40") ) == -1 || t.compareTo(new BigDecimal("40" )) == 1 ||
                thermal_power.compareTo(new BigDecimal("40")) == -1 || thermal_power.compareTo(new BigDecimal("50.85")) == 1 ||
                thermal_power.compareTo( temp ) == 1 )
            return false;
        return true;
    }

    public BigDecimal ElectricPower( BigDecimal thermal_power )       //obliczenie mocy elektrycznej
    {
        BigDecimal temp = thermal_power.pow(3);
        temp = temp.multiply( new BigDecimal( "0.00024") );

        BigDecimal temp2 = thermal_power.pow(2);
        temp2 = temp2.multiply(new BigDecimal( "0.004"));

        BigDecimal temp3 = thermal_power.multiply(new BigDecimal( "0.4"));

        temp = temp.subtract(temp2).add(temp3).add(new BigDecimal( "3.9"));

        return temp;
    }

    public BigDecimal GasUse( BigDecimal thermal_power )   //obliczenie zuzycia gazu
    {
        BigDecimal temp = thermal_power.multiply( new BigDecimal( "6.33"));
        temp.subtract(new BigDecimal( "15.94"));
        return temp;
    }
}
