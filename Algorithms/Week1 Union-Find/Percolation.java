import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private int N;
    private boolean[] sites;
    private int count;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.N = n;
        this.sites = new boolean[N*N+2];
        sites[0] = true;
        sites[N*N+1] = true;
        count = 0;
        uf = new WeightedQuickUnionUF(N*N+2);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row < 1 || row > N || col < 1 || col > N)
            throw new IndexOutOfBoundsException("index out of bounds");

        if(sites[(row-1)*N+col])
            return;

        sites[(row-1)*N+col] = true;// open this site
        count++;

        // head & tail
        if(row == 1)
            uf.union(0,(row-1)*N+col);
        if(row == N)
            uf.union((row-1)*N+col,N*N+1);

        // union judge for 4 directions
        if(col != 1 && sites[(row-1)*N+col-1])
            uf.union((row-1)*N+col,(row-1)*N+col-1);
        if(col != N && sites[(row-1)*N+col+1])
            uf.union((row-1)*N+col,(row-1)*N+col+1);
        if(row != 1 && sites[(row-2)*N+col])
            uf.union((row-1)*N+col,(row-2)*N+col);
        if(row != N && sites[row*N+col])
            uf.union((row-1)*N+col,row*N+col);

    }

    public boolean isOpen(int row, int col) {
        if(row < 1 || row > N || col < 1 || col > N)
            throw new IndexOutOfBoundsException("index out of bounds");
        return sites[(row-1)*N+col];
    }

    //connected to an open site in the top row
    public boolean isFull(int row, int col) {
        if(row < 1 || row > N || col < 1 || col > N)
            throw new IndexOutOfBoundsException("index out of bounds");
        return uf.connected((row-1)*N+col,0);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.connected(0,N*N+1);
    }

    public static void main(String[] args) {
        try{
            FileInputStream input = new FileInputStream("heart25.txt");
            System.setIn(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());

    }

}
