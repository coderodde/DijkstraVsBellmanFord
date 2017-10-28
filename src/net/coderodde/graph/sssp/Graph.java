package net.coderodde.graph.sssp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements a graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 28, 2017)
 * @param <Node> the graph node type.
 */
public final class Graph<Node> implements Iterable<Node> {

    private final Set<Node> nodeSet = new HashSet<>();

    public void addNode(Node node) {
        nodeSet.add(Objects.requireNonNull(node, "The input node is null."));
    }

    public int size() {
        return nodeSet.size();
    }

    public List<Node> getNodeList() {
        return new ArrayList<>(nodeSet);
    }

    @Override
    public Iterator<Node> iterator() {
        return Collections.unmodifiableSet(nodeSet).iterator();
    }
}
