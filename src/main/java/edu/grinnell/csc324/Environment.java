package edu.grinnell.csc324;

import edu.grinnell.csc324.Agent.Position;

public class Environment {
  private char[][] board;

  public Environment() {
    board = new char[25][25];
    // initialize();
  }

  public void print() {
    for (int row = 0; row < 25; row++) {
      for (int column = 0; column < 25; column++) {
        System.out.print(board[row][column] + " ");
      }
      System.out.println();
    }
  }
  public static void main(String[] args) {
    // runTest();
  }
}




  

