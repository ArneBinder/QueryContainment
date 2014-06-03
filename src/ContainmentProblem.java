import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Arne on 03.06.14.
 */
public class ContainmentProblem {
    Query query1;
    Query query2;
    byte matches = -1; //-1: unknown; 0: true; 1: false

    public ContainmentProblem(Query q1, Query q2, String matches){
        query1 = q1;
        query2 = q2;
        if(matches.equals("true")){
            this.matches = 0;
        }else if(matches.equals("false")){
            this.matches = 1;
        }
    }

    class StackElement{
        Mapping mapping; //current mapping
        List<Mapping> possibleMappings;
        List<Literal> remainingLiterals; //could be realised via an index in the query1.literals list

        StackElement(Mapping mapping,List<Mapping> possibleMappings,List<Literal> remainingLiterals){
            this.mapping = mapping;
            this.possibleMappings = possibleMappings;
            this.remainingLiterals = remainingLiterals;
        }
    }

    public boolean containsNaive() throws Exception {
        Stack<StackElement> stack = new Stack<>();
        List<Literal> remainingLiterals = new ArrayList<>();
        for (Literal literal: query1.literals){
            remainingLiterals.add(literal);
        }
        Literal firstLiteral = remainingLiterals.remove(0);
        List<Mapping> possibleMappings = getMappings(firstLiteral, query2.literals);
        Mapping mapping = new Mapping();
        stack.push(new StackElement(mapping,possibleMappings,remainingLiterals));

        while (!stack.empty()){
            StackElement curStackElem = stack.pop();
            mapping = curStackElem.mapping.getCopy();
            possibleMappings = new ArrayList<>(curStackElem.possibleMappings);
            remainingLiterals = new ArrayList<>(curStackElem.remainingLiterals);
            if(!possibleMappings.isEmpty()){
                Mapping currentMapping = possibleMappings.remove(0);
                stack.push(new StackElement(mapping,possibleMappings,remainingLiterals));
                if(mapping.isCompatible(currentMapping)){
                    if(remainingLiterals.isEmpty()){
                        return true;
                    }else{
                        firstLiteral = remainingLiterals.remove(0);
                        possibleMappings = getMappings(firstLiteral, query2.literals);
                        stack.push(new StackElement(mapping,possibleMappings,remainingLiterals));
                    }
                }
            }

        }
        return false;
    }

    /**
     * Tries to create mapping for every occurrence of selectLiteral in literals.
     * @param selectLiteral Literal, the elements of literals have to match with.
     * @param literals List of Literals
     * @return List of Mappings
     */
    public static List<Mapping> getMappings(Literal selectLiteral, List<Literal> literals){
        List<Mapping> result = new ArrayList<>();
        for(Literal literal: literals){
            if(literal.id==selectLiteral.id) {
                Mapping currentMapping;
                try {
                    currentMapping = new Mapping(selectLiteral, literal);
                }catch(Exception e){
                    continue;
                }
                result.add(currentMapping);
            }
        }
        return result;
    }

}
