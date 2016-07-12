import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pisoteria here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        if(temAlguemAqui()  ){
            bloqueiaLadoEsquerdo();
            bloqueiaLadoDireito();
            bloqueiaQueda(); 
            bloqueiaFundo(); 
        }else{
            oMundo().oCenarioPodeAtualizar();
        }

    }

    /**
     * Realiza a bloqueio da passagem do ator pelo lado esquerdo do objeto.
     */
    protected void bloqueiaLadoEsquerdo(){
        if(personagem.estaIndoPraDireta()){
            if( houveColisaoDoLadoEsquerdo() && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                oMundo().oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();
            }
        }else{
            oMundo().oCenarioPodeAtualizar();
        }
    }

    /**
     * Realiza a bloqueio da passagem do ator pelo lado direito do objeto.
     */
    protected void bloqueiaLadoDireito(){
        if (personagem.estaIndoPraEsquerda()){
            if(  houveColisaoDoLadoDireito () && !oPersonagemEstaAcimaDoObjeto() && !estaSobMeuPerimetro() ){
                oMundo().oCenarioNaoPodeAtualizar();
                personagem.fiqueParado();
            }
        }else{
            oMundo().oCenarioPodeAtualizar();

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
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento
     */
    protected void saiaDeCenaPelaDireita(){

        if(limiteDireitoDoObjeto() >= 699 ){
            redesenhar(pegarTamanho() - 4,  pegarFiletaInicial());
        }

    }

    /**
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento de entrada pela direita
     */
    protected void entreEmCenaPelaDireita(){

        redesenhar(pegarTamanho() + 4,  pegarFiletaInicial());

    }

    /** 
     * Redesenha a imagem que será atualizada no objeto para dar ideia de movimento do mesmo junto ao movimento do cenário
     */
    public void redesenhar(int tamanho,int posicaoInicial){

        if(tamanho < 0) {tamanho =1;}
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

    public void moveSeParaEsquerda(){

        if(podeMoverObjetos()){
            
            if (oMundo().oHeroi().estaIndoPraDireta() && !estouCompleto() && oMundo().KMAtual() > this.ultimoKM){
                entreEmCenaPelaDireita();  
                this.ultimoKM = oMundo().KMAtual();
            }
            
            if(limiteEsquerdoDoObjeto() > 1 && estouCompleto()){ // impede que ambos os métodos de movimentação de objetos sejam execitados juntos

                move(oMundo().TAMANHO_DO_QUADRO * -1);

            }
            
            saiaDeCenaPelaEsquerda();
            
            
        }

    }
    
    /**
     * Faz com que o tamanho e imagem do piso seja atualizada, criando assim a ideia de movimento
     */
    protected void saiaDeCenaPelaEsquerda(){
        if(limiteEsquerdoDoObjeto() <= 1){
            redesenhar(pegarTamanho() - 4,  pegarProximaFileta() );
            move(oMundo().TAMANHO_DO_QUADRO * -1);
        }
        if(limiteEsquerdoDoObjeto() <= 1 && pegarTamanho() <= 4){
            oMundo().removeObject(this);
        }
    }
    
    public void moveSeParaDireita(){
        if(podeMoverObjetos()){
            if(limiteDireitoDoObjeto() < 700){ // impede que ambos os métodos de movimentação de objetos sejam execitados juntos
                move(oMundo().TAMANHO_DO_QUADRO );}
            else{
                saiaDeCenaPelaDireita(); 
            }

        }

    }


}
