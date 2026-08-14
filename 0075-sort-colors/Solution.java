import java.util.HashMap;

class Solution {
    public void sortColors(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 0);
        map.put(2, 0);

        for (int k : nums) {
            map.put(k, map.get(k) + 1);
        }

        int index = 0;
        for (int i = 0; i < 3; i++) {
            int f = map.get(i);
            for (int j = 0; j < f; j++) {
                nums[index] = i;
                index++;
            }
        }
    }
}
