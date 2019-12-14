package org.openjfx;


import java.math.BigDecimal;

public class PowerStorage {
    public BigDecimal storedPower;
    public BigDecimal Pt;

    public PowerStorage() {
        this.storedPower = new BigDecimal( "0" );
        this.Pt = new BigDecimal("0");
    }

    public void ChangeStoredPower( BigDecimal change )
    {
        this.storedPower = this.storedPower.add(change);
    }
}
