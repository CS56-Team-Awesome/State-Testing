
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
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.ssao.SSAOFilter;
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
        //Box boxshape1 = new Box(Vector3f.ZERO, 1f,1f,1f); 
        //Geometry cube = new Geometry("Block"+index, boxshape1); 
        Spatial cube = assetManager.loadModel("Models/Block.j3o");
        cube.setName("Block"+index);
        Material mat_stl = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat_stl.setBoolean("UseMaterialColors",true); 
        mat_stl.setTexture("DiffuseMap", assetManager.loadTexture("Textures/block_texture_blue_light.png"));
        //mat_stl.setTexture("AlphaMap", assetManager.loadTexture("Textures/block_texture_blue_light_alpha.png"));
        mat_stl.setTexture("GlowMap", assetManager.loadTexture("Textures/block_texture_blue_light_alpha.png")); 
        //mat_stl.setColor("GlowColor", ColorRGBA.White); 
        cube.addControl(new BlockControl());
        
        
//        switch(cube.getControl(BlockControl.class).color)
//        {
//            case Red: mat_stl.setColor("Diffuse", ColorRGBA.Red);  
//                      break;
//            case Blue: mat_stl.setColor("Diffuse", ColorRGBA.Blue);
//                       break;
//            case Black: mat_stl.setColor("Diffuse", ColorRGBA.Black);
//                        break;
//            case Yellow: mat_stl.setColor("Diffuse", ColorRGBA.Yellow);
//                         break;
//            case Green: mat_stl.setColor("Diffuse", ColorRGBA.Green);
//                        break;
//            case Grey: mat_stl.setColor("Diffuse", ColorRGBA.Gray);
//                       break;
//            case Orange: mat_stl.setColor("Diffuse", ColorRGBA.Orange);
//                         break;
//            case Rainbow: mat_stl.setColor("Diffuse", ColorRGBA.Orange);
//                         break;
//               
//        }
        cube.setMaterial(mat_stl);
        //Texture tex_ml = assetManager.loadTexture("Interface/background.png");
        //mat_stl.setTexture("ColorMap", tex_ml);
       
        n.attachChild(cube);
        //n.attachChildAt(cube, index);
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
        //viewPort.setBackgroundColor(ColorRGBA.White);
        
//        DirectionalLight sun = new DirectionalLight();
//        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
//        rootNode.addLight(sun);
        
//        PointLight lamp_light = new PointLight();
//        lamp_light.setColor(ColorRGBA.Yellow);
//        lamp_light.setRadius(4f);
//        lamp_light.setPosition(new Vector3f(rootNode.getLocalTranslation()));
//        rootNode.addLight(lamp_light);
        
        
        
//        AmbientLight al = new AmbientLight();
//        //al.setColor(ColorRGBA.White.mult(1f));
//        al.setColor(new ColorRGBA(1.8f, 1.8f, 1.8f, 1.0f));
//        rootNode.addLight(al);
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(3));
        rootNode.addLight(al);

//        DirectionalLight dl1 = new DirectionalLight();
//        dl1.setDirection(new Vector3f(1, -1, 1).normalizeLocal());
//        dl1.setColor(new ColorRGBA(0.965f, 0.949f, 0.772f, 1f).mult(0.7f));
//        rootNode.addLight(dl1);
//
//        DirectionalLight dl = new DirectionalLight();
//        dl.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
//        dl.setColor(new ColorRGBA(0.965f, 0.949f, 0.772f, 1f).mult(0.7f));
//        rootNode.addLight(dl);
        
        
        Node blockNode = new Node("blockNode");
        rootNode.attachChild(blockNode);
        
        blockList = new ArrayList<Spatial>();
        for (int i =0; i < 10; i++){
            //blockList.add(
            makeCube(i, blockNode);
        }
        for (int i =0; i < blockNode.getQuantity(); i++){
            //blockList.get(i).move(i+i, i+1, 50);
            blockNode.getChild(i).move(i+i, i+1, 40);
        }
        
        Spatial tile = assetManager.loadModel("Models/TIle.j3o");
        tile.setName("Tile");
        Material mat_stl = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture diff = assetManager.loadTexture("Textures/tile.png");
        mat_stl.setTexture("DiffuseMap", diff);
        mat_stl.setTexture("GlowMap", diff);
        tile.setMaterial(mat_stl);
        rootNode.attachChild(tile);
        tile.move(0, 0, 40);
        
        
        FilterPostProcessor fpp=new FilterPostProcessor(assetManager);
        BloomFilter bf=new BloomFilter(BloomFilter.GlowMode.Objects);
        bf.setBloomIntensity(5.0f);
        bf.setExposurePower(1.8f);
        fpp.addFilter(bf);
        viewPort.addProcessor(fpp);
        
//        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
//        SSAOFilter ssaoFilter = new SSAOFilter(5.1f, 1.2f, 0.2f, 0.1f);
//        //SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
//        fpp.addFilter(ssaoFilter);
//        viewPort.addProcessor(fpp);
        
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
                     RunningState.this.stateManager.getState(PausedState.class).setEnabled(true);
                     RunningState.this.stateManager.getState(RunningState.class).setEnabled(false);
                     System.out.println("RunningState disabled");
                     //rootNode.getChild("A Textured Box").setCullHint(CullHint.Always);
                     inputManager.removeListener(this);
                     
                 }
                 if ("Drop Block".equals(name) && !keyPressed) {
                     Spatial bc = rootNode.getChild("Block"+(i%10));
                     if(bc != null)
                        bc.getControl(BlockControl.class).setState(BlockControl.BlockState.playState);
                        //Material mat_stl = new Material(assetManager, "Materials/tronGlow_1.j3m");
                        bc.setMaterial( (Material)assetManager.loadAsset( "Materials/tronGlow_1.j3m") );

                        //bc.setMaterial(mat_stl);
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
        Material m = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        m.setBoolean("UseMaterialColors",true); 
        m.setColor("Diffuse", ColorRGBA.randomColor());
        Spatial bc = rootNode.getChild("Block"+i);
        for (; (bc.getControl(BlockControl.class).color != Color.Rainbow); i++)
        {
            bc = rootNode.getChild("Block"+(i%10));
        }
        if (bc != null) bc.setMaterial(m);
        i++;
    }
    
}
