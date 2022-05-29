package programmieraufgabeHuffman;

public class Leaf implements Comparable<Leaf> {

    char character;
    int occurrence;

    // Leaf constructor
    public Leaf(char character, int occurrence) {
        this.character = character;
        this.occurrence = occurrence;
    }

    // compare whether the occurence of the character is smaller
    public int compareTo(Leaf leaf) {
        return occurrence - leaf.occurrence;
    }

}
