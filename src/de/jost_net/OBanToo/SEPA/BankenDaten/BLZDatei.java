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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BLZDatei
{
  private BufferedReader br;

  public BLZDatei(InputStream is)
  {
    br = new BufferedReader(new InputStreamReader(is));
  }

  public BLZSatz getNext() throws IOException
  {
    BLZSatz blz = new BLZSatz(br);
    return blz;
  }
}
