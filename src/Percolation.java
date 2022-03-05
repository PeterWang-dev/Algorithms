/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF site;
    private boolean[] status;
    private int count; // The number of open sites.
    private int len;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n should above" + 0);

        len = n;

        // initialize site to be closed (status[0] is not used)
        status = new boolean[n * n + 1];
        count = 0;
        for (int i = 0; i < n * n + 1; i++) {
            status[i] = false;
        }

        // virtualize top site and bottom site
        site = new WeightedQuickUnionUF(n * n + 2);

    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int position = (row - 1) * len + col;
            status[position] = true;

            if (row == 1) { // first line
                site.union(position, 0);
            }
            else if (row == len) { // last line
                site.union(position, len * len + 1);
            }

            if (row < len && isOpen(row + 1, col))
                site.union(position, position + len);
            if (row > 1 && isOpen(row - 1, col))
                site.union(position, position - len);
            if (col < len && isOpen(row, col + 1))
                site.union(position, position + 1);
            if (col > 1 && isOpen(row, col - 1))
                site.union(position, position - 1);

            count++;
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        int position = (row - 1) * len + col;
        if (status[position])
            return true;
        return false;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int position = (row - 1) * len + col;
        if (site.find(position) == site.find(0))
            return true;
        return false;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            StdOut.println(row + " " + col + " is NOT valid.");
            throw new IllegalArgumentException("row or col is not between " + 1 + " and " + len);
        }
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        if (site.find(0) == site.find(len * len + 1))
            return true;
        return false;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation sites = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            if (sites.isOpen(row, col)) continue;
            sites.open(row, col);
        }
        StdOut.println("The number of opened sites is " + sites.numberOfOpenSites());

        if (sites.isFull(n / 2, n / 2))
            StdOut.println("(" + (n / 2) + "," + (n / 2) + ") is a full site");

        if (sites.percolates())
            StdOut.println("The system is percolated.");
        else
            StdOut.println("The system is not percolated.");
    }
}
