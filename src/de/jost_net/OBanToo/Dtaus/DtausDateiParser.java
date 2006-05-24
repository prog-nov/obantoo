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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parser für DTAUS-Dateien
 * <p>
 * Beispiel Auflistung aller Datensätze:<br>
 * <code>
 * DtausDateiParser p = new DtausDateiParser("/home/heiner/dtaus0.txt");
 * CSatz c = p.next();
 * while (c != null)
 * {
 *   System.out.println(c);
 *   c = p.next();
 * }
 * System.out.println("----");
 * System.out.println(p.getASatz());
 * System.out.println(p.getESatz());
 * </code>
 * 
 * Sollte die zu parsende DTAUS-Datei fehlerhaft sein, werden entsprechende
 * DtausExceptions geworfen.
 * 
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class DtausDateiParser
{
  private BufferedReader dtaus;

  private ASatz asatz = null;

  private ESatz esatz = null;

  public DtausDateiParser(String filename) throws IOException, DtausException
  {
    dtaus = new BufferedReader(new FileReader(filename));
    asatz = new ASatz(lese());
  }

  public CSatz next() throws IOException, DtausException
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
    return asatz;
  }

  public ESatz getESatz()
  {
    return esatz;
  }

  private String lese() throws IOException, DtausException
  {
    char[] inchar = new char[4];
    dtaus.read(inchar);
    String satzlaenge = new String(inchar);
    // Lese in der Satzlänge. Die Satzlänge ist um 4 Bytes zu verringern, da
    // diese
    // Bytes bereits gelesen wurden.
    inchar = new char[getSatzlaenge(satzlaenge) - 4];
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
      else if (sl >= 506 && sl <= 477)
      {
        return 512;
      }
      // C-Satz mit 15 Erweiterungsteilen
      else if (sl >= 622)
      {
        return 640;
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
      DtausDateiParser p = new DtausDateiParser("/home/heiner/dtaus0.txt");
      CSatz c = p.next();
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
 * Revision 1.1  2006/05/24 16:24:44  jost
 * Prerelease
 *
 */
