import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * Classe responsável por criar e gerenciar o cenário do mundo 1 do jogo do gato Zé.
 * 
 * @author (Jhonatan Morais  - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
public class Mundo1 extends World
{

    //Constantes do Mundo
    public static final String  NOME_ARQUIVO_IMAGEM = "cenarios/mundo1/m1f1_";
    public static final String  EXTENSAO_ARQUIVO_IMAGEM = ".png";
    public static final String  IMAGEM_INICIAL_MUNDO1 = "cenarios/mundo1/mundo1.png";
    public static final String  MUSICA_DO_MUNDO1 = "Summer_Smile.mp3";
    public static final int     QUANTIDADE_DE_QUADROS = 350;
    public static final int     TAMANHO_DO_QUADRO = 4;
    public static final int     POSICAO_INICIAL_PERSONAGEM = 250;
    public static final double  VELOCIDADE_ATUALIZACAO_QUADROS = 1;
    public static final int     TAMANHO_DO_MUNDO1 = 700 * 4; // Largura do cenário vezes 4
    public static final int     TAXA_INTERVALO_DE_ATUALIZACAO = 6;

    //Constantes da Natureza do mundo
    public static final int     LARGURA_CENARIO = 700;
    public static final int     ALTURA_CENARIO = 390;
    public static final int     NIVEL_DO_SOLO = 390;
    public static final int     FORCA_DA_GRAVIDADE = 5;

    //Variáveis de  controle do Jogo
    /**
     * Controla o quadro/fileta atual que foi apresentada no cenário 
     */
    private int quadroAtual = 1;

    /**
     * Conta quantas vezes todos os métodos act() de todos o s objetos foram executados
     */
    private int cicloAtual  = 0;

    /**
     * Marca o kilometro atual do personagem dentro do cenário
     */
    public int KMatual = 0;

    /**
     * Informa se o cenário pode ou não ser atualizado
     */
    private boolean oCenarioPodeAtualizar = true;

    /**
     * Marca o ultimo pixel que foi renderizado no cenario
     */
    public int colunaRenderizada = 0;

    //Objetos vivos do Jogo
    private GreenfootImage cenarioInicial;
    private Gato ze;
    private Objeto plataforma;
    private MostraTexto mt;
    private Placar placar;
    private Roteiro roteiro;

    /**
     * Contrutor do cenário.
     */
    public Mundo1()
    {    
        // Crio o Cenanario Inicial 
        super(LARGURA_CENARIO, ALTURA_CENARIO, 1); //Medidas do nosso cenário
        cenarioInicial = new GreenfootImage(IMAGEM_INICIAL_MUNDO1); //Crio a imagem inicial do cenário
        setBackground(cenarioInicial); //Coloco a imagem inicial dentro do nosso cenário

        roteiro = new Roteiro(this);

        //Crio os Objetos vivos
        ze = new  Gato();
        //setActOrder(Pisoteria.class,Gato.class);
        Instrucoes instrucoes = new  Instrucoes();
        addObject(instrucoes, 602, 80);

        placar = new Placar();
        addObject(new MostraTexto("Gato Zé"), 45,17);
        addObject(placar, 45 ,39);

        addObject(ze, POSICAO_INICIAL_PERSONAGEM, 300);
        KMatual = POSICAO_INICIAL_PERSONAGEM;

        //roteiro.addPiso(0,350);
        //roteiro.addPiso(400,550);
        roteiro.addPiso(270,370);
        roteiro.addPiso(400,440);
        roteiro.addPiso(460,560);
        //roteiro.criaPiso(0); 

        //Coloca o objeto que mostra valores na tela
        mt = new MostraTexto();
        addObject(mt, 250,10);

        //prepare();
    }

    /**
     * Retorna o valor do ciclo atual que varia de 0 a 1000, é útil para realizar cálculos secundários
     */
    public int getCiclo(){
        return cicloAtual;
    }

    /**
     * Atualiza o valor do ciclo até 1000, quando a contagem é então recomeça do 0
     */
    private void contaCiclo(){
        cicloAtual++;
        if(cicloAtual > 1000){

            cicloAtual = 0;
        }

    }

    /**
     * Executa recursivamente todas as ações de responsabilidade do mundo
     */
    public void act() 
    {
        contaCiclo();
        if(ze.estaIndoPraDireta() || ze.estaIndoPraEsquerda()  ){
            projetor(); 
            roteiro.coloqueOsPisosEmAcao();
            roteiro.coloqueOsAtoresEmAcao();
            verificarLimitesDoCenario();
        } 
        mt.atualiza("KMAtual: " + Integer.toString(KMAtual()  )  );
        aplicarForcaDaGravidade();
        
    }

    /**
     * Solicita que o cenário seja atualizado com a próxima cena
     */
    private void projetor(){
        GreenfootImage proximaCena = filme(); //Pego a proxima cena do filme

        if(oCenarioPodeAtualizar && estaNoIntervaloDeAtualizacao()){  
            controlaDirecao();  
            rebobinaFilme();
        }

        // Método que atualiza a imagem do cenário
        setBackground( proximaCena); 
    }

    /**
     * Verifica que o quadro atual já chegou ao limite de quadros da fita, se sim reinicia para o quadro inicial correspondente
     */
    private void rebobinaFilme(){
        if(this.quadroAtual > QUANTIDADE_DE_QUADROS){
            this.quadroAtual = 1;
        }else if(this.quadroAtual <= 0){
            this.quadroAtual = QUANTIDADE_DE_QUADROS;
        }

    }

    /**
     * Retorna verdadeiro para a constante que permite reduzir a taxa de atualização do cenario. 
     */
    private boolean estaNoIntervaloDeAtualizacao(){

        return getCiclo() % TAXA_INTERVALO_DE_ATUALIZACAO == 0; // EXperimental do paralax para o fundo

    }

    /**
     * Atualizo a direção em que a cena foi atualizada, se o herói foi pra direita o quadro atual avança, se para esquerda retrocede
     */
    private void controlaDirecao(){

        if(ze.estaIndoPraDireta()){
            this.quadroAtual += VELOCIDADE_ATUALIZACAO_QUADROS;

        }else{
            this.quadroAtual -= VELOCIDADE_ATUALIZACAO_QUADROS;
        }

    }

    /**
     * Cria e monta a próxima cena quadro a quadro e retorna a imagem pronta
     */
    private GreenfootImage filme(){
        GreenfootImage novaCena = new GreenfootImage(getWidth(),getHeight()); //Crio a moldura da nova imagem do cenário
        int posicaoDoQuadro = 0; //será a posição da fileta(slice/quadros) dentro da imagem do cenário
        int quadro = this.quadroAtual; // Pego o valor para saber apartir de qual fileta devo começar a criar a imagem
        while(posicaoDoQuadro < novaCena.getWidth()){ // Começo a criar a imagem

            if(quadro > QUANTIDADE_DE_QUADROS){ // Verifico se ja chequei ao final do filme, se sim reinicio do quadro inicial
                quadro =1; 
            }

            novaCena.drawImage(imagemDoNovoQuadro(quadro), posicaoDoQuadro, 0); //Coloco a fileta dentro da moldura da nova imagem
            quadro++; // atualizo para o valor da proxima fileta
            posicaoDoQuadro += TAMANHO_DO_QUADRO; // atualizo para a posição da nova fileta
        }

        return novaCena; // devolvemos a moldura da nova imagem totalmente preenchida com as filetas(quadros)
    }

    /**
     * Retorna o quadro/fileta/slice solicitado de dentro da pasta de imagens
     */
    private GreenfootImage imagemDoNovoQuadro(int proximoQuadro){
        String nomeDoArquivo = NOME_ARQUIVO_IMAGEM + proximoQuadro + EXTENSAO_ARQUIVO_IMAGEM;
        GreenfootImage novoquadro = new GreenfootImage(nomeDoArquivo);
        return novoquadro;
    }

    /**
     * Retorna todos os objetos do cenário que deve ser atualizados conforme a movimentaçao do personagem
     */
    protected ArrayList<Objeto> getObjetosDoCenario(){
        ArrayList<Objeto> listaDeObjetos = new ArrayList<Objeto>();
        listaDeObjetos.addAll( getObjects(Objeto.class));
        return listaDeObjetos;

    }

    /**
     * Solicita ao cenário para parar de atualizar sua movimentação
     */    
    public void pareDeAtualizarOCenario(){

        if(ze.estaIndoPraDireta() && oCenarioPodeAtualizar){
            plataforma.move(TAMANHO_DO_QUADRO * -1);
        }
        if(ze.estaIndoPraDireta() && oCenarioPodeAtualizar){
            plataforma.move(TAMANHO_DO_QUADRO );
        }

    }

    /**
     * Solicita ao cenário para voltar atualizar sua movimentação
     */  
    public void reinicieAtualizacaoDoCenario(){

        oCenarioPodeAtualizar = true;

    }

    /**
     * retorna verdadeiro se o cenário pode
     */  
    public boolean podeAtualizarCenario(){

        return oCenarioPodeAtualizar;

    }

    public void pareAtualizacaoDoCenario(){

        oCenarioPodeAtualizar = false;

    }

    public Gato oHeroi(){

        return ze;

    }

    /**
     * Retorna a altura inicial do solo para o ator solicitado
     */
    private int alturaInicialDoSolo(Actor ator){

        return NIVEL_DO_SOLO - ator.getImage().getHeight()/2;

    }

    /**
     * Aplica a gravidade em todos os atores do tipo personagem
     */
    public void aplicarForcaDaGravidade(){

        List<Personagem>  listaDePersonagem = getObjects(Personagem.class);
        for(Personagem ator : listaDePersonagem){
            int alturaDoGato = ator.alturaAtual();
            int alturaIicialSolo = alturaInicialDoSolo(ator);
            if(alturaDoGato > 0)  {  //temporario
                ator.setLocation(ator.getX(), ator.getY() + FORCA_DA_GRAVIDADE);
            }
            if(ator.getY() >= (ALTURA_CENARIO -1) || ator.getY() <= 0 ){

                removeObject(ator);

            }

        }

    }

    /**
     * Verifica se o personagem esta no inicio ou fim do cenário, se estiver ele coloca um objeto para impedir o avanço do personagem além desses limites
     */
    private void verificarLimitesDoCenario(){
        if(KMAtual() < 0 && getObjects(Linha.class).isEmpty()){
            addObject(new Linha(), 0, alturaInicialDoSolo(new Linha()));
        }

        if(KMAtual() > TAMANHO_DO_MUNDO1 && getObjects(Linha.class).isEmpty()){
            addObject(new Linha(), LARGURA_CENARIO, alturaInicialDoSolo(new Linha()));
        }
    }

    /**
     * Retorna um instancia do placar utilizada pelo mundo
     */
    public Placar getPlacar(){
        return placar;
    }

    /**
     * Sempre que o personagem vai para direita aumentamos um KM, na sua posicao no percurso total do cenário
     */
    public void aumentaKM(){
        if(oCenarioPodeAtualizar){
            this.KMatual++;
        }

    }

    /**
     * Sempre que o personagem vai para esquerda diminuimos um KM, na sua posicao no percurso total do cenário
     */
    public void diminuiKM(){
        this.KMatual--;
    }

    /**
     * Retorna a posicao atual do personagem dentro do tamanho do cenário
     */
    public int KMAtual(){
        return this.KMatual;
    }

    /**
     * Prepara o mundo para o início do programa.
     * Ou seja: criar os objetos iniciais e adicioná-los ao mundo.
     */
    private void prepare()
    {
        Pisoteria pisoteria = new Pisoteria();
        addObject(pisoteria,350,352);
        pisoteria.setLocation(257,366);
        pisoteria.redesenhar(500, 1);
        pisoteria.setLocation(404,363);
    }
}
