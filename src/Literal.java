import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arne on 03.06.14.
 */
public class Literal implements Comparable<Literal> {
    byte id;
    //List<LiteralEntry> entries;
    byte[] entries;

    public Literal(byte[] chars, int start, int last) {
        this.id = chars[start];
        //entries = new ArrayList<>();
        entries = new byte[(last-start-1)/2];
        int pos = 0;
        for (int i = start + 2; i < last; i += 2) {
            //entries.add(new LiteralEntry(chars, i));
            entries[pos] = chars[i];
            pos++;
        }
    }

    public Literal(Literal original, Mapping mapping) {

        id = original.id;
        //entries = new ArrayList<>(original.entries.size());
        entries = new byte[original.entries.length];

        byte newId;
        for (int i = 0; i < entries.length; i++) {


        //for (byte entry : original.entries) {
            if(Character.isUpperCase(original.entries[i]))
                continue; //if it is a constant, continue
            try {
                newId = mapping.map.get(original.entries[i]);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Mapping doesn't contain the requested source symbol.");
            }

            entries[i] = newId;
            //entries.add(new LiteralEntry(newId));


        }


    }

    boolean containsAll(Literal other){
        outer:
        for (byte otherEntry:other.entries){
            if(otherEntry==0)
                continue; //a mapped constant (have a look to the constructor Literal(Literal original, Mapping mapping))
            for(byte thisEntry: this.entries){
                if(otherEntry==thisEntry)
                    continue outer;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Literal literal = (Literal) o;

        if (id != literal.id) return false;
        if (!Arrays.equals(entries, literal.entries)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + Arrays.hashCode(entries);
        return result;
    }

    /**
     * Copy Constructor
     *
     * @param //other
     */
    /*public Literal(Literal other) {
        this.id = other.id;
        entries = new ArrayList<>();
        entries.addAll(other.entries);
    }*/

    @Override
    public String toString() {
        String entryString = "";//Arrays.toString(entries.toArray());
        for (byte entry: entries){
            entryString += ","+(char)entry;
        }
        return "" + (char) id + "(" + (entries.length==0?"":entryString.substring(1, entryString.length())) + ")";
    }


    @Override
    public int compareTo(Literal o) {
        return id-o.id;
    }
}
