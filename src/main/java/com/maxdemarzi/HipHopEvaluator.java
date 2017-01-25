package com.maxdemarzi;

import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.BranchState;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.PathEvaluator;

public class HipHopEvaluator implements PathEvaluator {
    @Override
    public Evaluation evaluate(Path path, BranchState branchState) {
        if (path.endNode().getLabels().iterator().next().name().equals("B")) {
            return Evaluation.EXCLUDE_AND_CONTINUE;
        } else {
            return Evaluation.INCLUDE_AND_CONTINUE;
        }
    }

    @Override
    public Evaluation evaluate(Path path) {
        return null;
    }
}
