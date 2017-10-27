package com.thewrenchess.quikchart.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.thewrenchess.quikchart.models.Graph;
import com.thewrenchess.quikchart.models.HValue;
import com.thewrenchess.quikchart.models.PValue;
import com.thewrenchess.quikchart.models.PieValue;
import com.thewrenchess.quikchart.models.User;
import com.thewrenchess.quikchart.repositories.GraphRepository;
import com.thewrenchess.quikchart.repositories.HValueRepository;
import com.thewrenchess.quikchart.repositories.PValueRepository;
import com.thewrenchess.quikchart.repositories.PieValueRepository;
import com.thewrenchess.quikchart.repositories.RoleRepository;
import com.thewrenchess.quikchart.repositories.UserRepository;

@Service
public class MainService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private GraphRepository graphRepository;
	private PValueRepository pvalueRepository;
	private HValueRepository hvalueRepository;
	private PieValueRepository pieValueRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public MainService(	UserRepository userRepository,
						RoleRepository roleRepository,
						GraphRepository graphRepository,
						PValueRepository pvalueRepository,
						HValueRepository hvalueRepository,
						PieValueRepository pieValueRepository,
						BCryptPasswordEncoder bCryptPasswordEncoder	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.graphRepository = graphRepository;
		this.pvalueRepository = pvalueRepository;
		this.hvalueRepository = hvalueRepository;
		this.pieValueRepository = pieValueRepository;
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
	
	public List<HValue> findHValuesByGraphId(long gid) {
		return this.hvalueRepository.findHValuesByGraphId(gid);
	}
	
	public List<PieValue> findPieValuesByGraphId(long gid) {
		return this.pieValueRepository.findPieValuesByGraphId(gid);
	}
	
	public PValue savePValue(PValue vals) {
		try {
			vals = this.pvalueRepository.save(vals);
			return vals;
		} catch (Exception e) {
			return null;
		}
	}
	
	public HValue saveHValue(HValue vals) {
		try {
			vals = this.hvalueRepository.save(vals);
			return vals;
		} catch (Exception e) {
			return null;
		}
	}
	
	public PieValue savePieValue(PieValue vals) {
		try {
			vals = this.pieValueRepository.save(vals);
			return vals;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Graph getGraphByPValueId(long pid) {
		return this.graphRepository.getGraphByPValueId(pid);
	}
	
	public Graph getGraphByHvalueId(long hid) {
		return this.graphRepository.getGraphByHValueId(hid);
	}
	
	public Graph getGraphByPieValueId(long pid) {
		return this.graphRepository.getGraphByPieValueId(pid);
	}
	
	public void deletePValue(long pid) {
		try {
			this.pvalueRepository.delete(pid);
		} catch (Exception e) {}
	}
	
	public void deleteHValue(long hid) {
		try {
			this.hvalueRepository.delete(hid);;
		} catch (Exception e) {}
	}
	
	public void deletePieValue(long pid) {
		try {
			this.pieValueRepository.delete(pid);
		} catch (Exception e) {}
	}
	
	public void deleteGraph(long gid) {
		List<PValue> pvals = this.pvalueRepository.findPValuesByGraphId(gid);
		if (pvals.size() > 0) {
			for (int i = 0; i < pvals.size(); i++) {
				try {
					this.pvalueRepository.delete(pvals.get(i));
				} catch (Exception e) {}
			}
		}
		List<HValue> hvals = this.hvalueRepository.findHValuesByGraphId(gid);
		if (hvals.size() > 0) {
			for (int i = 0; i < hvals.size(); i++) {
				try {
					this.hvalueRepository.delete(hvals.get(i));
				} catch (Exception e) {}
			}
		}
		List<PieValue> pievals = this.pieValueRepository.findPieValuesByGraphId(gid);
		if (pievals.size() > 0) {
			for (int i = 0; i < pievals.size(); i++) {
				try {
					this.pieValueRepository.delete(pievals.get(i));
				} catch (Exception e) {}
			}
		}
		try {
			this.graphRepository.delete(gid);
		} catch (Exception e) {}
	}
}
