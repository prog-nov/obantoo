/*
 * $Source$
 * $Revision$
 * $Date$
 *
 * Copyright 2013 by Heiner Jostkleigrewe
 * Diese Datei steht unter LGPL - siehe beigefügte lpgl.txt
 */

package de.jost_net.OBanToo.JUnit;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.Land.SEPALaender;
import de.jost_net.OBanToo.SEPA.Land.SEPALand;

@RunWith(JUnit4.class)
public class TestSEPALand
{
  @Test
  public void test01()
  {
    SEPALand sl = SEPALaender.getLand("DE");
    try
    {
      if (sl.check("DE12123"))
      {
        fail("Ungültige Länge wurde nicht erkannt");
      }
      ;
    }
    catch (SEPAException e)
    {
      // Alles in Ordnung
    }
  }

  @Test
  public void test02()
  {
    SEPALand sl = SEPALaender.getLand("DE");
    try
    {
      sl.check("DE89370400440532013000");
      {
        //
      }
    }
    catch (SEPAException e)
    {
      fail("Gültige Länge wurde als ungültig erkannt");
    }
  }

  @Test
  public void test03()
  {
    SEPALand sl = SEPALaender.getLand("DE");
    try
    {
      sl.check("DE893A0400440532013000");
      fail("Ungültiges Zeichen nicht erkannt");
    }
    catch (SEPAException e)
    {
      //
    }
  }

  @Test
  public void test04()
  {
    SEPALand sl = SEPALaender.getLand("DE");
    try
    {
      sl.check("DE8937040044053201300X");
      fail("Ungültiges Zeichen nicht erkannt");
    }
    catch (SEPAException e)
    {
      //
    }
  }

  @Test
  public void test05()
  {
    SEPALand sl = SEPALaender.getLand("DE");
    try
    {
      sl.check("DE8937040044053201300X");
      fail("Ungültiges Zeichen nicht erkannt");
    }
    catch (SEPAException e)
    {
      //
    }
  }

  @Test
  public void test06()
  {
    SEPALand sl = SEPALaender.getLand("BG");
    try
    {
      sl.check("BG800NBG96611020345678");
      fail("Ungültiges Zeichen nicht erkannt");
    }
    catch (SEPAException e)
    {
      //
    }
  }

  @Test
  public void test07()
  {
    SEPALand sl = SEPALaender.getLand("BG");
    try
    {
      sl.check("BG800NBG9661102034567$");
      fail("Ungültiges Zeichen nicht erkannt");
    }
    catch (SEPAException e)
    {
      //
    }
  }

  @Test
  public void test08()
  {
    for (SEPALand land : SEPALaender.getLaender())
    {
      try
      {
        new IBAN(land.getIBANSample());
      }
      catch (SEPAException e)
      {
        e.printStackTrace();
        fail();
      }
    }
  }
}
