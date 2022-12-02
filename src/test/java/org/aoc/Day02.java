package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

class Day02 {
    private static final Map<String, Integer> part1 = Map.ofEntries(
            entry("A X", 4), entry("B X", 1), entry("C X", 7),
            entry("A Y", 8), entry("B Y", 5), entry("C Y", 2),
            entry("A Z", 3), entry("B Z", 9), entry("C Z", 6)
    );
    private static final Map<String, Integer> part2 = Map.ofEntries(
            entry("A X", 3), entry("B X", 1), entry("C X", 2),
            entry("A Y", 4), entry("B Y", 5), entry("C Y", 6),
            entry("A Z", 8), entry("B Z", 9), entry("C Z", 7)
    );

    @Test
    void firstPuzzle() throws IOException {
        final var input = Utils.readInput("Day02");
        final var res =
                input.stream().map(part1::get).reduce(Integer::sum).orElseThrow();
        System.out.println(res);
    }

    @Test
    void secondPuzzle() throws IOException {
        final var input =
                Utils.readInput("Day02").stream().filter(it -> !it.isBlank());
        final var res =
                input.map(part2::get).reduce(Integer::sum).orElseThrow();
        System.out.println(res);
    }
}
