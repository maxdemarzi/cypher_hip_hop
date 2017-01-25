# cypher_hip_hop
Cypher Stored Procedure for odd even traversal


# Instructions

1. Build it:

        mvn clean package

2. Copy target/hip-hop-1.0-SNAPSHOT.jar to the plugins/ directory of your Neo4j server.

3. (Re)Start Neo4j server.

4. Call the Procedure:

        MATCH (c:C {id: $id})
        WITH c
        CALL com.maxdemarzi.hiphop(c) yield path
        RETURN path;
        