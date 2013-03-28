/*
 * $Source$
 * $Revision$
 * $Date$
 * $Author$
 *
 * Copyright 2006 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.Dtaus;

import java.util.Vector;

/**
 * Logische Datei innerhalb einer physikalischen DTAUS-Datei
 * 
 * @author Heiner Jostkleigrewe
 * 
 */
public class LogischeDatei
{

  private ASatz asatz;

  private Vector<CSatz> csaetze;

  private ESatz esatz;

  private int cpos = -1;

  public LogischeDatei(ASatz asatz)
  {
    this.asatz = asatz;
    csaetze = new Vector<CSatz>();
  }

  public ASatz getASatz()
  {
    return asatz;
  }

  public void addCSatz(CSatz csatz)
  {
    csaetze.addElement(csatz);
  }

  public CSatz getNextCSatz()
  {
    cpos++;
    if (cpos >= csaetze.size())
    {
      return null;
    }
    return csaetze.elementAt(cpos);
  }

  public void setESatz(ESatz esatz)
  {
    this.esatz = esatz;
  }

  public ESatz getESatz()
  {
    return esatz;
  }
}
/*
 * $Log$
 * Revision 1.3  2013/03/28 12:29:31  jverein
 * Überflüssiges Casting entfernt.
 * Revision 1.2 2011/10/29 06:59:00 jverein
 * Warnungen entfernt. Revision 1.1 2006/05/28 09:07:31 jost Neu: Logische
 * Dateien innerhalb einer physikalischen Datei Revision 1.2 2006/05/25 20:30:40
 * jost
 */
