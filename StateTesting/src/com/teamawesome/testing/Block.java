/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamawesome.testing;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

/**
 *
 * @author kayvanboudai
 */
public class Block extends AbstractControl implements Savable, Cloneable{
    
    protected Vector3f position;
    //TODO: protected Mesh mesh;
    public enum BlockState {idleState, clearingState, killState, currentState, playState, explodeState, flashingState, countDownState};
    protected BlockState state;
    protected int points;
    protected enum Color {Red, Blue, Black, Yellow, Green, Grey, Orange};
    protected Color color;
 
    private SimpleApplication app;
    private Node              rootNode;
    private AssetManager      assetManager;
    
    public Block(){
        this.app = (SimpleApplication) app; // can cast Application to something more specific
        this.rootNode     = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        
        
        /** A simple textured cube. */ 
        Box boxshape1 = new Box(Vector3f.ZERO, 1f,1f,1f); 
        Geometry cube = new Geometry("A Textured Box", boxshape1); 
        Material mat_stl = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); 
        Texture tex_ml = assetManager.loadTexture("Interface/background.png"); 
        mat_stl.setTexture("ColorMap", tex_ml); 
        cube.setMaterial(mat_stl);
        rootNode.attachChild(cube);
        /*------------------------------------*/
    }

    @Override
    public Spatial getSpatial() {
        return super.getSpatial();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void controlUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    public void draw(){}
    public void update(){}
    public void setState(BlockState s){}
    public BlockState getState(BlockState s){return state;}
    public int getPoints(){return points;}
    public Vector3f getPosition() {return position;}
    public void setPosition(Vector3f p){};
    public void setColor(Color c) {color = c;}
    public Color getColor() {return color;}
    
    
    
}
