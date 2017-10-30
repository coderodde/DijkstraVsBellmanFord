package net.coderodde.graph.sssp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class implements a graph path.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 28, 2017)
 * @param <Node> the graph node type.
 */
public final class GraphPath<Node> implements Iterable<Node> {

    private final List<Node> path;
    private final double cost;

    GraphPath(List<Node> pathAsList, double cost) {
        this.path = new ArrayList<>(pathAsList);
        this.cost = cost;
    }

    public int size() {
        return path.size();
    }

    public Node getNode(int index) {
        return path.get(index);
    }

    public double getCost() {
        return cost;
    }

    @Override
    public Iterator<Node> iterator() {
        return Collections.unmodifiableList(path).iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String separator = "";

        for (Node node : path) {
            sb.append(separator);
            separator = " -> ";
            sb.append(node.toString());
        }

        return sb.append(", cost: ")
                 .append(cost)
                 .append("]")
                 .toString();
    }
}
