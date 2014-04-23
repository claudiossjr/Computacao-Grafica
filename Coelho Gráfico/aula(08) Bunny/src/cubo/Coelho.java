/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cubo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.math.Vector4f;

/**
 *
 * @author claudio
 */
public class Coelho {
    protected ArrayList<Vector4f> normals;
    protected ArrayList<Vector4f> positions;
    protected int matOfTriangles[][];
    
    protected int nverts;
    protected int nfaces;
    
    protected Scanner in;
    
    public Coelho()
    {
        
        try {
            in = new Scanner(new File("bunny_tri.off"));
            
            System.out.println(in.nextLine()); 
            this.nverts = in.nextInt();
            this.nfaces = in.nextInt();
            matOfTriangles = new int[nfaces][3];
        
            positions = new ArrayList<>(nverts);
            normals = new ArrayList<>(nverts);
            
            for (int i = 0; i < nverts; i++) {
                float x = Float.parseFloat(in.next());
                float y = Float.parseFloat(in.next());
                float z = Float.parseFloat(in.next());
                float nx = Float.parseFloat(in.next());
                float ny = Float.parseFloat(in.next());
                float nz = Float.parseFloat(in.next());
                //System.out.println(x+" "+y+" "+z+" "+nx+" "+ny+" "+nz+" ");
                positions.add(new Vector4f(x,y,z,1.0f) );
                normals.add(new Vector4f(nx,ny,nz,0.0f) );
            }
            
            for (int i = 0; i < nfaces; i++) {
                int j = in.nextInt();
                matOfTriangles[i][0] = in.nextInt(); 
                matOfTriangles[i][1] = in.nextInt();
                matOfTriangles[i][2] = in.nextInt();
            }
            
            in.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Coelho.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
