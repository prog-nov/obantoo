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

import java.util.HashMap;

public class ATBank
{
  private String blz;

  private String name;

  private String bic;

  private String kennzeichen;

  public ATBank(HashMap<String, String> felder)
  {
    blz = felder.get("Bankleitzahl");
    name = felder.get("Bankenname");
    bic = felder.get("SWIFT-Code");
    kennzeichen = felder.get("Kennzeichen");
  }

  public String getBlz()
  {
    return blz;
  }

  public String getName()
  {
    return name;
  }

  public String getBic()
  {
    return bic;
  }

  public String getKennzeichen()
  {
    return kennzeichen;
  }

  @Override
  public String toString()
  {
    return (blz + ", " + bic + ", " + name + ", " + kennzeichen);
  }
}
