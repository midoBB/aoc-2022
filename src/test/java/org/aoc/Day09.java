package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


class Day09 {
  enum Dir {
    Right, Left, Up, Down;

    static Dir fromString(String self) {
      switch (self) {
        case "R" -> {
          return Dir.Right;
        }
        case "L" -> {
          return Dir.Left;
        }
        case "U" -> {
          return Dir.Up;
        }
        case "D" -> {
          return Dir.Down;
        }
        default -> throw new IllegalArgumentException();
      }
    }
  }


  record Cord(int x, int y) {
  }


  record Move(Dir dir, int length) {
  }

  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day09").stream().map(String::trim)
      .filter(it -> !it.isEmpty()).map(this::parseLine).toList();
    final var visitedSet = new HashSet<Cord>();
    var currHead = new Cord(0, 0);
    var currTail = new Cord(0, 0);
    visitedSet.add(currTail);
    for (var move : input) {
      while (move.length != 0) {
        currHead = switch (move.dir) {
          case Right -> new Cord(currHead.x + 1, currHead.y);
          case Left -> new Cord(currHead.x - 1, currHead.y);
          case Up -> new Cord(currHead.x, currHead.y + 1);
          case Down -> new Cord(currHead.x, currHead.y - 1);
        };
        if (tailShouldMove(currHead, currTail)) {
          currTail = calculateTailMove(currHead, currTail);
          visitedSet.add(currTail);
        }
        move = new Move(move.dir, move.length - 1);
      }
    }
    System.out.println(visitedSet.size());
  }

  @Test void secondPuzzle() throws IOException {
    final var input = Utils.readInput("Day09").stream().map(String::trim)
      .filter(it -> !it.isEmpty()).map(this::parseLine).toList();
    final var visitedSet = new HashSet<Cord>();
    final var knots = new LinkedList<Cord>();
    for (int i = 0; i < 10; i++) {
      knots.add(new Cord(0, 0));
    }
    visitedSet.add(knots.getLast());
    for (var move : input) {
      while (move.length != 0) {
        var currHead = knots.getFirst();
        currHead = switch (move.dir) {
          case Right -> new Cord(currHead.x + 1, currHead.y);
          case Left -> new Cord(currHead.x - 1, currHead.y);
          case Up -> new Cord(currHead.x, currHead.y + 1);
          case Down -> new Cord(currHead.x, currHead.y - 1);
        };
        knots.set(0, currHead);
        for (int i = 1; i < knots.size(); i++) {
          if (tailShouldMove(knots.get(i - 1), knots.get(i))) {
            var currTail = calculateTailMove(knots.get(i - 1), knots.get(i));
            knots.set(i, currTail);
          }
        }
        visitedSet.add(knots.getLast());
        move = new Move(move.dir, move.length - 1);
      }
    }
    System.out.println(visitedSet.size());
  }

  private Cord calculateTailMove(Cord head, Cord tail) {
    final var newY = head.y > tail.y ? tail.y + 1 : tail.y - 1;
    final var newX = head.x > tail.x ? tail.x + 1 : tail.x - 1;
    if (head.x == tail.x) {
      return new Cord(head.x, newY);
    }
    if (head.y == tail.y) {
      return new Cord(newX, head.y);
    }
    return new Cord(newX, newY);
  }

  private boolean tailShouldMove(Cord currHead, Cord currTail) {
    if (currHead.equals(currTail))
      return false;
    final var sqrt =
      sqrt(pow((currHead.x - currTail.x), 2) + pow(currHead.y - currTail.y, 2));
    return Math.round(sqrt) >= 2;
  }


  private Move parseLine(String line) {
    var parts = line.split(" ");
    var dir = Dir.fromString(parts[0]);
    var len = Integer.parseInt(parts[1]);
    return new Move(dir, len);
  }
}
