package net.coderodde.graph.sssp.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.coderodde.graph.sssp.DoubleWeightFunction;
import net.coderodde.graph.sssp.ForwardNodeExpander;
import net.coderodde.graph.sssp.ShortestPathTree;
import net.coderodde.graph.sssp.SingleSourceShortestPathAlgorithm;

/**
 * This class implements Bellman-Ford algorithm for finding a shortest path tree
 * starting from a given node.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public class BellmanFordSingleSourceShortestPathAlgorithm<Node> 
implements SingleSourceShortestPathAlgorithm<Node> {

    /**
     * Finds the shortest path tree starting from {@code sourceNode} using
     * Bellman-Ford algorithm.
     * 
     * @param sourceNode     the source node.
     * @param graph          the list of graph nodes.
     * @param nodeExpander   the node expander.
     * @param weightFunction the weight function.
     * @return the shortest path tree.
     */
    @Override
    public ShortestPathTree<Node> 
        computeShortestPaths(
                Node sourceNode,
                List<Node> graph,
                ForwardNodeExpander<Node> nodeExpander, 
                DoubleWeightFunction<Node> weightFunction) {
        Map<Node, Double> distances = new HashMap<>(graph.size());
        Map<Node, Node> parents = new HashMap<>(graph.size());
        
        distances.put(sourceNode, 0.0);
        parents.put(sourceNode, null);
        
        for (int i = 0; i < graph.size() - 1; ++i) {
            for (Node currentNode : graph) {
                for (Node childNode : nodeExpander.expand(currentNode)) {
                    double currentNodeDistance = 
                            distances.getOrDefault(currentNode,
                                                   Double.POSITIVE_INFINITY);
                    
                    double childNodeDistance =
                            distances.getOrDefault(childNode,
                                                   Double.POSITIVE_INFINITY);
                    
                    double weight = weightFunction.get(currentNode, childNode);
                    
                    if (currentNodeDistance + weight < childNodeDistance) {
                        distances.put(childNode, 
                                      currentNodeDistance + weight);
                        
                        parents.put(childNode, currentNode);
                    }
                }
            }
        }
        
        return new ShortestPathTree<>(parents,
                                      distances, 
                                      sourceNode, 
                                      weightFunction);
    }
}
