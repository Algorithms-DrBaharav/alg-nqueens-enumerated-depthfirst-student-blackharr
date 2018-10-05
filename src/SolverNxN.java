
/**
 * Solves 8-Queens problem
 * 
 * How to place 8-queens on a chess board such that none can capture another.
 * 
 * Some notes about implementation:
 *   b is one dimensional array, element b[i] represents the queens at location
 *    (row=b[i], col=i).
 * 
 * The function solve() return the board for display.
 * 
 * @author Dr. Baharav
 */
public class SolverNxN {

    
    // Board representation
    private int N;
    int[][] board;
	int[][] numBlanked;
	int[][] invalid;
	int[] initQueen;
	int[] lastQueen;

    // Initialize all to empty
    public SolverNxN(int n) {
        N=n;
        resetBoards();
		updateNumBlanks();
		invalid = new int[N][N];
		initQueen = new int[2];
		lastQueen = new int[2];
    }

    /**
     * 
     * @return int[][] - Board - 2D array, with 1 representing a queen, and 0 
     * represents NO queen (aka empty square)
     * 
     */
    public int[][] solve() {
        int queens;
		int attempts = 1;
		while (true) {
			for (queens = 0; queens < N+1; queens++) {
				if (isValidPosition()) {
					placeQueen();
					if (queens == 0) {
						initQueen[0] = lastQueen[0];
						initQueen[1] = lastQueen[1];
					}
					updateBoard();
					updateNumBlanks();
				} else
					break;
			}
			cleanup();
			printBoard(board);
			System.out.println("Attempts: "+ attempts +", Queens: "+ queens);
			if (queens == N) {
				return cleanup();
			}
			resetBoards();
			invalid[initQueen[0]][initQueen[1]] = 1;
			attempts++;
		}
    }
    
    private void placeQueen() {
		int minx = 0;
		int miny = 0;
                boolean type = false;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (invalid[minx][miny] == 1 && invalid[i][j] != 1 && board[i][j] != 1 && numBlanked[i][j] != -1) {
					minx = i;
					miny = j;
                                        type = true;
					continue;
				}
				boolean smallerValid = numBlanked[i][j] < numBlanked[minx][miny] || numBlanked[minx][miny] == -1;
				if (invalid[i][j] != 1 && numBlanked[i][j] != -1 && smallerValid) {
					minx = i;
					miny = j;
				} else if (invalid[i][j] != 1 && numBlanked[i][j] != -1 && type) {
                                    minx = i;
                                    miny = j;
                                    type = false;
                                }
			}
		board[minx][miny] = 1;
		lastQueen[0] = minx;
		lastQueen[1] = miny;
	}
		
		for (int count = 1; r+count < N && c+count < N; count++)
			board[r+count][c+count] = -1;
		for (int count = 1; r-count >= 0 && c+count < N; count++)
			board[r-count][c+count] = -1;
		for (int count = 1; r+count < N && c-count >= 0; count++)
			board[r+count][c-count] = -1;
		for (int count = 1; r-count >= 0 && c-count >= 0; count++)
			board[r-count][c-count] = -1;
	}
	
	private void updateNumBlanks() {
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++) {
				if (board[r][c] == -1 || board[r][c] == 1)
					numBlanked[r][c] = -1;
				else
					updateTile(r, c);
			}
	}
	
	private void updateTile(int r, int c) {
		int numBlankable = 0;
		for (int i = 0; i < N; i++) {
			if (i != c && board[r][i] == 0)
				numBlankable++;
			if (i != r && board[i][c] == 0)
				numBlankable++;
		}
		
		for (int count = 1; r+count < N && c+count < N; count++)
			if (board[r+count][c+count] == 0)
				numBlankable++;
		for (int count = 1; r-count >= 0 && c+count < N; count++)
			if (board[r-count][c+count] == 0)
				numBlankable++;
		for (int count = 1; r+count < N && c-count >= 0; count++)
			if (board[r+count][c-count] == 0)
				numBlankable++;
		for (int count = 1; r-count >= 0 && c-count >= 0; count++)
			if (board[r-count][c-count] == 0)
				numBlankable++;
		numBlanked[r][c] = numBlankable;
	}
	
	private boolean isValidPosition() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (board[i][j] == 0)
					return true;
		return false;
	}
	
	private void resetBoards() {
		board = new int[N][N];
		numBlanked = new int[N][N];
	}
	
	private int[][] cleanup() {
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (board[i][j] == -1)
					board[i][j] = 0;
		return board;
	}
    
    /*
    
    UTILITY methods below
    
    */
    

    //  Convert into a 2D representation.
    //  Used to communicate out. For display. Simple 8x8 array
    //      bDisplay[ii][jj] is square [ii][jj] on the board.
    //      content of cell: 1 is Queen present, 0 is empty cell.

    private int[][] b2board() {
        int[][]br = new int[N][N];
        for (int ii = 0; ii < N; ++ii) 
            for (int jj = 0; jj < N; ++jj) 
                br[ii][jj] = 0;
            
        
        for (int ii = 0; ii < N; ++ii)
            if (ii==4)
                br[N-1][ii] = 1;
            else    
                br[ii][ii] = 1;

        // Just if you want intermediate printouts, see how it works
        printBoard(br);
        
        return br;
    }

    
    private void printBoard(int[][] b) {
        System.out.println("Board");

        for (int cc = 0; cc < N; ++cc) 
            System.out.print("+-");
        System.out.println("+");

        for (int rr = 0; rr < N; ++rr) {
            for (int cc = 0; cc < N; ++cc) {
                if (b[rr][cc] == 1)
                    System.out.format("|*");
                else
                    System.out.format("| ");                    
            }
            System.out.println("|");
        
            for (int cc = 0; cc < N; ++cc) 
                System.out.print("+-");
            System.out.println("+");
        }

    }

}
