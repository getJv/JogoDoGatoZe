import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe manipulação das moedas do jogo
 * 
 * @author  (Jhonatan Morais - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
public class Moedaria extends Objeto
{
    public static final int INTERVALO_DE_ATUALIZACAO = 8;
    public static final int VALOR_PONTO_MOEDA = 100;
    private int proximaImagem = 2;

    public Moedaria(){
        definirTamanho(30);
        definirFiletaInicial(1);
        definirArquivoDaImagem("objetos/coin/coin_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(31);
        definirQuantidadeDeFiletas(45);
        criar();
    }

    public Moedaria(int tamanho,int posicaoInicial){
        definirTamanho(tamanho);
        definirFiletaInicial(posicaoInicial);
        definirArquivoDaImagem("objetos/coin/coin_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(31);
        definirQuantidadeDeFiletas(45);
        criar();
    }

    /**
     * Act - do whatever the Moedaria wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        super.act();
        rodaMoeda();
        contabilizaMoeda();

    }
    /**
     * Gira a imagem da moeda
     */
    public void rodaMoeda(){
        if(possoAtualizarImagem()){
            setImage(new GreenfootImage("/objetos/coin/split_coin/coin_" + proximaImagem + ".png"));
            proximaImagem++;
        }

        if(proximaImagem > 6){
            proximaImagem = 1;
        }

    }

    /**
     * Gerencia quando o personagem captura a moeda
     */
    public void contabilizaMoeda(){
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
