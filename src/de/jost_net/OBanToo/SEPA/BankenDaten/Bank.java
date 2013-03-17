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

public class Bank
{
  private String land;

  private String bezeichnung;

  private String blz;

  private String bic;

  public Bank(String land, String bezeichnung, String blz, String bic)
  {
    this.land = land;
    this.bezeichnung = bezeichnung;
    this.blz = blz;
    this.bic = bic;
  }

  public String getLand()
  {
    return land;
  }

  public String getBezeichnung()
  {
    return bezeichnung;
  }

  public String getBLZ()
  {
    return blz;
  }

  public String getBIC()
  {
    return bic;
  }

  @Override
  public String toString()
  {
    return land + ", " + bic + ", " + blz + ", " + bezeichnung;
  }
}
