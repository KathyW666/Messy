import java.util.LinkedList;

/** Week 4 - 8 puzzle
 * Reference: keyvanakbary
 * Url: https://github.com/keyvanakbary/princeton-algorithms/blob/master/week-4-8-puzzle
 */
public class Board {
    private static int[][] blocks;

    public Board(int[][] blocks) { //immutable data type
        this.blocks = copy(blocks);
    }

    private int[][] copy(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

    public int dimension() {
        return blocks.length;
    }

    /**
     * Number of blocks out of place
     * @return count
     */
    public int hamming() {
        int count = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (blocks[row][col] == 0) continue;
                if (blocks[row][col] != row * dimension() + col + 1)
                    count++;
            }
        }
        return count;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     * @return sum
     */
    public int manhattan() {
        int sum = 0;
        int r, c;
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (blocks[row][col] == 0) continue;
                r = (blocks[row][col] - 1) / dimension();
                c = (blocks[row][col] - 1) % dimension();
                sum += (Math.abs(row - r) + Math.abs(col - c));
            }
        }
        return sum;
    }

    public boolean isGoal() {
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (blocks[row][col] == 0) continue;
                if (blocks[row][col] != row * dimension() + col + 1) return false;
            }
        }
        return true;
    }

    /**
     * Judging whether a Board is solvable with a twin Board
     * @return twin Board
     */
    public Board twin() {
        int[][] copy = copy(blocks);
        int rr = 0;
        if (blocks[rr][0] * blocks[rr][1] == 0) rr = 1;
        copy[rr][0] = blocks[rr][1];
        copy[rr][1] = blocks[rr][0];
        return new Board(copy);
    }

    public boolean equals(Object that) { // instanceof 指出对象是否是特定类的一个实例
        if (this == that) return true;
        if (that == null || !(that instanceof Board)
                || ((Board) that).blocks.length != blocks.length) return false;
        for (int row = 0; row < dimension(); row++)
            for (int col = 0; col < dimension(); col++)
                if (((Board) that).blocks[row][col] != blocks[row][col]) return false;
        return true;
    }

    /**
     * Add the available neighbors to the priority queue
     * @return an Iterable LinkedList<Board>
     */
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();

        int sR= SpaceLocation()[0];
        int sC = SpaceLocation()[1];

        if (sR > 0) neighbors.add(new Board(swap(sR, sC, sR - 1, sC)));
        if (sR < dimension() - 1) neighbors.add(new Board(swap(sR, sC, sR + 1, sC)));
        if (sC > 0) neighbors.add(new Board(swap(sR, sC, sR, sC - 1)));
        if (sC < dimension() - 1) neighbors.add(new Board(swap(sR, sC, sR, sC + 1)));

        return neighbors;
    }

    /**
     * Swap two blocks
     * @param r1 row of block1
     * @param c1 col of block1
     * @param r2 row of block2
     * @param c2 col of block2
     * @return the swapped array
     */
    private int[][] swap(int r1, int c1, int r2, int c2) {
        int[][] sblocks = copy(blocks);
        int tmp = sblocks[r1][c1];
        sblocks[r1][c1] = sblocks[r2][c2];
        sblocks[r2][c2] = tmp;

        return sblocks;
    }

    /**
     * @return an array with the location of Space
     */
    private int[] SpaceLocation() {
        int flag = 0;
        int[] SL = new int[2];
        for (int row = 0; row < dimension(); row++) {
            for (int col = 0; col < dimension(); col++) {
                if (blocks[row][col] == 0) {
                    SL[0] = row;
                    SL[1] = col;
                    flag = 1;
                    break;
                }
            }
            if (flag != 0) break;
        }
        return SL;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
