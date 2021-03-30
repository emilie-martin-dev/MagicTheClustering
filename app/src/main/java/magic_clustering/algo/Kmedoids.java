package magic_clustering.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import magic_clustering.model.Deck;

public class Kmedoids {
	
	private List<Deck> decks;
	private int kMedoids;
	
	public Kmedoids(List<Deck> decks, int kMedois) {
		this.decks = decks;
		this.kMedoids = kMedois;	
	}
	
	public HashMap<Integer, List<Deck>> compute() {
		// Si on veut plus de KMedoids que de deck on stop
		if(kMedoids >= decks.size())
			return new HashMap<>();
		
		// On récupère les distances de Jaccard
		Jaccard jaccard = new Jaccard(decks);
		HashMap<Deck, HashMap<Deck, Float>> distanceDeJaccard = jaccard.jaccardDist();
		
		// On initialise les clusters
		HashMap<Integer, List<Deck>> clusters = new HashMap<>();
		for(int i = 0 ; i < kMedoids ; i++) {
			clusters.put(i, new ArrayList<>());
		}
		
		List<Integer> medoidsIndex = initKMedoids();
		
		assignCluster(medoidsIndex, distanceDeJaccard, clusters);
		
		return clusters;
	}	
	
	private List<Integer> initKMedoids() {
		List<Integer> rIndex = new ArrayList<>();
		Random rand = new Random();
		
		for(int i = 0 ; i < kMedoids ; i++) {
			int r = rand.nextInt(decks.size());
			
			// Si on a deja selectionné ce kmedois, on en reselectionne un autre
			if(rIndex.contains(r)) {
				i--;
				continue;
			}
			
			rIndex.add(r);
		}
		
		return rIndex;
	}

	private void assignCluster(List<Integer> medoidsIndex, HashMap<Deck, HashMap<Deck, Float>> distanceDeJaccard, HashMap<Integer, List<Deck>> clusters) {
		// Pour chaque deck on cherche la distance minimum avec les kmedois
		for(Deck d : decks) {
			int minMedoidIndex = -1;
			float minMedoidDist = 1;
			
			for(int mIndex : medoidsIndex) {
				float distWithMedoid = distanceDeJaccard.get(d).get(decks.get(mIndex));
				if(distWithMedoid < minMedoidDist) {
					minMedoidDist = distWithMedoid;
					minMedoidIndex = mIndex;
				}
			}
			
			clusters.get(medoidsIndex.indexOf(minMedoidIndex)).add(d);
		}
	}
	
}
