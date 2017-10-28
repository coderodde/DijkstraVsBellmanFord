package net.coderodde.graph.sssp;

import java.util.List;
import java.util.Map;

/**
 * This interface defines the API for single-source shortest path algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public abstract class AbstractSingleSourceShortestPathAlgorithm<Node> {
    
    /**
     * Computes a shortest path tree starting from {@code sourceNode}, using 
     * {@code nodeExpander} as the child node generator, and 
     * {@code weightFunction} as the weight function.
     * 
     * @param sourceNode     the source node.
     * @param graph          the list of graph nodes.
     * @param nodeExpander   the node expander.
     * @param weightFunction the weight function of the graph.
     * @return a shortest path tree of the reachable graph.
     */
    public abstract ShortestPathTree<Node> 
        computeShortestPaths(Node sourceNode,
                             Graph<Node> graph,
                             ForwardNodeExpander<Node> nodeExpander,
                             DoubleWeightFunction<Node> weightFunction);
        
    protected ShortestPathTree<Node> 
        constructShortestPathTree(Map<Node, Node> parents,
                                  Map<Node, Double> distances,
                                  Node sourceNode,
                                  DoubleWeightFunction<Node> weightFunction) {
        return new ShortestPathTree<>(parents,
                                      distances,
                                      sourceNode,
                                      weightFunction);
    }
}
