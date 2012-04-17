package com.teamawesome.testing;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Set;

/**
 * test
 * @author Hasen
 */
public class Main extends SimpleApplication {
    
    public static void main(String[] args) {
        
        Main app = new Main();
        //app.setShowSettings(false);
        //app.settings.setFullscreen(true);
        
            
        app.start();
    }
    
    
    @Override
    public void start(){
        // Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();
        
        AppSettings settings = new AppSettings(true);
        settings.setResolution((int)(scrnsize.width/1.2), (int)(scrnsize.height/1.2));
        settings.setTitle("BlockBlock");
        //settings.setFullscreen(true);
        setShowSettings(false);
        setSettings(settings);
        
        super.start();
    }

    @Override
    public void simpleInitApp() {
        
        inputManager.clearMappings();
        inputManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));inputManager.addMapping("Move Block Down", new KeyTrigger(KeyInput.KEY_DOWN), new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Move Block Up", new KeyTrigger(KeyInput.KEY_UP), new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Move Block Left", new KeyTrigger(KeyInput.KEY_LEFT), new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Move Block Right", new KeyTrigger(KeyInput.KEY_RIGHT), new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Drop Block", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Pause Game", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Unpause Game", new KeyTrigger(KeyInput.KEY_U));
     
        stateManager.attach(new RunningState());
        stateManager.attach(new PausedState());
        stateManager.getState(PausedState.class).setSettings(settings);
        stateManager.getState(RunningState.class).setCam(cam);
        
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
