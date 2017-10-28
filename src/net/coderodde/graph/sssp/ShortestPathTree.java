package net.coderodde.graph.sssp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final Node sourceNode;
    private final DoubleWeightFunction<Node> weightFunction;
    
    public ShortestPathTree(Map<Node, Node> parentMap,
                            Map<Node, Double> distanceMap,
                            Node sourceNode,
                            DoubleWeightFunction<Node> weightFunction) {
        this.parentMap = parentMap;
        this.distanceMap = distanceMap;
        this.sourceNode = sourceNode;
        this.weightFunction = 
                Objects.requireNonNull(weightFunction, 
                                       "The input weight function is null.");
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
    
    public Node getSourceNode() {
        return sourceNode;
    }
    
    public GraphPath<Node> getPath(Node targetNode) {
        if (!parentMap.containsKey(targetNode)) {
            throw new IllegalStateException(
                    "Target node \"" + targetNode + "\" is not reachable " + 
                    "from \"" + sourceNode + "\".");
        }
        
        Node currentNode = targetNode;
        List<Node> path = new ArrayList<>();
        
        while (currentNode != null) {
            path.add(currentNode);
            currentNode = parentMap.get(currentNode);
        }
        
        Collections.reverse(path);
        return new GraphPath<>(path, weightFunction);
    }
}
