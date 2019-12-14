package org.openjfx;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitCD {
    public BigDecimal Pe;
    public BigDecimal Pt;
    public boolean active;
    public BigDecimal R;
    public boolean startWork;
    public BigDecimal Zw;
    public BigDecimal Y;

    public UnitCD() {
        Pe=new BigDecimal("0");
        Pt=new BigDecimal("0");
        active=false;
        R=new BigDecimal("0");
        startWork=false;
        Zw=new BigDecimal("0");
        Y= new BigDecimal("0");
    }
    public boolean CheckBoundaries( BigDecimal th_power )
    {
        if( th_power.compareTo( new BigDecimal( "139" ) ) == -1 || th_power.compareTo(new BigDecimal( "220" )) == 1 )
            return false;
        return true;
    }

    public BigDecimal ElectricPower( BigDecimal th_power )
    {
        BigDecimal temp1 = th_power.multiply(new BigDecimal( "44" )).divide(new BigDecimal( "81" ),5, RoundingMode.HALF_UP);
        BigDecimal temp2 = (new BigDecimal( "-6116" )).divide(new BigDecimal( "81" ),5, RoundingMode.HALF_UP).add(new BigDecimal( "66" ));
        return temp1.add(temp2);
    }

    public BigDecimal CoalUse( BigDecimal th_power )
    {
        return th_power.multiply(new BigDecimal( "5.75" )).add(new BigDecimal( "57.82" ));
    }
}
