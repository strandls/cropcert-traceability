package cropcert.traceability.activity;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "activity")
@XmlRootElement
@JsonIgnoreProperties
public class Activity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -363964821213180818L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_id_generator")
    @SequenceGenerator(name = "activity_id_generator", sequenceName = "activity_id_seq", allocationSize = 50)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@Column(name = "object_type")
	private String objectType;

    @Column(name = "object_id")
    private Long objectId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "timestamp")
    private Timestamp timestamp;
    
    @Column(name = "activity_type")
    private String activityType;
    
    @Column(name = "activity_value")
    private String activityValue;

    @Column(name = "note")
    private String note;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    public Activity() {
    	
    }
    
    public Activity(String objectType, Long objectId, String userId, Timestamp timeStamp, 
    		String activityType, String activityValue) {
    	this.objectType = objectType;
    	this.objectId   = objectId;
    	this.userId     = userId;
    	this.timestamp  = timeStamp;
    	this.activityType  = activityType;
    	this.activityValue = activityValue;
    	this.note = "";
    	this.isDeleted = false;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getActivityType() {
		return activityType;
	}
	
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	public String getActivityValue() {
		return activityValue;
	}
	
	public void setActivityValue(String activityValue) {
		this.activityValue = activityValue;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
