package ctg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Entries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String entryText;
	private int assignmentsId;
	@Column(name = "Entry_Date_Created", insertable = false, updatable = false)
	private Date entryDateCreated;
	@Column(name = "Entry_Date_Edited", insertable = false, updatable = false)
	private Date entryDateEdited;
	private int usersId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntryText() {
		return entryText;
	}

	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}

	public int getAssignmentsId() {
		return assignmentsId;
	}

	public void setAssignmentsId(int assignmentsId) {
		this.assignmentsId = assignmentsId;
	}

	public Date getEntryDateCreated() {
		return entryDateCreated;
	}

	public Date getEntryDateEdited() {
		return entryDateEdited;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usersId", insertable = false, updatable = false)
	private Users users;

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
