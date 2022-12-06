package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class Day06 {
  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day06").get(0);
    for (int i = 3; i < input.length(); i++) {
      try {
        final var test =
          Set.of(input.charAt(i - 3), input.charAt(i - 2), input.charAt(i - 1),
            input.charAt(i));
        System.out.println(i + 1);
        break;
      } catch (IllegalArgumentException ignored) {
      }
    }
  }

  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day06").get(0);
    for (int i = 14; i < input.length(); i++) {
      final var chars =
        input.substring(i - 14, i).chars().mapToObj(c -> (char) c).toList();
      final var test = Set.copyOf(chars);
      if (test.size() == 14) {
        System.out.println(i);
        break;
      }
    }
  }
}
