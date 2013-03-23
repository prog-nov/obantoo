/**
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA.BankenDaten;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Banken
{
  private static ArrayList<Bank> banken = new ArrayList<Bank>();

  private static HashMap<String, Bank> bankcodealt = new HashMap<String, Bank>();

  private static HashMap<String, Bank> bankcodeneu = new HashMap<String, Bank>();

  static
  {
    try
    {
      // Deutschland
      InputStream is = Banken.class.getClassLoader().getResourceAsStream(
          "BLZ.txt");
      
      BLZDatei blz = new BLZDatei(is);
      BLZSatz blzs = blz.getNext();
      while (blzs.getBic() != null)
      {
        if (blzs.getZahlungsdienstleister().equals("1")
            && blzs.getBic().trim().length() > 0)
        {
          add("DE", blzs.getBezeichnung(), blzs.getBlz(), blzs.getBic());
        }
        blzs = blz.getNext();
      }
      // Österreich
      is = Bank.class.getClassLoader().getResourceAsStream("oesterreich.csv");
      ATBankdatei atbd = new ATBankdatei(is);
      boolean eof = false;
      while (!eof)
      {
        ATBank atb = atbd.next();
        if (atb == null)
        {
          eof = true;
          continue;
        }
        if (!atb.getKennzeichen().equals("Hauptanstalt")
            || atb.getBic().trim().length() == 0)
        {
          continue;
        }
        add("AT", atb.getName(), atb.getBlz(), atb.getBic());
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public static Bank getBankByBLZ(String blz)
  {
    return bankcodealt.get(blz);
  }

  public static Bank getBankByBIC(String bic)
  {
    return bankcodeneu.get(bic);
  }

  private static void add(String kennzeichen, String name, String blz,
      String bic)
  {
    Bank b = new Bank(kennzeichen, name, blz, bic);
    banken.add(b);
    bankcodealt.put(b.getBLZ(), b);
    bankcodeneu.put(b.getBIC(), b);
  }
}
