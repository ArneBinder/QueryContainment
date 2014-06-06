import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Arne on 03.06.14.
 */
public class ContainmentProblem {
    Query query;
    Query view;
    byte shouldMatch = -1; //-1: unknown; 0: true; 1: false

    public ContainmentProblem(Query query, Query view, String shouldMatch){
        this.query = query;
        this.view = view;
        if(shouldMatch.equals("true")){
            this.shouldMatch = 0;
        }else if(shouldMatch.equals("false")){
            this.shouldMatch = 1;
        }
    }

    class StackElement{
        Mapping mapping; //current mapping
        List<Mapping> possibleMappings;
        List<Literal> remainingLiterals; //could be realised via an index in the query.literals list

        StackElement(Mapping mapping,List<Mapping> possibleMappings,List<Literal> remainingLiterals){
            this.mapping = mapping;
            this.possibleMappings = possibleMappings;
            this.remainingLiterals = remainingLiterals;
        }
    }

    /**
     * Depth-First algorithm (according to lecture)
     * @return true, if view is covered by query, else false
     * @throws Exception
     */
    public boolean containsNaive() throws Exception {
        Stack<StackElement> stack = new Stack<>();

        List<Literal> remainingLiterals = new ArrayList<>();
        remainingLiterals.addAll(query.literals);

        Literal firstLiteral = remainingLiterals.remove(0);

        List<Mapping> possibleMappings = getMappings(firstLiteral, view.literals);
        Mapping mapping = new Mapping();
        stack.push(new StackElement(mapping,possibleMappings,remainingLiterals));

        while (!stack.empty()){
            StackElement curStackElem = stack.pop();
            mapping = new Mapping(curStackElem.mapping);

            //notwendig? einzelne Mappings werden eigentlich nicht modifiziert...
            possibleMappings = new ArrayList<>(curStackElem.possibleMappings);
//            for(Mapping tempMapping: curStackElem.possibleMappings){
//                possibleMappings.add(new Mapping(tempMapping));
//            }
            //possibleMappings = new ArrayList<>(curStackElem.possibleMappings);

            //notwendig? einzelne Literale werden eigentlich nicht modifiziert...
            remainingLiterals = new ArrayList<>(curStackElem.remainingLiterals);
//            for(Literal tempLiteral: curStackElem.remainingLiterals){
//                remainingLiterals.add(new Literal(tempLiteral));
//            }
            //remainingLiterals = new ArrayList<>(curStackElem.remainingLiterals);

            if(!possibleMappings.isEmpty()){
                Mapping currentMapping = possibleMappings.remove(0);
                if(possibleMappings.size()>0)
                    stack.push(new StackElement(new Mapping(mapping),new ArrayList<>(possibleMappings),new ArrayList<>(remainingLiterals)));
                if(mapping.isCompatible(currentMapping)){
                    mapping.mergeMapping(currentMapping);
                     if(remainingLiterals.isEmpty()){
                        try {
//                            System.out.println(mapping);
                            Literal mapped = new Literal(query.head, mapping);
                            if(view.head.entries.containsAll(mapped.entries)){

                                return true;
                            }
                        } catch(IllegalArgumentException e){}
                    }else{
                        firstLiteral = remainingLiterals.remove(0);
                        possibleMappings = getMappings(firstLiteral, view.literals);
                        if(possibleMappings.size()>0)
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
