import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe pai para todos os personagens vivos do jogo.
 * 
 * @author (Jhonatan Morais  - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
abstract class Personagem extends Actor
{
    //Constantes do Personagem
    public static final int ALTURA_DO_PULO = 20; 
    public static final int INTERVALO_DE_ATUALIZACAO = 6;

    //Variáveis de  controle dos movimentos
    protected int proximoPasso = 1;
    protected int alturaAtualDoPulo = 0;
    protected boolean estaPulando = false;
    protected boolean estaEmTerraFirme = true;
    protected boolean estaParado = true;
    protected boolean estaParaDireita = true;
    protected boolean estaParaEsquerda = false;
    protected Mundo1 mundo;


    /**
     * Act - do whatever the Personagens wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {

    }

    /**
     * Retorna uma instancia do mundo 
     */
    public Mundo1 oMundo(){

        return (Mundo1) getWorld();

    }

    /**
     * Utiliza o valor dos ciclos do cenário para criar o atraso necessário a atualização da sprite de movimento do personagem
     */
    abstract boolean possoAtualizarImagem();

    /**
     * Controla os passos do personagem para saber qual deve ser a sprite a se utilizar para representar sua caminhada
     */
    abstract void marcarPasso();

    /**
     * Garante que ao ficar parado a sprite do personagem seja atualizada para a posição inicial
     */
    abstract void ficandoParado();

    /**
     * Retorna a imagem que representa cada ação do personagem
     */
    abstract GreenfootImage retornaImagem();

    /**
     * Gerencia o movimento do pulo desde o inicio(subida) até a sua aterrisagem
     */
    abstract void pulando();

    /**
     * Pulo interrompido devido a colisão com um objeto
     */
    abstract void interromperPulo();

    /**
     * Retornar verdadeiro se atualmente o personagem estiver caminhando para direita
     */
    public boolean estaIndoPraDireta(){

        if(proximoPasso >= 1 && proximoPasso < 7 && estaParaDireita && !estaParado ){

            return true;
        }
        return false;
    }

    /**
     * Retornar verdadeiro se atualmente o personagem estiver caminhando para esquerda
     */
    public boolean estaIndoPraEsquerda(){

        if(proximoPasso >= 7 && proximoPasso < 13 && estaParaEsquerda && !estaParado){
            return true;
        }
        return false;
    }

    /**
     * Atualiza a direção de caminhada do personagem para direita
     */
    public void caminheParaDireita(){
        estaParaDireita = true;
        estaParaEsquerda = false;
        estaParado = false;
    }

    /**
     * Atualiza a direção de caminhada do personagem para esquerda
     */
    public void caminheParaEsquerda(){
        estaParaDireita = false;
        estaParaEsquerda = true;
        estaParado = false;
    }

    /**
     * Faz o personagem parar de caminhar
     */
    public void fiqueParado(){
        estaParado = true;
    }

    /**
     * Faz o personagem voltar a caminhar
     */
    public void podeCaminhar(){
        estaParado = false;
    }

    /**
     * Informa ao personagem que ele esta em terra firme
     */
    public void estaEmTerraFirme(){
        //Acho que nao voi precisar mais , basta usar a resistencia de gravidade do objeto
        estaEmTerraFirme = true;
        estaPulando = false;
    }

    /**
     *  Verifica se o personagem esta no nível do solo
     */
    public boolean estaNoNivelDoSolo(){
        int alturaDosPes = alturaDosPes();
        if(  alturaDosPes - Mundo1.NIVEL_DO_SOLO == 0 ){
            return true;
        }
        return false;

    }

    /**
     *  Verifica se o personagem esta  acima do nível do solo
     */
    public boolean estaAcimaDoSolo(){

        int alturaDosPes = alturaDosPes();

        if(  alturaDosPes < Mundo1.NIVEL_DO_SOLO){
            return true;

        }
        return false;

    }

    /**
     *  Verifica se o personagem esta  abaixo do nível do solo
     */
    public boolean estaAbaixoDoSolo(){

        int alturaDosPes = alturaDosPes();

        if(  alturaDosPes > Mundo1.NIVEL_DO_SOLO){
            return true;

        }
        return false;

    }

    /**
     * Retorna a altura atual do personagem considerando a altura dos pés
     */
    public int alturaAtual(){

        int alturaDosPes = alturaDosPes();
        int alturaAtual  = (alturaDosPes - Mundo1.NIVEL_DO_SOLO) *  -1; // apenas torna o valor positivo
        return alturaAtual;

    }

    /**
     * Retorna a altura dos pés do personagem dentro do jogo
     */
    public int alturaDosPes(){
        return getY() + getImage().getHeight()/2;
    }

    /**
     * Retorna a altura da cabeça do personagem dentro do jogo
     */
    public int alturaDaCabeca(){
        return getY() - getImage().getHeight()/2;
    }

    /**
     * Retorna o limite (coordenada X) direito do Personagem
     */
    public int limiteDireito(){
        return getX() + (getImage().getWidth()/2);
    }

    /**
     * Retorna o limite (coordenada X) esquerdo do Personagem
     */
    public int limiteEsquerdo(){
        return getX() - (getImage().getWidth()/2);
    }

}
