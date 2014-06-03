import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arne on 03.06.14.
 */
public class Literal {
    byte id;
    List<LiteralEntry> entries;

    public Literal(byte[] chars, int start, int last){
        this.id = chars[start];
        entries = new ArrayList<>();
        for (int i = start+2; i < last; i+=2) {
            entries.add(new LiteralEntry(chars,i));
        }
    }

    @Override
    public String toString(){
        String entryString = Arrays.toString(entries.toArray());
        return ""+(char)id+"("+ entryString.substring(1,entryString.length()-1)+")";
    }
}
