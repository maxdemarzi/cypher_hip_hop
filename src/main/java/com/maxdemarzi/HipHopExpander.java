package com.maxdemarzi;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.BranchState;

import java.util.Collections;

public class HipHopExpander implements PathExpander{

    private Label stopLabel = Label.label("B");

    @Override
    public Iterable<Relationship> expand(Path path, BranchState branchState) {
        if (path.endNode().hasLabel(stopLabel)) {
            return Collections.emptyList();
        }
        if (path.length() % 2 == 0) {
            return path.endNode().getRelationships(Direction.INCOMING);
        } else {
            return path.endNode().getRelationships(Direction.OUTGOING);
        }
    }

    @Override
    public PathExpander reverse() {
        return null;
    }
}
