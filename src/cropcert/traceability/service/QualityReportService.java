package cropcert.traceability.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import cropcert.traceability.Constants;
import cropcert.traceability.dao.QualityReportDao;
import cropcert.traceability.model.Lot;
import cropcert.traceability.model.QualityReport;
import cropcert.traceability.util.ValidationException;

public class QualityReportService extends AbstractService<QualityReport> {

	@Inject
	private ObjectMapper objectMappper;

	@Inject
	private LotService lotService;

	@Inject
	public QualityReportService(QualityReportDao dao) {
		super(dao);
	}

	public QualityReport save(String jsonString)
			throws JsonParseException, JsonMappingException, IOException, JSONException, ValidationException {
		QualityReport qualityReport = objectMappper.readValue(jsonString, QualityReport.class);

		validationCheck(qualityReport);

		Long lotId = qualityReport.getLotId();
		qualityReport = save(qualityReport);
		Lot lot = lotService.findById(lotId);
		lot.setGreenAnalysisId(qualityReport.getId());
		lotService.update(lot);

		return qualityReport;
	}

	public QualityReport update(String jsonString) throws JsonParseException, JsonMappingException, IOException, ValidationException {
		QualityReport qualityReport = objectMappper.readValue(jsonString, QualityReport.class);
		validationCheck(qualityReport);
		qualityReport = update(qualityReport);
		return qualityReport;
	}

	private void validationCheck(QualityReport qualityReport) throws ValidationException {
		float grading = 100f;
		if (Constants.WET.equals(qualityReport.getCoffeeType())) {
			grading = qualityReport.getGradeAA() + qualityReport.getGradeA() + qualityReport.getGradeB()
					+ qualityReport.getGradeAB() + qualityReport.getGradeC() + qualityReport.getGradePB()
					+ qualityReport.getGradeTriage();
		} else if (Constants.DRY.equals(qualityReport.getCoffeeType())) {
			grading = 100;
		} else {
			throw new ValidationException("Invalid coffee type");
		}
		// Severe defects
		float defects = qualityReport.getFullBlack() + qualityReport.getFullSour() + qualityReport.getPods()
				+ qualityReport.getFungasDamaged() + qualityReport.getEm() + qualityReport.getSevereInsect();
		// Less severe defects
		defects += qualityReport.getPartialBlack() + qualityReport.getPartialSour() + qualityReport.getPatchment()
				+ qualityReport.getFloatersChalky() + qualityReport.getImmature() + qualityReport.getWithered()
				+ qualityReport.getShells() + qualityReport.getBrokenChipped() + qualityReport.getHusks()
				+ qualityReport.getPinHole();

		// Back end check for grading, defects should not be more than grading.
		if (defects > grading) {
			throw new ValidationException("Defects should not be more than grading");
		}
		// update the transfer time stamp
		Timestamp transferTimestamp = qualityReport.getTimestamp();
		if (transferTimestamp == null) {
			transferTimestamp = new Timestamp(new Date().getTime());
			qualityReport.setTimestamp(transferTimestamp);
		}
		Long lotId = qualityReport.getLotId();
		if (lotId == null) {
			throw new ValidationException("Missing lotId");
		}
	}
}
