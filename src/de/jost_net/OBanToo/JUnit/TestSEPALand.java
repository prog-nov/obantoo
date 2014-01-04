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
  public void test()
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
