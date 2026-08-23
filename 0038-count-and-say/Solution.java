class Solution {
    public String countAndSay(int n) {
        if (n <= 0) return "";

        String currentTerm = "1";

        for (int i = 1; i < n; i++) {
            StringBuilder nextTerm = new StringBuilder();
            int count = 1;

            for (int j = 1; j < currentTerm.length(); j++) {
                if (currentTerm.charAt(j) == currentTerm.charAt(j - 1)) {
                    count++;
                } else {
                    nextTerm.append(count).append(currentTerm.charAt(j - 1));
                    count = 1;
                }
            }
            nextTerm.append(count).append(currentTerm.charAt(currentTerm.length() - 1));
            currentTerm = nextTerm.toString();
        }

        return currentTerm;
    }
}
