
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
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

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
    

    
    public RunningState() {
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }
    
    
    
    @Override
    public void cleanup() {
        super.cleanup();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app; // can cast Application to something more specific
        this.rootNode     = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.viewPort     = this.app.getViewPort();
        
        
        Block block = new Block();
        
        cam.setLocation(new Vector3f(0,0,50));
        stateManager.getState(RunningState.class).setEnabled(true);
        System.out.println("RunningState Initialized");
        
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
        
        rootNode.getChild("A Textured Box").setCullHint(CullHint.Inherit);
     
        ActionListener actionListener = new ActionListener() {
            float x = 0;
            float y = 0;
            public void onAction(String name, boolean keyPressed, float tpf) {
                 if ("Pause Game".equals(name) && !keyPressed) {
                     RunningState.this.stateManager.getState(RunningState.class).setEnabled(false);
                     RunningState.this.stateManager.getState(PausedState.class).setEnabled(true);
                     System.out.println("RunningState disabled");
                     //rootNode.getChild("A Textured Box").setCullHint(CullHint.Always);
                     inputManager.removeListener(this);
                     
             }
                 if ("Drop Block".equals(name) && !keyPressed) System.out.println("Drop pushed");
                 if ("Move Block Left".equals(name) && !keyPressed)   rootNode.getChild("A Textured Box").setLocalTranslation(x -= 2.5, y, 0);
                 if ("Move Block Right".equals(name) && !keyPressed)   rootNode.getChild("A Textured Box").setLocalTranslation(x += 2.5, y, 0);
                 if ("Move Block Up".equals(name) && !keyPressed)   rootNode.getChild("A Textured Box").setLocalTranslation(x, y += 2.5, 0);
                 if ("Move Block Down".equals(name) && !keyPressed)   rootNode.getChild("A Textured Box").setLocalTranslation(x, y -= 2.5, 0);
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
    }
    
}
