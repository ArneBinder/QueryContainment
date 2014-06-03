/**
 * Created by myri on 03.06.14.
 */
public class LiteralEntry {
    byte id;
    boolean isConstant = false;

    public LiteralEntry(byte[] chars, int pos) {
        this.id = chars[pos];
        isConstant=Character.isUpperCase(id);
    }

    @Override
    public String toString(){
        return ""+(char)id;
    }
}
