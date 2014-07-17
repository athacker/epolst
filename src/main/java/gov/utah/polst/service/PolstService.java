package gov.utah.polst.service;

import gov.utah.polst.model.Polst;

import java.util.Collection;




public interface PolstService {
	Collection<Polst>getPolstForPatient(Long patientId);
}
