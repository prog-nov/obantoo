/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2006 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.QIF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * QIF-Buchungssatz
 * 
 * @author Heiner Jostkleigrewe
 */
public class QIFBuchung
{
  private Date datum = null;

  private double betrag = 0;

  private String empfaenger = null;

  private String kategorie = null;

  private String clearedStatus = "";

  private String referenz = null;

  private String memo = null;

  private Vector adresse = new Vector();

  /**
   * Adress-Index
   */
  private int ai = -1;

  public QIFBuchung()
  {
  }

  public void add(String line) throws QIFException
  {
    if (line.startsWith("D"))
    {
      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
      try
      {
        datum = sdf.parse(line.substring(1));
        return;
      }
      catch (ParseException e)
      {
        //
      }
      sdf = new SimpleDateFormat("MM.dd.yy");
      try
      {
        datum = sdf.parse(line.substring(1));
        return;
      }
      catch (ParseException e)
      {
      }
      throw new QIFException(QIFException.UNGUELTIGES_DATUM, line.substring(1));
    }
    if (line.startsWith("T"))
    {
      betrag = Double.parseDouble(line.substring(1));
    }
    if (line.startsWith("P"))
    {
      empfaenger = line.substring(1);
    }
    if (line.startsWith("L"))
    {
      kategorie = line.substring(1);
    }
    if (line.startsWith("C"))
    {
      clearedStatus = line.substring(1);
    }
    if (line.startsWith("A"))
    {
      adresse.addElement(line.substring(1));
    }
    if (line.startsWith("N"))
    {
      referenz = line.substring(1);
    }
    if (line.startsWith("M"))
    {
      memo = line.substring(1);
    }
  }

  public Date getDatum()
  {
    return datum;
  }

  public double getBetrag()
  {
    return betrag;
  }

  public String getEmpfaenger()
  {
    return empfaenger;
  }

  public String getKategorie()
  {
    return kategorie;
  }

  public String getClearedStatus()
  {
    return clearedStatus;
  }

  public String getReferenz()
  {
    return referenz;
  }

  public String getMemo()
  {
    return memo;
  }

  public int getAnzahlAdresszeilen()
  {
    return adresse.size();
  }

  public String getAdresseNext()
  {
    ai++;
    if (ai >= adresse.size())
    {
      return null;
    }
    return (String) adresse.elementAt(ai);
  }

  public String toString()
  {
    String ret = "Datum=" + datum + ", Betrag=" + betrag + ", Empfänger="
        + empfaenger + ", Kategorie=" + kategorie + ", ClearedStatus="
        + clearedStatus + ", Referenz=" + referenz + ", Memo=" + memo
        + ", Adresse=";
    for (int i = 0; i < adresse.size(); i++)
    {
      ret += (String) adresse.elementAt(i) + ", ";
    }
    return ret;
  }
}
/*
 * $Log$
 * Revision 1.1  2006/05/30 17:40:40  jost
 * *** empty log message ***
 *
 */
