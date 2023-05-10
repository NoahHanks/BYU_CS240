package spell;

public class Trie implements ITrie{
        private Node root;
        private int wordCount;
        private int nodeCount;
        private int hash;

        public Trie(){
            root = new Node('\0');
            wordCount = 0;
            nodeCount = 1;
            hash = 0;
        }

    @Override
    public void add(String word) {
        final int hashRandomizer = 17;
        char c;
        int index;
        Node curr = root;

        word = word.toLowerCase();
        hash += hashRandomizer * word.hashCode();

        for(int i = 0; i < word.length(); i++){
            c = word.charAt(i);
            index = (int)(c - 'a');
            if(curr.getChildren()[index] == null){
                curr.getChildren()[index] = new Node(c);
                nodeCount++;
            }
            if(i == word.length() - 1){
                if(curr.getChildren()[index].getValue() == 0) wordCount++;
                curr.getChildren()[index].incrementValue();
            }
            curr = curr.getChildren()[index];
        }
    }

    @Override
    public INode find(String word) {
        char c;
        int index = 0;
        Node curr = root;
        word = word.toLowerCase();

        for(int i = 0; i < word.length(); i++) {
            c = word.charAt(i);
            index = c - 'a';
            Node child = curr.getChildren()[index];
            if(child == null) return null;
            if(i < word.length() - 1) curr = child;
        }
        if(curr.getChildren()[index].getValue() > 0) return curr.getChildren()[index];
        return null;
    }

    @Override
    public int getWordCount() { return wordCount; }

    @Override
    public int getNodeCount() { return nodeCount; }

    @Override
    public String toString(){
        StringBuilder word = new StringBuilder();
        StringBuilder wordList = new StringBuilder();
        toStringHelper(root, word, wordList);
        return wordList.toString();
    }

    public void toStringHelper(Node curr, StringBuilder word, StringBuilder wordList){
        if(curr.getValue() > 0){
            wordList.append(word);
            wordList.append("\n");
        }
        for(int i = 0; i < curr.getChildren().length; i++){
            Node child = curr.getChildren()[i];
            if(child != null){
                char add = (char)(i + 'a');
                word.append(add);
                toStringHelper((Node)child, word, wordList);
                word.deleteCharAt(word.length() - 1);
            }
        }
    }

    @Override
    public int hashCode(){ return hash; }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Trie)) return false;
        Trie trie = (Trie) o;
        if(trie.wordCount != this.wordCount) return false;
        if(trie.hash != this.hash) return false;
        return equalsHelper(this.root, trie.root);
    }

    public boolean equalsHelper(Node node1, Node node2){
        if(node1.getValue() != node2.getValue()) return false;
        for(int i = 0; i < node1. getChildren().length; i++){
            Node child1 = node1.getChildren()[i];
            Node child2 = node2.getChildren()[i];
            if(child1 != null && child2 != null) equalsHelper(child1, child2);
            else if (child1 != null || child2 != null) return false;
        }
        return true;
    }

}























