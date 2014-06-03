import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Arne on 03.06.14.
 */
public class Query {
    Literal head;
    List<Literal> literals;

    Query(byte[] chars){
        literals = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < chars.length; i++) {
            if(chars[i]==')'){
                literals.add(new Literal(chars,start,i));
                start = i+2;
            }
        }
        head = literals.remove(0);
    }

    @Override
    public String toString(){
        String literalString = Arrays.toString(literals.toArray());
        return (head+"-"+ literalString.substring(1,literalString.length()-1)+".").replaceAll(" ","");
    }

}
