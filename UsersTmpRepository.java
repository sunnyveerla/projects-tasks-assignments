package ctg.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ctg.model.Users;

public interface UsersTmpRepository extends CrudRepository<Users, Integer> {
	@Query(value = "SELECT ID FROM users WHERE USER_NAME=?1 AND USER_PASSWORD=?2", nativeQuery = true)
	String validUser(String userName, String password);
}
