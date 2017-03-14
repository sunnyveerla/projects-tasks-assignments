package ctg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ctg.dao.UsersRepository;
import ctg.model.Users;

@Service
public class UsersServiceImpl implements UserService {
    @Autowired
    private UsersRepository userRepository;   
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Users user) {
        user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
       // user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
