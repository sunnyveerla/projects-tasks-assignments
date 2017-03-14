package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.RoleRepository;
import ctg.model.Roles;

@Service
@Transactional
public class RoleService {
	private final RoleRepository roleRepository;
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	public List<Roles> findAll(){
		List<Roles> roles = new ArrayList<>();
		for (Roles role : roleRepository.findAll()) {
			roles.add(role);
		}
		return roles;
	}
}
