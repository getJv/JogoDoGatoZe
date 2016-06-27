import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Elenco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Elenco
{
    private int x;
    private int y;
    private String nomeDaClasse;

    public Elenco(int x,int y,String nomeDaClasse){

        this.x = x;
        this.y = y;
        this.nomeDaClasse =  nomeDaClasse;

    }

    public int getX(){

        return x;

    }
    
    public int getY(){

        return y;

    }
    
    public String nomeDaClasse(){

        return nomeDaClasse;

    }
    
    
    
    
    
}
