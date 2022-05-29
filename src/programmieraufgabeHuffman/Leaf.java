package programmieraufgabeHuffman;

public class Leaf {

    char character;
    int occurrence;

    public Leaf(char character, int occurrence) {
        this.character = character;
        this.occurrence = occurrence;
    }

    public int compareTo(Leaf leaf) {
        return occurrence - leaf.occurrence;
    }

}
