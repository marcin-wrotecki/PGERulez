package org.openjfx;

import java.math.BigDecimal;

public class UnitCD {
    public BigDecimal Pe;
    public BigDecimal Pt;
    public boolean active;
    public BigDecimal R;
    public boolean startWork;
    public BigDecimal Zw;

    public boolean CheckBoundaries( BigDecimal th_power )
    {
        if( th_power.compareTo( new BigDecimal( "139" ) ) == -1 || th_power.compareTo(new BigDecimal( "220" )) == 1 )
            return false;
        return true;
    }

    public BigDecimal ElectricPower( BigDecimal th_power )
    {
        BigDecimal temp1 = th_power.multiply(new BigDecimal( "44" )).divide(new BigDecimal( "81" ));
        BigDecimal temp2 = (new BigDecimal( "-6116" )).divide(new BigDecimal( "81" )).add(new BigDecimal( "66" ));
        temp1 = temp1.add(temp2);
        return temp1;
    }

    public BigDecimal CoalUse( BigDecimal th_power )
    {
        BigDecimal coalUse = th_power.multiply(new BigDecimal( "5.75" )).add(new BigDecimal( "57.82" ));
        return coalUse;
    }
}
