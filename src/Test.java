/**
 * Created by Arne on 03.06.14.
 */
public class Test {
    public static void main(String args[]) throws Exception {
        //byte[] chars = "q()-q(c,C,b),q(b,a,b),q(b,c,b),q(b,a,a),q(b,b,b),q(c,b,b),q(b,b,c),q(b,c,b),q(a,a,c),q(b,c,c),q(a,b,a),q(b,a,a),q(b,b,b),q(c,a,c),q(c,a,a),q(c,c,c),q(c,a,b),q(b,b,a),q(c,b,a),q(b,b,b).".getBytes();
        byte[] chars = "q()-q(c,C,b),q(b,a,b),q(b,c,b),x(b,a,a),q(b,b,b).".getBytes();

        LiteralEntry e = new LiteralEntry(chars,6);
        System.out.println(e);

        Literal l = new Literal(chars, 4, 11);
        System.out.println(l);

        Query q = new Query(chars);
        System.out.println(q);
        System.out.println(q.toString().equals(new String(chars)));
        System.out.println();

        Literal l1 = q.literals.get(1);
        Literal l2 = q.literals.get(2);

        System.out.println(l1);
        System.out.println(l2);
        Mapping mapping = new Mapping(l1, l2);
        System.out.println(mapping);
        System.out.println();

        byte[] chars2 = "q(b,a,b)".getBytes();
        Literal ltest = new Literal(chars2,0,chars2.length-1);
        System.out.println(ltest);
        for(Mapping mapping1: ContainmentProblem.getMappings(ltest,q.literals)){
            System.out.println(mapping1);
        }

        //q(a,b,c)-a(a),c(c,c),c(b,c).
        //q(a,b,c)-a(d),a(e),c(c,c),c(a,b).
        Query q1 = new Query("q(a,b,c)-a(a),c(c,c),c(b,c).".getBytes());
        Query q2 = new Query("q(a,b,c)-x(d),a(e),c(c,c),c(a,b).".getBytes());
        System.out.println("---------------");
        System.out.println(q1);
        System.out.println(q2);

        ContainmentProblem cp = new ContainmentProblem(q2,q1,"");
        System.out.println(cp.containsNaive());
    }
}
