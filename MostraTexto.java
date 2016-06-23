import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;
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

        GreenfootImage texto = new GreenfootImage(txt,30,Color.WHITE,new Color(0,0,0,0),Color.BLACK);
        setImage(texto);

    }
    
    public void act() 
    {

    }    
    
    public MostraTexto(){}
    
    public MostraTexto(String txt){
        //   GreenfootImage img = new GreenfootImage(txt,25,Color.WHITE,new Color(0,0,0,0),Color.BLACK);
        GreenfootImage img = new GreenfootImage(70,24);
        Font font = new Font("Impact", Font.PLAIN, 20);
        img.setFont(font);
        img.setColor(new Color(63,151,29)); 
        img.drawString(txt, 0  ,20);
        setImage(img);
    }
    
    
}
