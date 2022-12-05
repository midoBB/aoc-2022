package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

record CargoInstruction(int quantity, int origin, int dest) {
}


record CargoState(String content) {
  boolean isNotEmpty() {
    return content != null && !content.isEmpty();
  }
}


class Day05 {
  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day05");
    final var boxCount = countBoxes(input.get(0));
    final var crates = new ArrayList<LinkedList<CargoState>>();
    for (int i = 0; i < boxCount; i++) {
      crates.add(new LinkedList<>());
    }
    final var initialState = input.stream().takeWhile(it -> !it.isEmpty())
      .filter(it -> !it.matches(".*\\d+.*"));
    initialState.map(this::parseBoxes).forEachOrdered(it -> {
      for (int i = 0; i < it.size(); i++) {
        if (it.get(i).isNotEmpty())
          crates.get(i).addLast(it.get(i));
      }
    });
    final var skipCount = input.stream().takeWhile(it -> !it.isEmpty()).count();
    final var instructions =
      input.stream().skip(skipCount).filter(it -> !it.isEmpty());
    instructions.map(this::parseInstruction).filter(Objects::nonNull).forEachOrdered(it -> {
      for (int i = 0; i < it.quantity(); i++) {
        final var item = crates.get(it.origin() - 1).removeFirst();
        crates.get(it.dest() - 1).addFirst(item);
      }
    });
    System.out.println(
      crates.stream().map(LinkedList::getFirst).map(CargoState::content)
        .map(it -> String.valueOf(it.charAt(1))).collect(Collectors.joining()));
  }

  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day05");
    final var boxCount = countBoxes(input.get(0));
    final var crates = new ArrayList<LinkedList<CargoState>>();
    for (int i = 0; i < boxCount; i++) {
      crates.add(new LinkedList<>());
    }
    final var initialState = input.stream().takeWhile(it -> !it.isEmpty())
      .filter(it -> !it.matches(".*\\d+.*"));
    initialState.map(this::parseBoxes).forEachOrdered(it -> {
      for (int i = 0; i < it.size(); i++) {
        if (it.get(i).isNotEmpty())
          crates.get(i).addLast(it.get(i));
      }
    });
    final var skipCount = input.stream().takeWhile(it -> !it.isEmpty()).count();
    final var instructions =
      input.stream().skip(skipCount).filter(it -> !it.isEmpty());
    instructions.map(this::parseInstruction).filter(Objects::nonNull).forEachOrdered(it -> {
      final var buffer = new LinkedList<CargoState>();
      for (int i = 0; i < it.quantity(); i++) {
        final var item = crates.get(it.origin() - 1).removeFirst();
        buffer.add(item);
      }
      crates.get(it.dest() - 1).addAll(0, buffer);
    });
    System.out.println(
      crates.stream().map(LinkedList::getFirst).map(CargoState::content)
        .map(it -> String.valueOf(it.charAt(1))).collect(Collectors.joining()));
  }

  private List<CargoState> parseBoxes(String line) {
    final var regex = "( {3} |\\[\\w] *?)";
    final var pattern = Pattern.compile(regex);
    final var matcher = pattern.matcher(line);
    return matcher.results().map(MatchResult::group).map(it -> {
      if (it.isEmpty())
        return new CargoState(null);
      else
        return new CargoState(it.trim());
    }).toList();
  }

  private CargoInstruction parseInstruction(String instruction) {
    final var regex = "move (\\d+) from (\\d+) to (\\d+)";
    final var pattern = Pattern.compile(regex, Pattern.MULTILINE);
    final var matcher = pattern.matcher(instruction);
    if (matcher.find()) {
      return new CargoInstruction(Integer.parseInt(matcher.group(1)),
        Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
    }
    return null;
  }

  private long countBoxes(String instruction) {
    final var regex = "( {3} |\\[\\w] *?)";
    final var pattern = Pattern.compile(regex);
    final var matcher = pattern.matcher(instruction);
    return matcher.results().map(MatchResult::group).count();
  }
}
