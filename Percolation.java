import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int len;
    private int nopen;
    private int[] isopen;
    private int r, c;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n < 0) {
            throw new IllegalArgumentException("index " + n + " can not be negative");
        }
        nopen = 0;
        len = n;
        grid = new WeightedQuickUnionUF(n * n + 3);
        isopen = new int[n * n + 3];
        r = 0;
        c = 0;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        r = row - 1;
        c = col - 1;
        if (r < 0 || c < 0 || r > len || c > len) {
            throw new IllegalArgumentException(
                    "indexes are not valid in open(). R is " + r + " and C is " + c);
        }
        if (r == 0) {
            grid.union(0, convert(r, c) + 1);
        }
        if (r == len - 1) {
            grid.union(convert(r, c) + 1, len * len + 1);
        }
        if (!isOpen(row, col)) {
            int id = convert(r, c) + 1;
            isopen[id] = 1;
            nopen++;
            if (r == 0) {
                if (c == 0) {
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                }
                else if (c == len - 1) {
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                }
                else {
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                }
            }
            else if (r == len - 1) {
                if (c == 0) {
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                }
                else if (c == len - 1) {
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                }
                else {
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                }
            }
            else {
                if (c == 0) {
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                }
                else if (c == len - 1) {
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                }
                else {
                    if (isOpen(row, col + 1))
                        grid.union(convert(r, c) + 1, convert(r, c + 1) + 1);
                    if (isOpen(row + 1, col))
                        grid.union(convert(r, c) + 1, convert(r + 1, c) + 1);
                    if (isOpen(row, col - 1))
                        grid.union(convert(r, c) + 1, convert(r, c - 1) + 1);
                    if (isOpen(row - 1, col))
                        grid.union(convert(r, c) + 1, convert(r - 1, c) + 1);
                }
            }
        }
    }

    private int convert(int row, int col) {
        if (row < 0 || col < 0 || row > len || col > len) {
            throw new IllegalArgumentException("indexes are not valid in Convert");
        }
        return row * len + col;
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || col < 1 || row > len || col > len) {
            throw new IllegalArgumentException(
                    "indexes are not valid in isOpen. row = " + row + " and col = " + col);
        }
        return isopen[convert(row - 1, col - 1) + 1] != 0;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || col < 1 || row > len || col > len) {
            throw new IllegalArgumentException("indexes are not valid ");
        }
        boolean full = grid.connected(0, convert(row - 1, col - 1) + 1);
        return full;
    }

    public int numberOfOpenSites() {
        return nopen;
    }       // number of open sites

    public boolean percolates() {
        return grid.connected(0, len * len + 1);
    }              // does the system percolate?

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation jamma;
        jamma = new Percolation(n);
        jamma.open(-1, 6);
        jamma.open(11, 5);

    }


    // test client (optional)
}
