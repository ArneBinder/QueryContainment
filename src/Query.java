import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Arne on 03.06.14.
 */
public class Query {
    Literal head;
    Literal[] literals;

    Query(byte[] chars){
        List<Literal> tempLiterals = new ArrayList<>();
        int start = 0;
        Literal possibleLiteral;
        for (int i = 0; i < chars.length; i++) {
            if(chars[i]==')'){
                possibleLiteral = new Literal(chars, start, i);
                if(!containsLiteral(tempLiterals, possibleLiteral))
                    tempLiterals.add(possibleLiteral);
                start = i+2;
            }
        }
        head = tempLiterals.remove(0);
        Collections.sort(tempLiterals);
       // literals = new Literal[tempLiterals.size()];
        literals = tempLiterals.toArray(new Literal[tempLiterals.size()]);
    }

    static private boolean containsLiteral(List<Literal> litList, Literal literal){
        for (Literal thisLiteral: litList){
            if(thisLiteral.equals(literal))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        String literalString = Arrays.toString(literals);
        return (head+"-"+ literalString.substring(1,literalString.length()-1)+".").replaceAll(" ","");
    }

}
