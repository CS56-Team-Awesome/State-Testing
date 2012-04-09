
package com.teamawesome.testing;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.teamawesome.testing.BlockControl.Color;
import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author kaizokuace
 */
public class RunningState extends AbstractAppState {
    
    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private InputManager      inputManager;
    private ViewPort          viewPort;
    private Camera            cam;
    private ArrayList<Spatial> blockList;

    
    public RunningState() {
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }
    
    private Spatial makeCube(int index, Node n){
        /** A simple textured cube. */ 
        Box boxshape1 = new Box(Vector3f.ZERO, 1f,1f,1f); 
        Geometry cube = new Geometry("Block"+index, boxshape1); 
        Material mat_stl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");  
        cube.setMaterial(mat_stl);
        cube.addControl(new BlockControl());
        
        switch(cube.getControl(BlockControl.class).color)
        {
            case Red: mat_stl.setColor("Color", ColorRGBA.Red);  
                      break;
            case Blue: mat_stl.setColor("Color", ColorRGBA.Blue);
                       break;
            case Black: mat_stl.setColor("Color", ColorRGBA.Black);
                        break;
            case Yellow: mat_stl.setColor("Color", ColorRGBA.Yellow);
                         break;
            case Green: mat_stl.setColor("Color", ColorRGBA.Green);
                        break;
            case Grey: mat_stl.setColor("Color", ColorRGBA.Gray);
                       break;
            case Orange: mat_stl.setColor("Color", ColorRGBA.Orange);
                         break;
            case Rainbow: mat_stl.setColor("Color", ColorRGBA.Orange);
                         break;
               
        }
        //Texture tex_ml = assetManager.loadTexture("Interface/background.png");
        //mat_stl.setTexture("ColorMap", tex_ml);
       
        n.attachChild(cube);
        return cube;
        /*------------------------------------*/
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app; 
        this.rootNode     = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort     = this.app.getViewPort();
        
        cam.setLocation(new Vector3f(0,0,50));
        stateManager.getState(RunningState.class).setEnabled(true);
        System.out.println("RunningState Initialized");
        viewPort.setBackgroundColor(ColorRGBA.White);
        
        Node blockNode = new Node("blockNode");
        rootNode.attachChild(blockNode);
        
        blockList = new ArrayList<Spatial>();
        for (int i =0; i < 10; i++){
            blockList.add(makeCube(i, blockNode));
        }
        for (int i =0; i < blockList.size(); i++){
            blockList.get(i).move(i+i, i+1, 50);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
        
        //rootNode.getChild("A Textured Box").setCullHint(CullHint.Inherit);
     
        ActionListener actionListener = new ActionListener() {
            float x = 0;
            float y = 0;
            int i = 0;
            
            public void onAction(String name, boolean keyPressed, float tpf) {
                 if ("Pause Game".equals(name) && !keyPressed) {
                     RunningState.this.stateManager.getState(RunningState.class).setEnabled(false);
                     RunningState.this.stateManager.getState(PausedState.class).setEnabled(true);
                     System.out.println("RunningState disabled");
                     //rootNode.getChild("A Textured Box").setCullHint(CullHint.Always);
                     inputManager.removeListener(this);
                     
                 }
                 if ("Drop Block".equals(name) && !keyPressed) {
                     Spatial bc = rootNode.getChild("Block"+(i%10));
                     if(bc != null)
                        bc.getControl(BlockControl.class).setState(BlockControl.BlockState.playState);
                     i++;
                 }
                 if ("Move Block Left".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x -= 2.5, y, 0);
                 if ("Move Block Right".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x += 2.5, y, 0);
                 if ("Move Block Up".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x, y += 2.5, 0);
                 if ("Move Block Down".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x, y -= 2.5, 0);
                 if ("Exit".equals(name) && !keyPressed) app.stop();
            }
        };
        
        inputManager.addListener(actionListener, new String[]{"Pause Game","Drop Block","Move Block Right", 
                                                            "Move Block Left", "Move Block Up", "Move Block Down", "Exit"});
        }
        else{
           
        }
            
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        int i = 0;
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", ColorRGBA.randomColor());
        Spatial bc = rootNode.getChild("Block"+i);
        for (; (bc.getControl(BlockControl.class).color != Color.Rainbow); i++)
        {
            bc = rootNode.getChild("Block"+(i%10));
        }
        if (bc != null) bc.setMaterial(m);
        i++;
    }
    
}
