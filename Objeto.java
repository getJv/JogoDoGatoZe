import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Classe para pai para todo ator não vivo do jogo.
 * 
 * @author  (Jhonatan Morais - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
abstract class Objeto extends Actor
{
    public int id;
    //protected  Personagem personagem; // Guarda o personagem que é utilizado em toda a classe
    public static final int  LIMITE_DA_COLISAO = 9; // limita o limite da quina do objeto para que o personagem 'toque' e se considere como colisão
    protected int tamanho; //Guarda o tamanho desejado para o objeto
    protected int filetaInicial; //Guarda a posição inicial para desenho da nova imagem do objeto 
    protected String nomeDoArquivo; //Guarda o caminho para a imagem do objeto
    protected String extensaoDoArquivo; //Guarda a extensão do arquivo utilizado
    protected int larguraDaFileta; //Guarda a largura do filete utilizado
    protected int alturaDaFileta; //Guarda a altura do filete utilizado
    protected int quantidadeDeFiletas;//Guarda a quantidade do filete utilizado
    public static final int TAXA_INTERVALO_DE_ATUALIZACAO = 2;

    public Objeto(){
        this.id = Greenfoot.getRandomNumber(1000);
    }

    /**
     * Act - do whatever the Objeto wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {

    } 

    /**
     * Retorna o mundo em que o objeto esta inserido
     */
    public Mundo1 oMundo(){

        return  (Mundo1) getWorld();

    }

    /**
     * Confirma se houve de fato a colisão pelo lado esquerdo do objeto.
     */
    public boolean houveColisaoDoLadoEsquerdo(){

        List<Personagem> listaDeAtores = getIntersectingObjects(Personagem.class);
        for(Personagem ator: listaDeAtores){
            boolean houveColisao = (ator.limiteDireito() - limiteEsquerdoDoObjeto() ) < LIMITE_DA_COLISAO; 
            if(houveColisao){
                return houveColisao;
            }

        }
        return false;
      

    }

    /**
     * Retorna o limite (coordenada X) do lado esquerdo do objeto  ;
     */
    public int limiteEsquerdoDoObjeto(){

        return this.getX() - (this.getImage().getWidth()/2);

    }

    /**
     * Retorna o limite (coordenada X) do lado esquerdo do objeto  ;
     */
    public int limiteDireitoDoObjeto(){

        return this.getX() + (this.getImage().getWidth()/2);

    }

    /**
     * Retorna o limite (coordenada Y) do TOPO do objeto  ;
     */
    public int limiteDoTopoDoObjeto(){

        return this.getY() - (this.getImage().getHeight()/2);

    }

    /**
     * Retorna o limite (coordenada Y) da BASE do objeto  ;
     */
    public int limiteDaBaseDoObjeto(){

        return this.getY() + (this.getImage().getHeight()/2);

    }

    /**
     * Confirma se houve de fato a colisão pelo lado direito do objeto.
     */
    public boolean houveColisaoDoLadoDireito(){
        List<Personagem> listaDeAtores = getIntersectingObjects(Personagem.class);
        for(Personagem ator: listaDeAtores){
            //int limiteEsquerdoDoAtor = ator.getX() - (ator.getImage().getWidth()/2);
            boolean houveColisao = (limiteDireitoDoObjeto() - ator.limiteEsquerdo()) < LIMITE_DA_COLISAO;
            if(houveColisao){
                return houveColisao;
            }

        }
        return false;
  
    }

    /**
     * Confirma se houve de fato a colisão pelo lado de cima (topo) do objeto.
     */
    public boolean houveColisaoDoLadoDeCima(){
        return ((oMundo().oHeroi().alturaDosPes() - limiteDoTopoDoObjeto() ) <= LIMITE_DA_COLISAO  );
    }

    /**
     * Confirma se houve de fato a colisão pelo lado de baixo (base) do objeto.
     */
    public boolean houveColisaoDoLadoDeBaixo(){
        return  (limiteDaBaseDoObjeto() - oMundo().oHeroi().alturaDaCabeca() ) <= LIMITE_DA_COLISAO  ;
    }

    /**
     * Verifica se o ator que colidiu esta dentro dos limites do objeto
     */
    public boolean estaSobMeuPerimetro(){

        List<Personagem> listaDeAtores = getIntersectingObjects(Personagem.class);
        for(Personagem ator: listaDeAtores){
            int limiteEsquerdoDoObjeto = limiteEsquerdoDoObjeto() + LIMITE_DA_COLISAO; // Mais a margem limite de colisão
            int limiteDireitoDoObjeto  = limiteDireitoDoObjeto() - LIMITE_DA_COLISAO;  // menos a margem limite de colisão

            boolean todoLadoEsquerdoDoAtorEstaSobreObjeto = ator.limiteEsquerdo() < limiteDireitoDoObjeto && ator.limiteEsquerdo() > limiteEsquerdoDoObjeto;
            boolean todoLadoDireitoDoAtorEstaSobreObjeto = ator.limiteDireito() > limiteEsquerdoDoObjeto && ator.limiteDireito() < limiteDireitoDoObjeto;

            if(todoLadoEsquerdoDoAtorEstaSobreObjeto || todoLadoDireitoDoAtorEstaSobreObjeto ){
                return true;
            }

           
        }
        
        
        return false; 
    }
    
    /**
     * Retorna a altura do topo do objeto
     */
    public int alturaDoTopo(){
        return getY() - getImage().getHeight()/2;
    }

    /**
     * Verifica se a altura dos pes ator esta acima do topo do objeto
     */
    public boolean oPersonagemEstaAcimaDoObjeto(){
        
        List<Personagem> listaDeAtores = getIntersectingObjects(Personagem.class);
        for(Personagem ator: listaDeAtores){
            //int limiteEsquerdoDoAtor = ator.getX() - (ator.getImage().getWidth()/2);
            boolean houveColisao = ator.alturaDosPes() <= alturaDoTopo();
            if(houveColisao){
                return houveColisao;
            }

        }
        return false;
    }

    protected void definirLarguraDaFileta(int largura){
        this.larguraDaFileta = largura;
    }

    protected void definirQuantidadeDeFiletas(int quantidade){
        this.quantidadeDeFiletas = quantidade;
    }

    protected void definirAlturaDaFileta(int altura){
        this.alturaDaFileta = altura;
    }

    protected void definirArquivoDaImagem(String caminhoDoArquivo,String nomeDaExtensao){
        this.nomeDoArquivo     = caminhoDoArquivo;
        this.extensaoDoArquivo = nomeDaExtensao;
    }

    public int pegarTamanho(){
        if(this.tamanho < 0) {this.tamanho =1;}
        return this.tamanho;
    }

    public void definirTamanho(int novoTamanho){     
        this.tamanho = novoTamanho;
    }

    public int pegarFiletaInicial(){
        return this.filetaInicial;
    }

    /**
     * Retorna a proxima fileta para renderização da imagem
     */
    public int pegarProximaFileta(){
        int proximaFileta = ++this.filetaInicial;
        if(proximaFileta > quantidadeDeFiletas) { 
            proximaFileta = 1;
        }
        return proximaFileta;
    }

    /**
     * Retorna a fileta anterior para renderização da imagem
     */
    public int pegarFiletaAnterior(){
        int proximaFileta = --this.filetaInicial;
        if(proximaFileta < 1) { 
            proximaFileta = quantidadeDeFiletas;
        }
        return proximaFileta;
    }

    public void definirFiletaInicial(int proximaFileta){     
        this.filetaInicial = proximaFileta;
    }

    /**
     * Define de fato a imagem do objeto
     */
    protected void criar(){
        setImage(desenhar());
    }

    /**
     * Redesenha a imagem que será atualizada no objeto para dar ideia de movimento do mesmo junto ao movimento do cenário
     */
    abstract void redesenhar(int tamanho,int posicaoInicial);

    /**
     * Define a solução para criar a imagem que será renderizada
     */
    abstract GreenfootImage desenhar();

    /**
     * Define a solução para o objeto mover-se para a esquerda
     */
    abstract void moveSeParaEsquerda();

    /**
     * Define a solução para o objeto mover-se para a esquerda
     */
    abstract void moveSeParaDireita();

    public boolean podeMoverObjetos(){
        return (oMundo().getCiclo() % TAXA_INTERVALO_DE_ATUALIZACAO) == 0;
    }

}
