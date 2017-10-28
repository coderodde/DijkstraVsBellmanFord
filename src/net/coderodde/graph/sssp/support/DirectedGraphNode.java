    package net.coderodde.graph.sssp.support;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.List;

    /**
     * This class implements a graph node in a directed graph.
     * 
     * @author Rodion "rodde" Efremov
     * @version 1.6 (Oct 27, 2017)
     */
    public final class DirectedGraphNode {

        private final int id;
        private final List<DirectedGraphNode> children = new ArrayList<>();

        public DirectedGraphNode(int id) {
            this.id = id;
        }

        public void addChild(DirectedGraphNode child) {
            children.add(child);
        }

        public List<DirectedGraphNode> getChildren() {
            return Collections.unmodifiableList(children);
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (o == null) {
                return false;
            } else if (!getClass().equals(o.getClass())) {
                return false;
            }

            DirectedGraphNode other = (DirectedGraphNode) o;
            return id == other.id;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }
