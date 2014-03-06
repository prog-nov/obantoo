package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import de.jost_net.OBanToo.SEPA.IBAN;
import de.jost_net.OBanToo.SEPA.SEPAException;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.AccountIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.ActiveOrHistoricCurrencyAndAmountSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.ActiveOrHistoricCurrencyCodeEUR;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.BranchAndFinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.CashAccountSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.CashAccountSEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.ChargeBearerTypeSEPACode;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.CustomerDirectDebitInitiationV02;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.DirectDebitTransactionInformationSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.DirectDebitTransactionSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.Document;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.FinancialInstitutionIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.GroupHeaderSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.IdentificationSchemeNameSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.LocalInstrumentSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.LocalInstrumentSEPACode;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.MandateRelatedInformationSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PartyIdentificationSEPA1;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PartyIdentificationSEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PartyIdentificationSEPA3;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PartyIdentificationSEPA5;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PartySEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PaymentIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PaymentInstructionInformationSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PaymentMethod2Code;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PaymentTypeInformationSDD;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.PersonIdentificationSEPA2;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.RemittanceInformationSEPA1Choice;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.RestrictedPersonIdentificationSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.RestrictedPersonIdentificationSchemeNameSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.SequenceType1Code;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.ServiceLevelSEPA;
import de.jost_net.OBanToo.SEPA.Nachricht.pain_008_002_02.ServiceLevelSEPACode;
import de.jost_net.OBanToo.StringLatin.Zeichen;

/**
 * <h1>SEPA-Basislastschriften</h1> <h2>Dateien erstellen</h2>
 * <p>
 * Beispiel für die Erstellung einer SEPA-Basislastschrift-Datei: <code>
   Basislastschrift bl = new Basislastschrift();
   bl.setKomprimiert(true);
   bl.setMessageID("123"); // Z. B. Buchungslaufnummer
   bl.setBIC("WELADED1WDB");
   Calendar cal = Calendar.getInstance();
   cal.set(Calendar.YEAR, 2013);
   cal.set(Calendar.MONTH, 04);
   cal.set(Calendar.DAY_OF_MONTH, 15);
   bl.setFaelligskeitsdatum(cal.getTime());
   bl.setIBAN("DE61478535200001861889");
   bl.setName("Fa. SEPA GmbH und Co. Testenhausen");
   bl.setGlaeubigerID("DE98ZZZ09999999999");
   Zahler z1 = new Zahler();
   z1.setBetrag(new BigDecimal("100.00"));
   z1.setBic("DORTDE33XXX");
   z1.setIban("DE15440501990001052500");
   cal.set(Calendar.MONTH, 1);
   cal.set(Calendar.DAY_OF_MONTH, 22);
   z1.setMandatdatum(cal.getTime());
   z1.setMandatid("4711");
   z1.setName("Meier und Co.");
   z1.setVerwendungszweck("Beitrag 2013");
   bl.add(z1);
   Zahler z11 = new Zahler();
   z11.setBetrag(new BigDecimal("100.00"));
   z11.setBic("DORTDE33XXX");
   z11.setIban("DE15440501990001052500");
   cal.set(Calendar.MONTH, 1);
   cal.set(Calendar.DAY_OF_MONTH, 22);
   z11.setMandatdatum(cal.getTime());
   z11.setMandatid("4711");
   z11.setName("Meier und Co.");
   z11.setVerwendungszweck("Zusatzbetrag 2013");
   bl.add(z11);
   Zahler z2 = new Zahler();
   z2.setBetrag(new BigDecimal("50.00"));
   z2.setBic("WELADED1HER");
   z2.setIban("DE36450514850000000034");
   cal.set(Calendar.YEAR, 2001);
   z2.setMandatdatum(cal.getTime());
   z2.setMandatid("0815");
   z2.setName("Fritz Mueller");
   z2.setVerwendungszweck("Beitrag 2013");
   bl.add(z2);
   bl.write(new File("test.xml"));
 * </code>
 * </p>
 * <h2>Dateien einlesen</h2> <code>
    Basislastschrift bl = new Basislastschrift();
   bl.read(new File("test.xml"));
   // jetzt können über die get-Methoden alle Werte abgefragt werden
 * </code>
 * 
 * @author heiner
 * 
 */
