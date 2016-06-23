import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Write a description of class LinhaVertical here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Linha extends Pisoteria
{
    public Linha(){
        GreenfootImage linhaVermelha = new GreenfootImage(2,390);
        linhaVermelha.setColor(Color.RED);
        linhaVermelha.fill();
        setImage(linhaVermelha);
    
    
    
    }    
    
    public void act() 
    {
        super.act();
        
    }    
    
    
}
