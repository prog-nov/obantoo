/*
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

  public static enum Fehler
  {
    BLZ_LEER, BLZ_UNGUELTIGE_LAENGE, BLZ_UNGUELTIG, KONTO_LEER, KONTO_UNGUELTIGE_LAENGE, KONTO_PRUEFZIFFER_FALSCH, KONTO_PRUEFZIFFERNREGEL_NICHT_IMPLEMENTIERT, IBANREGEL_NICHT_IMPLEMENTIERT, UNGUELTIGES_LAND
  }

  private Fehler f = null;

  public SEPAException()
  {
    super();
  }

  public SEPAException(Fehler fehler)
  {
    f = fehler;
  }

  public SEPAException(String msg)
  {
    super(msg);
  }

  public SEPAException(Fehler fehler, String msg)
  {
    super(msg);
    f = fehler;
  }

  public Fehler getFehler()
  {
    return f;
  }

}
