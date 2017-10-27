package net.coderodde.graph.sssp;

/**
 * This interface defines the API for weight functions with floating-point 
 * weights.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 * @param <Node> the graph node type.
 */
public interface DoubleWeightFunction<Node> {
    
    /**
     * Sets the weight of a directed arc {@code (from, to)}.
     * 
     * @param from   the tail node of the arc.
     * @param to     the head node of the arc.
     * @param weight the weight of the arc.
     */
    public void put(Node from, Node to, double weight);
    
    /**
     * Returns the weight of the directed arc {@code (from, to)}.
     * 
     * @param from the tail node of the arc.
     * @param to   the head node of the arc.
     * @return     the weight of the directed arc.
     */
    public double get(Node from, Node to);
}
