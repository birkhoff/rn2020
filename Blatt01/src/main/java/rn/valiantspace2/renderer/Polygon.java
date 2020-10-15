package rn.valiantspace2.renderer;

/**
 * This class handles a polygon which is a primitive 2D shape
 * The polygon represents a distorted plane of a 3D object
 *
 * @author Mike
 */

public class Polygon {

    double[] x_coordinates;
    double[] y_coordinates;
    int[] color;
    boolean culled = false;
    float distance = Float.MAX_VALUE;
    float furthest_point_left = Float.MAX_VALUE;        // for culling sides
    float furthest_point_right = Float.MIN_VALUE;

    public Polygon(float[][] coords, int numPolygons, int[] color) {

        x_coordinates = new double[numPolygons];
        y_coordinates = new double[numPolygons];

        this.color = color;
        distance = 0;
        for (int j = 0; j < numPolygons; j++) {

            distance += coords[j][2];

            x_coordinates[j] = (double) coords[j][0];
            y_coordinates[j] = (double) coords[j][1];
        }

        distance = distance / numPolygons;

        // if the distance is behind the camera the object will be culled i.e not drawn
        if (distance >= 0)
            culled = true;
    }


    // method for creating a polygon out of an array with multiple polygons

    public Polygon(float[][] coords, int start_index, int numPolygons, int[] color,
                   double min_x_window_bound, double max_x_window_bound,
                   double min_y_window_bound, double max_y_window_bound) {

        x_coordinates = new double[numPolygons];
        y_coordinates = new double[numPolygons];

        this.color = color;
        distance = 0;
        float culling_distance = 0;

        for (int i = 0; i < numPolygons; i++) {
            if (coords[i][2] < distance)
                culling_distance = coords[start_index + i][2];

            // determine these points in order to cull polygons
            // which are left or right off screen
            if (coords[i][0] < furthest_point_right)
                furthest_point_right = coords[i][0];
            if (coords[i][0] > furthest_point_left)
                furthest_point_left = coords[i][0];


            distance += coords[start_index + i][2];

            x_coordinates[i] = (double) coords[start_index + i][0];
            y_coordinates[i] = (double) coords[start_index + i][1];
        }

        distance = distance / numPolygons;

        // if the distance is behind the camera the object will be culled i.e not drawn
        if (culling_distance >= 0
                || (furthest_point_right < min_x_window_bound && furthest_point_left < min_x_window_bound)
                || (furthest_point_right > max_x_window_bound && furthest_point_left > max_x_window_bound))
            culled = true;


    }


    public int[] get_color() {
        return color;
    }

    public float get_distance() {
        return distance;
    }

    public boolean shall_be_culled() {
        return culled;
    }

    public double[] get_x_coords() {
        return x_coordinates;
    }

    public double[] get_y_coords() {
        return y_coordinates;
    }
}

