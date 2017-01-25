package com.maxdemarzi;

import org.junit.Rule;
import org.junit.Test;
import org.neo4j.harness.junit.Neo4jRule;
import org.neo4j.test.server.HTTP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class HipHopTest {

    @Rule
    public final Neo4jRule neo4j = new Neo4jRule()
            .withFixture(MODEL_STATEMENT)
            .withProcedure(HipHop.class);

    @Test
    public void testHipHop() throws Exception {
        HTTP.Response response = HTTP.POST(neo4j.httpURI().resolve("/db/data/transaction/commit").toString(), QUERY);
        Map actual = response.content();
        ArrayList results = (ArrayList)actual.get("results");
        HashMap result = (HashMap)results.get(0);
        ArrayList<Map> data = (ArrayList)result.get("data");
        assertEquals(5, data.size());
    }

    private static final HashMap<String, Object> PARAMS = new HashMap<String, Object>(){{
        put("id", "c1");
    }};

    private static final HashMap<String, Object> QUERY = new HashMap<String, Object>(){{
        put("statements", new ArrayList<Map<String, Object>>() {{
            add(new HashMap<String, Object>() {{
                put("statement", "MATCH (c1:C {id: $id}) CALL com.maxdemarzi.hiphop(c1) yield path return path");
                put("parameters", PARAMS);
            }});
        }});
    }};

    private static final String MODEL_STATEMENT =
            // (c1)<--(a1)-->(c2)<--(a2)-->(c3)
            // (c1)<--(a1)-->(b1)<--(a2)-->(c3)
            "CREATE (c1:C {id:'c1'})" +
            "CREATE (a1:A {id:'a1'})" +
            "CREATE (b1:B {id:'b1'})" +
            "CREATE (c2:C {id:'c2'})" +
            "CREATE (a2:A {id:'a2'})" +
            "CREATE (c3:C {id:'c3'})" +
            "CREATE (c1)<-[:RELATED]-(a1)" +
            "CREATE (a1)-[:RELATED]->(c2)" +
            "CREATE (c2)<-[:RELATED]-(a2)" +
            "CREATE (a1)-[:RELATED]->(b1)" +
            "CREATE (b1)<-[:RELATED]-(a2)" +
            "CREATE (a2)-[:RELATED]->(c3)";
}
