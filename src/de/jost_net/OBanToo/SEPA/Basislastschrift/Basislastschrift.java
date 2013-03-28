package de.jost_net.OBanToo.SEPA.Basislastschrift;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXB;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
   * Fälligkeitsdatum
   */
  private Date faelligkeitsdatum;

  /**
   * Array von Zahlern
   */
  private ArrayList<Zahler> zahlerarray = new ArrayList<Zahler>();

  /**
   * Kontrollsumme
   */
  private BigDecimal kontrollsumme = new BigDecimal(0);

  public Basislastschrift()
  {
  }

  public void add(Zahler zahler)
  {
    zahlerarray.add(zahler);
  }

  public void create(String filename) throws DatatypeConfigurationException,
      SEPAException
  {
    Document doc = new Document();
    doc.setCstmrDrctDbtInitn(getCustumerDirectDebitInitiationV02());

    JAXB.marshal(doc, new File(filename));
    JAXB.marshal(doc, System.out);
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
    cddi.getPmtInf().add(getPaymentInstructionInformationSDD());
    return cddi;
  }

  private GroupHeaderSDD getGroupHeader()
      throws DatatypeConfigurationException, SEPAException
  {
    GroupHeaderSDD grH = new GroupHeaderSDD();
    // aktuelles Datum und Uhrzeit
    XMLGregorianCalendar creDtTm = DatatypeFactory.newInstance().newXMLGregorianCalendar(
        new GregorianCalendar());
    grH.setCreDtTm(creDtTm);
    // Kontrollsumme
    grH.setCtrlSum(kontrollsumme);

    PartyIdentificationSEPA1 partyid1 = new PartyIdentificationSEPA1();
    partyid1.setNm(getName());

    grH.setInitgPty(partyid1);
    grH.setMsgId(getMessageID());
    grH.setNbOfTxs(zahlerarray.size() + "");
    return grH;
  }

  private PaymentInstructionInformationSDD getPaymentInstructionInformationSDD()
      throws SEPAException, DatatypeConfigurationException
  {
    PaymentInstructionInformationSDD pii = new PaymentInstructionInformationSDD();
    pii.setBtchBookg(true); // true=Sammelbuchung, false=Einzelbuchung

    PartyIdentificationSEPA5 pi5 = new PartyIdentificationSEPA5();
    pi5.setNm(getName());
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

    PartyIdentificationSEPA3 pi3 = new PartyIdentificationSEPA3();
    pi3.setId(p2);
    pii.setCdtrSchmeId(pi3);

    pii.setChrgBr(ChargeBearerTypeSEPACode.SLEV);
    pii.setCtrlSum(kontrollsumme); // Transaktionen
    pii.setNbOfTxs(zahlerarray.size() + "");
    pii.setPmtInfId(getMessageID());
    pii.setPmtMtd(PaymentMethod2Code.DD); // Direct Debit
    pii.setPmtTpInf(getPaymentTypeInformationSDD());

    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(getFaelligkeitsdatum());
    XMLGregorianCalendar faelligkeitsdatum = DatatypeFactory.newInstance().newXMLGregorianCalendar(
        gc);
    pii.setReqdColltnDt(faelligkeitsdatum);

    for (Zahler z : zahlerarray)
    {
      pii.getDrctDbtTxInf().add(getDirectDebitTransactionInformationSDD(z));
    }
    return pii;
  }

  private DirectDebitTransactionInformationSDD getDirectDebitTransactionInformationSDD(
      Zahler z) throws DatatypeConfigurationException
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

    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(z.getMandatdatum());// Datum des Mandats.
    XMLGregorianCalendar dtofsigntr = DatatypeFactory.newInstance().newXMLGregorianCalendar(
        gc);
    mri.setDtOfSgntr(dtofsigntr);
    mri.setAmdmntInd(false);

    ddt.setMndtRltdInf(mri);
    ddti.setDrctDbtTx(ddt);

    BranchAndFinancialInstitutionIdentificationSEPA1 bafiis = new BranchAndFinancialInstitutionIdentificationSEPA1();
    FinancialInstitutionIdentificationSEPA1 fii = new FinancialInstitutionIdentificationSEPA1();
    fii.setBIC(z.getBic());
    bafiis.setFinInstnId(fii);

    ddti.setDbtrAgt(bafiis);

    PartyIdentificationSEPA2 pi2 = new PartyIdentificationSEPA2();
    pi2.setNm(z.getName());
    ddti.setDbtr(pi2);

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

  private PaymentTypeInformationSDD getPaymentTypeInformationSDD()
  {
    PaymentTypeInformationSDD pti = new PaymentTypeInformationSDD();
    ServiceLevelSEPA sls = new ServiceLevelSEPA();
    sls.setCd(ServiceLevelSEPACode.SEPA);
    pti.setSvcLvl(sls);
    LocalInstrumentSEPA lis = new LocalInstrumentSEPA();
    lis.setCd(LocalInstrumentSEPACode.CORE);
    pti.setLclInstrm(lis);
    pti.setSeqTp(SequenceType1Code.OOFF);
    return pti;
  }

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

  public void setBIC(String bic) throws SEPAException
  {
    if (bic == null || bic.length() == 0 || bic.length() > 11)
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

  public void setIBAN(String iban) throws SEPAException
  {
    if (iban == null || iban.length() == 0 || iban.length() > 35)
    {
      throw new SEPAException("IBAN nicht korrekt gefüllt");
    }
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

  public void setFaelligskeitsdatum(Date faelligkeitsdatum)
  {
    this.faelligkeitsdatum = faelligkeitsdatum;
  }

  public Date getFaelligkeitsdatum() throws SEPAException
  {
    if (faelligkeitsdatum == null)
    {
      throw new SEPAException("Fälligkeitsdatum ist nicht gefüllt");
    }
    return faelligkeitsdatum;
  }

  public static void main(String[] args) throws DatatypeConfigurationException
  {
    try
    {
      Basislastschrift bl = new Basislastschrift();
      bl.setMessageID("123"); // Z. B. Buchungslaufnummer
      bl.setBIC("WELADED1WDB");
      bl.setFaelligskeitsdatum(new Date());
      bl.setIBAN("DE61478535200001861889");
      bl.setName("Fa. SEPA GmbH und Co. Testenhausen");
      bl.setGlaeubigerID("DE98ZZZ0912345678");

      Zahler z1 = new Zahler();
      z1.setBetrag(new BigDecimal("100.00"));
      z1.setBic("DORTDE33XXX");
      z1.setIban("DE15440501990001052500");
      z1.setMandatdatum(new Date());
      z1.setMandatid("4711");
      z1.setName("Meier und Co.");
      z1.setVerwendungszweck("Beitrag 2013");
      bl.add(z1);

      Zahler z2 = new Zahler();
      z2.setBetrag(new BigDecimal("200.00"));
      z2.setBic("WELADED1HER");
      z2.setIban("DE36450514850000000034");
      z2.setMandatdatum(new Date());
      z2.setMandatid("0815");
      z2.setName("Fritz Müller");
      z2.setVerwendungszweck("Beitrag 2013");
      bl.add(z2);

      bl.create("test.xml");
    }
    catch (SEPAException e)
    {
      e.printStackTrace();
    }
  }
}
