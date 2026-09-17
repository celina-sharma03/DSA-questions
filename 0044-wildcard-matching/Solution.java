class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            if (p.charAt(i - 1) == '*') {
                dp[i] = dp[i - 1];
            }
        }

        for (int i = 1; i <= m; i++) {
            boolean[] temp = new boolean[n + 1];
            for (int j = 1; j <= n; j++) {
                if (p.charAt(j - 1) == '*') {
                    temp[j] = dp[j] || temp[j - 1];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    temp[j] = dp[j - 1];
                }
            }
            dp = temp;
        }

        return dp[n];
    }
}
