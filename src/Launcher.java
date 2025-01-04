
import model.DisjointSets;

public class Launcher {
    public static void main(String[] args) {
        DisjointSets disjointSets = DisjointSets.getInstance();
        System.out.println(disjointSets.a);

        DisjointSets ds = DisjointSets.getInstance();
        System.out.println(ds.a);

    }
}
