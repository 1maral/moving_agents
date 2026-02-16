package edu.grinnell.csc324;
import java.util.Random;

public class Agent {
    int[] moveProbability;
    public Position curPosition;
    public int foodEaten = 0;
    public int stepsTaken = 0;
    public boolean inFinalState = false;
    Random moveGenerator;

    public Agent(int left, int up, int right, int down, int startRow, int startColumn) {
        this.moveProbability = new int[] {left, up+left, right+up+left, down+right+up+left};
        this.curPosition = new Position(startRow, startColumn);
        this.moveGenerator = new Random();
    }

    public Position tryMove() {
            int moveChoice = this.moveGenerator.nextInt(100) + 1;
            if(moveChoice < this.moveProbability[0]) {
                return new Position(curPosition.row, curPosition.column - 1);
            } else if(moveChoice < this.moveProbability[1]) {
                return new Position(curPosition.row - 1, curPosition.column);
            } else if(moveChoice < this.moveProbability[2]) {
                return new Position(curPosition.row, curPosition.column + 1);
            } else {
                return new Position(curPosition.row + 1, curPosition.column);
            }
        
    }

    public void eatFood() {
        this.foodEaten += 1;
    }

    public class Position {
        public int row;
        public int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
