import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Reference: keyvanakbary
 * Url: https://github.com/keyvanakbary/princeton-algorithms/blob/master/week-4-8-puzzle
 * score: 80
 */
public class Solver {

    private class Prior implements Comparable<Prior> {
        private Prior prev;
        private Board board;
        private int num = 0;

        public Prior(Board board) {
            this.board = board;
        }

        public Prior(Board board, Prior prev) {
            this.board = board;
            this.prev = prev;
            this.num = prev.num + 1;
        }

        public Prior(Board board, Prior prev, int num) {
          this.board = board;
          this.prev = prev;
          this.num = num;
        }

        public int compareTo(Prior prev) {
            return (this.board.manhattan() + this.num
                    - prev.board.manhattan() - prev.num);
        }
    }

    private Prior lastMove;

    public Solver(Board initial) {
        MinPQ<Prior> moves = new MinPQ<>();
        moves.insert(new Prior(initial));

        MinPQ<Prior> twinMoves = new MinPQ<>();
        twinMoves.insert(new Prior(initial.twin()));

        while(true) {
            lastMove = Gametree(moves);
            if (lastMove != null || Gametree(twinMoves) != null) return;
        }
    }

    private Prior Gametree(MinPQ<Prior> moves) {
        if (moves.isEmpty()) return null;
        Prior bestMove = moves.delMin();
        if (bestMove.board.isGoal()) return bestMove;
        for (Board neighbor : bestMove.board.neighbors()) {
            if (bestMove.prev == null || !neighbor.equals(bestMove.prev.board)) {
                moves.insert(new Prior(neighbor, bestMove));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return (lastMove != null);
    }

    public int moves(){
        return isSolvable() ? lastMove.num : -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> tree = new Stack<>();
        Prior copyMove = new Prior(lastMove.board, lastMove.prev, lastMove.num);
        while (copyMove != null) {      // 将指针一直追溯到初始搜索节点，并以相反的顺序push
            tree.push(copyMove.board);
            copyMove = copyMove.prev;
        }
        return tree;
    }

    /**
     * Test
     * @param args file name
     */
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
