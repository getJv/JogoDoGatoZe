import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Moedaria here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Moedaria extends Objeto
{
    public static final int INTERVALO_DE_ATUALIZACAO = 8;
    public static final int VALOR_PONTO_MOEDA = 100;

    public Moedaria(){

        definirFiletaInicial(1);

    }

    /**
     * Act - do whatever the Moedaria wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        if(possoAtualizarImagem()){
            setImage( new GreenfootImage("objetos/coin/coin_"+filetaInicial+".png"));
            filetaInicial++;
        }
        if(filetaInicial >= 6){
            definirFiletaInicial(1);
        }

        if(temAlguemAqui()){
            mundo.removeObject(this);
            mundo.getPlacar().acrescenteMais(VALOR_PONTO_MOEDA);
            
        }

        
    }

    /**
     * Utiliza o valor dos ciclos do cenário para criar o atraso necessário a atualização da sprite de movimento do personagem
     */

    protected boolean possoAtualizarImagem(){

        return mundo.getCiclo() % INTERVALO_DE_ATUALIZACAO  == 0;
    }

    /**
     * Redesenha a imagem que será atualizada no objeto para dar ideia de movimento do mesmo junto ao movimento do cenário
     */
    public void redesenhar(int tamanho,int posicaoInicial){
        definirTamanho(tamanho);
        definirPosicaoInicial(posicaoInicial);
        setImage(desenhar());
    }

    /**
     * Define a solução para criar a imagem que será renderizada
     */
    protected GreenfootImage desenhar(){

        GreenfootImage novoPiso = new GreenfootImage(pegarTamanho(),alturaDaFileta); 
        int posicaoDaNovaFileta = 0; 
        int filetaAtual = this.filetaInicial; 
        while(posicaoDaNovaFileta < novoPiso.getWidth()){ 
            if(filetaAtual > quantidadeDeFiletas){ 
                filetaAtual = 1; 
            }else if(filetaAtual < 1){
                filetaAtual = quantidadeDeFiletas;
            }
            novoPiso.drawImage(new GreenfootImage(nomeDoArquivo + filetaAtual + extensaoDoArquivo), posicaoDaNovaFileta, 0); 
            filetaAtual++; 
            posicaoDaNovaFileta += larguraDaFileta; 
        }
        return novoPiso;

    }
    
    
    
    
    
   

    
    
    
}
