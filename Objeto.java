import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe para pai para todo ator não vivo do jogo.
 * 
 * @author  (Jhonatan Morais - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
abstract class Objeto extends Actor
{
    private  Personagem personagem; // Guarda o personagem que é utilizado em toda a classe
    private  Mundo1 mundo; // Guarda o mundo que é utilizado em toda a classe
    public static final int  LIMITE_DA_COLISAO = 9; // limita o limite da quina do objeto para que o personagem 'toque' e se considere como colisão
    protected int tamanho; //Guarda o tamanho desejado para o objeto
    protected int filetaInicial; //Guarda a posição inicial para desenho da nova imagem do objeto 
    protected String nomeDoArquivo; //Guarda o caminho para a imagem do objeto
    protected String extensaoDoArquivo; //Guarda a extensão do arquivo utilizado
    protected int larguraDaFileta; //Guarda a largura do filete utilizado
    protected int alturaDaFileta; //Guarda a altura do filete utilizado
    protected int quantidadeDeFiletas;//Guarda a quantidade do filete utilizado

    /**
     * Act - do whatever the Objeto wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        mundo = (Mundo1) getWorld();
        if(temAlguemAqui()  ){
            bloqueiaLadoEsquerdo();
            bloqueiaLadoDireito();
            bloqueiaQueda(); 
            bloqueiaFundo(); 
        }else{
            mundo.oCenarioPodeAtualizar();
        }
    } 

    /**
     * Realiza a bloqueio da passagem do ator pelo lado esquerdo do objeto.
     */
    protected void bloqueiaLadoEsquerdo(){
        if(personagem.estaIndoPraDireta()){
            if( houveColisaoDoLadoEsquerdo() && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                mundo.oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();
            }
        }else{
            mundo.oCenarioPodeAtualizar();
        }
    }

    /**
     * Confirma se houve de fato a colisão pelo lado esquerdo do objeto.
     */
    public boolean houveColisaoDoLadoEsquerdo(){

        return (this.personagem.limiteDireito() - limiteEsquerdoDoObjeto() ) < LIMITE_DA_COLISAO;

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
     * Realiza a bloqueio da passagem do ator pelo lado direito do objeto.
     */
    protected void bloqueiaLadoDireito(){
        if (personagem.estaIndoPraEsquerda()){
            if(  houveColisaoDoLadoDireito () && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                mundo.oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();
            }
        }else{
            mundo.oCenarioPodeAtualizar();

        }
    }

    /**
     * Confirma se houve de fato a colisão pelo lado direito do objeto.
     */
    public boolean houveColisaoDoLadoDireito(){
        return (limiteDireitoDoObjeto() - personagem.limiteEsquerdo()) < LIMITE_DA_COLISAO;
    }

    /**
     * Realiza a colisão topo-fundo para impedir o avanço através do objeto
     */
    protected void bloqueiaQueda(){
        if( estaSobMeuPerimetro() && houveColisaoDoLadoDeCima()){
            int novaAltura =  personagem.getY() - Mundo1.FORCA_DA_GRAVIDADE ; 
            personagem.setLocation(personagem.getX(),novaAltura);
            personagem.estaEmTerraFirme(); 
        }

    }

    /**
     * Confirma se houve de fato a colisão pelo lado de cima (topo) do objeto.
     */
    public boolean houveColisaoDoLadoDeCima(){
        return ((personagem.alturaDosPes() - limiteDoTopoDoObjeto() ) <= LIMITE_DA_COLISAO  );
    }

    /**
     * Realiza a colisão fundo-cabeça para impedir o avanço através do objeto de baixo para cima
     */
    protected void bloqueiaFundo(){
        if( estaSobMeuPerimetro() && houveColisaoDoLadoDeBaixo()){
            int novaAltura =  personagem.getY() + Mundo1.FORCA_DA_GRAVIDADE; 
            personagem.setLocation(personagem.getX(),novaAltura);
            personagem.interromperPulo();
        }
    }

    /**
     * Confirma se houve de fato a colisão pelo lado de baixo (base) do objeto.
     */
    public boolean houveColisaoDoLadoDeBaixo(){
        return  (limiteDaBaseDoObjeto() - personagem.alturaDaCabeca() ) <= LIMITE_DA_COLISAO  ;
    }

    /**
     * Verifica se o ator que colidiu esta dentro dos limites do objeto
     */
    private boolean estaSobMeuPerimetro(){

        int limiteEsquerdoDoObjeto = limiteEsquerdoDoObjeto() + LIMITE_DA_COLISAO; // Mais a margem limite de colisão
        int limiteDireitoDoObjeto  = limiteDireitoDoObjeto() - LIMITE_DA_COLISAO;  // menos a margem limite de colisão

        boolean todoLadoEsquerdoDoAtorEstaSobreObjeto = personagem.limiteEsquerdo() < limiteDireitoDoObjeto && personagem.limiteEsquerdo() > limiteEsquerdoDoObjeto;
        boolean todoLadoDireitoDoAtorEstaSobreObjeto = personagem.limiteDireito() > limiteEsquerdoDoObjeto && personagem.limiteDireito() < limiteDireitoDoObjeto;

        if(todoLadoEsquerdoDoAtorEstaSobreObjeto || todoLadoDireitoDoAtorEstaSobreObjeto ){
            return true;
        }
        return false; 

    }

    /**
     * verifica se houve um a colisão 
     */
    public boolean temAlguemAqui(){
        this.personagem = (Personagem) getOneIntersectingObject(Personagem.class);
        if(personagem != null){
            return true;
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
        return personagem.alturaDosPes() <= alturaDoTopo();
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
        return this.tamanho;
    }

    public void definirTamanho(int novoTamanho){     
        this.tamanho = novoTamanho;
    }

    public void definirPosicaoInicial(int proximaFileta){     
        this.filetaInicial = proximaFileta;
    }

    public int pegarFiletaInicial(){
        return this.filetaInicial;
    }

    public void definirFiletaInicial(int proximaFileta){     
        this.filetaInicial = proximaFileta;
    }

    protected void criar(){

        setImage(desenhar());
    }

    public void redesenhar(int tamanho,int posicaoInicial){
        definirTamanho(tamanho);
        definirPosicaoInicial(posicaoInicial);
        setImage(desenhar());
    }

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
