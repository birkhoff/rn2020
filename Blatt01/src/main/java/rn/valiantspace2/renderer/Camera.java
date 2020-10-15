package rn.valiantspace2.renderer;

/**
 * This class defines the camera of a 3D Renderer
 *
 * @author Mike
 */
public class Camera {

    float x, y, z;      // position of camera
    float rx, ry, rz;   // rotation of camera on each axis
    float focal;        // focal length of virtual camera
    float w = 1;

    float screen_dimensions[];

    public Camera(float x, float y, float z,
                  float rx, float ry, float rz,
                  float screen_dimension_x, float screen_dimension_y, float focal) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.rx = rx;
        this.ry = ry;
        this.rz = rz;

        this.focal = focal;
    }


    /**
     * calculates where the camera should move to when going forward
     * We do not need normalised coordinates here
     *
     * @param translation
     */
    public void move(float[][] translation) {
        float[][] rot_x = {{1, 0, 0},
                {0, (float) Math.cos(rx), -(float) Math.sin(rx)},
                {0, (float) Math.sin(rx), (float) Math.cos(rx)}};

        float[][] rot_y = {{(float) Math.cos(ry), 0, (float) Math.sin(ry)},
                {0, 1, 0},
                {(float) -Math.sin(ry), 0, (float) Math.cos(ry)}};

        float[][] rot_z = {{(float) Math.cos(rz), -(float) Math.sin(rz), 0},
                {(float) Math.sin(rz), (float) Math.cos(rz), 0},
                {0, 0, 1}};

        float[][] temp_c = MathBib.matrix_mult(rot_y, translation);
//		float[][] temp_b = MathBib.matrix_mult(rot_z, temp_a);
//		float[][] temp_c = MathBib.matrix_mult(rot_x, temp_b);


        x += temp_c[0][0];
        //y += temp_c[1][0]; // comment this line to stick to the ground
        z += temp_c[2][0];
    }

    public void set_height(float height) {
        y = -height;
    }

    public void set_position(float x, float y, float z) {
        this.x = -x;
        this.z = z;
        this.y = -y;
    }

    public float get_height() {
        return y;
    }

    public void add_rotation_values(float rx, float ry) {
        this.rx += rx;
        this.ry += ry;
    }

    public void add_rotation_x(float rx) {
        this.rx += rx;
    }

    public void add_rotation_y(float ry) {
        this.ry += ry;
    }

    /**
     * Model View Matrix of the camera.
     * Each point which is multiplied with this matrix will be transformed from
     * 3D coordinates to 2D coordinates
     *
     * @return returns the model view matrix
     */
    public float[][] get_model_view_matrix() {
        float cx = (float) Math.cos(-rx);
        float sx = (float) Math.sin(-rx);

        float[][] trans = {{1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, w}};

        float[][] rot_x = {{1, 0, 0, 0},
                {0, cx, -sx, 0},
                {0, sx, cx, 0},
                {0, 0, 0, w}};

        float cy = (float) Math.cos(-ry);
        float sy = (float) Math.sin(-ry);

        float[][] rot_y = {{cy, 0, sy, 0},
                {0, 1, 0, 0},
                {-sy, 0, cy, 0},
                {0, 0, 0, w}};

        float cz = (float) Math.cos(-rz);
        float sz = (float) Math.sin(-rz);

        float[][] rot_z = {{cz, -sz, 0, 0},
                {sz, cz, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, w}};

        float[][] m_f = {{-focal, 0, 0, 0},  // - for inversed points (projecting on a canvas)
                {0, focal, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, w}};


        float[][] temp_a = MathBib.matrix_mult(rot_z, trans);
        float[][] temp_b = MathBib.matrix_mult(rot_y, temp_a);
        float[][] temp_c = MathBib.matrix_mult(rot_x, temp_b);
        float[][] temp_d = MathBib.matrix_mult(m_f, temp_c);

        return temp_d;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public float getRz() {
        return rz;
    }
}

