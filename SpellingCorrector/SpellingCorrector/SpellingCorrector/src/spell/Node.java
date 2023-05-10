package spell;

public class Node implements INode {
    public char c;
    public Node[] children;
    public int count;

    public Node(char c) {
        this.c = c;
        children = new Node[26];
        count = 0;
    }

    @Override
    public int getValue() {
        return count;
    }

    @Override
    public void incrementValue() {
        count++;
    }

    @Override
    public Node[] getChildren() {
        return children;
    }
}