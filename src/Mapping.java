import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arne on 03.06.14.
 */
public class Mapping {

    Map<Byte,Byte> map;

    public Mapping(){
        map = new HashMap<>();
    }

    /**
     * Tries to create a mapping from all variables in "from" to all in "to".
     * WARNING: Constants not handled yet!!!
     * @param from Literal
     * @param to Literal
     * @throws Exception If it's not possible to build a mapping
     */
    public Mapping(Literal from, Literal to) throws Exception{
        map = new HashMap<>();
        if(from.id!=to.id)
            throw new Exception("literal ids are different");
        if(from.entries.size()!=to.entries.size())
            throw new Exception("wrong literal size");
        for (int i = 0; i < from.entries.size(); i++) {
            if(!map.containsKey(from.entries.get(i).id) || map.get(from.entries.get(i).id).equals(to.entries.get(i).id)) {
                map.put(from.entries.get(i).id, to.entries.get(i).id);
            }else{
                throw new Exception("already mapped. want to insert "+(char)from.entries.get(i).id+"->"+(char)to.entries.get(i).id+", but is incompatible with "+this);
            }
        }
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public Mapping getCopy(){
        Mapping newMapping = new Mapping();
        newMapping.mergeMapping(this);
        return newMapping;
    }

    /**
     * Checks if the mapping is incompatible with the other mapping:
     * Every key which is in both mappings has to point to the same value.
     * WARNING: check if this is correct! and if it is correct implemented!!!
     * @param other other mapping to check with
     * @return
     */
    public boolean isCompatible(Mapping other){
        for(Map.Entry<Byte, Byte> entry: map.entrySet()){
            if(!other.map.get(entry.getKey()).equals(entry.getValue())){
                return false;
            }
        }
        return true;
    }

    public void mergeMapping(Mapping other){
        map.putAll(other.map);
    }


    @Override
    public String toString(){
        if(map.isEmpty())
            return "empty mapping";
        String result = "";
        for(Map.Entry<Byte, Byte> entry: map.entrySet()){
            result += ", "+(char)entry.getKey().byteValue()+"->"+ (char)entry.getValue().byteValue();
        }
        return result.substring(2);
    }

}