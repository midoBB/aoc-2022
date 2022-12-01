package org.aoc;

import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

class Day01 {

  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day01");
    System.out.println(getElves(input).max(Comparator.naturalOrder()).orElseThrow());
  }
  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day01");
    final Integer result =
      getElves(input).reverseSorted().limit(3).reduce(Integer::sum).orElseThrow();
    System.out.println(result);
  }

  private static StreamEx<Integer> getElves(List<String> input) {
    final StreamEx<List<String>> lists =
      StreamEx.of(input).groupRuns((s, s2) -> !s.isEmpty());
    return lists.map(strings -> strings.stream().filter(s -> !s.isEmpty()))
      .map(it -> it.map(Integer::parseInt).reduce(Integer::sum).orElseThrow());
  }
}
