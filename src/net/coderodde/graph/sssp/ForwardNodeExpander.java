package net.coderodde.graph.sssp;

import java.util.List;

/**
 * This interface defines the API for expanding a graph node in forward
 * direction (from a node to its children).
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public interface ForwardNodeExpander<Node> {
    
    /**
     * Generates and returns a list of child nodes of {@code node}.
     * 
     * @param node the node to expand.
     * @return the list of child nodes.
     */
    public List<Node> expand(Node node);
}
