/**
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

import de.jost_net.OBanToo.SEPA.BankenDaten.Bank;
import de.jost_net.OBanToo.SEPA.BankenDaten.Banken;
import de.jost_net.OBanToo.SEPA.Land.SEPALaender;
import de.jost_net.OBanToo.SEPA.Land.SEPALand;

public class BIC
{
  private String bic;

  public BIC(String bic) throws SEPAException
  {
    if (bic == null)
    {
      throw new SEPAException("BIC ist leer");
    }
    if (bic.length() != 8 && bic.length() != 11)
    {
      throw new SEPAException(
          "Ungültige Länge der BIC. Die BIC muss entweder 8 oder 11 Stellen lang sein.");
    }
    String landkuerzel = bic.substring(4, 6);
    SEPALand land = SEPALaender.getLand(landkuerzel);
    if (land == null)
    {
      throw new SEPAException("Ungültiges Land " + landkuerzel);
    }
    if (landkuerzel.equals("DE") || landkuerzel.equals("AT"))
    {
      Bank b = Banken.getBankByBIC(bic);
      if (b == null)
      {
        throw new SEPAException("BIC nicht in der Banken-Datenbank enthalten: "
            + bic);
      }
      bic = b.getBIC();
    }
  }

  public BIC(String kontoNr, String blz, String landkennzeichen)
      throws SEPAException
  {
    if (kontoNr == null || kontoNr.trim().length() == 0 || blz == null
        || blz.trim().length() == 0)
    {
      return;
    }
    Bank b = Banken.getBankByBLZ(blz);
    if (b == null)
    {
      throw new SEPAException("BLZ nicht im Datenbestand vorhanden");
    }
    bic = b.getBIC();
  }

  public String getBIC()
  {
    return bic;
  }

}
