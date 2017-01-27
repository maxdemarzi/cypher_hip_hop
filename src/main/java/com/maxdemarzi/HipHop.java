package com.maxdemarzi;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.logging.Log;
import org.neo4j.procedure.*;

import java.util.stream.Stream;

public class HipHop {

    // This field declares that we need a GraphDatabaseService
    // as context when any procedure in this class is invoked
    @Context
    public GraphDatabaseService db;

    // This gives us a log instance that outputs messages to the
    // standard log, normally found under `data/log/console.log`
    @Context
    public Log log;

    static final HipHopExpander hipHopExpander = new HipHopExpander();
    static final HipHopEvaluator hiphopEvaluator = new HipHopEvaluator();

    //(A) --> (C) <-- (A) --> (C) <-- (A) --> (C) <-- (A) --> (C {id})
    @Description("com.maxdemarzi.hiphop(node) | Return Paths starting from node alternating pattern")
    @Procedure(name = "com.maxdemarzi.hiphop", mode = Mode.READ)
    public Stream<PathResult> hiphop( @Name("startNode") Node startNode) {
        TraversalDescription myTraversal = db.traversalDescription()
                .depthFirst()
                .expand(hipHopExpander)
                .evaluator(hiphopEvaluator);

        return  myTraversal.traverse(startNode).stream().map(PathResult::new);
    }

    @Description("com.maxdemarzi.hiphopdistinct(node) | Return Paths starting from node alternating pattern")
    @Procedure(name = "com.maxdemarzi.hiphopdistinct", mode = Mode.READ)
    public Stream<NodeResult> hiphopDistinct( @Name("startNode") Node startNode) {
        TraversalDescription myTraversal = db.traversalDescription()
                .depthFirst()
                .expand(hipHopExpander)
                .evaluator(hiphopEvaluator);

        return  myTraversal.traverse(startNode).nodes().stream().distinct().map(NodeResult::new);
    }

    public static class PathResult {
        public Path path;

        PathResult(Path path) {
            this.path = path;
        }
    }

    public static class NodeResult {
        public final Node node;

        public NodeResult(Node node) {
            this.node = node;
        }

        @Override
        public boolean equals(Object o) {
            return this == o || o != null && getClass() == o.getClass() && node.equals(((NodeResult) o).node);
        }

        @Override
        public int hashCode() {
            return node.hashCode();
        }
    }
}
