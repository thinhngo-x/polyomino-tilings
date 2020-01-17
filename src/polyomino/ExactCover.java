package polyomino;
import java.util.*;


public class ExactCover {
	public static Set<Set<Set<Object>>> solve(Set<Object> ground_old, Set< Set<Object> > collection_old) {
		Set<Object> ground = new HashSet<>();
		ground.addAll(ground_old);
		Set< Set<Object> > collection = new HashSet<>();
		collection.addAll(collection);
		
		Set<Set< Set<Object> > > result = new HashSet<>();
		
		Object x = ground.iterator().next();
		for(Set<Object> S: collection) {
			if(!S.contains(x)) continue;
			for(Object y: S) {
				ground.remove(y);
				for(Set<Object> otherS: collection) {
					if(collection.contains(y))
						collection.remove(otherS);
				}
			}
			for(Set<Set<Object>> P: solve(ground, collection)) {
				P.add(S);
				result.add(P);
			}
		}
		
		return result;
	}
}
