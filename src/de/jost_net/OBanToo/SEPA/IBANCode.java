/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */
package de.jost_net.OBanToo.SEPA;

public enum IBANCode
{
  GUELTIG("00", "Umstellung war erfolgreich oder nicht erforderlich", 1), //
  KONTONUMMERERSETZT(
      "01",
      "Umstellung war erfolgreich, die Kontonummer wurde bei der Berechnung ersetzt (z. B. bei Spendenkonten)",
      2), //
  PRUEFZIFFERNMETHODEFEHLT(
      "02",
      "Die Erforderliche Prüfziffernmethode existiert nicht in OBanToo. Keine Prüfung der Prüfziffer. IBAN kontrollieren.",
      2), //
  BLZUNGUELTIG("10", "Bankleitzahl ungültig", 3), //
  AUFBAUKONTONUMMERFALSCH("11",
      "Aufbau Kontonummer falsch, z.B. auf Grund der Prüfziffernrechnung", 3), //
  KOMBINATIONBLZKONTOUNZULAESSIG("12",
      "Kombination BLZ / Kontonummer nicht für IBAN Berechnung zugelassen", 3), //
  GEMELDETEBLZZURLOESCHUNGVORGEMERKT(
      "13",
      "Gemeldete Bankleitzahl ist zur Löschung vorgemerkt und wurde gegen die Nachfolgebankleitzahl ausgetauscht.",
      2), //
  GEMELDETEBLZURLOESCHUNGOHNENACHFOLGEBLZ(
      "14",
      "IBAN wurde auf Basis einer zur Löschung vorgemerkten Bankleitzahl ermittelt. Es liegt keine Nachfolgebankleitzahl vor.",
      3), //
  IBANALTUNGUELTIG("20", "Aufbau der IBAN alt ungültig", 3), //
  PRUEFZIFFERIBANALTFALSCH("21", "Prüfziffernrechnung der IBAN alt falsch", 3), //
  BICNICHTGUELTIG("22", "BIC ist nicht gültig", 3), //
  KONTOKEINKONTODERUMSTELLENDENSTELLE("40",
      "Konto ist kein Konto der umstellenden Stelle (gem. Feld 5 oder 5b)", 3), //
  IBANBERECHNUNGNICHTMOEGLICH("50", "IBAN-Berechnung nicht möglich", 3), //
  KONTONUMMERUNGUELTIG("90", "Kontonummer ungültig", 3);

  private final String number;

  private final String message;

  private final int status;

  IBANCode(String number, String message, int status)
  {
    this.number = number;
    this.message = message;
    this.status = status;
  }

  public String getNumber()
  {
    return number;
  }

  public String getMessage()
  {
    return message;
  }

  public int getStatus()
  {
    return status;
  }

  public static IBANCode fromString(String code)
  {
    if (code != null)
    {
      for (IBANCode ic : IBANCode.values())
      {
        if (code.equalsIgnoreCase(ic.number))
        {
          return ic;
        }
      }
    }
    return null;
  }
}
