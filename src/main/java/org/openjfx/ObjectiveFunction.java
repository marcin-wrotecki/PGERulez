package org.openjfx;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ObjectiveFunction {

    public ArrayList<ResultData> resultData = new ArrayList<>();
    public PowerStorage ps = new PowerStorage();
    public int TrC = 6;
    public int TrD = 6;
    public int TrG = 1;

    public ObjectiveFunction(ArrayList<String> linesOfFile) {

        String[] str = new String[8];
        for (int i = 1; linesOfFile.size() > (i + 1); i++) {
            str = linesOfFile.get(i).split(";");
            resultData.add(new ResultData(str));


        }
        for (int i = 0; i < resultData.size(); i++) {
            ChooseRange(new BigDecimal(resultData.get(i).Ptz), i);
        }

    }

    public void ChooseRange(BigDecimal th_needed, int i) {
        int choose = 0;
        BigDecimal mean_demand24 = new BigDecimal("0");
        BigDecimal sum24 = new BigDecimal("0");
        /*if (i == 6420)
            System.out.println(i);*/
        if (i < (resultData.size() - 24)) {
            for (int j = i; j < i + 24; j++) {
                sum24 = sum24.add(new BigDecimal(resultData.get(j).Ptz));
            }
            mean_demand24 = sum24.divide(new BigDecimal("24"), 5, RoundingMode.HALF_UP);
        } else {
            int index = 0;
            for (int j = i; index++ < (resultData.size() - i); j++) {
                sum24 = sum24.add(new BigDecimal(resultData.get(j).Ptz));
            }
            mean_demand24 = sum24.divide(new BigDecimal(index), 5, RoundingMode.HALF_UP);
        }

        if (mean_demand24.compareTo(new BigDecimal("140")) == 1) {
            choose = 1;
        }
         if( mean_demand24.compareTo(new BigDecimal("220")) == 1)
        {
            choose = 2;
        }
         if( mean_demand24.compareTo(new BigDecimal("280")) == 1)
        {
            choose = 3;
        }
         if( mean_demand24.compareTo(new BigDecimal("440")) == 1)
        {
            choose = 4;
        }
           if( mean_demand24.compareTo(new BigDecimal("615")) == 1)
        {
            choose = 5;
        }


        if ((th_needed.compareTo(new BigDecimal("0")) == 1) && (th_needed.compareTo(new BigDecimal("140")) == -1) && (choose == 0)) {
            Optimize0To140(th_needed, i);
        }
        //else if( (th_needed.compareTo(new BigDecimal("140")) >= 0 ) &&(th_needed.compareTo(new BigDecimal("220")) == -1) && (choose == 1))
        else if (choose == 1) {
            Optimize140To220(th_needed, i, TrC--);
        } //else if ((th_needed.compareTo(new BigDecimal("220")) >= 0) &&
            //    (th_needed.compareTo(new BigDecimal("280")) == -1)) {
        else if( choose == 2 ) {
            Optimize220To280( th_needed, i, TrC--, TrD--, TrG--);
        } //else if ((th_needed.compareTo(new BigDecimal("280")) >= 0) &&
            //    (th_needed.compareTo(new BigDecimal("440")) == -1)) {
        else if( choose == 3 ) {
            Optimize280To440(th_needed, i );
        } //else if ((th_needed.compareTo(new BigDecimal("440")) >= 1) &&
            //    (th_needed.compareTo(new BigDecimal("615")) == -1)) {
        else if( choose == 4 ) {
            Optimize440To615( th_needed, i );
        } else {
            OptimizeOver615( th_needed, i );
        }
    }

    public void Optimize0To140(BigDecimal th_needed, int i) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();

        if (resultData.get(i).DB.equals("1")) {
            if( (new BigDecimal("172")).subtract(th_needed).compareTo(new BigDecimal("10")) >= 0 )
            {
                if( (new BigDecimal("840")).subtract(ps.storedPower).compareTo( new BigDecimal("10")) >=0 )
                {
                    ps.storedPower = ps.storedPower.add(new BigDecimal("10"));
                }
            }
            b.Pt = th_needed.add(ps.storedPower);
            double max = 0.0;
            double Y = 0.0;
            double cu = 0.0;
            double Pe_new = 0.0;
            double cu_save = 0.0;

            for (double j = 72; j < 120; j += 0.1) {
                cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), th_needed).toString());
                Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                if (Y > max && Math.abs(cu) > 0.0001) {
                    max = Y;
                    Pe_new = j;
                    cu_save = cu;
                }
            }
            b.Pe = new BigDecimal(Pe_new);
            b.Zw = new BigDecimal(cu_save);
            b.Y = new BigDecimal(max);
            /*if (th_needed.compareTo(new BigDecimal("130")) >= 0 && ps.storedPower.compareTo(new BigDecimal("840")) <= 0) {
                b.Pt = new BigDecimal("140");
                BigDecimal sp = new BigDecimal("0");
                sp = sp.add(new BigDecimal("140")).subtract(th_needed);
                ps.storedPower = ps.storedPower.add(sp);
            }*/
        } else {
            double max = 0.0;
            double Y = 0.0;
            double gu = 0.0;
            double pe = 0.0;
            double Pe_new = 0.0;
            double Pt_new = 0.0;
            double gu_save = 0.0;

            /*for (double j = 40; a.CheckBoundaries(new BigDecimal(resultData.get(i).t), new BigDecimal(j)); j += 0.1) {
                gu = Double.parseDouble(a.GasUse(new BigDecimal(j)).toString());
                pe = Double.parseDouble(a.ElectricPower(new BigDecimal(j)).toString());
                Y = Double.parseDouble(resultData.get(i).K) * Pe_new + 130 * j - 32 * gu;
                if (Y > max) {
                    max = Y;
                    Pt_new = j;
                    gu_save = gu;
                    Pe_new = pe;
                }
            }*/
            a.Pt = th_needed;
            a.Pe = a.ElectricPower(a.Pt);
            a.Zg = a.GasUse(a.Pt);
            BigDecimal temp = a.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = a.Zg.multiply(new BigDecimal("-32"));
            a.Y = new BigDecimal(resultData.get(i).K).multiply(a.Pe).add(temp).add(temp2);
        }

        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);
    }

    public void Optimize140To220(BigDecimal th_needed, int i, int Tr) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();
        if(i==1651)
            System.out.println(i);
        if (resultData.get(i).DC.equals("1")) {
            if (Tr >= 0) {
                c.R = new BigDecimal("10000");
                Optimize0To140(th_needed, i);
                return;
                //if (Tr == 0)
                  //  b.startWork = false;
            } else {
                c.Pt = th_needed;
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;

                c.Pe = a.ElectricPower(c.Pt);
                c.Zw = c.CoalUse(c.Pt);
                BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
                BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
                c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);


                //dodatkowa produkcja pradu z B
                for (double j = 72; j < 120; j += 0.1) {
                    b.Pt = new BigDecimal("0");
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), new BigDecimal("0")).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = new BigDecimal(cu_save);
                b.Y = new BigDecimal(max);

                if (th_needed.compareTo(new BigDecimal("140")) == -1) {
                    c.Pt = new BigDecimal("140");
                    c.Pe = a.ElectricPower(c.Pt);
                    c.Zw = c.CoalUse(c.Pt);
                    temp = c.Pt.multiply(new BigDecimal("130"));
                    temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
                    c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);
                    BigDecimal dif = new BigDecimal("140").subtract(th_needed);
                    if ((new BigDecimal("840")).subtract(ps.storedPower).compareTo(dif) >= 0)
                        ps.storedPower = ps.storedPower.add(dif);

                } else if (th_needed.compareTo(new BigDecimal("150")) == -1) {
                    c.Pt = new BigDecimal("140");
                    c.Pe = a.ElectricPower(c.Pt);
                    c.Zw = c.CoalUse(c.Pt);
                    temp = c.Pt.multiply(new BigDecimal("140"));
                    temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
                    c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);
                    BigDecimal dif = th_needed.subtract(new BigDecimal("140"));

                }
                //TrC = 6;

            }
        } else if (resultData.get(i).DD.equals("1")) {
            if (Tr >= 0) {
                d.R = new BigDecimal("10000");
                Optimize0To140(th_needed, i);
                if (Tr >= 0) {
                    d.R = new BigDecimal("10000");
                    Optimize0To140(th_needed, i);
                    return;
                    /*if (Tr == 0)
                        b.startWork = false;*/
                } else {
                    d.Pt = th_needed;
                    double max = 0.0;
                    double Y = 0.0;
                    double cu = 0.0;
                    double Pe_new = 0.0;
                    double cu_save = 0.0;

                    d.Pe = a.ElectricPower(d.Pt);
                    d.Zw = d.CoalUse(d.Pt);
                    BigDecimal temp = d.Pt.multiply(new BigDecimal("130"));
                    BigDecimal temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                    d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);


                    //dodatkowa produkcja pradu z B
                    for (double j = 72; j < 120; j += 0.1) {
                        b.Pt = new BigDecimal("0");
                        cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), new BigDecimal("0")).toString());
                        Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                        if (Y > max && Math.abs(cu) > 0.0001) {
                            max = Y;
                            Pe_new = j;
                            cu_save = cu;
                        }
                    }
                    b.Pe = new BigDecimal(Pe_new);
                    b.Zw = new BigDecimal(cu_save);
                    b.Y = new BigDecimal(max);

                    if (th_needed.compareTo(new BigDecimal("140")) == -1) {
                        d.Pt = new BigDecimal("140");
                        d.Pe = a.ElectricPower(d.Pt);
                        d.Zw = d.CoalUse(d.Pt);
                        temp = d.Pt.multiply(new BigDecimal("130"));
                        temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                        d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);
                        BigDecimal dif = new BigDecimal("140").subtract(th_needed);
                        if ((new BigDecimal("840")).subtract(ps.storedPower).compareTo(dif) >= 0)
                            ps.storedPower = ps.storedPower.add(dif);

                    } else if (th_needed.compareTo(new BigDecimal("150")) == -1) {
                        d.Pt = new BigDecimal("140");
                        d.Pe = a.ElectricPower(d.Pt);
                        d.Zw = d.CoalUse(d.Pt);
                        temp = d.Pt.multiply(new BigDecimal("140"));
                        temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                        d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);
                        BigDecimal dif = th_needed.subtract(new BigDecimal("140"));

                    }

                }
            }
        }
        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);

    }

    public void Optimize220To280( BigDecimal th_needed, int i, int Trc, int Trd, int Trg ) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();

        if (i==2149)
            System.out.println("elo");
        /*if (resultData.get(i).DD.equals("1")) {
            if (Trc >= 0) {
                c.R = new BigDecimal("10000");
                Optimize140To220(th_needed, i, TrC--);
            }
        }*/
        //else
        {
            c.Pt = new BigDecimal("220");
            c.Pe = c.ElectricPower( new BigDecimal("220"));
            c.Zw = c.CoalUse( new BigDecimal("220"));
            BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
            c.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);

            if( th_needed.compareTo(c.Pt) == 1 && th_needed.compareTo(new BigDecimal("230")) <= 0)
            {
                if( ps.storedPower.compareTo(new BigDecimal("0")) == 1 )
                {
                    ps.Pt = th_needed.subtract(c.Pt);
                    ps.storedPower = ps.storedPower.subtract(ps.Pt);
                }
            }
            else if( th_needed.compareTo( new BigDecimal("260")) <= 0 )
            {
                if (Trg >= 0) {
                    a.R = new BigDecimal("5000");
                    Optimize140To220(th_needed, i, TrC--);
                }
                else
                {
                    double max = 0.0;
                    double Y = 0.0;
                    double cu = 0.0;

                    double Pe_new = 0.0;
                    double cu_save = 0.0;

                    if(th_needed.subtract(c.Pt).subtract(ps.Pt).compareTo(new BigDecimal("0"))>=0)
                    {
                        a.Pt = th_needed.subtract(c.Pt).subtract(ps.Pt);
                    a.Pe = a.ElectricPower(a.Pt);
                    a.Zg = a.GasUse(a.Pt);
                    temp = a.Pt.multiply(new BigDecimal("130"));
                    temp2 = a.Zg.multiply(new BigDecimal("-32"));
                    a.Y = new BigDecimal(resultData.get(i).K).multiply(a.Pe).add(temp).add(temp2);
                    }


                    //dodatkowa produkcja pradu z B
                    for (double j = 72; j < 120; j += 0.1) {
                        b.Pt = new BigDecimal("0");
                        cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), new BigDecimal("0")).toString());
                        Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                        if (Y > max && Math.abs(cu) > 0.0001) {
                            max = Y;
                            Pe_new = j;
                            cu_save = cu;
                        }
                    }
                    b.Pe = new BigDecimal(Pe_new);
                    b.Zw = new BigDecimal(cu_save);
                    b.Y = new BigDecimal(max);
                }
            }
            else
            {
                if (Trd >= 0) {
                    d.R = new BigDecimal("10000");
                    Optimize140To220(th_needed, i, TrD--);
                }
                else
                {
                    d.Pt = th_needed.subtract(c.Pt).subtract(a.Pt);
                    d.Pe = d.ElectricPower( d.Pt);
                    d.Zw = d.CoalUse(d.Pt);
                     temp = d.Pt.multiply(new BigDecimal("130"));
                     temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                    d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);

                    //dodatkowa produkcja pradu z B
                    double max = 0.0;
                    double Y = 0.0;
                    double cu = 0.0;
                    double Pe_new = 0.0;
                    double cu_save = 0.0;
                    for (double j = 72; j < 120; j += 0.1) {
                        b.Pt = new BigDecimal("0");
                        cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), new BigDecimal("0")).toString());
                        Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                        if (Y > max && Math.abs(cu) > 0.0001) {
                            max = Y;
                            Pe_new = j;
                            cu_save = cu;
                        }
                    }
                    b.Pe = new BigDecimal(Pe_new);
                    b.Zw = new BigDecimal(cu_save);
                    b.Y = new BigDecimal(max);
                }
            }
        }
        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);

    }

    public void Optimize280To440(BigDecimal th_needed, int i ) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();

        if( resultData.get(i).DC.equals("1") && resultData.get(i).DD.equals("1"))
        {
            c.Pt = new BigDecimal("220");
            c.Pe = c.ElectricPower(c.Pt);
            c.Zw = c.CoalUse(c.Pt);
            BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
            c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);

            if( th_needed.subtract(c.Pt).compareTo(new BigDecimal("0")) > 0 ) {
            //if( (new BigDecimal("440")).subtract(th_needed).compareTo(new BigDecimal("0")) >= 0 ) {
                //d.Pt = (new BigDecimal("440")).subtract(c.Pt);
                d.Pt = th_needed.subtract(c.Pt);
                d.Pe = d.ElectricPower(d.Pt);
                d.Zw = d.CoalUse(d.Pt);
                temp = d.Pt.multiply(new BigDecimal("130"));
                temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);
            }
        }
        else if ( resultData.get(i).DC.equals("1") )
        {
            c.Pt = new BigDecimal("220");
            c.Pe = c.ElectricPower(c.Pt);
            c.Zw = c.CoalUse(c.Pt);
            BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
            c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);
            if (i==158)
                System.out.println("elo");
            BigDecimal dif = th_needed.subtract(c.Pt);
            if( dif.compareTo(new BigDecimal("172")) <= 0 )
            {
                b.Pt = dif;
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;
                for (double j = 72; j < 120; j += 0.1) {
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), b.Pt).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = b.CoalUse( b.Pe, b.Pt );
                temp = b.Pt.multiply(new BigDecimal("130"));
                temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
                b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);
            }
            else
            {
                b.Pt = new BigDecimal("172");
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;
                for (double j = 72; j < 120; j += 0.1) {
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), b.Pt).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = b.CoalUse( b.Pe, b.Pt );
                temp = b.Pt.multiply(new BigDecimal("130"));
                temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
                b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);

                a.Pt = th_needed.subtract(c.Pt).subtract(b.Pt);
                a.Pe = a.ElectricPower(a.Pt);
                a.Zg = a.GasUse(a.Pt);
                temp = a.Pt.multiply(new BigDecimal("130"));
                temp2 = a.Zg.multiply(new BigDecimal("-32"));
                a.Y = new BigDecimal(resultData.get(i).K).multiply(a.Pe).add(temp).add(temp2);
            }
        }
        else if ( resultData.get(i).DD.equals("1") )
        {
            d.Pt = new BigDecimal("220");
            d.Pe = d.ElectricPower(d.Pt);
            d.Zw = d.CoalUse(d.Pt);
            BigDecimal temp = d.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
            d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);

            BigDecimal dif = th_needed.subtract(d.Pt);
            if( dif.compareTo(new BigDecimal("172")) <= 0 )
            {
                b.Pt = dif;
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;
                for (double j = 72; j < 120; j += 0.1) {
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), b.Pt).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = b.CoalUse( b.Pe, b.Pt );
                temp = b.Pt.multiply(new BigDecimal("130"));
                temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
                b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);
            }
            else
            {
                b.Pt = new BigDecimal("172");
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;
                for (double j = 72; j < 120; j += 0.1) {
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), b.Pt).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = b.CoalUse( b.Pe, b.Pt );
                temp = b.Pt.multiply(new BigDecimal("130"));
                temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
                b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);

                a.Pt = th_needed.subtract(d.Pt).subtract(b.Pt);
                a.Pe = a.ElectricPower(a.Pt);
                a.Zg = a.GasUse(a.Pt);
                temp = a.Pt.multiply(new BigDecimal("130"));
                temp2 = a.Zg.multiply(new BigDecimal("-32"));
                a.Y = new BigDecimal(resultData.get(i).K).multiply(a.Pe).add(temp).add(temp2);
            }
        }
        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);
    }

    public void Optimize440To615( BigDecimal th_needed, int i ) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();

        if( resultData.get(i).DC.equals("1") && resultData.get(i).DD.equals("1") && resultData.get(i).DB.equals("1") )
        {
            c.Pt = new BigDecimal("220");
            c.Pe = c.ElectricPower(c.Pt);
            c.Zw = c.CoalUse(c.Pt);
            BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
            c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);

                d.Pt = new BigDecimal("220");
                d.Pe = d.ElectricPower(d.Pt);
                d.Zw = d.CoalUse(d.Pt);
                temp = d.Pt.multiply(new BigDecimal("130"));
                temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
                d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);



            if( th_needed.subtract(new BigDecimal("440")).compareTo(new BigDecimal("0")) > 0 ) {
                b.Pt = th_needed.subtract(new BigDecimal("440"));
                double max = 0.0;
                double Y = 0.0;
                double cu = 0.0;
                double Pe_new = 0.0;
                double cu_save = 0.0;
                for (double j = 72; j < 120; j += 0.1) {
                    cu = Double.parseDouble(b.CoalUse(new BigDecimal(j), b.Pt).toString());
                    Y = Double.parseDouble(resultData.get(i).K) * j + 130 * Double.parseDouble(b.Pt.toString()) - 20.8 * cu;
                    if (Y > max && Math.abs(cu) > 0.0001) {
                        max = Y;
                        Pe_new = j;
                        cu_save = cu;
                    }
                }
                b.Pe = new BigDecimal(Pe_new);
                b.Zw = b.CoalUse(b.Pe, b.Pt);
                temp = b.Pt.multiply(new BigDecimal("130"));
                temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
                b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);
            }

        }
        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);
    }

    public void OptimizeOver615(BigDecimal th_needed, int i) {
        UnitA a = new UnitA();
        UnitB b = new UnitB();
        UnitCD c = new UnitCD();
        UnitCD d = new UnitCD();
        if (i==2149)
            System.out.println("elo");
        if( resultData.get(i).DA.equals("1") && resultData.get(i).DB.equals("1") && resultData.get(i).DC.equals("1") && resultData.get(i).DD.equals("1") )
        {
            c.Pt = new BigDecimal("220");
            c.Pe = c.ElectricPower(c.Pt);
            c.Zw = c.CoalUse(c.Pt);
            BigDecimal temp = c.Pt.multiply(new BigDecimal("130"));
            BigDecimal temp2 = c.Zw.multiply(new BigDecimal("-20.8"));
            c.Y = new BigDecimal(resultData.get(i).K).multiply(c.Pe).add(temp).add(temp2);

            d.Pt = new BigDecimal("220");
            d.Pe = d.ElectricPower(d.Pt);
            d.Zw = d.CoalUse(d.Pt);
            temp = d.Pt.multiply(new BigDecimal("130"));
            temp2 = d.Zw.multiply(new BigDecimal("-20.8"));
            d.Y = new BigDecimal(resultData.get(i).K).multiply(d.Pe).add(temp).add(temp2);

            b.Pt = new BigDecimal("172");
            b.Pe = new BigDecimal("99");
            b.Zw = b.CoalUse(b.Pe, b.Pe);
            temp = b.Pt.multiply(new BigDecimal("130"));
            temp2 = b.Zw.multiply(new BigDecimal("-20.8"));
            b.Y = new BigDecimal(resultData.get(i).K).multiply(b.Pe).add(temp).add(temp2);

            if( th_needed.compareTo(new BigDecimal("650")) >= 0 )
            {
                a.Pt = new BigDecimal("50");
                a.Pe = a.ElectricPower(a.Pt);
                a.Zg = a.GasUse(a.Pt);
                temp = a.Pt.multiply(new BigDecimal("130"));
                temp2 = a.Zg.multiply(new BigDecimal("-32"));
                a.Y = new BigDecimal(resultData.get(i).K).multiply(a.Pe).add(temp).add(temp2);

                BigDecimal dif = th_needed.subtract(new BigDecimal("650"));
                if( (new BigDecimal("840")).subtract(ps.storedPower).compareTo(dif) >= 0)
                {
                    ps.storedPower = ps.storedPower.add(dif);
                    a.Pt = a.Pt.subtract(dif);
                }
                dif = th_needed.subtract(new BigDecimal("662"));
                if( dif.compareTo(new BigDecimal("0")) > 0  )
                {
                    ps.Pt = dif;
                }
            }
        }
        resultData.get(i).saveDataFromUnits(a, b, c, d, ps);
    }


}
