import java.util.HashMap;

class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] word = s.split(" ");

        if (pattern.length() != word.length) {
            return false;
        }

        HashMap<Character, String> map = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            boolean key = map.containsKey(ch);

            if (map.containsValue(word[i]) && !key) {
                return false;
            }

            if (key && !map.get(ch).equals(word[i])) {
                return false;
            } else {
                map.put(ch, word[i]);
            }
        }

        return true;
    }
}
