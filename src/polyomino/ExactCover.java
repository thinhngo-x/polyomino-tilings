package polyomino;
import java.util.*;


public class ExactCover {
	public static Set<Set<Set<Integer>>> solve(Set<Integer> ground, Set< Set<Integer> > collection) {
		Set<Set< Set<Integer> > > result = new HashSet<>();
		if(ground.isEmpty()) {
			result.add(new HashSet<>());
			return(result);
		}
			
		Set<Integer> ground_ = new HashSet<>(ground);
		Set< Set<Integer> > collection_ = new HashSet<>(collection);
		
		Integer x = ground_.iterator().next();
		for(Set<Integer> S: collection) {
			if(!S.contains(x)) continue;
			for(Integer y: S) {
				ground_.remove(y);
				for(Set<Integer> otherS: collection) {
					if(otherS.contains(y))
						collection_.remove(otherS);
				}
			}
			
			for(Set<Set<Integer>> P: solve(ground_, collection_)) {
				P.add(S);
				result.add(P);
			}
		}
		
		return result;
	}
}
