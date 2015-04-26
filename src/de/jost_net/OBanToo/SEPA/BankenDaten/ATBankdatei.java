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
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Österreichische Bankendatei
 * 
 * @author heiner
 * 
 */
public class ATBankdatei
{
  private BufferedReader in;

  private String[] headers;

  public ATBankdatei(InputStream is) throws IOException
  {
    in = new BufferedReader(
        new InputStreamReader(is, Charset.forName("CP1252")));
    String line = in.readLine(); // Header lesen
    headers = line.split(";");
  }

  public ATBank next() throws IOException
  {
    String line = in.readLine();
    if (line == null)
    {
      return null;
    }
    HashMap<String, String> felder = new HashMap<String, String>();
    String[] f = line.split(";");
    for (int i = 0; i < f.length; i++)
    {
      if (f[i].startsWith("\"") && f[i].endsWith("\""))
      {
        f[i] = f[i].substring(1, f[i].length() - 1);
      }
      felder.put(headers[i], f[i]);
    }
    return new ATBank(felder);
  }
}
