package rn.valiantspace2.renderer;


/**
 * This class defines the 3D dimensional values for each renderable
 *
 * @author Mike
 */
public class Renderable {

    public enum Mesh {
        Cube,
        Plane_X
    }

    // the vertices must always align to a quad
    private float[][] vertices;
    private int numPolygons = 4;            // 4 for drawing quads number of polygons for each plane
    private int[] color;

    public Renderable(Mesh type, float x, float y, float z) {
        switch (type) {
            case Cube:
                vertices = new float[][]{
                        {0 + x, 0 + y, 0 + z}, {1 + x, 0 + y, 0 + z},
                        {1 + x, 1 + y, 0 + z}, {0 + x, 1 + y, 0 + z},

                        {1 + x, 0 + y, 0 + z}, {1 + x, 0 + y, -1 + z},
                        {1 + x, 1 + y, -1 + z}, {1 + x, 1 + y, 0 + z},

                        {0 + x, 0 + y, -1 + z}, {1 + x, 0 + y, -1 + z},
                        {1 + x, 1 + y, -1 + z}, {0 + x, 1 + y, -1 + z},

                        {0 + x, 0 + y, 0 + z}, {0 + x, 0 + y, -1 + z},
                        {0 + x, 1 + y, -1 + z}, {0 + x, 1 + y, 0 + z}
                };
                break;
            case Plane_X:
                vertices = new float[][]{
                        {0 + x, 0 + y, 0 + z}, {1 + x, 0 + y, 0 + z},
                        {1 + x, 0 + y, 1 + z}, {0 + x, 0 + y, 1 + z},

                };
                break;
            default:
                break;
        }

        color = new int[]{(int) (Math.random() * 255),
                (int) (Math.random() * 255),
                (int) (Math.random() * 255)};
    }

    /**
     * alternative constructor for a plane with color
     *
     * @param plane
     * @param r
     * @param g
     * @param b
     */
    public Renderable(float[][] plane, int r, int g, int b) {

        vertices = plane;
        color = new int[]{r, g, b};
    }


    public int get_number_of_polygons() {
        return numPolygons;
    }

    public int[] get_color() {
        return color;
    }


    public float[][] get_vertices() {
        float[][] returnVertices = new float[vertices.length][3];

        for (int i = 0; i < vertices.length; i++) {
            returnVertices[i][0] = vertices[i][0];
            returnVertices[i][1] = vertices[i][1];
            returnVertices[i][2] = vertices[i][2];
        }

        return returnVertices;
    }


}

