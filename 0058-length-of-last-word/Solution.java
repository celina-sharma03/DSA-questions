class Solution {
    public int lengthOfLastWord(String s) {
        s = s.trim();
        int last = s.lastIndexOf(' ');    // index of the final space, or -1 if there's only one word
        return s.length() - last - 1;
    }
}
