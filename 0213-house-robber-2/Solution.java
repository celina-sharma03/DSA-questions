import java.util.Arrays;
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        return Math.max(robHelper(nums, 0, nums.length - 2), robHelper(nums, 1, nums.length - 1));
    }
    
    private int robHelper(int[] nums, int start, int end) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, -1);
        return robRecursive(nums, start, end, dp);
    }
    
    private int robRecursive(int[] nums, int i, int end, int[] dp) {
        if (i > end) {
            return 0; 
        }
        if (dp[i] != -1) {
            return dp[i];
        }
        int skip = robRecursive(nums, i + 1, end, dp);
        int take = nums[i] + robRecursive(nums, i + 2, end, dp);        
        dp[i] = Math.max(skip, take); 
        return dp[i];
    }
}