package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Person")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
		@NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
		@NamedQuery(name = "Person.findByFirstName", query = "SELECT p FROM Person p WHERE p.firstName = :firstName"),
		@NamedQuery(name = "Person.findByLastName", query = "SELECT p FROM Person p WHERE p.lastName = :lastName"),
		@NamedQuery(name = "Person.findByDateOfBirth", query = "SELECT p FROM Person p WHERE p.dateOfBirth = :dateOfBirth"),
		@NamedQuery(name = "Person.findBySex", query = "SELECT p FROM Person p WHERE p.sex = :sex"),
		@NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email"),
		@NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
		@NamedQuery(name = "Person.findByPlace", query = "SELECT p FROM Person p WHERE p.place = :place"),
		@NamedQuery(name = "Person.findByWebsite", query = "SELECT p FROM Person p WHERE p.website = :website"),
		@NamedQuery(name = "Person.findByOccupation", query = "SELECT p FROM Person p WHERE p.occupation = :occupation"),
		@NamedQuery(name = "Person.findByEmployment", query = "SELECT p FROM Person p WHERE p.employment = :employment"),
		@NamedQuery(name = "Person.findByPicture", query = "SELECT p FROM Person p WHERE p.picture = :picture") })
public class CFile implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	// @NotNull
	@Column(name = "file_id")
	private Integer id;
	@Column(name = "formattedDate")
	private String formattedDate;
	@Column(name = "image")
	private String image;
	@Column(name = "post")
	private String videos;
	@Column(name = "text")
    private String text;
	@Size(max = 255)
    @Column(name = "title")
    private String title;
	

	public String getTitle() {
		return title;
	}
	public void setTitle(String tle) {
		System.out.print("text to pora h "+ tle);		
		this.title = tle== null ? " " : tle;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	
	public void setText(String txt) {
		System.out.print("text to pora h "+ txt);			
		this.text = txt == null ? " " : txt;
	}
	public String getText() {
		return text;
	}


	public String getVideos() {
		return videos;
	}

	public void setVideos(String videos) {
		this.videos = videos;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setFormattedDate(String formattedDate) {
		this.formattedDate = formattedDate;
	}

	public String getFormattedDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		return formatter.format(new Date());
	}

}
