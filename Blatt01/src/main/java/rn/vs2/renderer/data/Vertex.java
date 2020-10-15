package rn.vs2.renderer.data;


/**
 * Created by Mike on 11.03.18.
 */
public class Vertex {

    public float x, y, z;
    public int r, g, b;

    /**
     * Objext to store position and color of a vertex
     *
     * @param x
     * @param y
     * @param z
     * @param r
     * @param g
     * @param b
     */
    public Vertex(float x, float y, float z, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.r = r;
        this.g = g;
        this.b = b;
    }
}
