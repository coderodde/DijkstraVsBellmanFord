package net.coderodde.graph.sssp.support;

import java.util.List;
import net.coderodde.graph.sssp.ForwardNodeExpander;

/**
 * This class implements a forward node expander in directed graphs.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 */
public final class DirectedGraphNodeForwardExpander 
        implements ForwardNodeExpander<DirectedGraphNode> {

    @Override
    public List<DirectedGraphNode> expand(DirectedGraphNode node) {
        return node.getChildren();
    }
}
