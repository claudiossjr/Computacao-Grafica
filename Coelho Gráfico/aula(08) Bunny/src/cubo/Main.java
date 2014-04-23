package cubo;

/**
 *
 * @author marcoslage
 */
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
import util.camera.Camera;
import util.math.FastMath;
import util.math.Matrix4f;
import util.math.Vector4f;
import util.projection.Projection;

public class Main {

    // Creates a new cube
    private final CubeGL cube = new CubeGL();

    // Animation:
    private float  currentAngle = 0.0f;
    
    // Projection Matrix
    private final Projection proj = new Projection(45, 1.3333f, 0.01f, 100f);
    
    // View Matrix
    private final Vector4f eye = new Vector4f( 0.0f, 0.0f,-5.0f, 1.0f);
    private final Vector4f at  = new Vector4f( 0.0f, 0.0f, 0.0f, 1.0f);
    private final Vector4f up  = new Vector4f( 0.0f, 1.0f, 0.0f, 1.0f);
    
    private final Camera cam = new Camera(eye, at, up);

    // Rotation Matrix:
    private final Matrix4f rotationMatrixZ = new Matrix4f();
    private final Matrix4f rotationMatrixY = new Matrix4f();
    // Translation Matrix:
    private final Matrix4f translationMatrix = new Matrix4f();
    // Scale Matrix:
    private final Matrix4f scaleMatrix = new Matrix4f();
    
    // Model Matrix
    private final Matrix4f modelViewMatrix  = new Matrix4f();
    // NormalMatrix Matrix
    private final Matrix4f normalMatrix     = new Matrix4f();
    // Projection Matrix
    private final Matrix4f projectionMatrix = new Matrix4f();
    
    /**
     * General initialization stuff for OpenGL
     * @throws org.lwjgl.LWJGLException
     */
    public void initGl() throws LWJGLException {
        
        // width and height of window and view port
        int width = 640;
        int height = 480;

        // set up window and display
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setVSyncEnabled(true);
        Display.setTitle("Shader OpenGL Hello");

        // set up OpenGL to run in forward-compatible mode
        // so that using deprecated functionality will
        // throw an error.
        PixelFormat pixelFormat = new PixelFormat();
        ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        Display.create(pixelFormat, contextAtrributes);
        
        // Standard OpenGL Version
        System.out.println("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("GLSL version: "   + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
         
        // initialize basic OpenGL stuff
        GL11.glViewport(0, 0, width, height);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void run() {
        // Creates the vertex array object. 
        // Must be performed before shaders compilation.
        cube.fillVAOs();
        cube.loadShaders();
       
        // Model Matrix setup
        translationMatrix.m41 = 0.8f;

        scaleMatrix.m11 = 1.0f;
        scaleMatrix.m22 = 1.0f;
        scaleMatrix.m33 = 1.0f;
                        
        while (Display.isCloseRequested() == false) {

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glEnable(GL11.GL_CULL_FACE);
            
            projectionMatrix.setTo(proj.perspective());            
            modelViewMatrix.setTo(cam.viewMatrix());
            
            currentAngle += 0.01f;
            float c = FastMath.cos(currentAngle);
            float s = FastMath.sin(currentAngle);

            rotationMatrixZ.m11 = c; rotationMatrixZ.m21 = s;
            rotationMatrixZ.m12 =-s; rotationMatrixZ.m22 = c;
            
            rotationMatrixY.m11 = c; rotationMatrixY.m31 = s;
            rotationMatrixY.m13 =-s; rotationMatrixY.m33 = c;

            modelViewMatrix.multiply(rotationMatrixY);
            //modelViewMatrix.multiply(translationMatrix);
            //modelViewMatrix.multiply(rotationMatrixZ);
            //modelViewMatrix.multiply(scaleMatrix);

            normalMatrix.setTo(modelViewMatrix);
            normalMatrix.inverse();
            normalMatrix.transpose();
                                    
            cube.setMatrix("NormalMatrix", normalMatrix);
            cube.setMatrix("ModelViewMatrix", modelViewMatrix);
            cube.setMatrix("ProjectionMatrix", projectionMatrix);
            cube.setVector("eyePosition", eye);
            
            cube.render();

            // check for errors
            if (GL11.GL_NO_ERROR != GL11.glGetError()) {
                throw new RuntimeException("OpenGL error: " + GLU.gluErrorString(GL11.glGetError()));
            }

            // swap buffers and sync frame rate to 60 fps
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }

    /**
     * main method to run the example
     * @param args
     * @throws org.lwjgl.LWJGLException
     */
    public static void main(String[] args) throws LWJGLException {
        Main example = new Main();
        example.initGl();
        example.run();
    }
}
