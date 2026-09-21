import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> ans = new ArrayList<>();
        ans.add(new int[] { intervals[0][0], intervals[0][1] });
        int index = 0;

        for (int[] interval : intervals) {
            int a = interval[0];
            int b = interval[1];

            if (a <= ans.get(index)[1]) {
                if (b > ans.get(index)[1]) {
                    ans.get(index)[1] = b;
                }
            } else {
                ans.add(new int[] { a, b });
                index++;
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }
}
