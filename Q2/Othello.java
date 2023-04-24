import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];

    // add required class variables here
    private int[] minimax(int [][] state, int depth, int maximizingPlayer,int alpha , int beta,int turn) {   
        if (depth == 0 || terminal(state,maximizingPlayer)) {
            return new int [] {0 , evaluate(state)};
        }
        int bestMove = 0;
        int bestValue;
        if (maximizingPlayer == turn) {
            bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state[i][j] == -1) {
                        if (isValid(state, i, j, turn)) {
                            int[][] newState = getBoardCopy(state);
                            newState[i][j] = turn;
                            convert(newState, i, j, turn);
                            int []child = minimax(newState, depth - 1,alpha,beta, 1 - maximizingPlayer,turn);
                            if (child[1] > bestValue) {
                                bestValue = child[1];
                                bestMove=8*i+j;
                            }
                            alpha = Math.max(alpha, bestValue);
                            if (beta<=alpha)
                                break;
                        }
                    }
                }
            }
        } 
        else {
            bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state[i][j] == -1) {
                        if (isValid(state, i, j, turn)) {   
                            int[][] newState = getBoardCopy(state);
                            newState[i][j] = turn;
                            convert(newState, i, j, turn);
                            int [] child = minimax(newState, depth - 1, alpha,beta,1 - maximizingPlayer,turn);
                            if (child[1] < bestValue) {
                                bestValue = child[1];
                                bestMove=8*i+j;
                            }
                            beta = Math.min(beta, bestValue);
                            if (beta<=alpha)
                                break;
                        }
                    }
                }
            }
        }
        return new int [] {bestMove,bestValue};
    }

    private void print(int[][] state) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int evaluate(int[][] state) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == 0) {
                    score++;
                } else if (state[i][j] == 1) {
                    score--;
                }
            }
        }
        return (score);
    }

    private Boolean isValid(int[][] state, int i, int j, int turn) {
        if (i - 1 >= 0 && state[i - 1][j] == 1 - turn) {
            int k = i - 1;
            while (k >= 0 && state[k][j] == 1 - turn) {
                k--;
            }
            if (k >= 0 && state[k][j] == turn) {
                return true;
            }
        }
        if (i + 1 < 8 && state[i + 1][j] == 1 - turn) {
            int k = i + 1;
            while (k < 8 && state[k][j] == 1 - turn) {
                k++;
            }
            if (k < 8 && state[k][j] == turn) {
                return true;
            }
        }
        if (j - 1 >= 0 && state[i][j - 1] == 1 - turn) {
            int k = j - 1;
            while (k >= 0 && state[i][k] == 1 - turn) {
                k--;
            }
            if (k >= 0 && state[i][k] == turn) {
                return true;
            }
        }
        if (j + 1 < 8 && state[i][j + 1] == 1 - turn) {
            int k = j + 1;
            while (k < 8 && state[i][k] == 1 - turn) {
                k++;
            }
            if (k < 8 && state[i][k] == turn) {
                return true;
            }
        }
        if (i - 1 >= 0 && j - 1 >= 0 && state[i - 1][j - 1] == 1 - turn) {
            int k = i - 1;
            int l = j - 1;
            while (k >= 0 && l >= 0 && state[k][l] == 1 - turn) {
                k--;
                l--;
            }
            if (k >= 0 && l >= 0 && state[k][l] == turn) {
                return true;
            }
        }
        if (i - 1 >= 0 && j + 1 < 8 && state[i - 1][j + 1] == 1 - turn) {
            int k = i - 1;
            int l = j + 1;
            while (k >= 0 && l < 8 && state[k][l] == 1 - turn) {
                k--;
                l++;
            }
            if (k >= 0 && l < 8 && state[k][l] == turn) {
                return true;
            }
        }
        if (i + 1 < 8 && j - 1 >= 0 && state[i + 1][j - 1] == 1 - turn) {
            int k = i + 1;
            int l = j - 1;
            while (k < 8 && l >= 0 && state[k][l] == 1 - turn) {
                k++;
                l--;
            }
            if (k < 8 && l >= 0 && state[k][l] == turn) {
                return true;
            }
        }
        if (i + 1 < 8 && j + 1 < 8 && state[i + 1][j + 1] == 1 - turn) {
            int k = i + 1;
            int l = j + 1;
            while (k < 8 && l < 8 && state[k][l] == 1 - turn) {
                k++;
                l++;
            }
            if (k < 8 && l < 8 && state[k][l] == turn) {
                return true;
            }
        }

        return false;
    }

    private void convert(int[][] state, int i, int j, int turn) {
        if (i - 1 >= 0 && state[i - 1][j] == 1 - turn) {
            int k = i - 1;
            while (k >= 0 && state[k][j] == 1 - turn) {
                k--;
            }
            if (k >= 0 && state[k][j] == turn) {
                for (int l = k + 1; l < i; l++) {
                    state[l][j] = turn;
                }
            }
        }
        if (i + 1 < 8 && state[i + 1][j] == 1 - turn) {
            int k = i + 1;
            while (k < 8 && state[k][j] == 1 - turn) {
                k++;
            }
            if (k < 8 && state[k][j] == turn) {
                for (int l = i + 1; l < k; l++) {
                    state[l][j] = turn;
                }
            }
        }
        if (j - 1 >= 0 && state[i][j - 1] == 1 - turn) {
            int k = j - 1;
            while (k >= 0 && state[i][k] == 1 - turn) {
                k--;
            }
            if (k >= 0 && state[i][k] == turn) {
                for (int l = k + 1; l < j; l++) {
                    state[i][l] = turn;
                }
            }
        }
        if (j + 1 < 8 && state[i][j + 1] == 1 - turn) {
            int k = j + 1;
            while (k < 8 && state[i][k] == 1 - turn) {
                k++;
            }
            if (k < 8 && state[i][k] == turn) {
                for (int l = j + 1; l < k; l++) {
                    state[i][l] = turn;
                }
            }
        }
        if (i - 1 >= 0 && j - 1 >= 0 && state[i - 1][j - 1] == 1 - turn) {
            int k = i - 1;
            int l = j - 1;
            while (k >= 0 && l >= 0 && state[k][l] == 1 - turn) {
                k--;
                l--;
            }
            if (k >= 0 && l >= 0 && state[k][l] == turn) {
                for (int m = k + 1, n = l + 1; m < i; m++, n++) {
                    state[m][n] = turn;
                }
            }
        }
        if (i - 1 >= 0 && j + 1 < 8 && state[i - 1][j + 1] == 1 - turn) {
            int k = i - 1;
            int l = j + 1;
            while (k >= 0 && l < 8 && state[k][l] == 1 - turn) {
                k--;
                l++;
            }
            if (k >= 0 && l < 8 && state[k][l] == turn) {
                for (int m = k + 1, n = l - 1; m < i; m++, n--) {
                    state[m][n] = turn;
                }
            }
        }
        if (i + 1 < 8 && j - 1 >= 0 && state[i + 1][j - 1] == 1 - turn) {
            int k = i + 1;
            int l = j - 1;
            while (k < 8 && l >= 0 && state[k][l] == 1 - turn) {
                k++;
                l--;
            }
            if (k < 8 && l >= 0 && state[k][l] == turn) {
                for (int m = k - 1, n = l + 1; m > i; m--, n++) {
                    state[m][n] = turn;
                }
            }
        }
        if (i + 1 < 8 && j + 1 < 8 && state[i + 1][j + 1] == 1 - turn) {
            int k = i + 1;
            int l = j + 1;
            while (k < 8 && l < 8 && state[k][l] == 1 - turn) {
                k++;
                l++;
            }
            if (k < 8 && l < 8 && state[k][l] == turn) {
                for (int m = k - 1, n = l - 1; m > i; m--, n--) {
                    state[m][n] = turn;
                }
            }
        }
    }

    private Boolean terminal(int[][] state, int turn) {
        // Check if the board is full
        boolean boardFull = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == -1) {
                    boardFull = false;
                    break;
                }
            }
        }
        if (boardFull) {
            return true;
        }
        
        Boolean noValidMoves = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == -1 && isValid(state, i, j, turn)) {
                    noValidMoves = false;
                    break;
                }
            }
        }
        if (noValidMoves) {
            return true;
        }
        Boolean noPieces = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == turn) {
                    noPieces = false;
                    break;
                }
            }
        }
        if (noPieces) {
            return true;
        }
        int numPiecesPlayer1 = 0;
        int numPiecesPlayer2 = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == 0) {
                    numPiecesPlayer1++;
                } else if (state[i][j] == 1) {
                    numPiecesPlayer2++;
                }
            }
        }
        if (numPiecesPlayer1 == 0 || numPiecesPlayer2 == 0) {
            return true;
        }
        
        return false;
    }

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        // Student can choose to add preprocessing here
    }

    // add required helper functions here
    private int score(int[][] state, int turn) {
        int num_black_tiles = 0;
        int num_white_tiles = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (state[i][j] == 0) {
                    num_black_tiles++;
                } else if (state[i][j] == 1) {
                    num_white_tiles++;
                }
            }
        }
        return turn==0?(num_black_tiles - num_white_tiles):(num_white_tiles - num_black_tiles);
    }

    public int boardScore() {
        /*
         * Complete this function to return num_black_tiles - num_white_tiles if turn =
         * 0,
         * and num_white_tiles-num_black_tiles otherwise.
         */
        return score(board, turn);
    }

    public int bestMove(int k) {
        /*
         * Complete this function to build a Minimax tree of depth k (current board
         * being at depth 0),
         * for the current player (siginified by the variable turn), and propagate
         * scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j;
         * return i*8+j
         * In case of ties, return the smallest integer value representing the tile with
         * best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the
         * boardScore() is the score
         * for the same player throughout the Minimax tree.
         */
        int [][]state =  getBoardCopy();
        int [] result = minimax(state, k, 0,Integer.MIN_VALUE,Integer.MAX_VALUE ,turn);
        // print(state);
        return result[0];
    }

    public ArrayList<Integer> fullGame(int k) {
        /*
         * Complete this function to compute and execute the best move for each player
         * starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and
         * the turn
         * at each step. In the end, modify the winner variable as required.
         */
        ArrayList<Integer> moves = new ArrayList<Integer>();
        int [][] state = getBoardCopy();
        int turn = getTurn();
        print(state);
        System.out.print(k);
        while(!terminal(state,turn)){
            int[] result = minimax(state,k, 0,Integer.MIN_VALUE,Integer.MAX_VALUE, turn);
            int bestMove = result[0];
            int i =bestMove/8;
            int j = bestMove%8;
            if(!isValid(state, i, j, turn)){
                turn = 1 - turn;
                moves.add(0);
                continue;
            }
            state[i][j] = turn;
            convert(state, i, j, turn);
            turn = 1 - turn;
            moves.add(bestMove);
        }
        if(evaluate(state)>0){
            winner = 0;
        }
        else if(evaluate(state)<0){
            winner = 1;
        }
        return moves;
    }
    private int[][] getBoardCopy(int board[][]) {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for (int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }
}