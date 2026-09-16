class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int element = nums[i];                    // 4
            if (element >= 1 && element <= n) {
                int chair = element - 1;              // 3 (if it's not missing)
                if (nums[chair] != element) {
                    swap(nums, chair, i);
                    i--;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (i + 1 != nums[i]) return i + 1;
            // at position 0, 1 is sitting - that's fine; same for 1 and 2
            // (i+1 == nums[i]) -> at its correct position
            // (i+1 != nums[i]) -> not at its correct position
        }

        return n + 1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
