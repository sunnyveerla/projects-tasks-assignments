package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.UsersTmpRepository;
import ctg.model.Users;

@Service
@Transactional
public class UsersService {
	private final UsersTmpRepository usersRepository;
	
	public UsersService(UsersTmpRepository usersRepository){
		this.usersRepository = usersRepository;
	}
	public List<Users> findAll(){
		List<Users> users = new ArrayList<>();
		for(Users user:usersRepository.findAll()){
			users.add(user);
		}
		return users;
	}
	public Users findById(int id){
		return usersRepository.findOne(id);
		
	}
	
	public void save(Users user){
		usersRepository.save(user);
	}
	public int validUser(String userName, String password){
		System.out.println("Login Checking...");
			String userId = usersRepository.validUser(userName, password);
			if(userId !=null){
				return Integer.parseInt(userId);
			}
		return 0;
	}
}
