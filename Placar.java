import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * Classe para manipular o placar de pontuação do jogo.
 * 
 * @author (Jhonatan Morais - jhonatanvinicius@gmail.com - www.getjv.com.br) 
 * @version (1.0)
 */
public class Placar extends Actor
{
    private int pontuacao = 0;   
    private int BufferDaPontuacao = 0;
    public static final int FATOR_DE_ATUALIZACAO = 4;
    public static final int TAMANHO_FONTE = 28;
    public static final Color COR_FONTE = Color.WHITE;
    public static final Color COR_CONTORNO_FONTE = Color.BLACK;
    public static final String NOME_FONTE = "Helvetica";

    /**
     * Act - do whatever the Placar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        atualizaPontuacao();
    }

    public Placar(){
        atualizaImagem(convertePontuacao(0)); 
    }

    /**
     * Acrescenta a nova pontuação ao placar
     */
    public void acrescenteMais(int xPontos){
        BufferDaPontuacao += xPontos;

    }

    /**
     * Converte um numero para o formato string e acrescenta zeros a esquerda deste, quando necessário
     */
    private String convertePontuacao(int pontos){
        return String.format("%06d", pontos);
    }

    /**
     * Atualiza o novo valor no objeto Placar
     */
    private void atualizaImagem(String txt){

        GreenfootImage pontos = new GreenfootImage(txt,TAMANHO_FONTE,COR_FONTE,new Color(0,0,0,0),COR_CONTORNO_FONTE);
        Font fonte = new Font(NOME_FONTE, Font.BOLD, 0);
        pontos.setFont(fonte);
        setImage(pontos);

    }

    /**
     * Atualiza a pontuação do placar
     */
    private void atualizaPontuacao(){

        if( BufferDaPontuacao > 0){
            pontuacao += FATOR_DE_ATUALIZACAO;
            BufferDaPontuacao -= FATOR_DE_ATUALIZACAO;
            atualizaImagem(convertePontuacao(pontuacao)); 

        }

    }
}
