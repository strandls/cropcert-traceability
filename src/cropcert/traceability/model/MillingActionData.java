package cropcert.traceability.model;

import java.sql.Timestamp;

public class MillingActionData {

	private Long id;
	private Float weightArrivingFactory;
	private Float weightLeavingFactory;
	private Float mcArrivingFactory;
	private Float mcLeavingFactory;
	private Timestamp millingTime;
	private Boolean finalizeMillingStatus;
	private Long unionCode;

	public MillingActionData() {
		super();
	}

	public MillingActionData(Long id, Float weightArrivingFactory, Float weightLeavingFactory, Float mcArrivingFactory,
			Float mcLeavingFactory, Timestamp millingTime, Timestamp dispatchTime, Boolean finalizeMillingStatus,
			Long unionCode) {
		super();
		this.id = id;
		this.weightArrivingFactory = weightArrivingFactory;
		this.weightLeavingFactory = weightLeavingFactory;
		this.mcArrivingFactory = mcArrivingFactory;
		this.mcLeavingFactory = mcLeavingFactory;
		this.millingTime = millingTime;
		this.finalizeMillingStatus = finalizeMillingStatus;
		this.unionCode = unionCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getWeightArrivingFactory() {
		return weightArrivingFactory;
	}

	public void setWeightArrivingFactory(Float weightArrivingFactory) {
		this.weightArrivingFactory = weightArrivingFactory;
	}

	public Float getWeightLeavingFactory() {
		return weightLeavingFactory;
	}

	public void setWeightLeavingFactory(Float weightLeavingFactory) {
		this.weightLeavingFactory = weightLeavingFactory;
	}

	public Float getMcArrivingFactory() {
		return mcArrivingFactory;
	}

	public void setMcArrivingFactory(Float mcArrivingFactory) {
		this.mcArrivingFactory = mcArrivingFactory;
	}

	public Float getMcLeavingFactory() {
		return mcLeavingFactory;
	}

	public void setMcLeavingFactory(Float mcLeavingFactory) {
		this.mcLeavingFactory = mcLeavingFactory;
	}

	public Timestamp getMillingTime() {
		return millingTime;
	}

	public void setMillingTime(Timestamp millingTime) {
		this.millingTime = millingTime;
	}

	public Boolean getFinalizeMillingStatus() {
		return finalizeMillingStatus;
	}

	public void setFinalizeMillingStatus(Boolean finalizeMillingStatus) {
		this.finalizeMillingStatus = finalizeMillingStatus;
	}

	public Long getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}

}
