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

  private String pruefziffernmethode;

  private String ibanregel;
  
  private String hinweisloeschung;

  public Bank(String land, String bezeichnung, String blz, String bic,
      String pruefziffernmethode, String ibanregel, String hinweisloeschung)
  {
    this.land = land;
    this.bezeichnung = bezeichnung;
    this.blz = blz;
    this.bic = bic;
    this.pruefziffernmethode = pruefziffernmethode;
    this.ibanregel = ibanregel;
    this.hinweisloeschung=hinweisloeschung;
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

  public String getPruefziffernmethode()
  {
    return pruefziffernmethode;
  }

  public String getIBANRegel()
  {
    return ibanregel;
  }
  
  public String getHinweisloeschung()
  {
    return hinweisloeschung;
  }

  @Override
  public String toString()
  {
    return land + ", " + bic + ", " + blz + ", " + bezeichnung;
  }
}
