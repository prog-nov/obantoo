/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2006 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.Dtaus;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class Tool
{
  private static String leer = "                                                                              ";

  public static String space(int anzahl)
  {
    return leer.substring(0, anzahl);
  }

  public static String formatSL(int value)
  {
    DecimalFormat dfSL = new DecimalFormat("0000");
    return dfSL.format(value);
  }

  public static String formatErweiterung(int value)
  {
    DecimalFormat dfErw = new DecimalFormat("00");
    return dfErw.format(value);
  }

  public static String formatKontollAnzahl(int value)
  {
    DecimalFormat dfCtrlAnz = new DecimalFormat("0000000");
    return dfCtrlAnz.format(value);
  }

  public static String formatKontrollSumme(BigInteger value)
  {
    DecimalFormat dfCtrlSum = new DecimalFormat("0000000000000");
    return dfCtrlSum.format(value);
  }

  public static String formatKontroll17(BigInteger value)
  {
    DecimalFormat dfCtrl17 = new DecimalFormat("00000000000000000");
    return dfCtrl17.format(value);
  }

  public static String formatBetrag(BigInteger value)
  {
    BigDecimal bdBetrag = new BigDecimal(value);
    bdBetrag = bdBetrag.divide(new BigDecimal(100));
    DecimalFormat nf = new DecimalFormat("#,###,##0.00 EUR");
    return nf.format(bdBetrag);
  }

  public static String formatBLZ(long value)
  {
    DecimalFormat dfBLZ = new DecimalFormat("00000000");
    return dfBLZ.format(value);
  }

  public static String formatKonto(long value)
  {
    DecimalFormat dfKonto = new DecimalFormat("0000000000");
    return dfKonto.format(value);
  }

  public static String formatTextschluessel(int value)
  {
    DecimalFormat dfTextschluessel = new DecimalFormat("00000");
    return dfTextschluessel.format(value);
  }
}
/*
 * $Log$
 * Revision 1.1  2006/06/05 09:36:11  jost
 * Erweiterungen f. d. DtausDateiWriter
 *
 */
