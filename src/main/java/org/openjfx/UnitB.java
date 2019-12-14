package org.openjfx;

import java.math.BigDecimal;

public class UnitB {
    public BigDecimal Pe;
    public BigDecimal Pt;
    public boolean active;
    public BigDecimal R;
    public boolean startWork;
    public BigDecimal Zw;

    public int ChooseOperatingField( BigDecimal e_power, BigDecimal th_power )
    {
        if( CheckBoundaries1( e_power, th_power ) )
            return 1;
        if( CheckBoundaries2( e_power, th_power ) )
            return 2;
        return -1;
    }

    private boolean CheckBoundaries1( BigDecimal e_power, BigDecimal th_power )
    {
        BigDecimal temp1 = th_power.multiply(new BigDecimal("-0.1875"));
        temp1 = temp1.add( new BigDecimal("131.25" ) );

        BigDecimal temp2 = th_power.multiply(new BigDecimal("27"));
        temp2 = temp2.divide(new BigDecimal("26"));
        BigDecimal temp3 = new BigDecimal("1701").divide(new BigDecimal("23"));
        temp2 = temp2.add(new BigDecimal("72")).add(temp3);

        if( e_power.compareTo( new BigDecimal("72")) == -1 || e_power.compareTo(new BigDecimal("105")) == 1 ||
                e_power.compareTo(new BigDecimal("105")) == 0 || th_power.compareTo(new BigDecimal("0")) == -1 ||
                e_power.compareTo(temp1) == 1 || e_power.compareTo(temp2) == -1 )
            return false;
        return true;
    }

    private boolean CheckBoundaries2( BigDecimal e_power, BigDecimal th_power )
    {
        BigDecimal temp1 = th_power.multiply(new BigDecimal("-8")).divide(new BigDecimal("95" )).add(new BigDecimal("120"));
        BigDecimal temp2 = th_power.multiply(new BigDecimal("-7")).divide(new BigDecimal("37")).add(new BigDecimal("112"));
        BigDecimal temp3 = new BigDecimal("665").divide(new BigDecimal("37"));
        temp2 = temp2.add(temp3);
        if( e_power.compareTo(new BigDecimal("105")) == -1 || th_power.compareTo(new BigDecimal("0")) == -1 ||
                e_power.compareTo(temp1) == 1 || e_power.compareTo(temp2) == 1 )
            return false;
        return true;
    }

    public BigDecimal CoalUse( BigDecimal e_power, BigDecimal th_power )
    {
        BigDecimal coalUse = new BigDecimal("0" );
        if( ChooseOperatingField(e_power, th_power) == 1 )
        {
            if( th_power.compareTo(new BigDecimal("0")) == 0 )
            {
                coalUse = e_power.multiply(new BigDecimal("9.03" )).add(new BigDecimal("124.79" ));
            }
            BigDecimal temp1 = e_power.multiply(new BigDecimal("1.98" )).add(new BigDecimal("61.34" ));
            BigDecimal temp2 = e_power.multiply(new BigDecimal("1.72" )).add(new BigDecimal("2.43" ));
            BigDecimal temp3 = e_power.multiply(new BigDecimal("8.66" )).add(new BigDecimal("129.68" ));
            coalUse = th_power.multiply(temp1).divide(temp2).add(temp3);
        }
        if( ChooseOperatingField(e_power, th_power) == 2 )
        {
            if( th_power.compareTo(new BigDecimal("0")) == 0 )
            {
                coalUse = e_power.multiply(new BigDecimal("9.03" )).add(new BigDecimal("124.79" ));
            }
            BigDecimal temp1 = e_power.multiply(new BigDecimal("0.5" )).add(new BigDecimal("61.34" ));
            BigDecimal temp2 = e_power.multiply(new BigDecimal("-5.28" )).add(new BigDecimal("686.83" ));
            BigDecimal temp3 = e_power.multiply(new BigDecimal("8.66" )).add(new BigDecimal("129.68" ));
            coalUse = th_power.multiply(temp1).divide(temp2).add(temp3);
        }
        return coalUse;
    }
}
