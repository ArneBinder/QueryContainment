/**
 * Created by myri on 03.06.14.
 */
public class ContainmentProblem {
    Query query1;
    Query query2;
    byte matches = -1; //-1: unknown; 0: true; 1: false

    public ContainmentProblem(String q1, String q2, String matches){
        query1 = new Query(q1.getBytes());
        query2 = new Query(q2.getBytes());
        if(matches.equals("true")){
            this.matches = 0;
        }else if(matches.equals("false")){
            this.matches = 1;
        }
    }
}
