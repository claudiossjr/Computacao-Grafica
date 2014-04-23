package cubo;

import java.util.ArrayList;
import util.math.Vector4f;

/**
 *
 * @author marcoslage
 */
class Cube {
    protected ArrayList<Vector4f> normals;
    protected ArrayList<Vector4f> positions;
    
    protected int nverts = 8;
    protected int nfaces = 12;
    
    public Cube(){
        positions = new ArrayList<>(8);
        normals   = new ArrayList<>(8);
        
        // Fill the vertices
        positions.add( new Vector4f(-0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f, 0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f,-0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f(-0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f, 0.5f,-0.5f, 1.0f) );
        positions.add( new Vector4f( 0.5f,-0.5f,-0.5f, 1.0f) );

        // Fill the colors
        normals.add( new Vector4f(-0.5f,-0.5f, 0.5f, 0.0f) );
        normals.add( new Vector4f(-0.5f, 0.5f, 0.5f, 0.0f) );
        normals.add( new Vector4f( 0.5f, 0.5f, 0.5f, 0.0f) );
        normals.add( new Vector4f( 0.5f,-0.5f, 0.5f, 0.0f) );
        normals.add( new Vector4f(-0.5f,-0.5f,-0.5f, 0.0f) );
        normals.add( new Vector4f(-0.5f, 0.5f,-0.5f, 0.0f) );
        normals.add( new Vector4f( 0.5f, 0.5f,-0.5f, 0.0f) );
        normals.add( new Vector4f( 0.5f,-0.5f,-0.5f, 0.0f) );
    }
}
