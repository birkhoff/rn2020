package rn.valiantspace2.renderer;

/**
 * Matrix Multiplication
 *
 * @author Mike
 */
public class MathBib {

    public static float PI = 3.14159265359f;

    /**
     * Multiply two matrices
     *
     * @param a
     * @param b
     * @return
     */
    public static float[][] matrix_mult(float[][] a, float[][] b) {

        float[][] c = new float[a.length][b[0].length];

        for (int i = 0; i < b[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                float cell = 0;

                for (int k = 0; k < b.length; k++) {
                    cell += a[j][k] * b[k][i];
                }
                c[j][i] = cell;
            }
        }

        return c;
    }

    /**
     * Multiplay a float3 by a scalar
     *
     * @param vec3
     * @param scalar
     * @return scalar multiplied float vector as 1x3 matrix
     */
    public static float[][] multiply_vec3(float[][] vec3, float scalar) {

        vec3[0][0] = vec3[0][0] * scalar;
        vec3[1][0] = vec3[1][0] * scalar;
        vec3[2][0] = vec3[2][0] * scalar;

        return vec3;
    }

    public static double getDistance(float[][] nodeA, float[][] nodeB) {
        return (Math.sqrt(((float) (Math.pow(nodeA[0][0] - nodeB[0][0], 2)))
                + ((float) (Math.pow(nodeA[1][0] - nodeB[1][0], 2)))
                + ((float) (Math.pow(nodeA[2][0] - nodeB[2][0], 2)))));
        
    }
}

