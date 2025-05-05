import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class GkaParser {
  private static final Pattern edgePattern = Pattern.compile(
      "(\\w+)\\s*(->|--)\\s*(\\w+)(?:\\s*\\((\\w+)\\))?(?::\\s*(\\d+))?;");

  public static boolean isDirected(List<String> lines) {
    for (String line : lines) {
      if (line.contains("->")) return true;
    }
    return false;
  }

  public static List<Node> parseNodes(List<String> lines) {
    Set<Node> nodes = new HashSet<>();
    for (String line : lines) {
      Matcher m = edgePattern.matcher(line);
      if (m.matches()) {
        nodes.add(new Node(m.group(1)));
        nodes.add(new Node(m.group(3)));
      } else if (line.matches("\\w+;")) {
        nodes.add(new Node(line.replace(";", "").trim()));
      } else {
        System.out.println("Ungültige Zeile ignoriert: " + line);
      }
    }
    return new ArrayList<>(nodes);
  }

  public static List<Edge> parseEdges(List<String> lines) {
    List<Edge> edges = new ArrayList<>();
    for (String line : lines) {
      Matcher m = edgePattern.matcher(line);
      if (m.matches()) {
        String from = m.group(1);
        String type = m.group(2);
        String to = m.group(3);
        String name = m.group(4);
        int weight = m.group(5) != null ? Integer.parseInt(m.group(5)) : 1;
        boolean directed = type.equals("->");
        edges.add(new Edge(new Node(from), new Node(to), name, weight, directed));
      } else if (!line.matches("\\w+;")) {
        System.out.println("Ungültige Zeile ignoriert: " + line);
      }
    }
    return edges;
  }

  public static GraphData loadGraphFromFile(String path) throws IOException {
    List<String> lines = Files.readAllLines(Path.of(path));
    List<Node> nodes = parseNodes(lines);
    List<Edge> edges = parseEdges(lines);
    return new GraphData(nodes, edges);
  }
}
