package rn.valiantspace2.renderer.data;


import rn.valiantspace2.renderer.MathBib;
import rn.valiantspace2.renderer.Renderable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 11.03.18.
 */
public class SceneNode {

    private SceneNode parent;
    private boolean visibility = true;
    private List<SceneNode> children = new ArrayList<>();
    private List<Renderable> renderables = new ArrayList<>();

    private float[][] lastCalculatedForwardVector = {{0.f}, {0.f}, {0.f}};

    private float x = 0.f, y = 0.f, z = 0.f;
    private float w = 1.f;
    private float rx, ry, rz;


    /**
     * Constructor for Parent Scene Node
     */
    public SceneNode() {
    }

    /**
     * constructor for child scene node
     *
     * @param parent
     */
    public SceneNode(SceneNode parent) {
        this.parent = parent;
    }

    public float[][] getTransform() {
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

        float[][] temp_a = MathBib.matrix_mult(rot_y, rot_z);
        float[][] temp_b = MathBib.matrix_mult(rot_x, temp_a);
        float[][] temp_c = MathBib.matrix_mult(trans, temp_b);

        return temp_c;
    }


    public void addRenderablePolygon(Renderable renderable) {
        this.renderables.add(renderable);
    }

    ///// getter and setter ////////

    public void setTranslate(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float[][] getTranslate() {
        float[][] translate = {{this.x}, {this.y}, {this.z}};
        return translate;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * calculates the forwardVector and also saves the result to the
     * lastCalculatedForwardVector reference to avoid the same calculation multiple times
     *
     * @return
     */
    public float[][] getForwardVector() {

        float[][] forward_vec = {{0.f}, {0.f}, {-1.f}};

        float[][] rot_x = {{1, 0, 0},
                {0, (float) Math.cos(rx), -(float) Math.sin(rx)},
                {0, (float) Math.sin(rx), (float) Math.cos(rx)}};

        float[][] rot_y = {{(float) Math.cos(ry), 0, (float) Math.sin(ry)},
                {0, 1, 0},
                {(float) -Math.sin(ry), 0, (float) Math.cos(ry)}};

        float[][] rot_z = {{(float) Math.cos(rz), -(float) Math.sin(rz), 0},
                {(float) Math.sin(rz), (float) Math.cos(rz), 0},
                {0, 0, 1}};
        // multiply rotation matrices
        float[][] temp_a = MathBib.matrix_mult(rot_z, forward_vec);
        float[][] temp_b = MathBib.matrix_mult(rot_y, temp_a);
        float[][] temp_c = MathBib.matrix_mult(rot_x, temp_b);
        // save to reference for faster access
        this.lastCalculatedForwardVector = temp_c;

        return temp_c;
    }

    /**
     * check if vector was already calculated if so reuse last result
     *
     * @return
     */
    public float[][] getLastCalculatedForwardVector() {
        if (lastCalculatedForwardVector[0][0] != 0
                && lastCalculatedForwardVector[1][0] != 0
                && lastCalculatedForwardVector[2][0] != 0) {
            return lastCalculatedForwardVector;
        } else {
            return this.getForwardVector();
        }
    }

    public SceneNode getParent() {
        return parent;
    }

    public void setParent(SceneNode parent) {
        this.parent = parent;
    }

    public List<SceneNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<SceneNode> children) {
        this.children = children;
    }

    public void addChild(SceneNode child) {
        child.setParent(child);
        this.children.add(child);
    }

    public List<Renderable> getRenderables() {
        return renderables;
    }

    public void setRenderables(ArrayList<Renderable> renderables) {
        this.renderables = renderables;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getRx() {
        return rx;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public float getRz() {
        return rz;
    }

    public void setRz(float rz) {
        this.rz = rz;
    }
}

