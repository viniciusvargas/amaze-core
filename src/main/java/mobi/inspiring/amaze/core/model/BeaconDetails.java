package mobi.inspiring.amaze.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "beacon_details")
@NamedQuery(name="BeaconDetails.findAll", query="SELECT b FROM BeaconDetails b")
public class BeaconDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_beacon_details")
	private int idBeaconDetails;
	
	@Column(name="customized_action_url")
	private String customizedActionUrl;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="text")
	private String text;

	public int getIdBeaconDetails() {
		return idBeaconDetails;
	}

	public void setIdBeaconDetails(int idBeaconDetails) {
		this.idBeaconDetails = idBeaconDetails;
	}

	public String getCustomizedActionUrl() {
		return customizedActionUrl;
	}

	public void setCustomizedActionUrl(String customizedActionUrl) {
		this.customizedActionUrl = customizedActionUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
