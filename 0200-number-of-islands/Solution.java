class Solution {
    private static final int[][] DIRECTIONS = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    public int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int island = 0;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    island++;
                    dfs(i, j, grid);
                }
            }
        }

        return island;
    }

    public void dfs(int row, int col, char[][] grid) {
        if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length || grid[row][col] == '0') {
            return;
        }

        grid[row][col] = '0';

        for (int[] dir : DIRECTIONS) {
            dfs(row + dir[0], col + dir[1], grid);
        }
    }
}
