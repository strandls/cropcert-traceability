package cropcert.traceability.lotproduction;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "lot_production")
@XmlRootElement
@JsonIgnoreProperties
public class LotProduction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1520798245020829559L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lot_production_id_generator")
	@SequenceGenerator(name = "lot_production_id_generator", sequenceName = "lot_production_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "lot_production_name")
	private String lotProductionName;
	
	@Column(name = "grn_number")
	private String GRNNumber;
	
	@Column(name = "quality_report")
	private String qualityReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFinalLotName() {
		return lotProductionName;
	}

	public void setFinalLotName(String lotProductionName) {
		this.lotProductionName = lotProductionName;
	}

	public String getGRNNumber() {
		return GRNNumber;
	}

	public void setGRNNumber(String gRNNumber) {
		GRNNumber = gRNNumber;
	}

	public String getQualityReport() {
		return qualityReport;
	}

	public void setQualityReport(String qualityReport) {
		this.qualityReport = qualityReport;
	}

	
}
