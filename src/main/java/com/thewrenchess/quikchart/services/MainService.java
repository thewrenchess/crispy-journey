package com.thewrenchess.quikchart.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thewrenchess.quikchart.models.Graph;
import com.thewrenchess.quikchart.models.PValue;
import com.thewrenchess.quikchart.models.User;
import com.thewrenchess.quikchart.repositories.GraphRepository;
import com.thewrenchess.quikchart.repositories.PValueRepository;
import com.thewrenchess.quikchart.repositories.RoleRepository;
import com.thewrenchess.quikchart.repositories.UserRepository;

@Service
public class MainService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private GraphRepository graphRepository;
	private PValueRepository pvalueRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public MainService(	UserRepository userRepository,
						RoleRepository roleRepository,
						GraphRepository graphRepository,
						PValueRepository pvalueRepository,
						BCryptPasswordEncoder bCryptPasswordEncoder	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.graphRepository = graphRepository;
		this.pvalueRepository = pvalueRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public User saveUserAsUser(User user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(this.roleRepository.findByName("ROLE_USER"));
		try {
			user = this.userRepository.save(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	public User saveUserAsAdmin(User user) {
		user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(this.roleRepository.findByName("ROLE_ADMIN"));
		try {
			user = this.userRepository.save(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
	
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public List<User> getAllAdmins() {
		return this.userRepository.getAllAdmins();
	}
	
	public Graph saveGraph(Graph graph, User user) {
		graph.setUser(user);
		try {
			graph = this.graphRepository.save(graph);
			return graph;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Graph findGraphById(long id) {
		return this.graphRepository.findOne(id);
	}
	
	public Graph updateGraph(Graph graph) {
		try {
			graph = this.graphRepository.save(graph);
			return graph;
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Graph> last5Graphs(long uid) {
		List<Graph> graphs = this.graphRepository.getAllGraphByUserId(uid);
		if (graphs.size() <= 5) {
			return graphs;
		}
		return graphs.subList(0, 5);
	}
	
	public List<PValue> findPValuesByGraphId(long gid) {
		return this.pvalueRepository.findPValuesByGraphId(gid);
	}
	
	public PValue savePValue(PValue vals) {
		try {
			vals = this.pvalueRepository.save(vals);
			return vals;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Graph getGraphByPValueId(long pid) {
		return this.graphRepository.getGraphByPValueId(pid);
	}
	
	public void deletePValue(long pid) {
		this.pvalueRepository.delete(pid);
	}
}
