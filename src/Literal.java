import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arne on 03.06.14.
 */
public class Literal implements Comparable<Literal> {
    byte id;
    List<LiteralEntry> entries;

    public Literal(byte[] chars, int start, int last) {
        this.id = chars[start];
        entries = new ArrayList<>();
        for (int i = start + 2; i < last; i += 2) {
            entries.add(new LiteralEntry(chars, i));
        }
    }

    public Literal(Literal original, Mapping mapping) {

        id = original.id;
        entries = new ArrayList<>(original.entries.size());

        byte newId;
        for (LiteralEntry entry : original.entries) {
            if(entry.isConstant) continue;
            try {
                newId = mapping.map.get(entry.id);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Mapping doesn't contain the requested source symbol.");
            }

            entries.add(new LiteralEntry(newId));

        }


    }

    /**
     * Copy Constructor
     *
     * @param other
     */
    public Literal(Literal other) {
        this.id = other.id;
        entries = new ArrayList<>();
        entries.addAll(other.entries);
    }

    @Override
    public String toString() {
        String entryString = Arrays.toString(entries.toArray());
        return "" + (char) id + "(" + entryString.substring(1, entryString.length() - 1) + ")";
    }


    @Override
    public int compareTo(Literal o) {
        return id-o.id;
    }
}
