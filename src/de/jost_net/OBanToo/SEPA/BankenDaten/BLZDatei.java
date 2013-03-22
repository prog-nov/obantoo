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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BLZDatei
{
  private BufferedInputStream bin;

  public BLZDatei(InputStream is)
  {
    bin = new BufferedInputStream(is);
  }

  public BLZSatz getNext() throws IOException
  {
    BLZSatz blz = new BLZSatz(bin);
    return blz;
  }
}
