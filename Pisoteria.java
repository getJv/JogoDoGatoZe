import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pisoteria here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pisoteria extends Objeto
{
    

    public Pisoteria(){
        definirTamanho(100);
        definirFiletaInicial(1);
        definirArquivoDaImagem("objetos/piso/piso_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(54);
        definirQuantidadeDeFiletas(41);
        criar();
    }

    public Pisoteria(int tamanho,int posicaoInicial){
        definirTamanho(tamanho);
        definirFiletaInicial(posicaoInicial);
        definirArquivoDaImagem("objetos/piso/piso_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(54);
        definirQuantidadeDeFiletas(41);
        criar();
    }

    public void act() 
    {
        super.act();
    }
}
