/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2006 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.Tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util
{
  static DecimalFormat DECIMALFORMAT = new DecimalFormat("###,##0.00");

  public static String toHex(String val)
  {
    String ret = "0x";
    for (int i = 0; i < val.length(); i++)
    {
      ret += toHex(val.charAt(i), 2);
    }
    return ret;
  }

  public static String formatCurrency(double value)
  {
    return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(value);
  }

  public static String formatCurrency(BigDecimal value)
  {
    return DECIMALFORMAT.format(value);
  }

  public static String toHex(char c, int width)
  {
    int i = c;
    return toHex(i, width);
  }

  public static String toHex(int val, int width)
  {
    String s = Integer.toHexString(val).toUpperCase();
    while (s.length() < width)
    {
      s = "0" + s;
    }
    return s;
  }

  public static String DateTTMMJJJJ(Date date)
  {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    return sdf.format(date);
  }

}
