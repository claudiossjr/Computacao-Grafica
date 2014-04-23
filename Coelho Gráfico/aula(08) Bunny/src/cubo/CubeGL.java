package cubo;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import util.math.Matrix4f;
import util.math.Vector4f;
import util.shader.ObjectGL;
import util.shader.ShaderProgram;

/**
 *
 * @author marcoslage
 */
public class CubeGL extends Coelho implements ObjectGL {

    // Vertex Array Object Id
    private int vaoHandle;
    // Shader Program
    private ShaderProgram shader;
    // Buffer with the Positions
    private FloatBuffer positionBuffer;
    // Buffer with the Colors
    private FloatBuffer normalBuffer;
    
    // Vector Id
    private int vectorId = -1;
    // Matrix Id
    private int matrixId = -1;
    // Buffer with the Matrix uniform
    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    
    //Constructor
    public CubeGL() {
        super();
    }

    @Override
    public void fillVAOs() {
        // fills the VBOs
        fillVBOs();

        // create vertex byffer object (VBO) for vertices
        int positionBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        // create VBO for color values
        int colorBufferHandle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        // create vertex array object (VAO)
        vaoHandle = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        // assign vertex VBO to slot 0 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferHandle);
        GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, 0, 0);

        // assign vertex VBO to slot 1 of VAO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorBufferHandle);
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 0, 0);

        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
 
    @Override
    public void loadShaders() {
        // compile and link vertex and fragment shaders into
        // a "program" that resides in the OpenGL driver
        shader = new ShaderProgram();

        // do the heavy lifting of loading, compiling and linking
        // the two shaders into a usable shader program
        shader.init("shaders/phong.vert", "shaders/phong.frag");
 
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());
    }

    public void setMatrix(String nameMatrix, Matrix4f matrix) {
        // converts from matrix to FloatBuffer
        matrixBuffer.clear();
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        
        // defines the uniform variable
        matrixId = GL20.glGetUniformLocation(shader.getProgramId(), nameMatrix);
        GL20.glUniformMatrix4(matrixId, false, matrixBuffer);
    }
    
    public void setVector(String nameVector, Vector4f vector) {
        // defines the uniform variable
        vectorId = GL20.glGetUniformLocation(shader.getProgramId(), nameVector);
        GL20.glUniform4f(vectorId, vector.x, vector.y, vector.z, vector.w);
    }

    @Override
    public void render() {
        // tell OpenGL to use the shader
        GL20.glUseProgram(shader.getProgramId());

        // bind vertex and color data
        GL30.glBindVertexArray(vaoHandle);
        GL20.glEnableVertexAttribArray(0); // VertexPosition
        GL20.glEnableVertexAttribArray(1); // VertexColor

        // draw VAO
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3*nfaces);
    }
    
    @Override
    public void fillVBOs() {
        // convert vertex array to buffer
        positionBuffer = BufferUtils.createFloatBuffer(4 * nverts * nfaces); //4(coordinates)*3(vertices)*12(triangles)
        // convert color array to buffer
        normalBuffer = BufferUtils.createFloatBuffer(4 * nverts * nfaces); //4(coordinates)*3(vertices)*12(triangles)

        // bluid the quad faces 
        /**
        buildQuad(1, 0, 3, 2);
        buildQuad(2, 3, 7, 6);
        buildQuad(3, 0, 4, 7);
        buildQuad(6, 5, 1, 2);
        buildQuad(4, 5, 6, 7);
        buildQuad(5, 4, 0, 1);
        /**/
        
        /**/
        for (int i = 0; i < nfaces; i++) {
            if(matOfTriangles[i][0] == 3)
            {
                buildTriangles(matOfTriangles[i][1], matOfTriangles[i][2], matOfTriangles[i][3]);
            }
            else{
                buildQuad(matOfTriangles[i][1], matOfTriangles[i][2], matOfTriangles[i][3], matOfTriangles[i][4]);
            }
            //System.out.println(matOfTriangles[i][0]+" "+matOfTriangles[i][1]+" "+matOfTriangles[i][2]);
        }
        /**/
        
        positionBuffer.flip();
        normalBuffer.flip();
    }

    // private methods
    
    private void buildTriangles(int a, int b, int c)
    {
        positions.get(a).store(positionBuffer);
        positions.get(b).store(positionBuffer);
        positions.get(c).store(positionBuffer);
        
        normals.get(a).store(normalBuffer);
        normals.get(b).store(normalBuffer);
        normals.get(c).store(normalBuffer);
    }

    private void buildQuad(int a, int b, int c, int d) {
        positions.get(a).store(positionBuffer);
        positions.get(b).store(positionBuffer);
        positions.get(c).store(positionBuffer);

        positions.get(a).store(positionBuffer);
        positions.get(c).store(positionBuffer);
        positions.get(d).store(positionBuffer);

        normals.get(a).store(normalBuffer);
        normals.get(b).store(normalBuffer);
        normals.get(c).store(normalBuffer);

        normals.get(a).store(normalBuffer);
        normals.get(c).store(normalBuffer);
        normals.get(d).store(normalBuffer);
    }
}
