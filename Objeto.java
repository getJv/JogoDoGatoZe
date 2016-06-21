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
    public static final int  LIMITE_DA_QUINA = 10; // limita o limite da quina do objeto para que o personagem 'pise'
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
            
        }
    } 

    /**
     * Realiza a colisão esquerda-direita para impedir o avanço através do objeto
     */
    protected void bloqueiaLadoEsquerdo(){

        int ladoDireitoDoator = this.personagem.getX() + (this.personagem.getImage().getWidth()/2) - Mundo1.TAMANHO_DO_QUADRO;
        int ladoesquerdoDaPlataforma = 1 + this.getX() - (this.getImage().getWidth()/2);
        if(personagem.estaIndoPraDireta()){
            if( (ladoDireitoDoator - ladoesquerdoDaPlataforma) < 10 && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro(this.personagem) ){

                mundo.oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();

            }else{

                mundo.oCenarioPodeAtualizar();

            }
        }

    }

    /**
     * Realiza a colisão direita-esquerda para impedir o avanço através do objeto
     */
    protected void bloqueiaLadoDireito(){

        int ladoEsquerdoDoator = this.personagem.getX() - (this.personagem.getImage().getWidth()/2) - Mundo1.TAMANHO_DO_QUADRO;
        int ladoDireitoDaPlataforma = 1 + this.getX() + (this.getImage().getWidth()/2);
        if (personagem.estaIndoPraEsquerda()){
            if( (ladoDireitoDaPlataforma - ladoEsquerdoDoator) < 10 && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro(this.personagem)){

                mundo.oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();

            }else{

                mundo.oCenarioPodeAtualizar();

            }

        }

    }

    /**
     * Realiza a colisão topo-fundo para impedir o avanço através do objeto
     */
    protected boolean bloqueiaQueda(){

        int peDoator = this.personagem.getY() + (this.personagem.getImage().getHeight()/2);
        int tetoDaPlataforma = this.getY() - (this.getImage().getHeight()/2);
        int tt = tetoDaPlataforma - peDoator;

        if( estaSobMeuPerimetro(personagem) && (tetoDaPlataforma > peDoator  )){

            mundo.oCenarioPodeAtualizar();
            //O espaço entre o pé e a plataforma é devido a imagem do gato, mostrar isso ao alunos
            int novaAltura =  peDoator - (this.personagem.getImage().getHeight()/2) - Mundo1.FORCA_DA_GRAVIDADE ; 
            personagem.setLocation(personagem.getX(),novaAltura);
            personagem.estaEmTerraFirme(); 
            return true;
        }
        return false;
    } 

    /**
     * Realiza a colisão fundo-cabeça para impedir o avanço através do objeto de baixo para cima
     */
    protected boolean bloqueiaFundo(){

        int cabecaDoAtor = this.personagem.getY() - (this.personagem.getImage().getHeight()/2);
        int fundoDoObjeto = this.getY() + (this.getImage().getHeight()/2);
        int tt =  cabecaDoAtor - fundoDoObjeto;

        if( estaSobMeuPerimetro(personagem) && ( cabecaDoAtor > fundoDoObjeto  )){

            mundo.oCenarioPodeAtualizar();
            //O espaço entre o pé e a plataforma é devido a imagem do gato, mostrar isso ao alunos
            int novaAltura =  cabecaDoAtor + (this.personagem.getImage().getHeight()/2) + Mundo1.FORCA_DA_GRAVIDADE ; 
            personagem.setLocation(personagem.getX(),novaAltura);
            personagem.interromperPulo();
            return true;
        }
        return false;

    }
    
    
    /**
     * verifica se a colisão topo-fundo aconteceu
     */
    private boolean estaSobMeuPerimetro(Personagem ator){

        int limiteEsquerdoDoAtor = ator.getX() - ator.getImage().getWidth()/2;
        int limiteDireitoDoAtor  = ator.getX() + ator.getImage().getWidth()/2;
        int meuLimiteEsquerdo = getX() - getImage().getWidth()/2 + LIMITE_DA_QUINA;
        int meuLimiteDireito  = getX() + getImage().getWidth()/2 - LIMITE_DA_QUINA;

        boolean teste1 = limiteEsquerdoDoAtor < meuLimiteDireito && limiteEsquerdoDoAtor > meuLimiteEsquerdo;
        boolean teste2 = limiteDireitoDoAtor > meuLimiteEsquerdo && limiteDireitoDoAtor < meuLimiteDireito;
        boolean teste3 = ator.getX() < meuLimiteDireito && ator.getX() > meuLimiteEsquerdo;

        if(teste1 || teste2 || teste3){
            return true;
        }
        return false; 

    }
    
        
    

    /**
     * verifica se houve um a colisão 
     */
    protected boolean temAlguemAqui(){
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

    public boolean oPersonagemEstaAcimaDoObjeto(){
        int pes = this.personagem.alturaDosPes();
        int topo = alturaDoTopo();
        boolean tt = (pes - topo) <= 4; // usei a diferença pq a precisão de (pes < topo) não nos atende, o valor 4 foi selecionado pq é o valor minimo da diferença de (pes - topo)
        return tt;
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
