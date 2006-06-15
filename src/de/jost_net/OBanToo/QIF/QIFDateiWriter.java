/*
 * $Source$
 */
package de.jost_net.OBanToo.QIF;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Erstellung von QIF-Dateien
 * <p>
 * 
 * Beispiel<br>
 * 
 * <code>
 * FileOutputStream fos = new FileOutputStream("test.qif");<br>
 * QIFDateiWriter qifw = new QIFDateiWriter(fos);<br>
 * qifw.setBetrag(100.01);<br>
 * qifw.setDatum(new Date());<br>
 * qifw.setEmpfänger("Fritzchen Müller");<br>
 * qifw.addAdresse("Testenhausen");<br>
 * qifw.write();<br>
 *<br>
 * qifw.setBetrag(200);<br>
 * qifw.setDatum(new Date());<br>
 * qifw.setEmpfänger("Franz Sepplhuber");<br>
 * qifw.setKategorie("Reisen");<br>
 * qifw.write();<br>
 *<br>
 * qifw.close();<br>
 *  </code>
 * 
 * @author Heiner Jostkleigrewe
 * 
 */

public class QIFDateiWriter
{

  private DataOutputStream dos;

  private QIFBuchung qif;

  public QIFDateiWriter(OutputStream os) throws IOException
  {
    dos = new DataOutputStream(os);
    open();
  }

  private void open() throws IOException
  {
    dos.writeBytes("!Type:Bank\n");
    qif = new QIFBuchung();
  }

  public void setDatum(Date value)
  {
    qif.setDatum(value);
  }

  public void setBetrag(double value)
  {
    qif.setBetrag(value);
  }

  public void setEmpfänger(String value)
  {
    qif.setEmpfaenger(value);
  }

  public void setKategorie(String value)
  {
    qif.setKategorie(value);
  }

  public void setClearedStatus(String value)
  {
    qif.setClearedStatus(value);
  }

  public void setReferenz(String value)
  {
    qif.setReferenz(value);
  }

  public void setMemo(String value)
  {
    qif.setMemo(value);
  }

  public void addAdresse(String value)
  {
    qif.addAdresse(value);
  }

  public void write() throws IOException
  {
    qif.write(dos);
    qif = new QIFBuchung();
  }

  public void close() throws IOException
  {
    dos.flush();
    dos.close();
  }
}
/*
 * $Log$
 * Revision 1.1  2006/06/15 12:27:45  jost
 * Erweiterung um QIFDateiWriter
 *
 */
