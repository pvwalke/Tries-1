// Time Complexity:
//   insert(): O(L), where L is the length of the word
//   search(): O(L)
//   startsWith(): O(L)
// Space Complexity: O(26 * L * N) in worst case, where N is number of words and L is avg word length

//We use a Trie to store words where each node represents a character.
// Each path from the root to a leaf or intermediate node may represent a full word (tracked using isEnd).
// The insert method builds the Trie, search checks for full-word presence, and startsWith checks if any word starts with a given prefix.
class Trie {
    TrieNode root;

    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            this.children = new TrieNode[26]; // For 'a' to 'z'
        }
    }

    public Trie() {
        this.root = new TrieNode();
    }

    // Inserts a word into the trie
    public void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                curr.children[index] = new TrieNode();
            }
            curr = curr.children[index];
        }
        curr.isEnd = true; // Mark end of word
    }

    // Returns true if the word exists in the trie
    public boolean search(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                return false;
            }
            curr = curr.children[index];
        }
        return curr.isEnd; // Ensure it's a complete word
    }

    // Returns true if there is any word in the trie that starts with the given prefix
    public boolean startsWith(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            int index = c - 'a';
            if (curr.children[index] == null) {
                return false;
            }
            curr = curr.children[index];
        }
        return true;
    }
}

// Time Complexity: O(N * L + M * K)
//   - N: number of words in dictionary, L: average length of words in dictionary
//   - M: number of words in the sentence, K: average length of sentence words
// Space Complexity: O(N * L) for the Trie structure

//We build a Trie using all root words from the dictionary to allow fast prefix lookups.
// Then, for each word in the sentence, we traverse the Trie to find the shortest matching root prefix and replace the word if such a root is found.
// The result is reconstructed with each word replaced by its root (if applicable).



class Solution {
    TrieNode root;

    // Trie Node definition
    class TrieNode {
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            this.children = new TrieNode[26]; // for lowercase a-z
            isEnd = false;
        }
    }

    // Insert a word into the Trie
    private void insert(String word) {
        TrieNode curr = root;
        for (char c : word.toCharArray()) {
            if (curr.children[c - 'a'] == null) {
                curr.children[c - 'a'] = new TrieNode();
            }
            curr = curr.children[c - 'a'];
        }
        curr.isEnd = true; // Mark end of a root word
    }

    public String replaceWords(List<String> dictionary, String sentence) {
        this.root = new TrieNode();

        // Build the Trie from the dictionary
        for (String word : dictionary) {
            insert(word);
        }

        StringBuilder result = new StringBuilder();
        String[] splitArr = sentence.split(" ");

        // Process each word in the sentence
        for (int i = 0; i < splitArr.length; i++) {
            if (i > 0) result.append(" ");
            String word = splitArr[i];
            result.append(getShortestVersion(word));
        }

        return result.toString();
    }

    // Retrieve shortest root prefix for a word using Trie
    private String getShortestVersion(String word) {
        TrieNode curr = root;
        StringBuilder sb = new StringBuilder();

        for (char c : word.toCharArray()) {
            // If path breaks or a valid root is found, stop traversal
            if (curr.children[c - 'a'] == null || curr.isEnd) {
                break;
            }
            curr = curr.children[c - 'a'];
            sb.append(c);
        }

        // If root prefix was found, return it; else return original word
        if (curr.isEnd) {
            return sb.toString();
        }
        return word;
    }
}

