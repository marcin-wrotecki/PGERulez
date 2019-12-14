package org.openjfx;


import java.math.BigDecimal;

public class Storage {
    public BigDecimal storedPower;

    public Storage() {
        this.storedPower = new BigDecimal( "0" );
    }

    public void ChangeStoredPower( BigDecimal change )
    {
        this.storedPower = this.storedPower.add(change);
    }
}
