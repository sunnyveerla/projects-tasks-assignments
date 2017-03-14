package ctg.dao;

import ctg.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> { 
	Users findByUserName(String username);
}
