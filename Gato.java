import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe para Criar o personagem do Gato Zé
 * 
 * @author (Jhonatan Morais - jhonatanvinicius@gmail.com or jhonatan@dfjug.org) 
 * @version (1.0)
 */
public class Gato extends Personagem
{
    

    /**
     * Act - do whatever the Ze wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();

        if(Greenfoot.isKeyDown("right")){
            caminheParaDireita();
            marcarPasso();
            setImage(retornaImagem());
            if(getX() < getWorld().getWidth()/6 || estaPulando){
                //move(TAMANHO_DO_PASSO);
            }
        }else if(Greenfoot.isKeyDown("left")){
            caminheParaEsquerda();
            marcarPasso();
            setImage(retornaImagem());
            if(getX() > getWorld().getWidth()/3|| estaPulando){
                // move(TAMANHO_DO_PASSO *(-1));
            }
        }

        if(Greenfoot.isKeyDown("space") && estaEmTerraFirme ){
            estaPulando      = true;
            estaEmTerraFirme = false;
        }

        if(estaPulando){
            pulando();
        }else if(alturaAtual() == 0){
            estaEmTerraFirme = true;
        }

        ficandoParado();

    } 

    /**
     * Utiliza o valor dos ciclos do cenário para criar o atraso necessário a atualização da sprite de movimento do personagem
     */

    protected boolean possoAtualizarImagem(){

        return oMundo().getCiclo() % INTERVALO_DE_ATUALIZACAO  == 0;
    }

    /**
     * Controla os passos do personagem para saber qual deve ser a sprite a se utilizar para representar sua caminhada
     */
    protected void marcarPasso(){

        if(estaParaDireita ){

            if(possoAtualizarImagem()){
                proximoPasso++;
                oMundo().aumentaKM();
            } 

            if(proximoPasso > 6){
                proximoPasso = 1;
            }

            

        }else if(estaParaEsquerda){

            if(possoAtualizarImagem()){
                proximoPasso++;
                oMundo().diminuiKM();
            }
            if(proximoPasso < 7 || proximoPasso > 12 ){
                proximoPasso = 7;
            }

            
        }

    }

    /**
     * Garante que ao ficar parado a sprite do personagem seja atualizada para a posição inicial
     */
    protected void ficandoParado(){
        if(proximoPasso >= 1 && proximoPasso < 7 && estaParaDireita && !Greenfoot.isKeyDown("right")){
            proximoPasso = 1;
            setImage(retornaImagem());
            fiqueParado();
        }
        if(proximoPasso > 6 && proximoPasso < 13 && estaParaEsquerda && !Greenfoot.isKeyDown("left")){
            proximoPasso = 7;
            setImage(retornaImagem());
            fiqueParado();
        }

    }

    /**
     * Retorna a imagem que representa cada ação do personagem
     */
    protected GreenfootImage retornaImagem(){
        //Retorna imagem parado e virado para direita
        if(estaParaDireita && estaNoNivelDoSolo()){
            return new GreenfootImage("images/persons/ze/ze_"+proximoPasso+".png");
        }
        // Retorna imagem para caminhada a direita quando se esta em terraFirma acima do nivel do solo
        if(estaParaDireita && estaAcimaDoSolo() && estaEmTerraFirme){
            return new GreenfootImage("images/persons/ze/ze_"+proximoPasso+".png");
        } 
        //Retorna imagem pulando/caindo lado direito
        if(estaParaDireita && estaAcimaDoSolo() ){
            return new GreenfootImage("images/persons/ze/ze_2.png");
        }
        //Retorna imagem parado e virado para esquerda
        if(estaParaEsquerda && estaNoNivelDoSolo()){
            return new GreenfootImage("images/persons/ze/ze_"+proximoPasso+".png");
        }
        // Retorna imagem para caminhada a esquerda quando se esta em terraFirma acima do nivel do solo
        if(estaParaEsquerda && estaAcimaDoSolo() && estaEmTerraFirme){
            return new GreenfootImage("images/persons/ze/ze_"+proximoPasso+".png");
        } 
        //Retorna imagem pulando/caindo lado esquerda 
        if(estaParaEsquerda && estaAcimaDoSolo()){
            return new GreenfootImage("images/persons/ze/ze_8.png");
        } 

        return new GreenfootImage("images/persons/ze/ze_1.png");
    }

    /**
     * Gerencia o movimento do pulo desde o inicio(subida) até a sua aterrisagem
     */
    protected void pulando(){

        if(alturaAtualDoPulo < ALTURA_DO_PULO){
            setImage(retornaImagem());
            alturaAtualDoPulo++;
            setLocation(getX(), getY() - Mundo1.FORCA_DA_GRAVIDADE *2);
        }
        if(alturaAtualDoPulo == ALTURA_DO_PULO){
            estaPulando = false;
            alturaAtualDoPulo = 0;
            setImage(retornaImagem());
        }

    }

    /**
     * Pulo interrompido devido a colisão com um objeto
     */
    protected void interromperPulo(){
        estaPulando = false;
        alturaAtualDoPulo = 0;
    }

}
