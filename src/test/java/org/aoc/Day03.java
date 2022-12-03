package org.aoc;

import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class Day03 {
  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day03");
    final var res =
      input.stream().map(Day03::LineToValue).reduce(Integer::sum).orElseThrow();
    System.out.println(res);
  }

  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day03");
    final var res = StreamEx.ofSubLists(input, 3).map(Day03::ElvesToBadges)
      .reduce(Integer::sum).orElseThrow();
    System.out.println(res);
  }

  private static int ElvesToBadges(List<String> strings) {
    for(char c : strings.get(0).toCharArray()){
      if(strings.get(1).indexOf(c) != -1 && strings.get(2).indexOf(c)!= -1)
        return calcValue(c);
    }
    return 0;
  }

  private static int LineToValue(String line) {
    final var firstCompart = line.substring(0, line.length() / 2);
    final var secondCompart = line.substring((line.length() / 2));
    for (int i = 0; i < firstCompart.length(); i++) {
      char ch = firstCompart.charAt(i);
      if (secondCompart.indexOf(ch) != -1) {
        return calcValue(ch);
      }
    }
    return 0;
  }

  private static int calcValue(char ch) {
    if (ch >= 'A' && ch <= 'Z') {
      return 27 + ch - 'A';
    } else {
      return 1 + ch - 'a';
    }
  }
}
