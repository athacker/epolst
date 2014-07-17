package gov.utah.polst.repository;

import gov.utah.polst.model.Patient;
import gov.utah.polst.model.Polst;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-Test.xml")
@Transactional
public class PolstRepositoryTest {

	@Autowired
	PolstRepository polstRepository;
	
	@Autowired
	PatientRepository patientRepository;
	
	 
	
	@Test
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class) 
	public void savePolst() throws Exception{
		
		ArrayList<Patient> tests = (ArrayList<Patient>)patientRepository.findAll();
		
		
		String sectionA = "Treatment options when the patient has no pulse and is not breathing";
		String sectionB = "Treatment options when the patient has a pulse and is breathing:";
		String sectionC = "Full treatment: Includes all care above plus";
		String sectionD = "Antibiotics:";
		String sectionE = "Artificially administered fluid and nutrition: (Comfort measures are always provided)";
		String sectionF = "Discussed with: ";
		String sectionG = "Brief summary of medical condition and brief explanation of treatment choice:";
		
		
		for (Patient pt: tests){
			Polst p = new Polst();
			p.setPatient(pt);
			p.setEffectiveDate(new Date());
			p.setActive(Boolean.TRUE);
			p.setSectionA(sectionA);
			p.setSectionB(sectionB);
			p.setSectionC(sectionC);
			p.setSectionD(sectionD);
			p.setSectionE(sectionE);
			p.setSectionF(sectionF);
			p.setSectionG(sectionG);
			polstRepository.save(p);
		}
		
		
		
	}
}