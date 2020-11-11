package rn.valiantspace2.renderer;


import rn.valiantspace2.renderer.data.SceneNode;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Software Renderer which renders the frame with a camera, renderables and polygons
 *
 * @author Mike
 */

public class SoftwareRenderer {

    Camera cam;
    final float screen_dimension_x = 1200;
    final float screen_dimension_y = 1000;

    final float screen_scale_min_x = -1;
    final float screen_scale_max_x = 1;

    final float screen_scale_min_y = -1;
    final float screen_scale_max_y = 1;

    boolean draw_raster = false;

    ArrayList<SceneNode> sceneNodes;
    ArrayList<Polygon> drawablePolygons;


    public SoftwareRenderer() {

        StdDraw.setCanvasSize((int) screen_dimension_x, (int) screen_dimension_y);
        StdDraw.setXscale(screen_scale_min_x, screen_scale_max_x);
        StdDraw.setYscale(screen_scale_min_y, screen_scale_max_y);
        cam = new Camera(0, 0, 0, 0, 0, 0, screen_dimension_x, screen_dimension_y, 1);

        sceneNodes = new ArrayList<>();
        drawablePolygons = new ArrayList<>();

        StdDraw.enableDoubleBuffering();
    }


    public void toggle_draw_raster() {
        draw_raster = !draw_raster;
    }

    public Camera get_camera() {
        return this.cam;
    }


    /**
     * Renders one frame
     * Multiplies each vertex of the renderables with the modelview matrix and draws them
     * on a 2D plane
     *
     * @return time elapsed for calculating frame
     */
    public long render() {
        long startTime = System.currentTimeMillis();
        StdDraw.clear();
        drawablePolygons.clear();
        float[][] modelView = cam.get_model_view_matrix();
        this.rotatePolygongs(sceneNodes, modelView);
        this.drawPolygongs();
        StdDraw.show();
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Pauses Rendering Loop
     *
     * @param ms length of pause in milliseconds
     */
    public void pause(long ms) {
        StdDraw.pause((int) ms);
    }

    /**
     * close the rendering window
     */
    public void close() {
        JFrame frame = StdDraw.getJFrame();
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private void rotatePolygongs(List<SceneNode> nodes, float[][] modelView) {
        for (SceneNode node : nodes) {
            if (node.isVisible()) {
                float[][] nodeTransform = node.getTransform();
                for (Renderable r : node.getRenderables()) {
                    float[][] rolled_points = this.get_points_through_model_view_matrix(r, modelView, nodeTransform);
                    this.add_polygons_to_drawable_polygons(rolled_points, r.get_number_of_polygons(), r.get_color());
                }
                // traverse children
                this.rotatePolygongs(node.getChildren(), MathBib.matrix_mult(modelView, nodeTransform));
            }
        }
    }

    private void drawPolygongs() {
        // draw polygons
        for (Polygon drawablePolygon : drawablePolygons) {
            int color[] = drawablePolygon.get_color();
            StdDraw.setPenColor(color[0], color[1], color[2]);
            StdDraw.filledPolygon(drawablePolygon.get_x_coords(),
                    drawablePolygon.get_y_coords());

            if (draw_raster) {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.polygon(drawablePolygon.get_x_coords(),
                        drawablePolygon.get_y_coords());
            }
        }
    }


    public float[][] get_points_through_model_view_matrix(Renderable r, float[][] modelview, float[][] transform) {

        float[][] returnVertices = r.get_vertices();

        for (int i = 0; i < returnVertices.length; i++) {

            float x = returnVertices[i][0];
            float y = returnVertices[i][1];
            float z = returnVertices[i][2];

            float[][] coordinate = {{x}, {y}, {z}, {1}};
            float[][] transformedCoordinate = MathBib.matrix_mult(transform, coordinate);

            float[][] projectedCoordinate = MathBib.matrix_mult(modelview, transformedCoordinate);

            float w = projectedCoordinate[2][0];

            if (w > -0.0001f) {
                w = -0.0001f;

            }

            returnVertices[i] = new float[]{projectedCoordinate[0][0] / w,
                    -projectedCoordinate[1][0] / w,
                    projectedCoordinate[2][0]};

        }

        return returnVertices;
    }


    /**
     * creates a polygon out of a list of points
     *
     * @param coords      coordinates
     * @param numPolygons how many points each polygon has
     *                    triangles have 3, quads have 4
     * @param color       color of polygon
     */


    public void add_polygons_to_drawable_polygons(float[][] coords, int numPolygons, int[] color) {
        for (int i = 0; i < coords.length / numPolygons; i++) {

            int k = i * numPolygons;

            Polygon p = new Polygon(coords, k, numPolygons, color,
                    screen_scale_min_x, screen_scale_max_x, screen_scale_min_y, screen_scale_max_y);

            if (!p.shall_be_culled())
                insert_sorted_by_distance(p);
        }

    }


    /**
     * inserts polygon to the drawable list in order which they are drwn
     * polygons furthest away are drawn first, polygons closer are drawn over them
     * this is a simple depth test
     *
     * @param p polygon
     */


    public void insert_sorted_by_distance(Polygon p) {
        boolean not_added = true;
        for (int i = 0; i < drawablePolygons.size(); i++) {

            if (drawablePolygons.get(i).get_distance() > p.get_distance()) {
                not_added = false;
                drawablePolygons.add(i, p);
                break;
            }
        }

        if (not_added)
            drawablePolygons.add(p);
    }


    public void add_renderable(SceneNode renderableSceneNode) {
        sceneNodes.add(renderableSceneNode);
    }

    public void remove_renderable(SceneNode renderableSceneNode) {
        sceneNodes.remove(renderableSceneNode);
    }


}

