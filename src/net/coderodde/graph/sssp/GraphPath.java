package net.coderodde.graph.sssp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements a graph path.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 28, 2017)
 * @param <Node> the graph node type.
 */
public final class GraphPath<Node> {
    
    private final List<Node> path;
    private final double cost;
    
    public GraphPath(List<Node> pathAsList, 
                     DoubleWeightFunction<Node> weightFunction) {
        Objects.requireNonNull(pathAsList, "The path node list is null.");
        this.path = new ArrayList<>(pathAsList);
        
        double cost = 0.0;
        
        for (int i = 0; i < path.size() - 1; ++i) {
            cost += weightFunction.get(path.get(i), path.get(i + 1));
        }
        
        this.cost = cost;
    }
    
    public Node getNode(int index) {
        return path.get(index);
    }
    
    public double getCost() {
        return cost;
    }
}
