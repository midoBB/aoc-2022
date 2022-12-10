package org.aoc;

import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

class Day10 {
  enum InstructionType {
    NOOP, ADDX
  }
  enum Pixel {
    HASH("#"), DOT(".");
    final String value;

    Pixel(String s) {
      value = s;
    }
  }
  static final class Instruction {
    private final InstructionType type;
    private int cyclesNeeded;
    private final int value;
    Instruction(InstructionType type, int cyclesNeeded, int value) {
      this.type = type;
      this.cyclesNeeded = cyclesNeeded;
      this.value = value;
    }
    public void setCyclesNeeded(int cyclesNeeded) {
      this.cyclesNeeded = cyclesNeeded;
    }
    public int value() {
      return value;
    }
  }

  @Test void firstPuzzle() throws IOException {
    final var input =
      Utils.readInput("Day10").stream().filter(it -> !it.isEmpty())
        .map(String::trim).map(this::parseLine)
        .collect(Collectors.toCollection(LinkedList::new));
    var cycle = 1;
    var register = 1;
    final var signalReadings = new LinkedList<Integer>();
    final var pixels = new LinkedList<Pixel>();
    while (input.size() > 0) {
      readSignal(cycle, register, signalReadings);
      writePixel(cycle, register, pixels);
      var instruction = input.getFirst();
      instruction.setCyclesNeeded(instruction.cyclesNeeded - 1);
      if (instruction.cyclesNeeded == 0) {
        if (instruction.type == InstructionType.ADDX) {
          register += instruction.value;
        }
        input.removeFirst();
      }
      cycle++;
    }
    System.out.println(
      signalReadings.stream().reduce(Integer::sum).orElseThrow());
    final var collect = StreamEx.ofSubLists(pixels, 40)
      .map(it ->it.stream().map(x->x.value).collect(Collectors.joining())).joining("\n");
    System.out.println(collect);
  }

  private void writePixel(int cycle, int register, LinkedList<Pixel> pixels) {
    final var comparedTo = (cycle % 40) - 1;
    final var contains =
      register - 1 <= comparedTo && comparedTo <= register + 1;
    final var pixel = contains ? Pixel.HASH : Pixel.DOT;
    pixels.addFirst(pixel);
  }

  private void readSignal(int cycle, int register,
    LinkedList<Integer> signalReadings) {
    if ((cycle - 20) % 40 == 0) {
      signalReadings.addFirst(cycle * register);
    }
  }

  private Instruction parseLine(String line) {
    if ("noop".equals(line)) {
      return new Instruction(InstructionType.NOOP, 1, 0);
    }
    return new Instruction(InstructionType.ADDX, 2,
      Integer.parseInt(line.split(" ")[1]));
  }
}
