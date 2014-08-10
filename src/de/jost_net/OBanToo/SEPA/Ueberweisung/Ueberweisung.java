package de.jost_net.OBanToo.SEPA.Ueberweisung;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.AccountIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.ActiveOrHistoricCurrencyAndAmountSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.ActiveOrHistoricCurrencyCodeEUR;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.AmountTypeSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.BranchAndFinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.BranchAndFinancialInstitutionIdentificationSEPA3;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.CashAccountSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.CashAccountSEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.ChargeBearerTypeSEPACode;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.CreditTransferTransactionInformationSCT;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.CustomerCreditTransferInitiationV03;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.Document;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.FinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.FinancialInstitutionIdentificationSEPA3;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.GroupHeaderSCT;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PartyIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PartyIdentificationSEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PaymentIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PaymentInstructionInformationSCT;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PaymentMethodSCTCode;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.PaymentTypeInformationSCT1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.RemittanceInformationSEPA1Choice;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_001_003_03.ServiceLevelSEPA;
import de.jost_net.OBanToo.StringLatin.Zeichen;

/**
 * 
 * @author heiner
 * 
 */
public class Ueberweisung
{

  /**
   * Message-ID für die Prüfung auf Doppeleinreichung
   */
  private String messageID = null;

  /**
   * BIC des Auftraggebers
   */
  private String bic = null;

  /**
   * IBAN des Auftraggebers
   */
  private String iban = null;

  /**
   * Name des Auftraggebers
   */
  private String name = null;

  /**
   * Array von Empfängern
   */
  private ArrayList<Empfaenger> empfaengerarray = new ArrayList<Empfaenger>();

  /**
   * Sammelbuchung? Standardmäßig Einzelbuchung.
   */
  private boolean sammelbuchung = false;

  /**
   * Kontrollsumme
   */
  private BigDecimal kontrollsumme = new BigDecimal(0);

  /**
   * Anzahl Buchungen (read-only)
   */
  private String anzahlbuchungen;

  /**
   * Datum und Uhrzeit der Erzeugung der Datei
   */
  private Date creationdatetime;

  /**
   * Datum der Ausführung
   */
  private Date dateofexecution;

  public Ueberweisung()
  {
  }

  /**
   * Für jede Buchung wird ein Zahler-Object übergeben
   */
  public void add(Empfaenger empfaenger)
  {
    empfaengerarray.add(empfaenger);
  }

  public void setSammelbuchung(boolean sammelbuchung)
  {
    this.sammelbuchung = sammelbuchung;
  }

  /**
   * Schreibt die SEPA-Datei. Vorher sind alle Werte über die set-Methoden sowie
   * die add(Zahler)-Methode übergeben werden.
   * 
   */
  public void write(File file) throws DatatypeConfigurationException,
      SEPAException, JAXBException, FileNotFoundException
  {
    write(new BufferedOutputStream(new FileOutputStream(file)));
  }

