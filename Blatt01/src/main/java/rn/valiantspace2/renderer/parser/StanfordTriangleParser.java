package rn.valiantspace2.renderer.parser;


import rn.valiantspace2.renderer.Renderable;
import rn.valiantspace2.renderer.SoftwareRenderer;
import rn.valiantspace2.renderer.data.Index;
import rn.valiantspace2.renderer.data.PlyObject;
import rn.valiantspace2.renderer.data.SceneNode;
import rn.valiantspace2.renderer.data.Vertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Created by Mike on 11.03.18.
 */
public class StanfordTriangleParser {

    enum ParserState {
        NOT_PARSING,
        PARSE_HEADER,
        PARSE_VERTICES,
        PARSE_INDICES,
        FINISHED
    }

    ParserState mState = ParserState.NOT_PARSING;
    int mVertexCount = -1;


    public StanfordTriangleParser() {

    }


    /**
     * parse Stanford triangle mesh into PlyObject
     *
     * @param filePath
     * @return Plyobject to use with the Software Renderer
     */
    public PlyObject parsePlyFile(String filePath) {
        PlyObject ply = new PlyObject();

        try {
            FileReader input = new FileReader(filePath);
            BufferedReader bufRead = new BufferedReader(input);
            String currentLine;

            mState = ParserState.PARSE_HEADER;
            int currentVertexIndex = 1;

            while ((currentLine = bufRead.readLine()) != null) {
                switch (mState) {

                    case PARSE_HEADER:
                        this.parseHeader(currentLine);
                        break;

                    case PARSE_VERTICES:
                        this.parseVertices(currentLine, ply);

                        if (currentVertexIndex >= mVertexCount)
                            mState = ParserState.PARSE_INDICES;
                        currentVertexIndex++;
                        break;

                    case PARSE_INDICES:
                        this.parseIndices(currentLine, ply);
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mState = ParserState.NOT_PARSING;

        return ply;
    }

    /**
     * parse the header of a ply file and extraxt the vertex count
     *
     * @param line
     */
    private void parseHeader(String line) {

        // if end header start parsing vertices
        if (line.contains("end_header"))
            mState = ParserState.PARSE_VERTICES;

        // search for vertex count
        if (line.contains("element vertex ")) {
            String[] splitted = line.split("\\s+");
            mVertexCount = Integer.parseInt(splitted[2]);
        }
    }


    /**
     * parse vertices and write them to the ply object
     *
     * @param line
     */
    private void parseVertices(String line, PlyObject plyObject) {

        System.out.println("Parse Vertices: " + line);

        String[] vertexString = line.split("\\s+");

        float x = Float.parseFloat(vertexString[0]);
        float y = Float.parseFloat(vertexString[1]);
        float z = Float.parseFloat(vertexString[2]);

        int r = Integer.parseInt(vertexString[6]);
        int g = Integer.parseInt(vertexString[7]);
        int b = Integer.parseInt(vertexString[8]);


        Vertex vertex = new Vertex(x, y, z, r, g, b);
        plyObject.addVertex(vertex);
    }

    /**
     * parse index elements for a polygon
     *
     * @param line
     * @param plyObject
     */
    private void parseIndices(String line, PlyObject plyObject) {
//        System.out.println("Parse Indices: "+ line);
        // last line is always empty so check if we finished parsing
        if (!line.isEmpty()) {
            String[] indexString = line.split("\\s+");
            int vertexCountOfPolygon = Integer.parseInt(indexString[0]);
            // check if current polygon is actually an quad
            if (vertexCountOfPolygon == 4) {

                int a = Integer.parseInt(indexString[1]);
                int b = Integer.parseInt(indexString[2]);
                int c = Integer.parseInt(indexString[3]);
                int d = Integer.parseInt(indexString[4]);

                Index index = new Index(a, b, c, d);
                plyObject.addIndex(index);
            } else {
                System.out.println("! ERROR: " + line
                        + "\nLine is not applicable for quads! Vertex count is: "
                        + vertexCountOfPolygon);
            }
        } else {
            mState = ParserState.FINISHED;
        }
    }


    /**
     * this functions puts all planes of a ply into the scene node and adds it
     * to the renderer queue
     */
    public static SceneNode fill_ply_object(PlyObject plyObject, SoftwareRenderer renderer) {

        SceneNode sceneNode = new SceneNode();

        for (int currentQuad = 0; currentQuad < plyObject.getIndexList().size(); currentQuad++) {
            Index currentQuadIndexList = plyObject.getIndexList().get(currentQuad);

            int r = 0, g = 0, b = 0;

            float[][] vertices = new float[][]{
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0},
            };

            for (int i = 0; i < currentQuadIndexList.getIndexes().length; i++) {
                Vertex vertex = plyObject.getVertices().get(currentQuadIndexList.getIndexes()[i]);
                r = vertex.r;
                g = vertex.g;
                b = vertex.b;

                vertices[i] = new float[]{vertex.x, vertex.z, vertex.y};
            }

            Renderable renderable;
            renderable = new Renderable(vertices, r, g, b);
            sceneNode.addRenderablePolygon(renderable);
        }

        return sceneNode;
    }
}

