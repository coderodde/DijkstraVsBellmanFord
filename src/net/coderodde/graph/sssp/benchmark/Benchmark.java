package net.coderodde.graph.sssp.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.coderodde.graph.sssp.support.DirectedGraphNode;
import net.coderodde.graph.sssp.support.DirectedGraphWeightFunction;

/**
 * This class benchmarks the Dijkstra's algorithm against Bellman-Ford 
 * algorithm.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Oct 27, 2017)
 */
public class Benchmark {

    private static final int WARMUP_ITERATIONS = 50;
    private static final int WARMUP_NODES = 10_000;
    private static final int WARMUP_ARCS = 100_000;
    private static final int BENCHMARK_NODES = 50_000;
    private static final int BENCHMARK_ARCS = 500_000;
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
        DirectedGraphNode sourceNode = choose(graphData.getGraph(), random);
        DirectedGraphNode targetNode = choose(graphData.getGraph(), random);
        System.out.println("Source node: " + sourceNode);
        System.out.println("Target node: " + targetNode);
        perform(graphData, sourceNode, true);
    }
    
    private static void warmup() {
        Random random = new Random();
        DirectedGraphData graphData = createRandomGraph(WARMUP_NODES,
                                                        WARMUP_ARCS,
                                                        random,
                                                        MIN_WEIGHT,
                                                        MAX_WEIGHT);
        for (int i = 0; i < WARMUP_ITERATIONS; ++i) {
            DirectedGraphNode sourceNode = choose(graphData.getGraph(), random);
            warmup(graphData, sourceNode);
        }
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
        long start = System.currentTimeMillis();
        List<DirectedGraphNode> path1 = null;
        long end = System.currentTimeMillis();
        
        if (print) {
            System.out.println("Dijkstra's algorithm in " + 
                    (end - start) + " milliseconds.");
        }
        
        start = System.currentTimeMillis();
        List<DirectedGraphNode> path2 = null;
        end = System.currentTimeMillis();
        
        if (print) {
            System.out.println("Bellman-Ford algorithm in " + 
                    (end - start) + " milliseconds.");
        }
        
        if (!Objects.equals(path1, path2)) {
            throw new IllegalStateException("Algorithms disagreed.");
        }
        
        if (print) {
            System.out.println("Algorithms agreed.");
            System.out.println("Paths:");
            path1.forEach(System.out::println);
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
        
        private final List<DirectedGraphNode> graph;
        private final DirectedGraphWeightFunction weightFunction;
        
        DirectedGraphData(List<DirectedGraphNode> graph,
                                 DirectedGraphWeightFunction weightFunction) {
            this.graph = graph;
            this.weightFunction = weightFunction;
        }
        
        List<DirectedGraphNode> getGraph() {
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
