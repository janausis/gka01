import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GraphVisualizer {
  public static void visualize(GraphData data) {
    Graph graph = new MultiGraph("GKA Graph");
    graph.setAttribute("ui.stylesheet", "node { text-size: 18px; text-color: red; } edge { text-size: 14px; text-color: red; }");


    for (Node node : data.nodes()) {
      org.graphstream.graph.Node n = graph.addNode(node.name());
      n.setAttribute("ui.label", node.name());
    }

    int i = 0;
    for (Edge edge : data.edges()) {
      String id = "e" + i++;
      String from = edge.from().name();
      String to = edge.to().name();
      if (graph.getEdge(id) != null) continue;
      org.graphstream.graph.Edge gsEdge = graph.addEdge(id, from, to, edge.directed());
      String label = edge.name() != null ? edge.name() : "";
      if (edge.weight() != 1) label += " : " + edge.weight();
      gsEdge.setAttribute("ui.label", label);

    }

    graph.display();
  }
}
