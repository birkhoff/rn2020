package rn.vs2.renderer.data;


import java.util.ArrayList;

/**
 * Created by Mike on 11.03.18.
 */
public class PlyObject {

    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Index> indexList = new ArrayList<>();

    public PlyObject() {

    }

    /**
     * add vertex to vertex buffer
     *
     * @param vertex
     */
    public void addVertex(Vertex vertex) {
        this.vertices.add(vertex);
    }


    public void addIndex(Index index) {
        this.indexList.add(index);
    }


    ////////  getter and setter ////////

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Index> getIndexList() {
        return indexList;
    }

    public void setIndexList(ArrayList<Index> indexList) {
        this.indexList = indexList;
    }
}
