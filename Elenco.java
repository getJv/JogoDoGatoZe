import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Elenco here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Elenco
{
    private int x = 0;
    private int y = 0;
    private int larguraTotal = 0;
    private String nomeDaClasse;

    public Elenco(int x,int y,String nomeDaClasse){

        this.x = x;
        this.y = y;
        this.nomeDaClasse =  nomeDaClasse;

    }
    
    public Elenco(int largura){

        larguraTotal = largura;

    }
    
    public void setLarguraTotal(int largura){
        larguraTotal = largura;
    
    }
    public int getLarguraTotal(){
        return larguraTotal;
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
