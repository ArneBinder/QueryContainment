/**
 * Created by Arne on 03.06.14.
 */
public class Test {
    public static void main(String args[]) throws Exception {
        //byte[] chars = "q()-q(c,C,b),q(b,a,b),q(b,c,b),q(b,a,a),q(b,b,b),q(c,b,b),q(b,b,c),q(b,c,b),q(a,a,c),q(b,c,c),q(a,b,a),q(b,a,a),q(b,b,b),q(c,a,c),q(c,a,a),q(c,c,c),q(c,a,b),q(b,b,a),q(c,b,a),q(b,b,b).".getBytes();
        byte[] chars = "q()-q(c,C,b),q(b,a,b),q(b,c,b),x(b,a,a),q(b,b,b).".getBytes();

//        LiteralEntry le = new LiteralEntry("a".getBytes(), 0);
//        System.out.println("is constant "+le.isConstant);

        //q(a,b,c)-a(a),c(c,c),c(b,c).
        //q(a,b,c)-a(d),a(e),c(c,c),c(a,b).

//        Query view = new Query("q(b)-f(a,b,c),f(b,b,c),f(a,b,c),f(a,b,c),f(a,b,c),f(a,b,b),f(a,b,b).".getBytes());
//        Query query = new Query("q(c)-f(a,b,c),f(b,b,c),f(a,b,b),f(a,c,c).".getBytes());

        // a->a b->b c->b/c
        Query view =  new Query("q(b)-f(b,b,c),f(a,b,b).".getBytes());
        Query query = new Query("q(c)-f(b,b,c),f(a,c,c).".getBytes());
        System.out.println("---------------");
        System.out.println(view);
        System.out.println(query);

        ContainmentProblem cp1 = new ContainmentProblem(query/*query*/,view/*view*/,"");
        System.out.println("result: "+cp1.containsNaive());
//        ContainmentProblem cp2 = new ContainmentProblem(view/*query*/,query/*view*/,"");
//        System.out.println("swapped: "+cp2.containsNaive());
    }
}
