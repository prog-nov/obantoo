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
          add("DE", blzs.getBezeichnung(), blzs.getBlz(), blzs.getBic(),
              blzs.getPruefziffernmethode(), blzs.getIBANRegel(),
              blzs.getHinweisloeschung());
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
        if (atb.getBic() != null && atb.getBic().trim().length() == 0)
        {
          continue;
        }
        add("AT", atb.getName(), atb.getBlz(), atb.getBic(), null, "000000", "");
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
      String bic, String pruefziffernmethode, String ibanregel,
      String hinweisloeschung)
  {
    Bank b = new Bank(kennzeichen, name, blz, bic, pruefziffernmethode,
        ibanregel, hinweisloeschung);
    banken.add(b);
    bankcodealt.put(b.getBLZ(), b);
    bankcodeneu.put(b.getBIC(), b);
  }

  public static ArrayList<Bank> getByPruefziffernMethode(String methode)
  {
    ArrayList<Bank> ret = new ArrayList<Bank>();
    for (Bank b : banken)
    {
      if (b.getLand().equals("DE")
          && b.getPruefziffernmethode().equals(methode))
      {
        ret.add(b);
      }
    }
    return ret;
  }

  public static ArrayList<Bank> getByGruppe(String gruppe)
  {
    ArrayList<Bank> ret = new ArrayList<Bank>();
    for (Bank b : banken)
    {
      if (b.getLand().equals("DE") && b.getBLZ().substring(3, 4).equals(gruppe))
      {
        ret.add(b);
      }
    }
    return ret;
  }

  public static ArrayList<Bank> getBanken()
  {
    return banken;
  }

}
