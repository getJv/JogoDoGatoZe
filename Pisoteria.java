import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Classe responsável por criar e gerenciar os pisos do cenário do mundo.
 * 
 * @author (Jhonatan Morais  - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
public class Pisoteria extends Objeto
{
    int larguraTotal = 0;
    int ultimoKM = 0;

    public  boolean estouCompleto(){
        int pegarTamanho = pegarTamanho();

        return pegarTamanho() ==  larguraTotal;
    }

    public Pisoteria(int larguraTotal,int posicaoNoMundo){
        this.larguraTotal = larguraTotal;
        this.ultimoKM = posicaoNoMundo;

        definirTamanho(4);
        definirFiletaInicial(1);
        definirArquivoDaImagem("objetos/piso/piso_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(54);
        definirQuantidadeDeFiletas(41);
        criar();
    }

    public Pisoteria(){

        definirTamanho(4);
        definirFiletaInicial(1);
        definirArquivoDaImagem("objetos/piso/piso_",".png");
        definirLarguraDaFileta(4);
        definirAlturaDaFileta(54);
        definirQuantidadeDeFiletas(41);
        criar();
    }

    public void act() 
    {
        super.act();

        bloqueiaLadoEsquerdo();
        bloqueiaLadoDireito();
        bloqueiaQueda(); 
        bloqueiaFundo(); 

        if(oMundo().oHeroi().estaIndoPraDireta() && oMundo().podeAtualizarCenario()){

            moveSeParaEsquerda();
        }

        if(oMundo().oHeroi().estaIndoPraEsquerda() && oMundo().podeAtualizarCenario()){
            moveSeParaDireita();
        }
        // Deve ser o ultimo metodo a ser chamado, pois ele exclui-se a si proprio do mundo. 
        //e se for executado antes das linhas acima teremos um eero
        sairDoMundo();
    }

    /**
     * Verifica se algum dos outros pisos do mundo fora o atual esta colidido com o ator.
     */
    public boolean oAtorColidiuComAlgumOutroPiso(){

        List<Pisoteria> pisosNoMundo = oMundo().getObjects(Pisoteria.class);
        for(Pisoteria piso : pisosNoMundo){
            if(piso.houveColisaoDoLadoEsquerdo() || piso.houveColisaoDoLadoDireito()){
                return true;
            }

        }
        return false;
    }

    /**
     * Realiza a bloqueio da passagem do ator pelo lado esquerdo do objeto.
     */
    protected void bloqueiaLadoEsquerdo(){
        if(oMundo().oHeroi().estaIndoPraDireta()){
            if( houveColisaoDoLadoEsquerdo() && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                oMundo().pareAtualizacaoDoCenario();
                oMundo().oHeroi().fiqueParado();
            }
        }else if(oMundo().oHeroi().estaIndoPraEsquerda()){
            oMundo().reinicieAtualizacaoDoCenario();
            oMundo().oHeroi().podeCaminhar();
        }
    }

    /**
     * Realiza a bloqueio da passagem do ator pelo lado direito do objeto.
     */
    protected void bloqueiaLadoDireito(){
        if (oMundo().oHeroi().estaIndoPraEsquerda()){
            if(  houveColisaoDoLadoDireito () && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                oMundo().pareAtualizacaoDoCenario();
                oMundo().oHeroi().fiqueParado();
            }
        }else if(oMundo().oHeroi().estaIndoPraDireta()){
            oMundo().reinicieAtualizacaoDoCenario();
            oMundo().oHeroi().podeCaminhar();
        }

    }

    /**
     * Realiza a colisão fundo-cabeça para impedir o avanço através do objeto de baixo para cima
     */
    protected void bloqueiaFundo(){
        if( estaSobMeuPerimetro() && houveColisaoDoLadoDeBaixo()){
            int novaAltura =  oMundo().oHeroi().getY() + Mundo1.FORCA_DA_GRAVIDADE; 
            oMundo().oHeroi().setLocation(oMundo().oHeroi().getX(),novaAltura);
            oMundo().oHeroi().interromperPulo();
        }
    }

    /**
     * Realiza a colisão topo-fundo para impedir o avanço através do objeto
     */
    protected void bloqueiaQueda(){
        if( estaSobMeuPerimetro() && houveColisaoDoLadoDeCima()){
            int novaAltura =  oMundo().oHeroi().getY() - Mundo1.FORCA_DA_GRAVIDADE ; 
            oMundo().oHeroi().setLocation(oMundo().oHeroi().getX(),novaAltura);
            oMundo().oHeroi().estaEmTerraFirme(); 
        }

    }

    /**
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento de entrada 
     */
    protected void entreEmCenaPela(){

        redesenhar(pegarTamanho() + 4,  pegarFiletaInicial());

    }

    /** 
     * Redesenha a imagem que será atualizada no objeto para dar ideia de movimento do mesmo junto ao movimento do cenário
     */
    public void redesenhar(int tamanho,int posicaoInicial){

        if(tamanho < 1) {tamanho =1;}
        definirTamanho(tamanho);
        definirFiletaInicial(posicaoInicial);
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

    /**
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento
     */
    protected void saiaDeCenaPelaEsquerda(){
        if(limiteEsquerdoDoObjeto() <= 1){
            redesenhar(pegarTamanho() - 4,  pegarProximaFileta() );
            move(oMundo().TAMANHO_DO_QUADRO * -1);
        }

    }

    /**
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento
     */
    protected void saiaDeCenaPelaDireita(){

        if(limiteDireitoDoObjeto() >= 699 ){
            redesenhar(pegarTamanho() - 4,  pegarFiletaInicial());
            move(oMundo().TAMANHO_DO_QUADRO);
        }

    }

    /**
     * Retira o objeto do mundo
     */
    private void sairDoMundo(){
        if (oMundo().oHeroi().estaIndoPraEsquerda()){

            if((limiteEsquerdoDoObjeto() >= 699  && pegarTamanho() <= 4) ){
                oMundo().removeObject(this);
            }
        }else if (oMundo().oHeroi().estaIndoPraDireta()){
            if( (limiteDireitoDoObjeto() <= 1  && pegarTamanho() <= 4)){
                oMundo().removeObject(this);
            }
        }
    }

    /**
     * Controla o movimento do objeto para direita conforme os atores interagem com o mundo.
     */
    protected void moveSeParaDireita(){
        if(podeMoverObjetos()){
            if (oMundo().oHeroi().estaIndoPraEsquerda() && !estouCompleto() && oMundo().KMAtual() > this.ultimoKM){
                entreEmCenaPela();  
                this.ultimoKM = oMundo().KMAtual();
            }

            if(limiteDireitoDoObjeto() < 700 && estouCompleto() && oMundo().podeAtualizarCenario() && !oAtorColidiuComAlgumOutroPiso()){ 
                move(oMundo().TAMANHO_DO_QUADRO );
            }

            saiaDeCenaPelaDireita(); 

        }
    }

    /**
     * Controla o movimento do objeto para esquerda conforme os atores interagem com o mundo.
     */
    public void moveSeParaEsquerda(){

        if(podeMoverObjetos() ){

            if (oMundo().oHeroi().estaIndoPraDireta() && !estouCompleto() && oMundo().KMAtual() > this.ultimoKM){
                entreEmCenaPela();  
                this.ultimoKM = oMundo().KMAtual();

            }

            if(limiteEsquerdoDoObjeto() > 1 && estouCompleto() && oMundo().podeAtualizarCenario() && !oAtorColidiuComAlgumOutroPiso()){ 
                move(oMundo().TAMANHO_DO_QUADRO * -1);

            }

            saiaDeCenaPelaEsquerda();

        }
    }

}
