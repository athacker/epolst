package gov.utah.epolst.reports;

import gov.utah.epolst.model.PatientBean;
import gov.utah.epolst.model.PolstAuthorization;
import gov.utah.epolst.model.PolstDiscussion;
import gov.utah.epolst.model.PolstReportBean;
import gov.utah.epolst.model.enums.PolstStatus;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class EPolstFormPDFView extends AbstractPdfView { 
	private static Logger LOG = LoggerFactory.getLogger(EPolstFormPDFView.class);
	
	private static Font checked = new Font(Font.HELVETICA, 12,  Font.BOLD);
	private static Font largeFont = new Font(Font.HELVETICA, 16,  Font.BOLD);
	private static Font subFont = new Font(Font.HELVETICA, 14, Font.BOLD);
	private static Font subFont12 = new Font(Font.HELVETICA, 12, Font.BOLD);
	private static Font normalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
	private static Font normalFontBold = new Font(Font.HELVETICA, 10, Font.BOLD);
	private static Font smallFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
	private static Font label = new Font(Font.HELVETICA, 10, Font.BOLD);
	
 	private static float indent=10f;
 	private static float padding=10f;
 	private static float paddingTopAlign=1f;
 	private static float paddingBtmAlign=30f;
 	
	private static String SUB_HEADING_INFO = "This is a physician order sheet based on patient wishes and medical indications for life-sustaining treatment. Place this order in a prominently visible part of the patient's record. Both the patient and the physician must sign this order (two physicians must sign if the patient is a minor child). When the patient's condition makes this order applicable, first follow this order, and then, if necessary, contact the signing physician.";
	private static String WARNING = "(IF NOTHING IN A SECTION IS CHECKED, CAREGIVERS SHOULD PROVIDE THE FULLEST TREATMENT DESCRIBED IN THAT SECTION UNLESS THAT TREATMENT DIRECTLY CONFLICTS WITH A TREATMENT CHECKED IN ANOTHER SECTION)";
	
	//sectionB Medical Treatment
	private static String B_COMFORT_MEASURES = "Oral and body hygiene; reasonable efforts to offer food and fluids	orally; medication, oxygen, positioning, warmth, and other measures to relieve pain and suffering. Provide privacy and respect for the dignity and humanity of the patient. Transfer to hospital only if comfort measures can no longer be effectively managed at current setting.";
	private static String B_LIMITED_MEASURES ="Includes care above. May also include suction, treatment of airway obstruction, bag/valve/mask ventilation, monitoring of cardiac rhythm, medications, IV fluids. Transfer to hospital if indicated, but no endotracheal intubation or long-term life support measures.";	
	private static String B_FULL_TREATMENT="Includes all care above plus endotracheal intubation, defibrillation/cardioversion,and any other life sustaining care required.";
	
	private static String F_TITLE ="Patient preferences to guide physician in ordering life-sustaining treatment Section F";
	
	private static String F_PARA_1="I have given significant thought to life-sustaining treatment. Please see the following for more information about my preferences:";
	private static String F_PARA_2="I have expressed my preferences to my physician or health care provider(s) and agree with the treatment order on this document. Please review these orders if there is a substantial permanent change in my health status, such as:";
	
	private static String SUMMARY="Brief summary of medical condition and brief explanation of treatment choice:";
	
	@Override
	protected Document newDocument() {
	  Document d = new Document(PageSize.LEGAL);
	  d.setMargins(2, 2, 30, 30);
	  d.setMarginMirroringTopBottom(true);
	  return d;
	}
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response)  throws DocumentException {
		
		//get data for report
		Map<String,Object> dataCollections = (Map<String,Object> ) model.get("formData");
		PolstReportBean polstReportBean =  (PolstReportBean)dataCollections.get("polst");
		PatientBean patientBean =  (PatientBean)dataCollections.get("patient");
		
		try{ 
		    buildHeader(document);
		    buildSubHeader(document, patientBean); 
		    warning(document);
		    if (PolstStatus.IN_ACTIVE.equals(polstReportBean.getPolstStatus() )){
		    	writer.setPageEvent(new Watermark());	
		    }
		    //codeStatus
		    sectionA(document, polstReportBean); 
		    //medicalCare
		    sectionB(document, polstReportBean); 
		    //antibiotics
		    sectionC(document, polstReportBean);  
		    //nutrition (feeding tube/iv) 
		    sectionD(document, polstReportBean); 
		    //discussion
		    sectionE(document, polstReportBean); 
		    sectionFTitle(document);
		    //advanced directive
		    sectionF(document, polstReportBean);
		    summary(document, polstReportBean);
		    signature(document,polstReportBean);
		    footer(document);
		    
		    		    
		    // to open the PDF in 100% zoom
		    writer.setOpenAction(PdfAction.gotoLocalPage(1, new PdfDestination( PdfDestination.XYZ, 0, 10000, 1), writer));
		}catch(DocumentException e){
			LOG.error("Exception was caught while preparing ePOLST report.", e);
		}
		    
	}
	
	
	//CODE STATUS
	private void sectionA(Document document, PolstReportBean bean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,5});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionA = new Paragraph("Section A", normalFont); 
	 	sectionA.setAlignment(Element.ALIGN_CENTER);
	 	sectionA.setLeading(indent);
	 	cell.addElement(sectionA);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
		
		
		
		//col 2 Code Status Info
		cell = new PdfPCell();
		cell.setPadding(padding);
		cell.addElement(new Phrase("Treatment options when the patient has no pulse and is not breathing:", label));
	 
		//ATTEMPT TO RESUSCITATE 
		Phrase p = new Phrase();
		if (!StringUtils.isEmpty(bean.getCodeStatusAttempt()  )){
	 		p.add(new Chunk(bean.getCodeStatusAttempt(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
		p.add(new Chunk(" Attempt to resuscitate", normalFont));
	 	cell.addElement(p);
	 	
	 	
	 	//DNR
	 	p = new Phrase();
	 	if (!StringUtils.isEmpty(bean.getCodeStatusDoNotAttempt()  )){
	 		p.add(new Chunk(bean.getCodeStatusDoNotAttempt(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	 
	 	p.add(new Chunk( " Do not attempt or continue any resuscitation (DNR)", normalFont) );
		cell.addElement(p);
	  	
		cell.addElement(new Phrase("Other instructions or clarification:", label));
	 	Paragraph codeStatusInstructions = new Paragraph(bean.getCodeStatusInstructions(), normalFont); 
		codeStatusInstructions.setIndentationLeft(indent);
		cell.addElement(codeStatusInstructions);	
		
		table.addCell(cell);
	    document.add( table );
		
	}
	
	//MEDICAL CARE
	private void sectionB(Document document, PolstReportBean bean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,5});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionB = new Paragraph("Section B", normalFont); 
	 	sectionB.setAlignment(Element.ALIGN_CENTER);
	 	sectionB.setLeading(indent);
	 	cell.addElement(sectionB);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
		
	 
		//col 2 //MEDICAL CARE
		cell = new PdfPCell();
		cell.setPadding(padding);
	 
		//COMFORT MEASURES
	    Phrase p = new Phrase();
	    
	    if (!StringUtils.isEmpty(bean.getMedCareComfort()  )){
	 		p.add(new Chunk(bean.getMedCareComfort(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk(" Comfort measures only: ", normalFontBold));
	    p.add(new Chunk(  B_COMFORT_MEASURES, normalFont)  );
		cell.addElement(p);
		
		//LIMITED MEASURES
	    p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getMedCareLimited()  )){
	 		p.add(new Chunk(bean.getMedCareLimited(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk(" Limited additional interventions: ", normalFontBold));
	    p.add(new Chunk( B_LIMITED_MEASURES, normalFont) );
		cell.addElement(p);
		
	   	cell.addElement(new Phrase("Other instructions or clarification:", label));
		
		//FULL MEASURES
	    p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getMedCareFull()  )){
	 		p.add(new Chunk(bean.getMedCareFull(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk(" Full treatment: ", normalFontBold));
	    p.add(new Chunk( B_FULL_TREATMENT, normalFont) );
		cell.addElement(p);
		
	    
		cell.addElement(new Phrase("If necessary, transfer to (hospital name): ______________________________", label));
		cell.addElement(new Phrase("Other instructions or clarification:", label));
		
	 	Paragraph medicalCareInstructions = new Paragraph(bean.getMedicalCareInstructions(), normalFont); 
	 	medicalCareInstructions.setIndentationLeft(indent);
	 	cell.addElement(medicalCareInstructions);	
		
		table.addCell(cell);
	   
	    document.add( table );
		
	}
	
	//ANTIBIOTICS
	private void sectionC(Document document, PolstReportBean bean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,5});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionC = new Paragraph("Section C", normalFont); 
	 	sectionC.setAlignment(Element.ALIGN_CENTER);
	 	sectionC.setLeading(indent);
	 	cell.addElement(sectionC);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
		
		
		
		//col 2
		cell = new PdfPCell();
		cell.setPadding(padding);
		
		cell.addElement(new Phrase("Antibiotics: (Comfort measures are always provided)",label));
		
		Phrase p = new Phrase();
		//NO -- Antibiotics
	    if (!StringUtils.isEmpty(bean.getAntibioticsNo()  )){
	 		p.add(new Chunk(bean.getAntibioticsNo(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    
	    p.add(new Chunk( " No antibiotics", normalFont) );
		cell.addElement(p);
		
		
		
		//YES -- Antibiotics
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getAntibioticsYes()  )){
	 		p.add(new Chunk(bean.getAntibioticsYes(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    
	    p.add(new Chunk( " Antibiotics may be administered", normalFont) );
		cell.addElement(p);
			 		
	
		cell.addElement(new Chunk("Other Instructions or clarification:", normalFontBold));
		Paragraph antibioticInstructions = new Paragraph(bean.getAntibioticInstructions(), normalFont ); 
	 	antibioticInstructions.setIndentationLeft(indent);
		cell.addElement(antibioticInstructions);
		table.addCell(cell);
	    document.add( table );
		
	}
	
	
	//NUTRITION
	private void sectionD(Document document, PolstReportBean bean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,2.5f,2.5f});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionD = new Paragraph("Section D", normalFont); 
	 	sectionD.setAlignment(Element.ALIGN_CENTER);
	 	sectionD.setLeading(indent);
	 	cell.addElement(sectionD);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
		
		
		
		//col 2
		Phrase p;
		cell = new PdfPCell();
		cell.setPadding(padding);
	
		cell.addElement( new Phrase("Artificially administered fluid and nutrition: ", normalFontBold ) );
		
		cell.addElement( new Phrase("Feeding Tube:", normalFontBold ));
		
		//NO -- Feeding Tube
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getFeedingTubeNo()  )){
	 		p.add(new Chunk(bean.getFeedingTubeNo(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " No feeding tube", smallFont) );
		cell.addElement(p);
		
		//DEFINED
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getFeedingTubeDefinedTrial() )){
	 		p.add(new Chunk(bean.getFeedingTubeDefinedTrial(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Defined trial period of feeding tube", smallFont) );
		cell.addElement(p);
		
		//LONG TERM
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getFeedingTubeLongTerm())){
	 		p.add(new Chunk(bean.getFeedingTubeLongTerm(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Long-term feeding tube", smallFont) );
		cell.addElement(p);		 

		cell.addElement( new Phrase("Other Instructions or Clarification:" , normalFontBold));
		cell.addElement( new Phrase( bean.getNutritionInstructions(), normalFont  ));
		table.addCell(cell);
	   

		
		
		
		//Col 3
		cell = new PdfPCell();
		cell.setPadding(padding);
	 	cell.addElement( new Phrase("(Comfort measures are always provided)", normalFontBold ));
		cell.addElement( new Phrase("IV Fluids:", normalFontBold ));
		
		//No IV
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getIvNo())){
	 		p.add(new Chunk(bean.getIvNo(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " No IV fluids", smallFont) );
		cell.addElement(p);		 
		
		//Defined IV
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getIvDefinedTrial())){
	 		p.add(new Chunk(bean.getIvDefinedTrial(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Defined trial period of IV fluids", smallFont) );
		cell.addElement(p);	
		
		//YES IV
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getIvLongTerm())){
	 		p.add(new Chunk(bean.getIvLongTerm(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " IV Fluids", smallFont) );
		cell.addElement(p);	
	 
		table.addCell(cell);
		
	  
	 
		document.add( table );
		
	}
	
	//DISCUSSIONS
	private void sectionE(Document document, PolstReportBean bean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,5});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionE = new Paragraph("Section E", normalFont); 
	 	sectionE.setAlignment(Element.ALIGN_CENTER);
	 	sectionE.setLeading(indent);
	 	cell.addElement(sectionE);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
		
		
		
		//col 2
		cell = new PdfPCell();
		Phrase p;
		cell.setPadding(padding);
		cell.addElement(new Phrase("Discussed with:", normalFontBold));
		
		
		//Discussed w/Patient 
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getDiscussedPatient())){
	 		p.add(new Chunk(bean.getDiscussedPatient(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Patient", smallFont) );
		cell.addElement(p);	
		
		
		//Discussed with Parent
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getDiscussedParent())){
	 		p.add(new Chunk(bean.getDiscussedParent(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( "Parent(s) of Minor Child", smallFont) );
		
	    /**
	     * DISCUSSION PARENT
	     */
	    if (null !=bean.getDiscussedParentInfo())  {
	    	for (PolstDiscussion pd: bean.getDiscussedParentInfo()){
	    		p.add(new Chunk("\n\t\t\t\t\t\t\t\t\t\t\t\t"+ pd.getDiscussorsRelationship()  + "\t\t\t" + pd.getDiscussorsName() + "\t\t\t" +  pd.getDiscussorsPhone() , smallFont));
	    	}
	    	cell.addElement(p);	
	    }
	 	
		
		//Discussed w/Surrogate
 		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getDiscussedSurrogate() )){
	 		p.add(new Chunk(bean.getDiscussedSurrogate(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Surrogate (source of legal authority, name, and phone number):", smallFont) );
	   
	    /**
	     * DISCUSSION SURROGATE INFORMATION.. 
	     */
	    if (null !=bean.getDiscussedSurrogateInfo())  {
	    	for (PolstDiscussion pd: bean.getDiscussedSurrogateInfo()){
	    		p.add(new Chunk("\n\t\t\t\t\t\t\t\t\t\t\t\t"+ pd.getDiscussorsRelationship()  + "\t\t\t" + pd.getDiscussorsName() + "\t\t\t" + pd.getDiscussorsPhone() , smallFont));
	    	}
	    	cell.addElement(p);	
	    }
	    
	    
	 	//Discussed Other
		p = new Phrase();
	    if (!StringUtils.isEmpty(bean.getDiscussedOther() )){
	 		p.add(new Chunk(bean.getDiscussedOther(), checked) );
	 	}else{
	 		p.add(new Chunk("__", normalFont) );
	 	}
	    p.add(new Chunk( " Other (name and phone number):", smallFont) );
	    //DISCUSSION OTHER INFORMATION.. 
	    if (null != bean.getDiscussedOtherInfo()){
		    for (PolstDiscussion pd: bean.getDiscussedOtherInfo()){
		    	 p.add(new Chunk("\n\t\t\t\t\t\t\t\t\t\t\t\t" + pd.getDiscussorsRelationship()+ "\t\t\t" + pd.getDiscussorsName() + "\t\t\t" +  pd.getDiscussorsPhone() , smallFont));
		     }
	    }    
		cell.addElement(p);	
	 	table.addCell(cell);
 
		
	    document.add( table );
		
	}
	
	//advanced directive
	private void sectionFTitle(Document document ) throws DocumentException{
		PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);
		cell.setBorder(0);
	 	Paragraph warning = new Paragraph(F_TITLE, normalFontBold); 
		warning.setAlignment(Element.ALIGN_CENTER);
		warning.setLeading(indent);
		cell.addElement(warning);
		table.addCell(cell);
		document.add( table );
	}
	//Advanced Directive
	private void sectionF(Document document, PolstReportBean polstReportBean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{1,5});
        PdfPCell cell = new PdfPCell();
        
        cell.setPadding(padding);
       
	 	Paragraph sectionF = new Paragraph("Section F", normalFont); 
	 	sectionF.setAlignment(Element.ALIGN_CENTER);
	 	sectionF.setLeading(indent);
	 	cell.addElement(sectionF);
		
	 	Paragraph checkOne = new Paragraph("Check One", normalFont); 
	 	checkOne.setAlignment(Element.ALIGN_CENTER);
	 	checkOne.setLeading(indent);
	 	
		cell.addElement(checkOne);
		table.addCell(cell);
	 	
		//col 2
		cell = new PdfPCell();
		cell.setPadding(padding);
		
		cell.addElement(new Phrase(F_PARA_1)); 
		StringBuffer sb = new StringBuffer("Advance Directive: ");
		sb.append(polstReportBean.getAdvancedDirectiveNo());
		sb.append("NO\t\t\t");
		sb.append(polstReportBean.getAdvancedDirectiveYes());
		sb.append("YES");
		cell.addElement(new Phrase(sb.toString()));
		cell.addElement(new Phrase("Other: "));
		cell.addElement(new Phrase(polstReportBean.getAdvancedDirectiveInfo()));
		cell.addElement(new Phrase(F_PARA_2));
		
		cell.addElement(new Phrase("Advanced progressive illness\t\t\t\tClose to death\t\t\t\t\t\tExtraordinary suffering ", smallFont) );
		cell.addElement(new Phrase("Permanently unconscious\t\t\t\t\t\t\tImproved condition\t\t\t\t\t\tSurgical procedures",smallFont));
	  
		table.addCell(cell);
		document.add( table );
		
	}
	
	
	private void summary(Document document,PolstReportBean polstBean) throws DocumentException{
	    PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);
        cell.addElement(new Phrase(SUMMARY,normalFont));
        cell.addElement(new Phrase(polstBean.getMedicalCondition(),normalFont));
        table.addCell(cell);
        document.add(table);
    }
	
	
	private void signature(Document document, PolstReportBean polstBean) throws DocumentException{
		PdfPTable table = new PdfPTable(new float[]{3,2,1});
        PdfPCell cell = new PdfPCell();
         
        
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
        cell.addElement(new Phrase("Signature of person preparing form (if not patient's physician)", smallFont));
        table.addCell(cell);
 
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
    	cell.addElement(new Phrase("Print name and phone number", smallFont));
    	cell.addElement(new Phrase( "\n\t" + polstBean.getSignaturePreparerName()   , smallFont  ));
        table.addCell(cell);
        
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingBottom(paddingBtmAlign);
        cell.setPaddingTop(paddingTopAlign);
    	cell.addElement(new Phrase("Date prepared:",smallFont));
    	cell.addElement(new Phrase( "\n\t" + polstBean.getSignaturePreparedDate()  , smallFont  ));
    	table.addCell(cell);
        
    	
    	
    	//ROW TWO
    	cell = new PdfPCell();
    	cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
    	cell.addElement(new Phrase("Signature of physician or other licensed practitioner", smallFont));
    	table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
        cell.addElement(new Phrase("Print name and license number", smallFont));
     	cell.addElement(new Phrase( "\n\t" + polstBean.getSignaturePhysicianName() + " " + polstBean.getSignaturePhysicianLicenseNumber()   , smallFont  ));
     	table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Date signed:",smallFont));
      	cell.addElement(new Phrase( "\n\t" + polstBean.getSignaturePhysicianDateCertified()  , smallFont  ));
      	table.addCell(cell); 
        
   
      	
    	//ROW THREE
    	cell = new PdfPCell();
    	cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
        cell.addElement(new Phrase("Signature of second physician or other licensed practitioner (required for minor patients only)", smallFont));
        
        table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Print name and license number", smallFont));
        table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Date signed:",smallFont));
      	table.addCell(cell); 
    	
      	
       //ROW FOUR
    	cell = new PdfPCell();
    	cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
        cell.addElement(new Phrase("Patient and/or Parent Signatures", smallFont));
        for (PolstAuthorization pa: polstBean.getSignatureParentAuthorizationInfo()){
        	cell.addElement( new Phrase(" \n____________________________________ ", smallFont));
         }
        table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Print name and phone number", smallFont));
        for (PolstAuthorization pa: polstBean.getSignatureParentAuthorizationInfo()){
          	cell.addElement(new Phrase( "\n\t" + pa.getAuthorizerName() + " " +   pa.getAuthorizerPhone(), smallFont  ));
         }
      	table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Date signed:",smallFont));
        for (PolstAuthorization pa: polstBean.getSignatureParentAuthorizationInfo()){
          	cell.addElement(new Phrase( "\n\t" +  formatDateToString( pa.getDateAuthorized() )  , smallFont  ));
         }
     
     
      	
      	table.addCell(cell); 
      	
       //ROW FIVE
    	cell = new PdfPCell();
    	cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
    	cell.addElement(new Phrase("Surrogate Signature(s)", smallFont));
    	 for (PolstAuthorization pa: polstBean.getSignatureSurrogateAuthorizationInfo()){
    			cell.addElement( new Phrase("\n____________________________________ ", smallFont));
          }
        table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Print name and phone number", smallFont));
        for (PolstAuthorization pa: polstBean.getSignatureSurrogateAuthorizationInfo()){
          	cell.addElement(new Phrase( "\n\t" + pa.getAuthorizerName() + " " +   pa.getAuthorizerPhone()    , smallFont  ));
         }
        table.addCell(cell);
          
        cell = new PdfPCell();
        cell.setPadding(padding);
        cell.setPaddingTop(paddingTopAlign);
        cell.setPaddingBottom(paddingBtmAlign);
      	cell.addElement(new Phrase("Date signed:",smallFont));
        for (PolstAuthorization pa: polstBean.getSignatureSurrogateAuthorizationInfo()){
          	cell.addElement(new Phrase( "\n\t" + formatDateToString( pa.getDateAuthorized() )  , smallFont  ));
         }
      	table.addCell(cell); 
      	
      	
    	
    	document.add(table);
	}
	
	
	
	
	
	private void footer(Document document) throws DocumentException{
		PdfPTable table = new PdfPTable(1);
		PdfPCell cell = new PdfPCell();
		Paragraph preface = new Paragraph("Review and Change to Life with Dignity Order", subFont); 
		preface.setAlignment(Element.ALIGN_CENTER);
		document.add( preface );	
		
		
		cell.setBorder(0);
		cell.addElement(new Phrase("Review this form whenever any of the following happen:" , normalFont)  );
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBorder(0);
		cell.addElement(new Phrase("1. The patient is transferred from one care setting to another;", normalFont));
		table.addCell(cell);	
		
		
		cell = new PdfPCell();
		cell.setBorder(0);
		cell.addElement(new Phrase("2. The patient's health status changes substantially and permanently; or", normalFont));
		table.addCell(cell);		
		
		cell = new PdfPCell();
		cell.setBorder(0);
		cell.addElement(new Phrase("3. The patient's treatment preferences change.", normalFont));
		table.addCell(cell);	
		
		
		cell = new PdfPCell();
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.addElement(new Phrase("If the patient or the patient's surrogate changes the treatment preferences in this order, complete a new form and place it in the patient's medical record. This form is valid for both adult and pediatric patients", normalFontBold));
		table.addCell(cell);
		
		cell = new PdfPCell();
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.addElement(new Phrase("A COPY OF THIS FORM MUST ACCOMPANY THE PATIENT WHEN TRANSFERRED OR DISCHARGED (INCLUDING TRANSFERS TO HOSPITAL EMERGENCY DEPARTMENTS)", normalFontBold));
		preface.setAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
		document.add(table);
		
	}
	
	private void warning(Document document) throws DocumentException{
		PdfPTable table = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setPadding(padding);
		cell.setBorder(0);
	 	Paragraph warning = new Paragraph(WARNING, smallFont); 
		warning.setAlignment(Element.ALIGN_CENTER);
		warning.setLeading(indent);
		cell.addElement(warning);
		table.addCell(cell);
		
		
	    document.add( table );
		
	}
	
	
	private void buildSubHeader(Document document, PatientBean bean) throws DocumentException{
		 	document.add(new Paragraph(" " ,subFont  ));
		 	 
		    PdfPTable table = new PdfPTable(2);
	        PdfPCell cell = new PdfPCell();
	        cell.setPadding(padding);
	    
	        //col1
	        Paragraph heading = new Paragraph(SUB_HEADING_INFO, normalFont);
	        heading.setAlignment(Element.ALIGN_JUSTIFIED);
	        cell.addElement(heading);
	        
	        cell.addElement(new Phrase("Physician Name", label));
	        Paragraph dr = new Paragraph(bean.getPhysicianFullName(), normalFont); 
		    dr.setIndentationLeft(indent);
		    cell.addElement( dr );
	    
	        cell.addElement(new Phrase("Physician Phone", label));
	        Paragraph drPhone = new Paragraph(bean.getPhysicianPhoneNumber(), normalFont); 
	        drPhone.setIndentationLeft(indent);
		    cell.addElement( drPhone );
		    
	        table.addCell(cell);
	        
	        //col2
	     
	        cell = new PdfPCell();
	        cell.setPadding(padding);
	       
		    //LAST NAME
	        cell.addElement(new Phrase("Last Name Of Patient", label));
	        Paragraph lastName = new Paragraph(bean.getLastName(), normalFont); 
	        lastName.setIndentationLeft(indent);
		    cell.addElement( lastName );
	        
	        
		    //FIRST NAME
	        Phrase p = new Phrase("First Name Middle Initial", label); 
	        cell.addElement(p);
	        Paragraph firstName = new Paragraph(bean.getFirstName() + " " + bean.getMiddleName(), normalFont); 
		    firstName.setIndentationLeft(indent);
		    cell.addElement(firstName );
	      
		 
	        //DATE OF BIRTH
	        cell.addElement(new Phrase("Date Of Birth", label));
	        Paragraph dob = new Paragraph(bean.getDob(), normalFont); 
	        dob.setIndentationLeft(indent);
		    cell.addElement( dob );
	        
	        //EFFECITVE DATE
	        cell.addElement(new Phrase("Effective Date of this Order", label));
	        Paragraph effDate = new Paragraph(bean.getPolstLastUpdatedDate(), normalFont); 
	        effDate.setIndentationLeft(indent);
		    cell.addElement( effDate );
	        table.addCell(cell);
	               
	        document.add(table);
	}
	
	
	
	private void buildHeader(Document document) throws DocumentException{
		 
	    Paragraph preface = new Paragraph("Utah Department of Health", subFont); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    preface = new Paragraph("Bureau of Health Facility Licensing, Certification and Resident Assessment", subFont12); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    document.add(new Paragraph("" ,subFont  ));
	    
	    preface = new Paragraph("Physician Order for Life Sustaining Treatment" ,largeFont ); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    preface = new Paragraph("Utah Life with Dignity Order", subFont ); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    preface = new Paragraph("Version 2.0 9/09", normalFont); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    document.add(new Paragraph("" ,subFont  ));
	    
	    preface = new Paragraph("State of Utah Rule R432-31" ,subFont ); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
	    
	    preface = new Paragraph("(http://health.utah.gov/hflcra/forms.php)", normalFont); 
	    preface.setAlignment(Element.ALIGN_CENTER);
	    document.add( preface );
 	    
 }
	
 	
 	

	private String formatDateToString(Date date) {
		String formattedDate = "";
		if (null != date) {
			DateFormat formatter = new SimpleDateFormat("M-dd-yyyy");
			formattedDate = formatter.format(date);
		}
		return formattedDate;
	}
	 
	
	
	 class Watermark extends PdfPageEventHelper {
		 
	        Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new  GrayColor(0.75f));
	 
	        public void onEndPage(PdfWriter writer, Document document) {
	            ColumnText.showTextAligned(writer.getDirectContentUnder(),
	                    Element.ALIGN_CENTER, new Phrase("IN-ACTIVE ePOLST FORM", FONT),
	                    297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
	        }
	    }
	
	
}
