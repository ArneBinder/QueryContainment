/**
 * Created by myri on 03.06.14.
 */
public class Test {
    public static void main(String args[]) {
        byte[] chars = "q()-q(c,C,b),q(b,a,b),q(b,c,b),q(b,a,a),q(b,b,b),q(c,b,b),q(b,b,c),q(b,c,b),q(a,a,c),q(b,c,c),q(a,b,a),q(b,a,a),q(b,b,b),q(c,a,c),q(c,a,a),q(c,c,c),q(c,a,b),q(b,b,a),q(c,b,a),q(b,b,b).".getBytes();

        LiteralEntry e = new LiteralEntry(chars,6);
        System.out.println(e);

        Literal l = new Literal(chars, 4, 11);
        System.out.println(l);

        Query q = new Query(chars);
        System.out.println(q);
        System.out.println(q.toString().equals(new String(chars)));
    }
}
