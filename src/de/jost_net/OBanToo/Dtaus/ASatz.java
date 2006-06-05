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

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A-Satz - Datei-Vorsatz
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class ASatz extends Satz
{
  /**
   * Feld a01, 4 Bytes, numerisch, Satzlängenfeld, Konstant 0128
   */
  private String aSatzlaenge = "0128";

  /**
   * Feld a02, 1 Byte, alpha, Satzart, Konstant A
   */
  private String aSatzart = "A";

  /**
   * Feld a03, 2 Byte, alpha, Kennzeichen GK oder LK, Hinweis auf Gutschriften
   * (G) bzw. Lastschriften (L), K = Kundendatei
   */
  private String aGutschriftLastschrift = null;

  /**
   * Feld a04, 8 Byte, numerisch, Bankleitzahl, Bankleitzahl des Kreditinstituts
   * (Dateiempfänger)
   */
  private long aBlz = 0;

  /**
   * Feld a06, 27 Byte, alpha, Kundenname, Dateiabsender
   */
  private String aKundenname = null;

  /**
   * Feld a07, 6 Byte, numerisch, Datum, Dateierstellungsdatum (TTMMJJ)
   */
  private String aDateierstellungsdatum = null;

  /**
   * Feld a09, 10 Byte, numerisch, Kontonummer. Empfänger/Absender Kunde, max 10
   * Stellen. Über dieses Konto wird der Gegenwert verrechnet.
   */
  private long aKonto = 0;

  /**
   * Feld a10, 10 Byte, numerisch, Referenznummer des Einreichers, Angabe
   * freigestellt
   */
  private String aReferenz = "          ";

  /**
   * Feld a11b, 8 Byte, alpha, Ausführungsdatum (TTMMJJJJ) Angabe freigestellt.
   * Nicht jünger als Dateierstellungsdatum (Feld A7), jedoch höchstens 15
   * Kalendertage über Erstellungsdatum aus Feld A7. Soweit in diesem Datenfeld
   * ein Ausführungstermin angegeben wird, ist zu beachten, dass der in den
   * Sonderbedingungen genannte Nachweiszeitraum von mindestens 10 Kalendertagen
   * erst ab dem genannten Ausführungstermin zu berechnen ist.
   */
  private Date aAusfuehrungsdatum = null;

  /**
   * Feld a12, 1 Byte, alpha, Währungskennzeichen, konstant '1'
   */
  private String aWaehrungskennzeichen = "1";

  /**
   * Konstruktor mit der Übergabe eines zu parsenden Satzes
   * 
   * @param satz
   */
  public ASatz(String satz) throws DtausException
  {
    super(satz);
    if (!satz.substring(0, 4).equals(aSatzlaenge))
    {
      throw new DtausException(DtausException.A_SATZLAENGENFELD_FEHLERHAFT);
    }
    if (!satz.substring(4, 5).equals(aSatzart))
    {
      throw new DtausException(DtausException.A_SATZART_FEHLERHAFT);
    }
    setGutschriftLastschrift(satz.substring(5, 7));
    setBlz(satz.substring(7, 15));
    setKundenname(satz.substring(23, 50));
    setDateierstellungsdatum(satz.substring(50, 56));
    setKonto(satz.substring(60, 70));
    setReferenz(satz.substring(70, 80));
    setAusfuehrungsdatum(satz.substring(80, 88));
    setWaehrungskennzeichen(satz.substring(127, 128));
  }

  public ASatz() throws DtausException
  {

  }

  public void setGutschriftLastschrift(String value) throws DtausException
  {
    if (value.equals("GK") || value.equals("LK"))
    {
      aGutschriftLastschrift = value;
    }
    else
    {
      throw new DtausException(
          DtausException.A_GUTSCHRIFT_LASTSCHRIFT_FEHLERHAFT);
    }
  }

  public String getGutschriftLastschrift()
  {
    return aGutschriftLastschrift;
  }

  public void setBlz(String value) throws DtausException
  {
    try
    {
      aBlz = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.A_BLZ_FEHLERHAFT);
    }
  }

  public void setBlz(long value)
  {
    aBlz = value;
  }

  public long getBlz()
  {
    return aBlz;
  }

  public void setKundenname(String value)
  {
    aKundenname = value.trim();
  }

  public String getKundenname()
  {
    return aKundenname;
  }

  public void setDateierstellungsdatum(String value) throws DtausException
  {
    aDateierstellungsdatum = value;
  }

  public String getDateierstellungsdatum()
  {
    return aDateierstellungsdatum;
  }

  public void setKonto(String value) throws DtausException
  {
    try
    {
      aKonto = Long.parseLong(value);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.A_KONTO_FEHLERHAFT);
    }
  }

  public void setKonto(long value)
  {
    aKonto = value;
  }

  public long getKonto()
  {
    return aKonto;
  }

  public void setReferenz(String value)
  {
    aReferenz = value;
  }

  public String getReferenz()
  {
    return aReferenz;
  }

  public void setAusfuehrungsdatum(String value) throws DtausException
  {
    if (value.equals("        "))
    {
      aAusfuehrungsdatum = null;
      return;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
    try
    {
      aAusfuehrungsdatum = sdf.parse(value);
    }
    catch (ParseException e)
    {
      throw new DtausException(DtausException.A_AUSFUEHRUNGSDATUM_FEHLERHAFT);
    }
  }

  public void setAusfuehrungsdatum(Date value)
  {
    aAusfuehrungsdatum = value;
  }

  public String getAusfuehrungsdatumString()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
    return sdf.format(aAusfuehrungsdatum);
  }

  public Date getAusfuehrungsdatum()
  {
    return aAusfuehrungsdatum;
  }

  public void setWaehrungskennzeichen(String value) throws DtausException
  {
    if (value.equals("1"))
    {
      aWaehrungskennzeichen = value;
    }
    else
    {
      throw new DtausException(
          DtausException.A_WAEHRUNGSKENNZEICHEN_FEHLERHAFT, value);
    }

  }

  public void write(DataOutputStream dos) throws IOException
  {
    // Feld 1 - Satzlänge
    dos.writeBytes("0128");
    // Feld 2 - Satzart
    dos.writeBytes("A");
    // Feld 3 - Gutschrift/Lastschrift
    dos.writeBytes(aGutschriftLastschrift);
    // Feld 4 - Bankleitzahl der Bank, bei der die Diskette eingereicht wird
    dos.writeBytes(Tool.formatBLZ(aBlz));
    // Feld 5 - Konstant 0
    dos.writeBytes("00000000");
    // Feld 6 - Auftraggeber
    dos.writeBytes(make27(aKundenname.toUpperCase()));
    // Feld 7 - Datum
    if (aAusfuehrungsdatum == null)
    {
      aAusfuehrungsdatum = new Date();
    }
    SimpleDateFormat sdf6 = new SimpleDateFormat("ddMMyy");
    dos.writeBytes(sdf6.format(aAusfuehrungsdatum));
    // Feld 8 - Konstant 4 Leerzeichen
    dos.writeBytes(Tool.space(4));
    // Feld 9 - Kontonummer des Auftraggebers
    DecimalFormat dfKonto = new DecimalFormat("0000000000");
    dos.writeBytes(dfKonto.format(aKonto));
    // Feld 10 - Referenznummer des Einreichers
    dos.writeBytes("0000000000");
    // Feld 11a - Reserve
    dos.writeBytes(Tool.space(15));
    // Feld 11b - Ausführungsdatum
    if (aAusfuehrungsdatum != null)
    {
      SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
      dos.writeBytes(sdf.format(aAusfuehrungsdatum));
    }
    else
    {
      dos.writeBytes(Tool.space(8));
    }
    // Feld 11c - Reserve
    dos.writeBytes(Tool.space(24));
    // Feld 12 - Währung
    dos.writeBytes("1");
  }

  public String toString()
  {
    return "Satzlaenge=" + aSatzlaenge + ", Satzart=" + aSatzart
        + ", Gutschrift/Lastschrift=" + aGutschriftLastschrift + ", BLZ="
        + aBlz + ", Kundenname=" + aKundenname + ", Dateierstellungsdatum="
        + aDateierstellungsdatum + ", Konto=" + aKonto + ", Referenz="
        + aReferenz + ", Ausführungsdatum=" + aAusfuehrungsdatum
        + ", Währungskennzeichen=" + aWaehrungskennzeichen;
  }
}
/*
 * $Log$
 * Revision 1.2  2006/06/05 09:34:06  jost
 * Erweiterungen f. d. DtausDateiWriter
 * Revision 1.1 2006/05/24 16:24:44 jost Prerelease
 * 
 */

