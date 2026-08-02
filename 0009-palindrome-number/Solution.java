class Solution {
    public boolean isPalindrome(int x) {
        String str = Integer.toString(x);
        int n = str.length();
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
