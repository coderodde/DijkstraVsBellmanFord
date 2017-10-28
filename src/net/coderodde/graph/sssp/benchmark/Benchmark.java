package net.coderodde.graph.sssp.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.coderodde.graph.sssp.Graph;
import net.coderodde.graph.sssp.ShortestPathTree;
import net.coderodde.graph.sssp.support.BellmanFordSingleSourceShortestPathAlgorithm;
import net.coderodde.graph.sssp.support.DijkstraSingleSourceShortestPathAlgorithm;
import net.coderodde.graph.sssp.support.DirectedGraphNode;
import net.coderodde.graph.sssp.support.DirectedGraphNodeForwardExpander;
import net.coderodde.graph.sssp.support.DirectedGraphWeightFunction;

/**
 * This class benchmarks the Dijkstra's algorithm against Bellman-Ford 
 * algorithm.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 */
public class Benchmark {

    private static final int WARMUP_ITERATIONS = 2;
    private static final int WARMUP_NODES = 10000;
    private static final int WARMUP_ARCS = 30000;
    private static final int BENCHMARK_NODES = 10000;
    private static final int BENCHMARK_ARCS = 30000;
    private static final double MIN_WEIGHT = 0.0;
    private static final double MAX_WEIGHT = 20.0;
    
    public static void main(String[] args) {
        warmup();
        benchmark();
    }   
    
    private static void benchmark() {
        long seed = System.currentTimeMillis();
        Random random = new Random();
        DirectedGraphData graphData = createRandomGraph(BENCHMARK_NODES,
                                                        BENCHMARK_ARCS,
                                                        random,
                                                        MIN_WEIGHT,
                                                        MAX_WEIGHT);
        System.out.println("bechmark(), seed = " + seed);
        DirectedGraphNode sourceNode = 
                choose(graphData.getGraph().getNodeList(), random);
        
        perform(graphData, sourceNode, true);
    }
    
    private static void warmup() {
        System.out.println("=== Warming up...");
        
        Random random = new Random();
        DirectedGraphData graphData = createRandomGraph(WARMUP_NODES,
                                                        WARMUP_ARCS,
                                                        random,
                                                        MIN_WEIGHT,
                                                        MAX_WEIGHT);
        for (int i = 0; i < WARMUP_ITERATIONS; ++i) {
            DirectedGraphNode sourceNode = 
                    choose(graphData.getGraph().getNodeList(), random);
            
            warmup(graphData, sourceNode);
        }
        
        System.out.println("=== Warming up done!");
        System.out.println();
    }
    
    private static void warmup(DirectedGraphData graphData,
                               DirectedGraphNode sourceNode) {
        perform(graphData, sourceNode, false);
    }
    
    private static void benchmark(DirectedGraphData graphData,
                                  DirectedGraphNode sourceNode) {
        perform(graphData, sourceNode, true);
    }
    
    private static void perform(DirectedGraphData graphData, 
                                DirectedGraphNode suorceNode,
                                boolean print) {
        Graph<DirectedGraphNode> graph = graphData.getGraph();
        
        DirectedGraphWeightFunction weightFunction =
                graphData.getWeightFunction();
        
        DirectedGraphNodeForwardExpander expander = 
                new DirectedGraphNodeForwardExpander();
        
        long start = System.currentTimeMillis();
        
        ShortestPathTree<DirectedGraphNode> tree1 =
            new DijkstraSingleSourceShortestPathAlgorithm<DirectedGraphNode>()
                .computeShortestPaths(suorceNode, 
                                      graph,
                                      expander, 
                                      weightFunction);
        
        long end = System.currentTimeMillis();
        
        if (print) {
            System.out.println("Dijkstra's algorithm in " + 
                    (end - start) + " milliseconds.");
        }
        
        start = System.currentTimeMillis();
        
        ShortestPathTree<DirectedGraphNode> tree2 = 
        new BellmanFordSingleSourceShortestPathAlgorithm<DirectedGraphNode>()
                .computeShortestPaths(suorceNode, 
                                      graph, 
                                      expander, 
                                      weightFunction);
        
        end = System.currentTimeMillis();
        
        if (print) {
            System.out.println("Bellman-Ford algorithm in " + 
                    (end - start) + " milliseconds.");
        }
        
        if (!Objects.equals(tree1, tree2)) {
            throw new IllegalStateException("Algorithms disagreed.");
        }
        
        if (print) {
            System.out.println("Algorithms agreed.");
        }
    }
    
    private static DirectedGraphData createRandomGraph(int nodes,
                                               int arcs,
                                               Random random,
                                               double minWeight,
                                               double maxWeight) {
        List<DirectedGraphNode> graph = new ArrayList<>(nodes);
        
        for (int id = 0; id < nodes; ++id) {
            graph.add(new DirectedGraphNode(id));
        }
        
        DirectedGraphWeightFunction weightFunction = 
                new DirectedGraphWeightFunction();
        
        while (arcs-- > 0) {
            DirectedGraphNode tail = choose(graph, random);
            DirectedGraphNode head = choose(graph, random);
            double weight = minWeight + 
                           (maxWeight - minWeight) * random.nextDouble();
            weightFunction.put(tail, head, weight);
        }
        
        return new DirectedGraphData(graph, weightFunction);
    }
    
    private static final class DirectedGraphData {
        
        private final Graph<DirectedGraphNode> graph;
        private final DirectedGraphWeightFunction weightFunction;
        
        DirectedGraphData(List<DirectedGraphNode> graph,
                          DirectedGraphWeightFunction weightFunction) {
            this.graph = new Graph<>();
            this.weightFunction = weightFunction;
            
            for (DirectedGraphNode node : graph) {
                this.graph.addNode(node);
            }
        }
        
        Graph<DirectedGraphNode> getGraph() {
            return graph;
        }
        
        DirectedGraphWeightFunction getWeightFunction() {
            return weightFunction;
        }
    }
    
    private static <T> T choose(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
