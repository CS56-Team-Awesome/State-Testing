
package com.teamawesome.testing;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author kaizokuace
 */
public class PausedState extends AbstractAppState implements ScreenController{
    
    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    private AppStateManager   stateManager;
    private InputManager      inputManager;
    private ViewPort          viewPort;
    private Node              guiNode;
    private AppSettings       settings;
    
    private Nifty nifty;
    private Screen screen;

    public void setSettings(AppSettings s) {
        this.settings = s;
    }
    

    public PausedState() {
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
        this.guiNode      = this.app.getGuiNode();
        
        stateManager.getState(PausedState.class).setEnabled(false);
        System.out.println("PausedState Initialized");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
            
//            guiNode.detachAllChildren();
//            Picture pic = new Picture("HUD Picture");
//            pic.setImage(assetManager, "Interface/background.png", true);
//            pic.setWidth(settings.getWidth()/2);
//            pic.setHeight(settings.getHeight()/2);
//            pic.setPosition(settings.getWidth()/4, settings.getHeight()/4);
//            guiNode.attachChild(pic);
//            System.out.println("PausedState enabled");
            
            
            
            NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, app.getAudioRenderer(), app.getGuiViewPort());
            nifty = niftyDisplay.getNifty();
            app.getGuiViewPort().addProcessor(niftyDisplay);
            app.getFlyByCamera().setDragToRotate(true);
            
            nifty.loadStyleFile("nifty-default-styles.xml");
            nifty.loadControlFile("nifty-default-controls.xml");
 
            // <screen>
            nifty.addScreen("Screen_ID", new ScreenBuilder("Hello Nifty Screen"){{
             controller(new DefaultScreenController()); // Screen properties       
 
            // <layer>
            layer(new LayerBuilder("Layer_ID") {{
                childLayoutVertical(); // layer properties, add more...
 
            // <panel>
            panel(new PanelBuilder("Panel_ID") {{
             childLayoutCenter(); // panel properties, add more...               
 
            // GUI elements
            control(new ButtonBuilder("Button_ID", "Hello Nifty"){{
              alignCenter();
                valignCenter();
                height("5%");
                width("15%");
                     }});
 
           //.. add more GUI elements here              
 
             }});
           // </panel>
             }});
           // </layer>
             }}.build(nifty));
           // </screen>
 
        nifty.gotoScreen("Screen_ID"); // start the screen
   
            
            

        
        ActionListener actionListener = new ActionListener() {
            float x = 0;
            float y = 0;
            public void onAction(String name, boolean keyPressed, float tpf) {
                 if ("Pause Game".equals(name) && !keyPressed) {
                     PausedState.this.stateManager.getState(PausedState.class).setEnabled(false);
                     PausedState.this.stateManager.getState(RunningState.class).setEnabled(true);
                     System.out.println("PausedState disabled");
                     inputManager.removeListener(this);
                 
                 }
                 if ("Drop Block".equals(name) && !keyPressed) System.out.println("Drop pushed");
                 if ("Move Block Left".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x -= 2.5, y, 0);
                 if ("Move Block Right".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x += 2.5, y, 0);
                 if ("Move Block Up".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x, y += 2.5, 0);
                 if ("Move Block Down".equals(name) && !keyPressed)   rootNode.getChild("blockNode").setLocalTranslation(x, y -= 2.5, 0);
              }
            };
        
        inputManager.addListener(actionListener, new String[]{"Pause Game","Drop Block","Move Block Right", 
                                                            "Move Block Left", "Move Block Up", "Move Block Down"});
        
        }
        else{
        
        }
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }

    //------------Nifty-----------//
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void onStartScreen() {
        
    }

    public void onEndScreen() {
        
    }
    
}
