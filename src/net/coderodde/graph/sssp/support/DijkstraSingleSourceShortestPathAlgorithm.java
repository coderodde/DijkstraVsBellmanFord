package net.coderodde.graph.sssp.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import net.coderodde.graph.sssp.DoubleWeightFunction;
import net.coderodde.graph.sssp.ForwardNodeExpander;
import net.coderodde.graph.sssp.ShortestPathTree;
import net.coderodde.graph.sssp.AbstractSingleSourceShortestPathAlgorithm;
import net.coderodde.graph.sssp.Graph;

/**
 * This class implements Dijkstra's algorithm for finding a shortest path tree
 * starting from a given node.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public class DijkstraSingleSourceShortestPathAlgorithm<Node>
extends AbstractSingleSourceShortestPathAlgorithm<Node>{

    /**
     * Finds the shortest path tree starting from {@code sourceNode} using
     * Dijkstra's algorithm.
     * 
     * @param sourceNode     the source node.
     * @param graph          ignored.
     * @param nodeExpander   the node expander.
     * @param weightFunction the weight function.
     * @return the shortest path tree.
     */
    @Override
    public ShortestPathTree<Node> 
        computeShortestPaths(Node sourceNode, 
                             Graph<Node> graph,
                             ForwardNodeExpander<Node> nodeExpander, 
                             DoubleWeightFunction<Node> weightFunction) {
        Map<Node, Double> distances = new HashMap<>(graph.size());
        Map<Node, Node> parents = new HashMap<>(graph.size());
        Set<Node> closed = new HashSet<>(graph.size());
        Queue<NodeHolder<Node>> open = new PriorityQueue<>(graph.size());

        distances.put(sourceNode, 0.0);
        parents.put(sourceNode, null);
        open.add(new NodeHolder<>(sourceNode, 0.0));

        while (!open.isEmpty()) {
            Node currentNode = open.remove().getNode();

            if (closed.contains(currentNode)) {
                continue;
            }

            closed.add(currentNode);

            for (Node childNode : nodeExpander.expand(currentNode)) {
                if (closed.contains(childNode)) {
                    continue;
                }

                double tentativeDistance = 
                        distances.get(currentNode) +
                        weightFunction.get(currentNode, childNode);

                if (!distances.containsKey(childNode) ||
                    distances.get(childNode) > tentativeDistance) {
                    distances.put(childNode, tentativeDistance);
                    parents.put(childNode, currentNode);
                    open.add(new NodeHolder<>(childNode, tentativeDistance));
                }
            }
        }

        return constructShortestPathTree(parents, 
                                         distances, 
                                         sourceNode, 
                                         weightFunction);
    }

    private static final class NodeHolder<Node> implements Comparable<NodeHolder<Node>> {

        private final double distance;
        private final Node node;

        NodeHolder(Node node, double distance) {
            this.distance = distance;
            this.node = node;
        }

        @Override
        public int compareTo(NodeHolder<Node> o) {
            return Double.compare(distance, o.distance);
        }

        Node getNode() {
            return node;
        }
    }
}
