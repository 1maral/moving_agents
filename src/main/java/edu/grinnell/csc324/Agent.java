package edu.grinnell.csc324;
import java.util.Random;

public class Agent {
    Random moveGenerator;
    // 0 -> left 1-> right 2-> up 3-> down 4-> still
    int currentMove;
    public int weight;
    public int payOff;
    // -1 -> penalty, 0 -> non-penalty, 1 -> terminal
    public int Type;


    public Agent(int weight, int Type, int payOff) {
        this.moveGenerator = new Random();
        this.payOff = payOff;
    }

    public int voteMove() {
        currentMove = this.moveGenerator.nextInt(5);
        return currentMove;
    }
}
