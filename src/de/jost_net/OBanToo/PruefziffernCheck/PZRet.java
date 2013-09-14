/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.PruefziffernCheck;

public class PZRet
{
  private boolean valid;

  private int pos = -1;

  private String alg = null;

  private Exception exception = null;

  public PZRet(boolean valid)
  {
    this.valid = valid;
  }

  public PZRet(boolean valid, Exception exception)
  {
    this.valid = valid;
    this.exception = exception;
  }

  public PZRet(boolean valid, int pos, String alg)
  {
    this.valid = valid;
    this.pos = pos;
    this.alg = alg;
  }

  public boolean isValid()
  {
    return valid;
  }

  public int getPos()
  {
    return pos;
  }

  public String getAlg()
  {
    return alg;
  }

  public Exception getException()
  {
    return exception;
  }

}
