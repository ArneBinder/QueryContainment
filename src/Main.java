import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created by macbookdata on 06.06.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String inputPath = "./data/training.txt";
        if(args.length > 0){
            inputPath = args[0];
        }

        List<String> lines = FileUtils.readLines(new File(inputPath));

        for (int i = 0; i+3 < lines.size(); i+=4) {
            Query view = new Query(lines.get(i+1).getBytes());
            Query query = new Query(lines.get(i+2).getBytes());
            ContainmentProblem problem = new ContainmentProblem(query, view, lines.get(i+3));
            if(problem.shouldMatch != -1){
                boolean expectedResult = problem.shouldMatch == 0;
                boolean result = problem.containsNaive();
                if(result != expectedResult ){
                    System.out.println(lines.get(i));
                    System.out.println(String.format("view  %s\nquery %s\nexpected %s got %s",view, query,expectedResult, result));
                }

            }
        }


    }


}
