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

public class SEPAException extends Exception
{
  private static final long serialVersionUID = 3303826666784401237L;

  public SEPAException()
  {
    super();
  }

  public SEPAException(String msg)
  {
    super(msg);
  }

}
