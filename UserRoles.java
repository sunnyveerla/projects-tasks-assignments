package ctg.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserRoles implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "usersId", insertable = false, updatable = false)
	private int usersId;
	private long rolesId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsers_id() {
		return usersId;
	}

	public void setUsers_id(int users_id) {
		this.usersId = users_id;
	}

	public long getRoles_id() {
		return rolesId;
	}

	public void setRoles_id(long roles_id) {
		this.rolesId = roles_id;
	}

	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="usersId", referencedColumnName="id")
	private Users users;
	
}
