package org.aoc;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
final class Item {
  private final String name;
  private long size;
  private final Item parent;

  Item(String name, long size, Item parent) {
    this.name = name;
    this.size = size;
    this.parent = parent;
  }

  public String name() {
    return name;
  }

  public long size() {
    return size;
  }

  public Item parent() {
    return parent;
  }

  public void setSize(long size) {
    this.size = size;
  }
  @Override public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (obj == null || obj.getClass() != this.getClass())
      return false;
    var that = (Item) obj;
    return Objects.equals(this.name, that.name) && this.size == that.size
      && Objects.equals(this.parent, that.parent);
  }

  @Override public int hashCode() {
    return Objects.hash(name, size, parent);
  }

  @Override public String toString() {
    return "Item[" + "name=" + name + ", " + "size=" + size + ", " + "parent="
      + parent + ']';
  }
}


class Day07 {
  @Test void firstPuzzle() throws IOException {
    final var input =
      Utils.readInput("Day07").stream().filter(it -> !it.isEmpty())
        .toList();
    final HashMap<String, Item> fsItems = getFsItems(input);
    System.out.println(
      fsItems.values().stream().map(Item::size).filter(size -> size < 100000L).reduce(
        Long::sum).orElseThrow());
  }
  @Test void secondPuzzle() throws IOException {
    final var input =
      Utils.readInput("Day07").stream().filter(it -> !it.isEmpty())
        .toList();
    final HashMap<String, Item> fsItems = getFsItems(input);
    final var freeSpace = 70000000L-fsItems.get("/").size();
    System.out.println(
      fsItems.values().stream().sorted(Comparator.comparingLong(Item::size))
        .filter(item -> freeSpace + item.size() >= 30000000L).findFirst()
        .orElseThrow().size());
  }

  private HashMap<String, Item> getFsItems(List<String> input) {
    final var fsItems = new HashMap<String, Item>();
    fsItems.put("/", new Item("/", 0L, null));
    var current = "/";
    for (var item : input) {
      if (item.startsWith("$ cd")) {
        var next = item.split("\\$ cd ")[1];
        if ("..".equals(next)) {
          current = getParent(current);
        } else {
          if ("/".equals(next)) {
            current = "/";
          } else {
            current = current + next + "/";
          }
        }
      } else if (item.startsWith("dir ")) {
        var dirName = item.split("dir ")[1];
        fsItems.put(current + dirName + "/",
          new Item(dirName, 0L, fsItems.get(current)));
      } else if (Character.isDigit(item.charAt(0))) {
        var size = Long.parseLong(item.split(" ")[0]);
        var temp = fsItems.get(current);
        while(temp != null) {
          temp.setSize(temp.size() + size);
          temp = temp.parent();
        }
      }
    }
    return fsItems;
  }

  private String getParent(String inp) {
    var items =
      new LinkedList<>(Arrays.stream(inp.split("/")).toList());
    items.removeLast();
    return String.join("/",items)+"/";
  }
}
