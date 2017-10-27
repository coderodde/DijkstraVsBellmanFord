package net.coderodde.graph.sssp;

import java.util.Map;

/**
 * This class stores a shortest path tree returned by a single-source shortest
 * path algorithm.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public final class ShortestPathTree<Node> {
    
    private final Map<Node, Node> parentMap;
    private final Map<Node, Double> distanceMap;
    
    public ShortestPathTree(Map<Node, Node> parentMap,
                            Map<Node, Double> distanceMap) {
        this.parentMap = parentMap;
        this.distanceMap = distanceMap;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!getClass().equals(o.getClass())) {
            return false;
        }
        
        ShortestPathTree<Node> other = (ShortestPathTree<Node>) o;
        return parentMap.equals(other.parentMap) && 
               distanceMap.equals(other.distanceMap);
    }
}
