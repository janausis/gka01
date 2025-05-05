import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.*;
import java.util.List;

public class GkaTest {

  private static final String GRAPH_01_PATH = "src/main/resources/graph01.gka";
  private static final String GRAPH_02_PATH = "src/main/resources/graph02.gka";

  @BeforeAll
  public static void setup() {
    // Here you could ensure files exist, or load from an actual path if needed.
    try {
      Files.createDirectories(Paths.get("src/main/resources/"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGraph01() {
    try {
      // Load graph data for graph01
      GraphData graphData = GkaParser.loadGraphFromFile(GRAPH_01_PATH);
      assertNotNull(graphData, "Graph data for graph01 should not be null");

      // Test BFS traversal for graph01
      String start = "a";
      String end = "l";

      List<String> path = GraphTraversal.bfs(graphData, start, end);
      assertNotNull(path, "BFS result should not be null");
      assertFalse(path.isEmpty(), "BFS result should not be empty");

      // Print result (optional)
      System.out.println("Graph01 BFS path: " + String.join(" -> ", path));

      // Example assert for a known path (you can adjust this based on actual graph)
      assertEquals("a -> b -> l", String.join(" -> ", path.subList(0, 3)));

    } catch (Exception e) {
      fail("Error processing graph01: " + e.getMessage());
    }
  }

  @Test
  public void testGraph02() {
    try {
      // Load graph data for graph02
      GraphData graphData = GkaParser.loadGraphFromFile(GRAPH_02_PATH);
      assertNotNull(graphData, "Graph data for graph02 should not be null");

      // Test BFS traversal for graph02
      String start = "a";
      String end = "k";

      List<String> path = GraphTraversal.bfs(graphData, start, end);
      assertNotNull(path, "BFS result should not be null");
      assertFalse(path.isEmpty(), "BFS result should not be empty");

      // Print result (optional)
      System.out.println("Graph02 BFS path: " + String.join(" -> ", path));

      // Example assert for a known path (adjust this as needed)
      assertEquals("a -> c -> k", String.join(" -> ", path.subList(0, 3)));

    } catch (Exception e) {
      fail("Error processing graph02: " + e.getMessage());
    }
  }
}
