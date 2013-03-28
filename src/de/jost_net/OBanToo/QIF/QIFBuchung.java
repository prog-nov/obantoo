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

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import de.jost_net.OBanToo.Dtaus.Tool;

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

  private String clearedStatus = null;

  private String referenz = null;

  private String memo = null;

  private Vector<String> adresse = new Vector<String>();

  /**
   * Adress-Index
   */
  private int ai = -1;

  public QIFBuchung()
  {
    adresse = new Vector<String>();
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
      try
      {
        datum = Tool.parseQIFDate(line.substring(1));
        return;
      }
      catch (ParseException e)
      {
        //
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

  public void setDatum(Date value)
  {
    this.datum = value;
  }

  public double getBetrag()
  {
    return betrag;
  }

  public void setBetrag(double value)
  {
    this.betrag = value;
  }

  public String getEmpfaenger()
  {
    return empfaenger;
  }

  public void setEmpfaenger(String value)
  {
    this.empfaenger = value;
  }

  public String getKategorie()
  {
    return kategorie;
  }

  public void setKategorie(String value)
  {
    this.kategorie = value;
  }

  public String getClearedStatus()
  {
    return clearedStatus;
  }

  public void setClearedStatus(String value)
  {
    this.clearedStatus = value;
  }

  public String getReferenz()
  {
    return referenz;
  }

  public void setReferenz(String value)
  {
    this.referenz = value;
  }

  public String getMemo()
  {
    return memo;
  }

  public void setMemo(String value)
  {
    this.memo = value;
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
    return adresse.elementAt(ai);
  }

  public void addAdresse(String value)
  {
    adresse.addElement(value);
  }

  public void write(DataOutputStream dos) throws IOException
  {
    if (this.getDatum() != null)
    {
      dos.writeBytes("D" + Tool.formatQIFDate(this.getDatum()) + "\n");
    }
    dos.writeBytes("T" + Tool.formatQIFBetrag(this.getBetrag()) + "\n");
    if (this.getEmpfaenger() != null)
    {
      dos.writeBytes("P" + this.getEmpfaenger() + "\n");
    }
    if (this.getKategorie() != null)
    {
      dos.writeBytes("L" + this.getKategorie() + "\n");
    }
    if (this.getClearedStatus() != null)
    {
      dos.writeBytes("C" + this.getClearedStatus() + "\n");
    }
    ai = -1;
    for (int i = 0; i < this.getAnzahlAdresszeilen(); i++)
    {
      dos.writeBytes("A" + this.getAdresseNext() + "\n");
    }
    if (this.getReferenz() != null)
    {
      dos.writeBytes("N" + this.getReferenz() + "\n");
    }
    if (this.getMemo() != null)
    {
      dos.writeBytes("M" + this.getMemo() + "\n");
    }
    dos.writeBytes("^\n");
  }

  @Override
  public String toString()
  {
    String ret = "Datum=" + datum + ", Betrag=" + betrag + ", Empfänger="
        + empfaenger + ", Kategorie=" + kategorie + ", ClearedStatus="
        + clearedStatus + ", Referenz=" + referenz + ", Memo=" + memo
        + ", Adresse=";
    for (int i = 0; i < adresse.size(); i++)
    {
      ret += adresse.elementAt(i) + ", ";
    }
    return ret;
  }
}
/*
 * $Log$
 * Revision 1.5  2013/03/28 12:29:55  jverein
 * Überflüssiges Casting entfernt.
 * Revision 1.4 2012/10/04 17:23:09 jverein Annotation
 * 
 * Revision 1.3 2011/10/29 06:59:13 jverein Warnungen entfernt. Revision 1.2
 * 2006/06/15 12:27:30 jost Erweiterung um QIFDateiWriter Revision 1.1
 * 2006/05/30 17:40:40 jost *** empty log message ***
 */
