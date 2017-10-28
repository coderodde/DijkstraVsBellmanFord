package net.coderodde.graph.sssp.support;

import java.util.Arrays;
import net.coderodde.graph.sssp.Graph;
import net.coderodde.graph.sssp.GraphPath;
import net.coderodde.graph.sssp.ShortestPathTree;
import org.junit.Test;
import static org.junit.Assert.*;

public class DijkstraSingleSourceShortestPathAlgorithmTest {
    
    private final DijkstraSingleSourceShortestPathAlgorithm<DirectedGraphNode>
            algorithm = new DijkstraSingleSourceShortestPathAlgorithm<>();
    
    private final DirectedGraphNodeForwardExpander expander =
            new DirectedGraphNodeForwardExpander();
    
    @Test
    public void test1() {
        DirectedGraphNode a = new DirectedGraphNode(1);
        DirectedGraphNode b = new DirectedGraphNode(2);
        DirectedGraphNode c = new DirectedGraphNode(3);
        DirectedGraphNode d = new DirectedGraphNode(4);
        DirectedGraphNode e = new DirectedGraphNode(5);
        DirectedGraphNode f = new DirectedGraphNode(6);
        
        c.addChild(b);
        b.addChild(a);
        c.addChild(d);
        c.addChild(e);
        d.addChild(f);
        e.addChild(f);
        
        DirectedGraphWeightFunction weightFunction = 
                new DirectedGraphWeightFunction();
        
        weightFunction.put(c, b, 1.0);
        weightFunction.put(b, a, 2.0);
        weightFunction.put(c, d, 3.0);
        weightFunction.put(d, f, 10.0);
        weightFunction.put(c, e, 1.0);
        weightFunction.put(e, f, 20.0);
        
        Graph<DirectedGraphNode> graph = new Graph<>();
        
        for (DirectedGraphNode node : Arrays.asList(a, b, c, d, e, f)) {
            graph.addNode(node);
        }
        
        ShortestPathTree<DirectedGraphNode> tree = 
                algorithm.computeShortestPaths(c, 
                                               graph, 
                                               expander, 
                                               weightFunction);
        
        GraphPath<DirectedGraphNode> pathToA = tree.getPath(a);
        GraphPath<DirectedGraphNode> pathToB = tree.getPath(b);
        GraphPath<DirectedGraphNode> pathToC = tree.getPath(c);
        GraphPath<DirectedGraphNode> pathToD = tree.getPath(d);
        GraphPath<DirectedGraphNode> pathToE = tree.getPath(e);
        GraphPath<DirectedGraphNode> pathToF = tree.getPath(f);
        
        assertEquals(3.0, pathToA.getCost(), 0.001);
        assertEquals(1.0, pathToB.getCost(), 0.001);
        assertEquals(0.0, pathToC.getCost(), 0.001);
        assertEquals(3.0, pathToD.getCost(), 0.001);
        assertEquals(1.0, pathToE.getCost(), 0.001);
        assertEquals(13.0, pathToF.getCost(), 0.001);
    }
}
