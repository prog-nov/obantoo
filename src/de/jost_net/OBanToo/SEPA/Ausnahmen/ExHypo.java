/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA.Ausnahmen;

public class ExHypo
{

  private String nrkreis;

  private String blzin;

  private String ktofrom;

  private String ktoto;

  private String blzout;

  public ExHypo(String nrkreis, String blzin, String ktofrom, String ktoto,
      String blzout)
  {
    this.nrkreis = nrkreis;
    this.blzin = blzin;
    this.ktofrom = ktofrom;
    this.ktoto = ktoto;
    this.blzout = blzout;
  }

  public String getNrkreis()
  {
    return nrkreis;
  }

  public void setNrkreis(String nrkreis)
  {
    this.nrkreis = nrkreis;
  }

  public String getBlzin()
  {
    return blzin;
  }

  public void setBlzin(String blzin)
  {
    this.blzin = blzin;
  }

  public String getKtofrom()
  {
    return ktofrom;
  }

  public void setKtofrom(String ktofrom)
  {
    this.ktofrom = ktofrom;
  }

  public String getKtoto()
  {
    return ktoto;
  }

  public void setKtoto(String ktoto)
  {
    this.ktoto = ktoto;
  }

  public String getBlzout()
  {
    return blzout;
  }

  public void setBlzout(String blzout)
  {
    this.blzout = blzout;
  }

}
