package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

record Assignment(List<Integer> first, List<Integer> second) {
}


class Day04 {
  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day04");
    final var count =
      input.stream().map(this::parseLine).filter(this::checkPartOne).count();
    System.out.println(count);
  }

  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day04");
    final var count =
      input.stream().map(this::parseLine).filter(this::checkPartTwo).count();
    System.out.println(count);
  }

  private boolean checkPartTwo(Assignment assignment) {
    return !Collections.disjoint(assignment.first(), assignment.second());
  }

  private boolean checkPartOne(Assignment assignment) {
    return assignment.first().containsAll(assignment.second())
      || assignment.second().containsAll(assignment.first());
  }

  private Assignment parseLine(String line) {
    //2-4,6-8
    final var split = line.split(",");
    final var first = split[0].split("-");
    final var second = split[1].split("-");
    final var f = IntStream.rangeClosed(Integer.parseInt(first[0]),
      Integer.parseInt(first[1])).boxed().toList();
    final var s = IntStream.rangeClosed(Integer.parseInt(second[0]),
      Integer.parseInt(second[1])).boxed().toList();

    return new Assignment(f, s);
  }
}
