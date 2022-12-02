package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class Day02 {

  @Test void firstPuzzle() throws IOException {
    final var input = Utils.readInput("Day02");
    final var res =
      input.stream().map(Day02::rpsScore).reduce(Integer::sum).orElseThrow();
    System.out.println(res);
  }

  @Test void secondPuzzle() throws IOException {
    final var input =
      Utils.readInput("Day02").stream().filter(it -> !it.isBlank());
    final var res =
      input.map(Day02::rpsMake).reduce(Integer::sum).orElseThrow();
    System.out.println(res);
  }

  private static int rpsMake(String game) {
    final var hands = List.of(game.split(" "));
    final var opp = hands.get(0);
    final var result = hands.get(1);
    final var me = getMyHandFromResult(opp, result);
    return calculateGameScore(opp, me);
  }

  private static String getMyHandFromResult(String opp, String result) {
    if ("A".equals(opp)) {
      if ("X".equals(result))
        return "Z";
      else if ("Y".equals(result))
        return "X";
      else
        return "Y";
    } else if ("B".equals(opp)) {
      if ("X".equals(result))
        return "X";
      else if ("Y".equals(result))
        return "Y";
      else
        return "Z";
    } else {
      if ("X".equals(result))
        return "Y";
      else if ("Y".equals(result))
        return "Z";
      else
        return "X";
    }
  }

  private static int rpsScore(String game) {
    final var hands = List.of(game.split(" "));
    final var opp = hands.get(0);
    final var me = hands.get(1);
    return calculateGameScore(opp, me);
  }

  private static int calculateGameScore(String opp, String me) {
    int score = 0;
    if ("X".equals(me)) {
      score += 1;
      if ("A".equals(opp)) {
        score += 3;
      } else if ("C".equals(opp)) {
        score += 6;
      }
    } else if ("Y".equals(me)) {
      score += 2;
      if ("A".equals(opp)) {
        score += 6;
      } else if ("B".equals(opp)) {
        score += 3;
      }
    } else {
      score += 3;
      if ("B".equals(opp)) {
        score += 6;
      } else if ("C".equals(opp)) {
        score += 3;
      }
    }
    return score;
  }
}
