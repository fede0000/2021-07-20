package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Vertici, DefaultWeightedEdge> grafo;
	private Map<String, Vertici> VerticiIdMap;
	private List<Archi> Archilist;
	private List<Vertici> VerticiUser;
	
	public Model() {
		dao = new YelpDao();
		this.VerticiIdMap = new HashMap<>();
		this.Archilist = new ArrayList<>();
		this.VerticiUser = new ArrayList<>();
	}
	
	public void creaGrafo(int nRec, int anno) {
		
		this.grafo = new SimpleWeightedGraph<Vertici, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Vertici> VerticiUser = dao.getAllVertices(nRec);
		Graphs.addAllVertices(this.grafo, VerticiUser);
		
		for(Vertici v : VerticiUser) {
			this.VerticiIdMap.put(v.getUserid(), v);
		}
		
		Archilist = dao.getAllEdges(anno, VerticiIdMap);
		
		int peso = 1;
		
		
		for(Archi a1 : Archilist){
			for(Archi a2 : Archilist) {
				if(!a1.getUserid().equals(a2.getUserid())&&a1.getBusinessid().equals(a2.getBusinessid())) {
					if(this.grafo.containsEdge(this.VerticiIdMap.get(a1.getUserid()), this.VerticiIdMap.get(a2.getUserid()))) {
						
					}
						
					this.grafo.addEdge(this.VerticiIdMap.get(a1.getUserid()), this.VerticiIdMap.get(a2.getUserid()));
					this.grafo.setEdgeWeight(this.VerticiIdMap.get(a1.getUserid()), this.VerticiIdMap.get(a2.getUserid()), peso);
					
					
				}
			}
		}
	}
	
	
	public List<Vertici> TrovaPesoMaxTraVicini(String user) {
		
		Vertici v = this.VerticiIdMap.get(user);
		double max= 0;
		
		List<Vertici> result= new ArrayList<>();
		
		//prendo la lista di vertici vicini all'utente
		List<Vertici> adiacenti= new ArrayList <> (Graphs.neighborListOf(this.grafo, v));
		
		//ciclo sui vicini 
		for(Vertici v1 : adiacenti) {
			
			//prendo l'arco tra lo user selezionato e uno dei vertici adiacenti ad uno ad uno
			DefaultWeightedEdge e = this.grafo.getEdge(v, v1);
			
			//trovo il peso max
			if(this.grafo.getEdgeWeight(e)>max) {
				max=this.grafo.getEdgeWeight(e);
			}
		}
		
		
		for(Vertici v1 : adiacenti) {
			DefaultWeightedEdge e = this.grafo.getEdge(v, v1);
			
			//se l'arco ha peso uguale al peso massimo lo aggiungo ai risultati
			if(this.grafo.getEdgeWeight(e)==max) {
				result.add(v1);
			}
			
			}

	        return result;
	}
	
	
	
	public int getNVertici(){
		return this.grafo.vertexSet().size();
	}
	
	public List<String> getVertici(){
		
		List<String> Userlist = new ArrayList<>();
		
		for(Vertici v : this.grafo.vertexSet()) {
			Userlist.add(v.getUserid());
		}	
		
		return Userlist;
	}
	
	public int getNArchi(){
		return this.grafo.edgeSet().size();
	}
	
}
