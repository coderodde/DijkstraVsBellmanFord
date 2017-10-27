package net.coderodde.graph.sssp;

/**
 * This interface defines the API for single-source shortest path algorithms.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public interface SingleSourceShortestPathAlgorithm<Node> {
    
    /**
     * Computes a shortest path tree starting from {@code sourceNode}, using 
     * {@code nodeExpander} as the child node generator, and 
     * {@code weightFunction} as the weight function.
     * 
     * @param sourceNode     the source node.
     * @param nodeExpander   the node expander.
     * @param weightFunction the weight function of the graph.
     * @return a shortest path tree of the reachable graph.
     */
    public ShortestPathTree<Node> 
        computeShortestPaths(Node sourceNode,
                             ForwardNodeExpander<Node> nodeExpander,
                             DoubleWeightFunction<Node> weightFunction);
}
