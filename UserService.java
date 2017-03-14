package ctg.service;

import ctg.model.Users;

public interface UserService {	
    void save(Users user);

    Users findByUsername(String username);
}
