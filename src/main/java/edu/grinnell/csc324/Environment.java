package edu.grinnell.csc324;

public class Environment {
  private Agent[][] board;
  int[] objSize;
  int[] objTopLeft;
  int decidedMove;

  public Environment() {
    board = new Agent[7][7];

    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
          this.board[i][j] = new Agent(0, 0, 0);
      }
    }
    objSize = new int[2];
    objTopLeft = new int[2];
    decidedMove = -1;
    initialize();
  }

  private void initialize() {
    for (int i = 0; i < 7; i++) {
      board[i][0].Type = -1;
      board[i][6].Type = -1;

      for (int j = 1; j < 6; j++) {
        board[i][j].Type = 0;
      }
    } 
    board[5][2].Type = 1;
    objSize[0] = 2;
    objSize[1] = 3;

    objTopLeft[0] = 0;
    objTopLeft[1] = 3;
  }

  public void updateWeight(){
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        if (i == objTopLeft[0] || i == (objTopLeft[0] + 1)) {
          if (j == objTopLeft[1] || j == (objTopLeft[1] + 1) || j == (objTopLeft[1] + 2)) {
            board[i][j].weight = 5;
          } else if (j == (objTopLeft[1] - 1) || j == (objTopLeft[1] + 3)){
            board[i][j].weight = 1;
          } else {
            board[i][j].weight = 0;
          }
        } else if (i == objTopLeft[0] - 1 || i == (objTopLeft[0] + 2)) {
          if (j == objTopLeft[1] || j == (objTopLeft[1] + 1) || j == (objTopLeft[1] + 2)) {
            board[i][j].weight = 1;
          } else {
            board[i][j].weight = 0;
          }
        } else {
          board[i][j].weight = 0;
        }
      }
    }
  }


  public void decideMove() {
    int[] moves = new int[5];
    int votemove = -1;
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        votemove = board[i][j].voteMove();
        moves[votemove] = moves[votemove] + board[i][j].weight;
      }
    }
    int max = -1;
    int maxIndex = 0;
    for (int i = 0; i < 5; i++) {
      if(moves[i] > max) {
        max = moves[i];
        maxIndex = i;
      }
    }
    decidedMove = maxIndex;
  }

  public void moveObj() {
    decideMove();
    if (decidedMove == 0) {
      if (objTopLeft[1] != 0) {
        objTopLeft[1]--;
      }
    } else if (decidedMove == 1) {
      if (objTopLeft[1] != 4) {
        objTopLeft[1]++;
      }
    } else if (decidedMove == 2) {
      if (objTopLeft[0] != 0) {
        objTopLeft[0]--;
      }
    } else if (decidedMove == 3) {
      if (objTopLeft[0] != 5) {
        objTopLeft[0]++;
      }
    } else {
      // no move
    }
    updateWeight();
    //checkType();
  }

  public boolean checkType() {
    if (board[objTopLeft[0]][objTopLeft[1]].Type == -1 || board[objTopLeft[0]][objTopLeft[1] + 2].Type == -1) {
        assignPayoffs(-10);
        return true;
    } else if (board[objTopLeft[0]][objTopLeft[1]].Type == 1) {
      assignPayoffs(100);
      return false;
    } else {
      assignPayoffs(-1);
      return true;
    }
  }

  public void assignPayoffs(int score) {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        board[i][j].payOff += score;
      }
    }
  }

  public void print() {
    for (int row = 0; row < 7; row++) {
      for (int column = 0; column < 7; column++) {
        System.out.print(board[row][column].weight + " ");
      }
      System.out.println();
    }
  }
  public static void main(String[] args) {
    Environment environment = new Environment();
    System.out.println("Choose default mode (0) or fast mode (1). ");
    System.out.println("1");
    while (environment.checkType()) {
      environment.moveObj();
      //System.out.println("The move at " + environment.board[0][0].payOff+  " is " + environment.decidedMove);
      environment.print();
      System.out.println();
    }
    System.out.println("The final score is: " + environment.board[0][0].payOff);
  }
}




  

