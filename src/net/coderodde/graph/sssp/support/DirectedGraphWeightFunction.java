package net.coderodde.graph.sssp.support;

import java.util.HashMap;
import java.util.Map;
import net.coderodde.graph.sssp.DoubleWeightFunction;

/**
 * This class implements a weight function over a directed graph.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 */
public class DirectedGraphWeightFunction 
        implements DoubleWeightFunction<DirectedGraphNode> {

    private final Map<DirectedGraphNode,
                      Map<DirectedGraphNode, Double>> map = new HashMap<>();

    @Override
    public void put(DirectedGraphNode from, DirectedGraphNode to, double weight) {
        if (!map.containsKey(from)) {
            map.put(from, new HashMap<>());
        }

        map.get(from).put(to, weight);
    }

    @Override
    public double get(DirectedGraphNode from, DirectedGraphNode to) {
        return map.get(from).get(to);
    }
}