public class Basislastschrift
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
   * Gläubiger ID
   */
  private String glaeubigerid = null;

  /**
   * Array von Zahlern
   */
  private ArrayList<Zahler> zahlerarray = new ArrayList<Zahler>();

  /**
   * Map der Zahler
   */
  private HashMap<String, Zahler> zahlermap = new HashMap<String, Zahler>();

  /**
   * Kontrollsumme
   */
  private BigDecimal kontrollsumme = new BigDecimal(0);

  /**
   * Komprimiert. Buchungen mit gleicher Mandat-ID werden zusammengefügt
   */
  private boolean komprimiert = false;

  /**
   * Anzahl Buchungen (read-only)
   */
  private String anzahlbuchungen;

  /**
   * Datum und Uhrzeit der Erzeugung der Datei
   */
  private Date creationdatetime;

  public Basislastschrift()
  {
  }

  /**
   * Für jede Buchung wird ein Zahler-Object übergeben
   */
  public void add(Zahler zahler) throws SEPAException
  {
    if (komprimiert)
    {
      Zahler z = zahlermap.get(zahler.getMandatid());
      if (z == null)
      {
        zahlermap.put(zahler.getMandatid(), zahler);
      }
      else
      {
        z.add(zahler);
      }
    }
    else
    {
      zahlerarray.add(zahler);
    }
  }

  /**
   * Schreibt die SEPA-Datei. Vorher sind alle Werte über die set-Methoden sowie
   * die add(Zahler)-Methode übergeben werden.
   */
  public void write(File file) throws DatatypeConfigurationException,
      SEPAException, JAXBException
  {
    if (komprimiert)
    {
      Iterator<Entry<String, Zahler>> es = zahlermap.entrySet().iterator();
      while (es.hasNext())
      {
        Zahler zahler = es.next().getValue();
        zahlerarray.add(zahler);
      }
    }
    Document doc = new Document();
    doc.setCstmrDrctDbtInitn(getCustumerDirectDebitInitiationV02());

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
        "urn:iso:std:iso:20022:tech:xsd:pain.008.002.02 pain.008.002.02.xsd");
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    // m.marshal(doc, System.out);
    m.marshal(doc, file);
  }

  /**
   * SEPA-Datei einlesen. Nach dem Methodenaufruf können die Werte über die
   * get-Methoden abgefragt werden.
   */
  public void read(File file) throws JAXBException, SEPAException
  {
    JAXBContext jc = JAXBContext.newInstance(Document.class);
    Unmarshaller u = jc.createUnmarshaller();
    Document doc = (Document) u.unmarshal(file);
    setMessageID(doc.getCstmrDrctDbtInitn().getGrpHdr().getMsgId());
    setName(doc.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtr().getNm());
    setIBAN(doc.getCstmrDrctDbtInitn().getPmtInf().get(0).getCdtrAcct().getId()
        .getIBAN());
    int anzahlbuchungen = 0;
    BigDecimal kontrollsumme = new BigDecimal(0);
    for (PaymentInstructionInformationSDD pii : doc.getCstmrDrctDbtInitn()
        .getPmtInf())
    {
      anzahlbuchungen += new Integer(pii.getNbOfTxs());
      setBIC(pii.getCdtrAgt().getFinInstnId().getBIC());
      kontrollsumme = kontrollsumme.add(pii.getCtrlSum());
      setCreationDateTime(doc.getCstmrDrctDbtInitn().getGrpHdr().getCreDtTm()
          .toGregorianCalendar().getTime());
      // setFaelligskeitsdatum(doc.getCstmrDrctDbtInitn().getPmtInf().get(0)
      // .getReqdColltnDt().toGregorianCalendar().getTime());

      // TODO
      setGlaeubigerID(pii.getCdtrSchmeId().getId().getPrvtId().getOthr()
          .getId());

      setGlaeubigerID(doc.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty()
          .getId().getPrvtId().getOthr().getId());
      System.out.println(pii.getCdtrSchmeId().getId().getPrvtId().getOthr()
          .getId());

      System.out.println(doc.getCstmrDrctDbtInitn().getGrpHdr().getInitgPty()
          .getId().getPrvtId().getOthr().getId());

      List<DirectDebitTransactionInformationSDD> liste = pii.getDrctDbtTxInf();
      for (DirectDebitTransactionInformationSDD ddti : liste)
      {
        Zahler z = new Zahler();
        z.setBetrag(ddti.getInstdAmt().getValue());
        z.setBic(ddti.getDbtrAgt().getFinInstnId().getBIC());
        z.setIban(ddti.getDbtrAcct().getId().getIBAN());
        z.setMandatdatum(ddti.getDrctDbtTx().getMndtRltdInf().getDtOfSgntr()
            .toGregorianCalendar().getTime());
        z.setMandatid(ddti.getPmtId().getEndToEndId());
        z.setName(ddti.getDbtr().getNm());
        z.setVerwendungszweck(ddti.getRmtInf().getUstrd());
        zahlerarray.add(z);
        zahlermap.put(z.getMandatid(), z);
      }
    }
    setAnzahlBuchungen(anzahlbuchungen + "");
    setKontrollsumme(kontrollsumme);
  }

  private CustomerDirectDebitInitiationV02 getCustumerDirectDebitInitiationV02()
      throws DatatypeConfigurationException, SEPAException
  {
    CustomerDirectDebitInitiationV02 cddi = new CustomerDirectDebitInitiationV02();
    for (Zahler z : zahlerarray)
    {
      kontrollsumme = kontrollsumme.add(z.getBetrag());
    }
    cddi.setGrpHdr(getGroupHeader());
    for (SequenceType1Code sequ : SequenceType1Code.values())
    {
      PaymentInstructionInformationSDD pmtinstinf = getPaymentInstructionInformationSDD(sequ);
      if (new Integer(pmtinstinf.getNbOfTxs()) > 0)
      {
        cddi.getPmtInf().add(pmtinstinf);
      }
    }
    return cddi;
  }

  private GroupHeaderSDD getGroupHeader()
      throws DatatypeConfigurationException, SEPAException
  {
    GroupHeaderSDD grH = new GroupHeaderSDD();
    // aktuelles Datum und Uhrzeit
    // XMLGregorianCalendar creDtTm = DatatypeFactory.newInstance()
    // .newXMLGregorianCalendar(new GregorianCalendar());
    creationdatetime = new Date();
    grH.setCreDtTm(getYYYMMDDHHMMSS(creationdatetime));
    // Kontrollsumme
    grH.setCtrlSum(kontrollsumme);

    PartyIdentificationSEPA1 partyid1 = new PartyIdentificationSEPA1();
    partyid1.setNm(Zeichen.convert(getName()));

    grH.setInitgPty(partyid1);
    grH.setMsgId(getMessageID());
    grH.setNbOfTxs(zahlerarray.size() + "");
    anzahlbuchungen = zahlerarray.size() + "";
    return grH;
  }

  private PaymentInstructionInformationSDD getPaymentInstructionInformationSDD(
      SequenceType1Code sequence) throws SEPAException,
      DatatypeConfigurationException
  {
    PaymentInstructionInformationSDD pii = new PaymentInstructionInformationSDD();
    pii.setBtchBookg(true); // true=Sammelbuchung, false=Einzelbuchung

    PartyIdentificationSEPA5 pi5 = new PartyIdentificationSEPA5();
    pi5.setNm(Zeichen.convert(getName()));
    pii.setCdtr(pi5);

    CashAccountSEPA1 ca1 = new CashAccountSEPA1();
    AccountIdentificationSEPA ai = new AccountIdentificationSEPA();
    ai.setIBAN(getIBAN());
    ca1.setId(ai);
    pii.setCdtrAcct(ca1);

    BranchAndFinancialInstitutionIdentificationSEPA1 bafii = new BranchAndFinancialInstitutionIdentificationSEPA1();
    FinancialInstitutionIdentificationSEPA1 fii = new FinancialInstitutionIdentificationSEPA1();
    fii.setBIC(getBIC());
    bafii.setFinInstnId(fii);
    pii.setCdtrAgt(bafii);

    PartySEPA2 p2 = new PartySEPA2();
    RestrictedPersonIdentificationSEPA rpi = new RestrictedPersonIdentificationSEPA();
    rpi.setId(getGlaeubigerID());
    RestrictedPersonIdentificationSchemeNameSEPA rpisn = new RestrictedPersonIdentificationSchemeNameSEPA();
    rpisn.setPrtry(IdentificationSchemeNameSEPA.SEPA);
    rpi.setSchmeNm(rpisn);
    PersonIdentificationSEPA2 pi2 = new PersonIdentificationSEPA2();
    pi2.setOthr(rpi);
    p2.setPrvtId(pi2);

    // PartyIdentificationSEPA3 pi3 = new PartyIdentificationSEPA3();
    // pi3.setId(p2);
    // pii.setCdtrSchmeId(pi3);

    pii.setChrgBr(ChargeBearerTypeSEPACode.SLEV);
    pii.setPmtInfId(getMessageID());
    pii.setPmtMtd(PaymentMethod2Code.DD); // Direct Debit
    pii.setPmtTpInf(getPaymentTypeInformationSDD(sequence));

    BigDecimal seqKontrollsumme = new BigDecimal(0);
    int seqAnzahl = 0;
    for (Zahler z : zahlerarray)
    {
      if (z.getMandatsequence().getCode().compareTo(sequence) == 0)
      {
        pii.setReqdColltnDt(getYYYMMDD(z.getFaelligkeit()));
        pii.getDrctDbtTxInf().add(getDirectDebitTransactionInformationSDD(z));
        seqKontrollsumme = seqKontrollsumme.add(z.getBetrag());
        seqAnzahl++;
      }
    }
    pii.setCtrlSum(seqKontrollsumme); // Transaktionen
    pii.setNbOfTxs(seqAnzahl + "");
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

  private DirectDebitTransactionInformationSDD getDirectDebitTransactionInformationSDD(
      Zahler z) throws DatatypeConfigurationException, SEPAException
  {
    DirectDebitTransactionInformationSDD ddti = new DirectDebitTransactionInformationSDD();
    PaymentIdentificationSEPA pis = new PaymentIdentificationSEPA();
    pis.setEndToEndId(z.getMandatid());
    ddti.setPmtId(pis);
    ActiveOrHistoricCurrencyAndAmountSEPA aohcaas = new ActiveOrHistoricCurrencyAndAmountSEPA();
    aohcaas.setCcy(ActiveOrHistoricCurrencyCodeEUR.EUR);
    aohcaas.setValue(z.getBetrag());
    ddti.setInstdAmt(aohcaas);

    DirectDebitTransactionSDD ddt = new DirectDebitTransactionSDD();
    MandateRelatedInformationSDD mri = new MandateRelatedInformationSDD();
    mri.setMndtId(z.getMandatid());
    mri.setDtOfSgntr(getYYYMMDD(z.getMandatdatum()));
    mri.setAmdmntInd(false);

    RestrictedPersonIdentificationSchemeNameSEPA rpisn = new RestrictedPersonIdentificationSchemeNameSEPA();
    rpisn.setPrtry(IdentificationSchemeNameSEPA.SEPA);
    RestrictedPersonIdentificationSEPA rpi = new RestrictedPersonIdentificationSEPA();
    rpi.setId(getGlaeubigerID());
    rpi.setSchmeNm(rpisn);
    PersonIdentificationSEPA2 pi2 = new PersonIdentificationSEPA2();
    pi2.setOthr(rpi);
    PartySEPA2 p2 = new PartySEPA2();
    p2.setPrvtId(pi2);

    PartyIdentificationSEPA3 pi3 = new PartyIdentificationSEPA3();

    pi3.setId(p2);
    ddt.setCdtrSchmeId(pi3);

    ddt.setMndtRltdInf(mri);
    ddti.setDrctDbtTx(ddt);

    BranchAndFinancialInstitutionIdentificationSEPA1 bafiis = new BranchAndFinancialInstitutionIdentificationSEPA1();
    FinancialInstitutionIdentificationSEPA1 fii = new FinancialInstitutionIdentificationSEPA1();
    fii.setBIC(z.getBic());
    bafiis.setFinInstnId(fii);

    ddti.setDbtrAgt(bafiis);

    PartyIdentificationSEPA2 pi22 = new PartyIdentificationSEPA2();
    pi22.setNm(Zeichen.convert(z.getName()));
    ddti.setDbtr(pi22);

    CashAccountSEPA2 ca2 = new CashAccountSEPA2();
    AccountIdentificationSEPA ais = new AccountIdentificationSEPA();
    ais.setIBAN(z.getIban());
    ca2.setId(ais);
    ddti.setDbtrAcct(ca2);

    RemittanceInformationSEPA1Choice ris = new RemittanceInformationSEPA1Choice();
    ris.setUstrd(z.getVerwendungszweck());
    ddti.setRmtInf(ris);
    return ddti;
  }

  private PaymentTypeInformationSDD getPaymentTypeInformationSDD(
      SequenceType1Code sequence)
  {
    PaymentTypeInformationSDD pti = new PaymentTypeInformationSDD();
    ServiceLevelSEPA sls = new ServiceLevelSEPA();
    sls.setCd(ServiceLevelSEPACode.SEPA);
    pti.setSvcLvl(sls);
    LocalInstrumentSEPA lis = new LocalInstrumentSEPA();
    lis.setCd(LocalInstrumentSEPACode.CORE);
    pti.setLclInstrm(lis);
    pti.setSeqTp(sequence);
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
      throw new SEPAException("Message-ID ist noch nicht gefüllt");
    }
    return messageID;
  }

  /**
   * BIC. Länge 8 oder 11 Stellen.
   */
  public void setBIC(String bic) throws SEPAException
  {
    if (bic == null || (bic.length() != 8 && bic.length() != 11))
    {
      throw new SEPAException("BIC nicht korrekt gefüllt");
    }
    this.bic = bic;
  }

  public String getBIC() throws SEPAException
  {
    if (bic == null)
    {
      throw new SEPAException("BIC ist noch nicht gefüllt");
    }
    return bic;
  }

  /**
   * IBAN. Länge in Abhängigkeit vom Land.
   * 
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
      throw new SEPAException("IBAN ist noch nicht gefüllt");
    }
    return iban;
  }

  /**
   * Name des Zahlungspflichtigen. Länge max. 70 Stellen.
   * 
   */
  public void setName(String name) throws SEPAException
  {
    if (name == null || name.length() == 0 || name.length() > 70)
    {
      throw new SEPAException(
          "Name des Zahlungsempfängers nicht korrekt gefüllt");
    }
    this.name = name;
  }

  public String getName() throws SEPAException
  {
    if (name == null)
    {
      throw new SEPAException(
          "Name des Zahlungsempfängers ist noch nicht gefüllt");
    }
    return name;
  }

  /**
   * Gläubiger-ID
   */
  public void setGlaeubigerID(String glaeubigerid) throws SEPAException
  {
    if (glaeubigerid == null || glaeubigerid.length() == 0)
    {
      throw new SEPAException("Gläubiger-ID nicht korrekt gefüllt");
    }
    this.glaeubigerid = glaeubigerid;
  }

  public String getGlaeubigerID() throws SEPAException
  {
    if (glaeubigerid == null)
    {
      throw new SEPAException("Gläubiger-ID ist noch nicht gefüllt");
    }
    return glaeubigerid;
  }

  /**
   * Komprimiert. Muss gesetzt werden, bevor der erste Zähler übergeben wird.
   * 
   * @param komprimiert
   *          true: Zahlungen mit gleicher Mandanten-ID werden zusammengefasst,
   *          false: keine Zusammenfassung.
   */
  public void setKomprimiert(boolean komprimiert) throws SEPAException
  {
    if (zahlerarray.size() > 0)
    {
      throw new SEPAException(
          "Komprimierung kann nicht gesetzt werden, wenn schon Zahlungen übergeben wurden.");
    }
    this.komprimiert = komprimiert;
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
   * Verfügung.
   * 
   * @return Kontrollsumme
   */
  public BigDecimal getKontrollsumme()
  {
    return kontrollsumme;
  }

  /**
   * Anzahl der Buchungen. Steht nach dem Einlesen einer Datei zur Verfügung.
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
   * Verfügung.
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
   * Gibt die Zahler nach dem Einlesen zurück.
   */
  public ArrayList<Zahler> getZahler()
  {
    return zahlerarray;
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
  // bl.setName("Fa. SEPA-Empfänger GmbH und Co. Testenhausen");
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
