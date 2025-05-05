import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    System.setProperty("org.graphstream.ui", "swing");

    String path = "src/main/resources/graph.gka";
    File file = new File(path);

    try {
      if (!file.exists()) {
        System.out.println("Keine .gka-Datei gefunden.");
        System.out.println("➡ Gib den Graphen ein (eine Zeile pro Kante). Format wie in .gka Dateien.");
        System.out.println("➡ Tippe `done` um zu speichern und fortzufahren.\n");

        Scanner inputScanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();
        while (true) {
          String line = inputScanner.nextLine().trim();
          if (line.equalsIgnoreCase("done")) break;
          if (!line.isEmpty()) lines.add(line);
        }

        if (lines.isEmpty()) {
          System.out.println("Keine Eingabe erkannt. Abbruch.");
          return;
        }

        Files.createDirectories(Paths.get("src/main/resources/"));
        Files.write(file.toPath(), lines);
        System.out.println("Datei gespeichert unter: " + path);
      }

      GraphData graphData = GkaParser.loadGraphFromFile(path);

      Scanner scanner = new Scanner(System.in);
      System.out.print("Startknoten: ");
      String start = scanner.nextLine().trim();
      System.out.print("Zielknoten: ");
      String end = scanner.nextLine().trim();

      GraphVisualizer.visualize(graphData);

      List<String> pathList = GraphTraversal.bfs(graphData, start, end);
      if (!pathList.isEmpty()) {
        System.out.println("\nBFS Pfad von " + start + " nach " + end + ":");
        System.out.println(String.join(" -> ", pathList));
        System.out.println("Anzahl Kanten: " + (pathList.size() - 1));
      } else {
        System.out.println("\nKein Pfad von " + start + " nach " + end + " gefunden.");
      }

    } catch (Exception e) {
      System.err.println("Fehler beim Einlesen oder Verarbeiten der Datei:");
      e.printStackTrace();
    }
  }
}
