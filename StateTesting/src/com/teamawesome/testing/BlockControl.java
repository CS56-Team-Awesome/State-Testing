/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamawesome.testing;

import com.jme3.export.Savable;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author kayvanboudai
 */
public class BlockControl extends AbstractControl implements Savable, Cloneable{
    
    public enum BlockState {idleState, clearingState, killState, currentState, playState, explodeState, flashingState, countDownState};
    protected BlockState state;
    protected int points;
    protected enum Color {Red, Blue, Black, Yellow, Green, Grey, Orange};
    protected Color color;
    
    public BlockControl(){
        state = BlockState.idleState;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if(spatial != null) {
            spatial.rotate(tpf,tpf,tpf); 
        }
        switch (state){
            case idleState:
                break;
            case playState:
                spatial.scale(1.2f);
                state = BlockState.idleState;
                break;
        }
    }

    public Control cloneForSpatial(Spatial spatial) {
        final BlockControl control = new BlockControl();
        /* Optional: use setters to copy userdata into the cloned control */
        // control.setIndex(i); // example
        control.points = this.points;
        control.color = this.color;
        control.state = this.state;
        control.setSpatial(spatial);
        return control;
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void setState(BlockState s){
        state = s;
    }
    
    public BlockState getState(BlockState s){
        return state;
    }
    
    public int getPoints(){
        return points;
    }
    
    public void setColor(Color c) {
        color = c;
    }
    
    public Color getColor() {
        return color;
    }
    
    
    
}