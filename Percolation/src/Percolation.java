import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private int opensites = 0;
    private int gridLength;
    private boolean[][] grid;
    private int end;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n <= 0){
            throw new IllegalArgumentException("number must be larger than 0");
        }
        //+2 for start and end points start:0 , end = n+1
        uf = new WeightedQuickUnionUF((n*n)+2);
        end = n*n+1;
        gridLength = n;
        grid = new boolean[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row < 1 || col < 1 || row > gridLength || col > gridLength){
            throw new IllegalArgumentException("Out of bounds: " + row + " " + col);
        }

        if(!grid[row-1][col-1]){
            grid[row-1][col-1] = true;
            opensites++;
            int p = ((row-1)*gridLength) + col;
            //Do this if opened top row
            if(row == 1){
                uf.union(((row-1)*gridLength) + col, 0);
            }
            //Do this if opened bottom row
            if(row == gridLength){
                uf.union(((row-1)*gridLength) + col, end);
            }
            //If up, down, left, right is true, connect
            if(row > 1){
                if(grid[row-2][col-1] == true){
                    uf.union(p, p-gridLength);
                }
            }
            if(col > 1){
                if(grid[row-1][col-2] == true){
                    uf.union(p, p-1);
                }
            }
            if(row < gridLength){
                if(grid[row][col-1] == true){
                    uf.union(p, p+gridLength);
                }
            }
            if(col < gridLength){
                if(grid[row-1][col] == true){
                    uf.union(p, p+1);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 1 || col < 1 || row > gridLength || col > gridLength){
            throw new IllegalArgumentException("Out of bounds");
        }
        return grid[row-1][col-1] ? true : false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 1 || col < 1 || row > gridLength || col > gridLength){
            throw new IllegalArgumentException("Out of bounds");
        }
        int p = ((row-1)*gridLength) + col;
        return uf.find(p) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return opensites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(end) == uf.find(0);
    }

    // test client (optional)
    public static void main(String[] args){

    }
}