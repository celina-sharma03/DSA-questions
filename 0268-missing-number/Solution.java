class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int x1 = 0;
        int x2 = 0;

        for (int i = 0; i <= n - 1; i++) {
            x1 = x1 ^ nums[i];
        }
        for (int i = 1; i <= n; i++) {
            x2 = x2 ^ i;
        }

        return x1 ^ x2;
    }
}
