package com.maxdemarzi;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpander;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.BranchState;

public class HipHopExpander implements PathExpander{


    @Override
    public Iterable<Relationship> expand(Path path, BranchState branchState) {
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
