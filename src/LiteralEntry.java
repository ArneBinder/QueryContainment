/**
 * Created by Arne on 03.06.14.
 */
public class LiteralEntry {
    byte id;
    boolean isConstant = false;

    public LiteralEntry(byte id){
        this.id = id;
        isConstant=Character.isUpperCase(id);
    }
    public LiteralEntry(byte[] chars, int pos) {
        this.id = chars[pos];
        isConstant=Character.isUpperCase(id);
    }

    @Override
    public String toString(){
        return ""+(char)id;
    }

    @Override
    public boolean equals(Object obj) {
        LiteralEntry other = (LiteralEntry) obj;
        return other.id == id;
    }
}
