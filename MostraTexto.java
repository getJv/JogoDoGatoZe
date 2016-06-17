import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * Write a description of class MostrTexto here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MostraTexto extends Actor
{
    /**
     * Act - do whatever the MostrTexto wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void atualiza(String txt){

        //        GreenfootImage img = new GreenfootImage(100,100);
        //      img.drawString( txt,10, 5);
        setImage(new GreenfootImage(txt,20,Color.WHITE,Color.RED));

    }
    
    public void act() 
    {

    }    
}
