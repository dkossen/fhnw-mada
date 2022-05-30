package programmieraufgabeHuffman;

public class Leaf implements Comparable<Leaf> {

    char character;
    int occurrence;

    // Leaf constructor
    public Leaf(char character, int occurrence) {
        this.character = character;
        this.occurrence = occurrence;
    }

    @Override
    public int compareTo(Leaf leaf) {
        return occurrence;
    }

}
