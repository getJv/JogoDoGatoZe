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
