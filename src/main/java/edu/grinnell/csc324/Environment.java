package edu.grinnell.csc324;

import edu.grinnell.csc324.Agent.Position;

public class Environment {
  /* The (negative) reward incurred when a state transitions to another
  (non-terminal) state. */
  private static final int RNEG = -1;

  /* The (positive) reward when a state reaches the terminal. */
  private static final int RPOS = 1;

  private char[][] board;

  public Environment() {
    board = new char[25][25];
    initialize();
  }
 

  public void initialize() {
    char[][] top_left = 
    {
    {'O','O','O','O','O','O',' ',' ',' ',' ',' ',' ',' '},
    {'O',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {'O',' ',' ','O','O',' ',' ',' ','O','O',' ','O','O'},
    {'O',' ','O','O','O',' ',' ',' ','O','O',' ','O',' '},
    {'O',' ','O','O','O',' ',' ',' ',' ',' ',' ',' ',' '},
    {'O',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
    {' ',' ','O','O',' ',' ',' ',' ','O','O','O','O',' '},
    {' ',' ','O','O',' ',' ',' ',' ','O','O','O',' ',' '},
    {' ',' ',' ',' ',' ',' ',' ',' ','O','O',' ',' ',' '},
    {' ',' ','O','O',' ',' ',' ',' ','O',' ',' ',' ',' '},
    {' ',' ','O',' ',' ',' ',' ',' ',' ',' ',' ',' ','O'}};
    // System.out.println(Arrays.deepToString(top_corner));

    

    for (int row = 0; row < 13; row++) {
        System.arraycopy(top_left[row], 0, board[row], 0, 13);
    }

    // char[][] top_right = new char[13][12];

    for (int row = 0; row < 12; row++) {
        for (int column = 24; column > 12; column--) {
          board[row][column] = top_left[row][24 - column];
      }
    }

    for (int row = 24; row > 12; row--) {
      for (int column = 0; column < 25; column++) {
        board[row][column] = board[24-row][column];
      }
    }

    board[4][9] = '$';
    board[4][19] = '$';
    board[5][20] = '$';
    board[10][14] = '$';
    board[10][17] = '$';
    board[10][22] = '$';
    board[11][0] = '$';
    board[13][14] = '$';
    board[14][12] = '$';
    board[14][4] = '$';
    board[14][2] = '$';
    board[20][5] = '$';
    board[23][1] = '$';
    board[23][22] = '$';
    board[23][23] = '$';
    board[18][12] = 'F';

  }

  public void print() {
    for (int row = 0; row < 25; row++) {
      for (int column = 0; column < 25; column++) {
        System.out.print(board[row][column] + " ");
      }
      System.out.println();
    }
  }

  public static void runTest() {
    Environment environment = new Environment();

    Agent agent1 = new Agent(20, 20, 30, 30, 13, 24);
    Agent agent2 = new Agent(20, 20, 30, 30, 7, 0);
    Agent agent3 = new Agent(20, 20, 30, 30, 24, 12);
    Agent.Position a1CurPos = agent1.curPosition;
    Agent.Position a2CurPos = agent2.curPosition;
    Agent.Position a3CurPos = agent3.curPosition;


    environment.board[a1CurPos.row][a1CurPos.column] = 'A';
    environment.board[a2CurPos.row][a2CurPos.column] = 'B';
    environment.board[a3CurPos.row][a3CurPos.column] = 'C';
    environment.print();

    while (!agent1.inFinalState || !agent2.inFinalState || !agent3.inFinalState) {
              if (!agent1.inFinalState) {
                Agent.Position attempt = agent1.tryMove();
                if (environment.checkMove(attempt, agent1)) {
                  agent1.stepsTaken += 1;
                  environment.board[agent1.curPosition.row][agent1.curPosition.column] = ' ';
                  agent1.curPosition = attempt;
                  environment.board[attempt.row][attempt.column] = 'A';
                }
              }

              if (!agent2.inFinalState) {
                Agent.Position attempt = agent2.tryMove();
                if (environment.checkMove(attempt, agent2)) {
                  agent2.stepsTaken += 1;
                  environment.board[agent2.curPosition.row][agent2.curPosition.column] = ' ';
                  agent2.curPosition = attempt;
                  environment.board[attempt.row][attempt.column] = 'B';
                }
              }

              if (!agent3.inFinalState) {
                Agent.Position attempt = agent3.tryMove();
                if (environment.checkMove(attempt, agent3)) {
                  agent3.stepsTaken += 1;
                  environment.board[agent3.curPosition.row][agent3.curPosition.column] = ' ';
                  agent3.curPosition = attempt;
                  environment.board[attempt.row][attempt.column] = 'C';
                }
                System.out.println("----------------------------------------------------\n");
                environment.print();
                // break;
              }
            }
    System.out.println("Agent 1 took " + agent1.stepsTaken + " steps.\nAgent 2 took " + agent2.stepsTaken + " steps. \nAgent 3 took " + agent3.stepsTaken + " steps!\n");
    System.out.println("Agent 1 ate " + agent1.foodEaten + " food.\nAgent 2 ate " + agent2.foodEaten + " food. \nAgent 3 ate " + agent3.foodEaten + " food!\n");

  }

  private boolean checkMove(Position attempt, Agent agent) {
    if(attempt.row < 0 || attempt.row > 24 || attempt.column < 0 || attempt.column > 24) {
      return false;
    }
    char attemptChar = this.board[attempt.row][attempt.column];
    if(attemptChar == 'O' || attemptChar == 'A' || attemptChar == 'B' || attemptChar == 'C') {
      return false;
    }

    if (board[attempt.row][attempt.column] == '$') {
      agent.eatFood();
    }

    if (board[attempt.row][attempt.column] == 'F') {
      agent.stepsTaken += 1;
      agent.inFinalState = true;
      this.board[agent.curPosition.row][agent.curPosition.column] = ' ';
      agent.curPosition = attempt;
      return false;
    }

    return true;
        
  }
  public static void main(String[] args) {
    runTest();
  }
}




  

