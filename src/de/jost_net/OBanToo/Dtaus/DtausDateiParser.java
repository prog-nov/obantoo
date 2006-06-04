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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * Parser für DTAUS-Dateien
 * <p>
 * Mit dem DTAUS-Parser können DTAUS-Dateien geparst werden, die eine oder
 * mehrere logische Dateien enthalten. Im Konstruktor wird der Parse-Vorgang
 * gestartet. Die gesamte DTAUS-Datei verarbeitet. Die Daten werden in Objekten
 * vom Typ "LogischeDatei" gespeichert.
 * <p>
 * Sollte die zu parsende DTAUS-Datei fehlerhaft sein, werden entsprechende
 * DtausExceptions geworfen.
 * <p>
 * Nachdem das DtausDateiParser-Objekt instanziiert ist, kann über die Methode
 * getAnzahlLogischerDateien() die Anzahl der enthaltenen logischen Dateien
 * ermittelt werden.
 * <p>
 * Standardmäßig beziehen sich die getASatz()-, next() und
 * getESatz()-Methoden-Aufrufe auf die erste logische Datei. Mit
 * setLogischeDatei(int) kann eine andere logische Datei ausgewählt werden.
 * <p>
 * <p>
 * Beispiel Auflistung aller Datensätze der 2. logischen Datei:<br>
 * <code>
 * DtausDateiParser p = new DtausDateiParser("/home/heiner/dtaus0.txt");<br>
 * p.setLogischeDatei(2);<br>
 * CSatz c = p.next();<br>
 * while (c != null)<br>
 * {<br>
 *   System.out.println(c);<br>
 *   c = p.next();<br>
 * }<br>
 * System.out.println("----");<br>
 * System.out.println(p.getASatz());<br>
 * System.out.println(p.getESatz());<br>
 * </code>
 * 
 * 
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class DtausDateiParser
{
  private InputStream dtaus;

  private ASatz asatz = null;

  private ESatz esatz = null;

  private Vector logischeDateien;

  private LogischeDatei logdat;

  public DtausDateiParser(String filename) throws IOException, DtausException
  {
    this(new BufferedInputStream(new FileInputStream(filename)));
  }

  public DtausDateiParser(InputStream is) throws IOException, DtausException
  {
    logischeDateien = new Vector();
    dtaus = is;
    while (is.available() > 0)
    {
      asatz = new ASatz(lese());
      LogischeDatei logdat = new LogischeDatei(asatz);
      CSatz c = internNext();
      while (c != null)
      {
        logdat.addCSatz(c);
        c = internNext();
      }
      logdat.setESatz(esatz);
      logischeDateien.addElement(logdat);
    }
    this.logdat = (LogischeDatei) logischeDateien.elementAt(0);
  }

  public int getAnzahlLogischerDateien()
  {
    return logischeDateien.size();
  }

  public void setLogischeDatei(int nr) throws DtausException
  {
    if (nr <= logischeDateien.size())
    {
      this.logdat = (LogischeDatei) logischeDateien.elementAt(nr - 1);
    }
    else
    {
      throw new DtausException(DtausException.UNGUELTIGE_LOGISCHE_DATEI, nr
          + "");
    }
  }

  public LogischeDatei getLogischeDatei(int nr)
  {
    return (LogischeDatei) logischeDateien.elementAt(nr - 1);
  }

  private CSatz internNext() throws IOException, DtausException
  {
    String satz = lese();
    if (satz.substring(4, 5).equals("C"))
    {
      return new CSatz(satz);
    }
    esatz = new ESatz(satz);
    return null;
  }

  public ASatz getASatz()
  {
    return this.logdat.getASatz();
  }

  public CSatz next()
  {
    return this.logdat.getNextCSatz();
  }

  public ESatz getESatz()
  {
    return this.logdat.getESatz();
  }

  private String lese() throws IOException, DtausException
  {
    byte[] inchar = new byte[4];
    dtaus.read(inchar);
    String satzlaenge = new String(inchar);
    // Lese in der Satzlänge. Die Satzlänge ist um 4 Bytes zu verringern, da
    // diese
    // Bytes bereits gelesen wurden.
    inchar = new byte[getSatzlaenge(satzlaenge) - 4];
    dtaus.read(inchar);
    return satzlaenge + new String(inchar);
  }

  /**
   * Umsetzung der logischen Satzlänge in die physikalische Satzlänge
   */
  private int getSatzlaenge(String satzlaengenfeld) throws DtausException
  {
    try
    {
      int sl = Integer.parseInt(satzlaengenfeld);
      // A- und E-Satz
      if (sl == 128)
      {
        return 128;
      }
      // C-Satz mit keinem, einem oder 2 Erweiterungsteilen
      else if (sl >= 187 && sl <= 245)
      {
        return 256;
      }
      // C-Satz mit 3 bis 6 Erweiterungsteilen
      else if (sl >= 274 && sl <= 361)
      {
        return 384;
      }
      // C-Satz mit 7 bis 10 Erweiterungsteilen
      else if (sl >= 390 && sl <= 477)
      {
        return 512;
      }
      // C-Satz mit 11 bis 14 Erweiterungsteilen
      else if (sl >= 506 && sl <= 593)
      {
        return 640;
      }
      // C-Satz mit 15 Erweiterungsteilen
      else if (sl >= 622)
      {
        return 728;
      }
      throw new DtausException(DtausException.SATZLAENGE_FEHLERHAFT,
          satzlaengenfeld);
    }
    catch (NumberFormatException e)
    {
      throw new DtausException(DtausException.SATZLAENGE_FEHLERHAFT,
          satzlaengenfeld);
    }

  }

  public static void main(String[] args)
  {
    try
    {
      DtausDateiParser p = new DtausDateiParser("/home/heiner/DTAUS6");
      CSatz c = p.next();
      while (c != null)
      {
        System.out.println(c);
        c = p.next();
      }
      System.out.println("----");
      System.out.println(p.getASatz());
      System.out.println(p.getESatz());

      System.out.println("====");
      System.out.println("Anzahl logischer Dateien: "
          + p.getAnzahlLogischerDateien());
      p.setLogischeDatei(2);
      c = p.next();
      while (c != null)
      {
        System.out.println(c);
        c = p.next();
      }
      System.out.println("----");
      System.out.println(p.getASatz());
      System.out.println(p.getESatz());
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (DtausException e)
    {
      e.printStackTrace();
    }
    System.out.println("Fertig");
  }
}
/*
 * $Log$
 * Revision 1.5  2006/06/04 12:23:51  jost
 * Redaktionelle Änderung
 *
 * Revision 1.4  2006/05/29 16:38:03  jost
 * Anpassungen für den Einsatz in Hibiscus
 * Revision 1.3 2006/05/28 09:06:32 jost -
 * Unterstützung mehrerer logischer Dateien pro physikalischer Datei - interne
 * Umstellung von Reader auf InputStream Revision 1.2 2006/05/25 20:30:40 jost
 * Korrektur Satzlängen und Doku Revision 1.1 2006/05/24 16:24:44 jost
 * Prerelease
 * 
 */
