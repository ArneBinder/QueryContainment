import java.util.*;

/**
 * Created by Arne on 03.06.14.
 */
public class ContainmentProblem {
    Query query;
    Query view;
    HashMap<Byte, int[]> viewPositions;
    byte shouldMatch = -1; //-1: unknown; 0: true; 1: false

    public ContainmentProblem(Query query, Query view, String shouldMatch) {
        this.query = query;
        this.view = view;
        if (shouldMatch.equals("true")) {
            this.shouldMatch = 0;
        } else if (shouldMatch.equals("false")) {
            this.shouldMatch = 1;
        }
        //System.out.println(query);
        //System.out.println(view);
        //calcViewPositions();
        //System.out.println();
    }

    class StackElement {
        Mapping mapping; //current mapping
        List<Mapping> possibleMappings;
        //List<Literal> remainingLiterals; //could be realised via an index in the query.literals list
        int literalPos;

        StackElement(Mapping mapping, List<Mapping> possibleMappings, int literalPos) {
            this.mapping = mapping;
            this.possibleMappings = possibleMappings;
            //this.remainingLiterals = remainingLiterals;
            this.literalPos = literalPos;
        }
    }

    /**
     * Depth-First algorithm (according to lecture)
     *
     * @return true, if view is covered by query, else false
     * @throws Exception
     */
    public boolean containsNaive() throws Exception {
        Stack<StackElement> stack = new Stack<>();
        int currentLiteralPos = 0;
        calcViewPositions();

        //List<Literal> remainingLiterals = new ArrayList<>();
        //remainingLiterals.addAll(Arrays.asList(query.literals));

        //Literal firstLiteral = remainingLiterals.remove(0);
        Literal firstLiteral = query.literals[currentLiteralPos];

        List<Mapping> possibleMappings = getMappings(firstLiteral);
        if(possibleMappings.isEmpty())
            return false;
        Mapping mapping = new Mapping();
        //stack.push(new StackElement(mapping,possibleMappings,remainingLiterals));
        stack.push(new StackElement(mapping, possibleMappings, currentLiteralPos));

        while (!stack.empty()) {
            StackElement curStackElem = stack.pop();
            mapping = new Mapping(curStackElem.mapping);

            possibleMappings = new ArrayList<>(curStackElem.possibleMappings);

            //remainingLiterals = new ArrayList<>(curStackElem.remainingLiterals);
            currentLiteralPos = curStackElem.literalPos;
            if (!possibleMappings.isEmpty()) {
                Mapping currentMapping = possibleMappings.remove(0);
                if (possibleMappings.size() > 0)
                    stack.push(new StackElement(new Mapping(mapping), new ArrayList<>(possibleMappings), currentLiteralPos));
                if (mapping.isCompatible(currentMapping)) {
                    mapping.mergeMapping(currentMapping);
                    //if(remainingLiterals.isEmpty()){
                    if (currentLiteralPos >= query.literals.length) {
                        try {
//                            System.out.println(mapping);
                            Literal mapped = new Literal(query.head, mapping);
                            if (view.head.entries.containsAll(mapped.entries)) {

                                return true;
                            }
                        } catch (IllegalArgumentException e) {
                        }
                    } else {

                        //firstLiteral = remainingLiterals.remove(0);
                        firstLiteral = query.literals[currentLiteralPos];
                        currentLiteralPos++;
                        possibleMappings = getMappings(firstLiteral);
                        if (possibleMappings.size() > 0)
                            stack.push(new StackElement(mapping, possibleMappings, currentLiteralPos));
                    }
                }
            }

        }

        return false;
    }

    /**
     * Tries to create mapping for every occurrence of selectLiteral in literals.
     *
     * @param selectLiteral Literal, the elements of literals have to match with.
     * @return List of Mappings
     */
    public List<Mapping> getMappings(Literal selectLiteral) {
        List<Mapping> result = new ArrayList<>();
        //for(Literal literal: view.literals){
        int[] positions = viewPositions.get(selectLiteral.id);
        if(positions==null)
            return result;
        for (int i = positions[0]; i < positions[1]; i++) {


            //if(literal.id==selectLiteral.id) {
            Mapping currentMapping;
            try {
                currentMapping = new Mapping(selectLiteral, view.literals[i]);
            } catch (Exception e) {
                continue;
            }
            result.add(currentMapping);
            // }
        }
        return result;
    }

    private void calcViewPositions() {
        viewPositions = new HashMap<>();
        byte lastId = view.literals[0].id;
        int lastStart = 0;
        for (int i = 1; i < view.literals.length; i++) {
            if (lastId != view.literals[i].id) {
                viewPositions.put(lastId, new int[]{lastStart, i});
                lastStart = i;
                lastId = view.literals[i].id;
            }
        }
        viewPositions.put(lastId, new int[]{lastStart, view.literals.length});

    }

}
