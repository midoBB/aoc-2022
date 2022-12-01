package org.aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
  public static List<String> readInput(String fileName) throws IOException {
    return Files.readAllLines(Paths.get("src/main/resources/"+fileName+".txt"));
  }
}
