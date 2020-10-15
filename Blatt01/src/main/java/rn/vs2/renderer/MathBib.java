package rn.vs2.renderer;

/**
 * Matrix Multiplication
 *
 * @author Mike
 */
public class MathBib {

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
}

