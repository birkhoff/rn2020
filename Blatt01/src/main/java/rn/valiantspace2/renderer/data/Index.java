package rn.valiantspace2.renderer.data;

/**
 * Created by Mike on 11.03.18.
 */
public class Index {

    int[] indexes;

    /**
     * create index array for a quad
     * this object stores the indexed of connected points of a quad
     *
     * @param index_a
     * @param index_b
     * @param index_c
     * @param index_d
     */
    public Index(int index_a, int index_b, int index_c, int index_d) {

        indexes = new int[]{index_a, index_b, index_c, index_d};
    }

    public int[] getIndexes() {
        return indexes;
    }
}
