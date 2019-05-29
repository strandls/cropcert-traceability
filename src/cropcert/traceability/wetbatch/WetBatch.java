package cropcert.traceability.wetbatch;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cropcert.traceability.batch.Batch;

@Entity
@Table(name="wet_batch")
@XmlRootElement
@JsonIgnoreProperties
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "WET")
public class WetBatch extends Batch{

	/**
	 * 
	 */
	private static final long serialVersionUID = 479267874198226418L;

	@Column( name = "start_time")
	private Timestamp startTime;
	
	@Column( name = "fermentation_end_time")
	private Timestamp fermentationEndTime;
	
	@Column( name = "drying_end_time")
	private Timestamp dryingEndTime;
	
	@Column( name = "perchment_quantity")
	private float perchmentQuantity;

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getFermentationEndTime() {
		return fermentationEndTime;
	}

	public void setFermentationEndTime(Timestamp fermentationEndTime) {
		this.fermentationEndTime = fermentationEndTime;
	}

	public Timestamp getDryingEndTime() {
		return dryingEndTime;
	}

	public void setDryingEndTime(Timestamp dryingEndTime) {
		this.dryingEndTime = dryingEndTime;
	}

	public float getPerchmentQuantity() {
		return perchmentQuantity;
	}

	public void setPerchmentQuantity(float perchmentQuantity) {
		this.perchmentQuantity = perchmentQuantity;
	}
	
}