  public void write(BufferedOutputStream bos) throws JAXBException,
      DatatypeConfigurationException, SEPAException
  {
    Document doc = new Document();
    doc.setCstmrCdtTrfInitn(getCustumerCreditTransferInitiationV03());

    /*
     * Die standardmäßig von xjc erzeugte Document-Klasse erzeugt beim
     * marshall-Aufruf immer einen "ns2"-Zusatz. Durch hinzufügen eines
     * 
     * @XmlRootElementes in die Klasse document wird das vermieden.
     * 
     * @XmlRootElement(name="Document")
     * 
     * @XmlAccessorType(XmlAccessType.FIELD)
     * 
     * @XmlType(name = "Document", propOrder = {"cstmrDrctDbtInitn"
     * 
     * public class Document
     */

    JAXBContext context = JAXBContext.newInstance(Document.class);
    Marshaller m = context.createMarshaller();

    m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
        "urn:iso:std:iso:20022:tech:xsd:pain.001.003.03 pain.001.003.03.xsd");

    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    try
    {
      m.marshal(doc, bos);
    }
    finally
    {
      try
      {
        bos.close();
      }
      catch (IOException e)
      {
        throw new SEPAException(e.getMessage());
      }
    }
  }

  /**
   * SEPA-Datei einlesen. Nach dem Methodenaufruf k�nnen die Werte �ber die
   * get-Methoden abgefragt werden.
   */
  // public void read(File file) throws JAXBException, SEPAException
  // {
  // JAXBContext jc = JAXBContext.newInstance(Document.class);
  // Unmarshaller u = jc.createUnmarshaller();
  // Document doc = (Document) u.unmarshal(file);
  // setMessageID(doc.getCstmrDrctDbtInitn().getGrpHdr().getMsgId());
  // setName(doc.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtr().getNm());
  // setIBAN(doc.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtrAcct().getId()
  // .getIBAN());
  // int anzahlbuchungen = 0;
  // BigDecimal kontrollsumme = new BigDecimal(0);
  // for (PaymentInstructionInformationSDD pii : doc.getCstmrDrctDbtInitn()
  // .getPmtInf())
  // {
  // anzahlbuchungen += new Integer(pii.getNbOfTxs());
  // setBIC(pii.getCdtrAgt().getFinInstnId().getBIC());
  // kontrollsumme = kontrollsumme.add(pii.getCtrlSum());
  // setCreationDateTime(doc.getCstmrDrctDbtInitn().getGrpHdr().getCreDtTm()
  // .toGregorianCalendar().getTime());
  // // setFaelligskeitsdatum(doc.getCstmrDrctDbtInitn().getPmtInf().get(0)
  // // .getReqdColltnDt().toGregorianCalendar().getTime());
  //
  //
  // setGlaeubigerID(pii.getCdtrSchmeId().getId().getPrvtId().getOthr()
  // .getId());
  //
  // setGlaeubigerID(doc.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty()
  // .getId().getPrvtId().getOthr().getId());
  // System.out.println(pii.getCdtrSchmeId().getId().getPrvtId().getOthr()
  // .getId());
  //
  // System.out.println(doc.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty()
  // .getId().getPrvtId().getOthr().getId());
  //
  // List<DirectDebitTransactionInformationSDD> liste = pii.getDrctDbtTxInf();
  // for (DirectDebitTransactionInformationSDD ddti : liste)
  // {
  // Empfaenger z = new Empfaenger();
  // z.setBetrag(ddti.getInstdAmt().getValue());
  // z.setBic(ddti.getDbtrAgt().getFinInstnId().getBIC());
  // z.setIban(ddti.getDbtrAcct().getId().getIBAN());
  // z.setMandatdatum(ddti.getDrctDbtTx().getMndtRltdInf().getDtOfSgntr()
  // .toGregorianCalendar().getTime());
  // z.setMandatid(ddti.getPmtId().getEndToEndId());
  // z.setName(ddti.getDbtr().getNm());
  // z.setVerwendungszweck(ddti.getRmtInf().getUstrd());
  // empfaengerarray.add(z);
  // empfaengermap.put(z.getMandatid(), z);
  // }
  // }
  // setAnzahlBuchungen(anzahlbuchungen + "");
  // setKontrollsumme(kontrollsumme);
  // }
  private CustomerCreditTransferInitiationV03 getCustumerCreditTransferInitiationV03()
      throws DatatypeConfigurationException, SEPAException
  {
    CustomerCreditTransferInitiationV03 cddi = new CustomerCreditTransferInitiationV03();
    for (Empfaenger e : empfaengerarray)
    {
      kontrollsumme = kontrollsumme.add(e.getBetrag());
    }
    cddi.setGrpHdr(getGroupHeader());

    cddi.getPmtInf().add(getPaymentInstructionInformationSCT());
    return cddi;
  }

  private GroupHeaderSCT getGroupHeader()
      throws DatatypeConfigurationException, SEPAException
  {
    GroupHeaderSCT grH = new GroupHeaderSCT();

    // aktuelles Datum und Uhrzeit
    creationdatetime = new Date();
    grH.setCreDtTm(getYYYMMDDHHMMSS(creationdatetime));
    // Kontrollsumme
    grH.setCtrlSum(kontrollsumme);

    PartyIdentificationSEPA1 partyid1 = new PartyIdentificationSEPA1();
    partyid1.setNm(Zeichen.convert(getName()));

    grH.setInitgPty(partyid1);
    grH.setMsgId(getMessageID());
    grH.setNbOfTxs(empfaengerarray.size() + "");
    anzahlbuchungen = empfaengerarray.size() + "";
    return grH;
  }

  private PaymentInstructionInformationSCT getPaymentInstructionInformationSCT()
      throws SEPAException, DatatypeConfigurationException
  {
    PaymentInstructionInformationSCT pii = new PaymentInstructionInformationSCT();
    pii.setBtchBookg(sammelbuchung); // true=Sammelbuchung, false=Einzelbuchung
    pii.setChrgBr(ChargeBearerTypeSEPACode.SLEV);

    BigDecimal seqKontrollsumme = new BigDecimal(0);
    int seqAnzahl = 0;
    for (Empfaenger e : empfaengerarray)
    {
      seqKontrollsumme = seqKontrollsumme.add(e.getBetrag());
      seqAnzahl++;
    }
    pii.setCtrlSum(seqKontrollsumme); // Betragssumme aller Transaktionen
    pii.setNbOfTxs(seqAnzahl + ""); // Anzahl der Buchungen

    PartyIdentificationSEPA2 pi2 = new PartyIdentificationSEPA2();
    pi2.setNm(Zeichen.convert(getName())); // Name des �berweisenden
    pii.setDbtr(pi2);

    AccountIdentificationSEPA ai = new AccountIdentificationSEPA();
    ai.setIBAN(getIBAN());
    CashAccountSEPA1 ca1 = new CashAccountSEPA1();
    ca1.setId(ai);
    pii.setDbtrAcct(ca1);

    BranchAndFinancialInstitutionIdentificationSEPA3 bafii = new BranchAndFinancialInstitutionIdentificationSEPA3();
    FinancialInstitutionIdentificationSEPA3 fii = new FinancialInstitutionIdentificationSEPA3();
    fii.setBIC(getBIC());
    bafii.setFinInstnId(fii);
    pii.setDbtrAgt(bafii);

    pii.setPmtInfId(getMessageID());

    pii.setPmtMtd(PaymentMethodSCTCode.TRF);

    pii.setPmtTpInf(getPaymentTypeInformationSCT1());

    pii.setReqdExctnDt(getYYYMMDD(dateofexecution));

    for (Empfaenger e : empfaengerarray)
    {
      pii.getCdtTrfTxInf().add(getCreditTransferTransactionInformationSCT(e));
    }

    return pii;
  }

  private static XMLGregorianCalendar getYYYMMDD(Date date)
      throws DatatypeConfigurationException
  {
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(date);
    XMLGregorianCalendar xmlgc = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(gc);

    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar();
    xmlGregorianCalendar.setDay(xmlgc.getDay());
    xmlGregorianCalendar.setMonth(xmlgc.getMonth());
    xmlGregorianCalendar.setYear(xmlgc.getYear());
    return xmlGregorianCalendar;
  }

  private static XMLGregorianCalendar getYYYMMDDHHMMSS(Date date)
      throws DatatypeConfigurationException
  {
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(date);
    XMLGregorianCalendar xmlgc = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar(gc);

    XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance()
        .newXMLGregorianCalendar();
    xmlGregorianCalendar.setDay(xmlgc.getDay());
    xmlGregorianCalendar.setMonth(xmlgc.getMonth());
    xmlGregorianCalendar.setYear(xmlgc.getYear());
    xmlGregorianCalendar.setHour(xmlgc.getHour());
    xmlGregorianCalendar.setMinute(xmlgc.getMinute());
    xmlGregorianCalendar.setSecond(xmlgc.getSecond());
    return xmlGregorianCalendar;
  }

  private CreditTransferTransactionInformationSCT getCreditTransferTransactionInformationSCT(
      Empfaenger e) throws SEPAException
  {
    CreditTransferTransactionInformationSCT ctti = new CreditTransferTransactionInformationSCT();

    AmountTypeSEPA at = new AmountTypeSEPA();
    ActiveOrHistoricCurrencyAndAmountSEPA aohcaa = new ActiveOrHistoricCurrencyAndAmountSEPA();
    aohcaa.setCcy(ActiveOrHistoricCurrencyCodeEUR.EUR);
    aohcaa.setValue(e.getBetrag());
    at.setInstdAmt(aohcaa);

    ctti.setAmt(at);

    PartyIdentificationSEPA2 pis2 = new PartyIdentificationSEPA2();
    pis2.setNm(e.getName());
    ctti.setCdtr(pis2);

    CashAccountSEPA2 ca = new CashAccountSEPA2();
    AccountIdentificationSEPA ai = new AccountIdentificationSEPA();
    ai.setIBAN(e.getIban());
    ca.setId(ai);
    ctti.setCdtrAcct(ca);

    BranchAndFinancialInstitutionIdentificationSEPA1 bafiis = new BranchAndFinancialInstitutionIdentificationSEPA1();
    FinancialInstitutionIdentificationSEPA1 fii = new FinancialInstitutionIdentificationSEPA1();
    fii.setBIC(e.getBic());
    bafiis.setFinInstnId(fii);
    ctti.setCdtrAgt(bafiis);

    PaymentIdentificationSEPA pis = new PaymentIdentificationSEPA();
    pis.setEndToEndId(e.getReferenz());
    ctti.setPmtId(pis);
    RemittanceInformationSEPA1Choice ri = new RemittanceInformationSEPA1Choice();
    ri.setUstrd(e.getVerwendungszweck());
    ctti.setRmtInf(ri);
    return ctti;
  }

  private PaymentTypeInformationSCT1 getPaymentTypeInformationSCT1()
  {
    PaymentTypeInformationSCT1 pti = new PaymentTypeInformationSCT1();

    ServiceLevelSEPA sls = new ServiceLevelSEPA();
    sls.setCd("SEPA");
    pti.setSvcLvl(sls);
    return pti;
  }

  /**
   * Message-ID. Z. B. Buchungslaufnummer. Max. 35 Stellen.
   */
  public void setMessageID(String messageid) throws SEPAException
  {
    if (messageid == null || messageid.length() == 0 || messageid.length() > 35)
    {
      throw new SEPAException(
          "Message-ID muss zwischen 1 und 35 Stellen lang sein");
    }
    this.messageID = messageid;
  }

  public String getMessageID() throws SEPAException
  {
    if (messageID == null)
    {
      throw new SEPAException("Message-ID ist noch nicht gef�llt");
    }
    return messageID;
  }

  /**
   * BIC. L�nge 8 oder 11 Stellen.
   */
  public void setBIC(String bic) throws SEPAException
  {
    if (bic == null || (bic.length() != 8 && bic.length() != 11))
    {
      throw new SEPAException("BIC nicht korrekt gef�llt");
    }
    this.bic = bic;
  }

  public String getBIC() throws SEPAException
  {
    if (bic == null)
    {
      throw new SEPAException("BIC ist noch nicht gef�llt");
    }
    return bic;
  }

  /**
   * IBAN. L�nge in Abh�ngigkeit vom Land.
   */
  public void setIBAN(String iban) throws SEPAException
  {
    new IBAN(iban);
    this.iban = iban;
  }

  public String getIBAN() throws SEPAException
  {
    if (iban == null)
    {
      throw new SEPAException("IBAN ist noch nicht gef�llt");
    }
    return iban;
  }

  /**
   * Name des Zahlungspflichtigen. L�nge max. 70 Stellen.
   */
  public void setName(String name) throws SEPAException
  {
    if (name == null || name.length() == 0 || name.length() > 70)
    {
      throw new SEPAException(
          "Name des Zahlungsempf�ngers nicht korrekt gef�llt");
    }
    this.name = name;
  }

  public String getName() throws SEPAException
  {
    if (name == null)
    {
      throw new SEPAException(
          "Name des Zahlungsempf�ngers ist noch nicht gef�llt");
    }
    return name;
  }

  /**
   * Wird nur intern beim einlesen einer Datei genutzt.
   */
  void setKontrollsumme(BigDecimal kontrollsumme)
  {
    this.kontrollsumme = kontrollsumme;
  }

  /**
   * Kontrollsumme aller Buchungen. Steht nach dem Einlesen einer Datei zur
   * Verf�gung.
   * 
   * @return Kontrollsumme
   */
  public BigDecimal getKontrollsumme()
  {
    return kontrollsumme;
  }

  /**
   * Anzahl der Buchungen. Steht nach dem Einlesen einer Datei zur Verf�gung.
   * 
   * @return Anzahl Buchungen
   */
  public String getAnzahlBuchungen()
  {
    return anzahlbuchungen;
  }

  void setAnzahlBuchungen(String anzahlbuchungen)
  {
    this.anzahlbuchungen = anzahlbuchungen;
  }

  /**
   * Datum der Erzeugung der Datei. Steht nach dem Einlesen einer Datei zur
   * Verf�gung.
   * 
   * @return Erzeugungsdatum
   */
  public Date getCreationDateTime()
  {
    return creationdatetime;
  }

  /**
   * Datum der Erzeugung der Datei. Wird beim Einlesen einer Datei genutzt.
   */
  void setCreationDateTime(Date creationdatetime)
  {
    this.creationdatetime = creationdatetime;
  }

  /**
   * Gew�nschtes Ausf�hrungsdatum.
   * 
   * @return ausf�hrungsdatum
   */
  public Date getAusfuehrungsdatum()
  {
    return dateofexecution;
  }

  /**
   * Gew�nschtes Ausf�hrungsdatum.
   */
  public void setAusfuehrungsdatum(Date dateofexecution)
  {
    this.dateofexecution = dateofexecution;
  }

  /**
   * Gibt die Zahler nach dem Einlesen zur�ck.
   */
  public ArrayList<Empfaenger> getZahler()
  {
    return empfaengerarray;
  }

  // public static void main(String[] args) throws
  // DatatypeConfigurationException,
  // JAXBException
  // {
  // try
  // {
  // Basislastschrift bl = new Basislastschrift();
  // bl.setKomprimiert(true);
  // bl.setMessageID("123"); // Z. B. Buchungslaufnummer
  // bl.setBIC("WELADED1WDB");
  // Calendar cal = Calendar.getInstance();
  // cal.set(Calendar.YEAR, 2013);
  // cal.set(Calendar.MONTH, 04);
  // cal.set(Calendar.DAY_OF_MONTH, 15);
  // bl.setFaelligskeitsdatum(cal.getTime());
  // bl.setIBAN("DE61478535200001861889");
  // bl.setName("Fa. SEPA-Empf�nger GmbH und Co. Testenhausen");
  // bl.setGlaeubigerID("DE98ZZZ09999999999");
  //
  // Zahler z1 = new Zahler();
  // z1.setBetrag(new BigDecimal("100.00"));
  // z1.setBic("DORTDE33XXX");
  // z1.setIban("DE15440501990001052500");
  // cal.set(Calendar.MONTH, 1);
  // cal.set(Calendar.DAY_OF_MONTH, 22);
  // z1.setMandatdatum(cal.getTime());
  // z1.setMandatid("4711");
  // z1.setName("Meier und Co.");
  // z1.setVerwendungszweck("Beitrag 2013");
  // bl.add(z1);

  // Zahler z11 = new Zahler();
  // z11.setBetrag(new BigDecimal("100.00"));
  // z11.setBic("DORTDE33XXX");
  // z11.setIban("DE15440501990001052500");
  // cal.set(Calendar.MONTH, 1);
  // cal.set(Calendar.DAY_OF_MONTH, 22);
  // z11.setMandatdatum(cal.getTime());
  // z11.setMandatid("4711");
  // z11.setName("Meier und Co.");
  // z11.setVerwendungszweck("Zusatzbetrag 2013");
  // bl.add(z11);

  // Zahler z2 = new Zahler();
  // z2.setBetrag(new BigDecimal("50.00"));
  // z2.setBic("WELADED1HER");
  // z2.setIban("DE36450514850000000034");
  // cal.set(Calendar.YEAR, 2001);
  // z2.setMandatdatum(cal.getTime());
  // z2.setMandatid("0815");
  // z2.setName("Fritz Mueller");
  // z2.setVerwendungszweck("Beitrag 2013");
  // bl.add(z2);
  //
  // bl.write(new File("C:/tmp/test.xml"));

  // bl = new Basislastschrift();
  // bl.read(new File("test.xml"));
  // ArrayList<Zahler> zahlerarray = bl.getZahler();
  // System.out.println(zahlerarray.size());
  // for (Zahler z : zahlerarray)
  // {
  // System.out.println(z);
  // }
  // System.out.println(bl.getMessageID());
  // System.out.println(bl.getName());
  // System.out.println(bl.getIBAN());
  // System.out.println(bl.getBIC());
  // System.out.println(bl.getAnzahlBuchungen());
  // System.out.println(bl.getKontrollsumme());
  // System.out.println(bl.getCreationDateTime());
  // System.out.println(bl.getFaelligkeitsdatum());
  // System.out.println(bl.getGlaeubigerID());
  // }
  // catch (SEPAException e)
  // {
  // e.printStackTrace();
  // }
  // }
}
