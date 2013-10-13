package de.jost_net.OBanToo.SEPA.Basislastschrift;

import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.SequenceType1Code;

public enum MandatSequence
{
  FRST("FRST", "Erste Lastschrift", SequenceType1Code.FRST), RCUR("RCUR",
      "Folgelastschrift", SequenceType1Code.RCUR), FNAL("FNAL",
      "Letzte Lastschrift", SequenceType1Code.FNAL), OOFF("OOFF",
      "Einmallastschrift", SequenceType1Code.OOFF);

  private final String txt;

  private final SequenceType1Code code;

  private final String anzeige;

  private MandatSequence(String txt, String anzeige, SequenceType1Code code)
  {
    this.txt = txt;
    this.anzeige = anzeige;
    this.code = code;
  }

  @Override
  public String toString()
  {
    return anzeige;
  }

  public static MandatSequence fromString(final String txt)
  {
    for (MandatSequence item : MandatSequence.values())
    {
      if (item.txt.equals(txt))
        return item;
    }
    return null;
  }

  public String getTxt()
  {
    return txt;
  }

  public SequenceType1Code getCode()
  {
    return code;
  }
}
