package cropcert.traceability.model;

import java.sql.Timestamp;

public class GRNNumberData {

	private Long id;
	private String grnNumber;
	private Timestamp grnTimestamp;
	private Boolean finalizeGrnStatus;

	public GRNNumberData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GRNNumberData(Long id, String grnNumber, Timestamp grnTimestamp, Boolean finalizeGrnStatus) {
		super();
		this.id = id;
		this.grnNumber = grnNumber;
		this.grnTimestamp = grnTimestamp;
		this.finalizeGrnStatus = finalizeGrnStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGrnNumber() {
		return grnNumber;
	}

	public void setGrnNumber(String grnNumber) {
		this.grnNumber = grnNumber;
	}

	public Timestamp getGrnTimestamp() {
		return grnTimestamp;
	}

	public void setGrnTimestamp(Timestamp grnTimestamp) {
		this.grnTimestamp = grnTimestamp;
	}

	public Boolean getFinalizeGrnStatus() {
		return finalizeGrnStatus;
	}

	public void setFinalizeGrnStatus(Boolean finalizeGrnStatus) {
		this.finalizeGrnStatus = finalizeGrnStatus;
	}

}
