package mobi.inspiring.amaze.core.model;

import java.io.Serializable;

import javax.persistence.*;

import com.google.common.base.Strings;


/**
 * The persistent class for the beacon database table.
 * 
 */
@Entity
@NamedQuery(name="Beacon.findAll", query="SELECT b FROM Beacon b")
public class Beacon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_beacon")
	private int idBeacon;

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_establishment")
	private Establishment establishment;
	
	private String major;

	private String minor;

	private String uuid;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_beacon_details")
	private BeaconDetails beaconDetails;
	
	public Beacon (){}
	
	public Beacon(String uuid, String major, String minor) {
		if (Strings.isNullOrEmpty(uuid) || Strings.isNullOrEmpty(major) || Strings.isNullOrEmpty(minor)) {
			return;
		}
		this.uuid = uuid;
		this.major = major;
		this.minor = minor;
	}
	
	public int getIdBeacon() {
		return idBeacon;
	}
	public void setIdBeacon(int idBeacon) {
		this.idBeacon = idBeacon;
	}

	public Establishment getEstablishment() {
		return establishment;
	}
	
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BeaconDetails getBeaconDetails() {
		return beaconDetails;
	}

	public void setBeaconDetails(BeaconDetails beaconDetails) {
		this.beaconDetails = beaconDetails;
	}

}