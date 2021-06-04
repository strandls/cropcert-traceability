package cropcert.traceability.model;

import java.sql.Timestamp;

public class CoopActionData {

	private Long id;
	private Float weightLeavingCooperative;
	private Float mcLeavingCooperative;
	private Timestamp timeToFactory;
	private Boolean finalizeCoopStatus;

	public CoopActionData() {
		super();
	}

	public CoopActionData(Long id, Float weightLeavingCooperative, Float mcLeavingCooperative, Timestamp timeToFactory,
			Boolean finalizeCoopStatus) {
		super();
		this.id = id;
		this.weightLeavingCooperative = weightLeavingCooperative;
		this.mcLeavingCooperative = mcLeavingCooperative;
		this.timeToFactory = timeToFactory;
		this.finalizeCoopStatus = finalizeCoopStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getWeightLeavingCooperative() {
		return weightLeavingCooperative;
	}

	public void setWeightLeavingCooperative(Float weightLeavingCooperative) {
		this.weightLeavingCooperative = weightLeavingCooperative;
	}

	public Float getMcLeavingCooperative() {
		return mcLeavingCooperative;
	}

	public void setMcLeavingCooperative(Float mcLeavingCooperative) {
		this.mcLeavingCooperative = mcLeavingCooperative;
	}

	public Timestamp getTimeToFactory() {
		return timeToFactory;
	}

	public void setTimeToFactory(Timestamp timeToFactory) {
		this.timeToFactory = timeToFactory;
	}

	public Boolean getFinalizeCoopStatus() {
		return finalizeCoopStatus;
	}

	public void setFinalizeCoopStatus(Boolean finalizeCoopStatus) {
		this.finalizeCoopStatus = finalizeCoopStatus;
	}

}
