import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphTraversal {
  public static List<String> bfs(GraphData graphData, String start, String end) {
    Map<String, List<String>> adjList = new HashMap<>();
    for (Node node : graphData.nodes()) {
      adjList.put(node.name(), new ArrayList<>());
    }
    for (Edge edge : graphData.edges()) {
      adjList.get(edge.from().name()).add(edge.to().name());
      if (!edge.directed()) {
        adjList.get(edge.to().name()).add(edge.from().name());
      }
    }

    Queue<String> queue = new LinkedList<>();
    Map<String, String> prev = new HashMap<>();
    Set<String> visited = new HashSet<>();

    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      String curr = queue.poll();
      if (curr.equals(end)) break;

      for (String neighbor : adjList.get(curr)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          prev.put(neighbor, curr);
          queue.add(neighbor);
        }
      }
    }

    if (!prev.containsKey(end)) return List.of(); // kein Weg

    List<String> path = new ArrayList<>();
    for (String at = end; at != null; at = prev.get(at)) {
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }
}
