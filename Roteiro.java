import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Roteiro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Roteiro extends Actor
{
    public static final int TAXA_INTERVALO_DE_ATUALIZACAO = 2;
    private int tamanhoCenario;
    private int filetaAtual = 1;
    private Mundo1 mundo;
    private Map<Integer,ArrayList<Elenco>> roteiro = new HashMap<Integer,ArrayList<Elenco>> ();
    private Map<Integer,Boolean> mapaDoPiso = new HashMap<Integer,Boolean> ();

    /**
     * Act - do whatever the Roteiro wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // Add your action code here.
    }

    public Roteiro(Mundo1 mundoAtual){
        mundo = mundoAtual;
        adicionaElenco(104, 704, 250, "Moedaria");

    }

    /**
     * Cadastras os atores o o ponto de entrada em que participarão no cenário
     */
    public void adicionaElenco(int pontoDeEntrada, int x, int y, String nomeDaClasseDoAtor){

        Elenco ator = new Elenco(x,y,nomeDaClasseDoAtor);

        try{

            roteiro.get(pontoDeEntrada).add(ator);

        }catch(NullPointerException e){

            ArrayList<Elenco> atores = new ArrayList<Elenco>();
            atores.add(ator);
            roteiro.put(pontoDeEntrada, atores );
        }

    }

    /**
     * Verifica se no quadro atual deve entrar algum ator em cena
     */
    public boolean temNovoAtor(int quadro){

        try{
            return !roteiro.get(quadro).isEmpty();
        }catch(NullPointerException e){

            return false;
        }

    }

    /**
     * Verifica e adiciona os atores que devem entrar em cena
     */
    public void coloqueOsAtoresEmAcao(int quadro)  {

        ArrayList<Elenco> Elenco = roteiro.get(quadro);
        for(Elenco ator : Elenco){

            try{
                Class clazz = Class.forName(ator.nomeDaClasse());
                Actor novoAtor  = (Actor) clazz.newInstance();
                mundo.addObject(novoAtor, ator.getX(), ator.getY());

            }catch(Exception e){

                System.out.println("Erro Desconhecido no roteiro ao tentar adicionar um novo ator no mundo." );
                e.printStackTrace();

            }

        }
    }

    public void addPiso(int pontoInicial,int pontoFinal){

        while(pontoInicial < pontoFinal){

            mapaDoPiso.put(pontoInicial, true);
            pontoInicial++;

        }

    }

    /**
     * Retira os atores que estão nos limites do cenario
     */
    public void removaOsAtoresDoCenario(){

        List<Actor> atores = mundo.getObjects(Actor.class);
        for (Actor ator: atores){
            int limiteDireito  = ator.getX() + ator.getImage().getWidth()/2;
            int limiteEsquerdo = ator.getX() - ator.getImage().getWidth()/2;
            if(limiteDireito <= 1 || limiteEsquerdo >= 699){
                mundo.removeObject(ator);
            }
        }

    }

    /**
     * Solicita ao Pisoteria que retire os pisos do cenario pelo lado esquerdo.
     */
    protected void tireOsPisosPelaEsquerda(){

        List<Pisoteria> pisos = mundo.getObjects(Pisoteria.class);
        for(Pisoteria piso: pisos){
            piso.saiaDeCenaPelaEsquerda();
        }
    }

    /**
     * Solicita ao Pisoteria que retorceda os pisos pelo cenario.
     */
    protected void tireOsPisosPelaDireita(){

        List<Pisoteria> pisos = mundo.getObjects(Pisoteria.class);
        for(Pisoteria piso: pisos){
            piso.saiaDeCenaPelaDireita();
        }
    }

    public int getFileta(){
        return filetaAtual;
    }

    public void criaPiso(int pontoInicial){
        //tiraPiso();

        int tamanhoBloco = 0;
        int xBloco = 0;
        int yBloco = 363;
        int controle = 0;

        while(controle < 700){

            if(controle == 350 || controle == 398){
                controle = controle;
            }

            try{

                if(mapaDoPiso.get(pontoInicial)){
                    tamanhoBloco++;

                }

            }catch(NullPointerException e){

                if(tamanhoBloco == 0 ){
                    xBloco +=4;
                }else{

                    Pisoteria p = new Pisoteria(tamanhoBloco,getFileta());
                    if(xBloco == 0){ // compensa o problema de renderização de imagens
                        xBloco = xBloco + p.getImage().getWidth()/2;
                    }
                    mundo.addObject(p, xBloco, yBloco);
                    xBloco = p.limiteDireitoDoObjeto();
                    tamanhoBloco = 0;
                }

            }

            pontoInicial++;
            controle++;
        }

        if(tamanhoBloco != 0 ){

            Pisoteria p = new Pisoteria(tamanhoBloco,getFileta());
            //xBloco = xBloco + p.getImage().getWidth()/2;
            mundo.addObject(p, xBloco, yBloco);
            xBloco = p.limiteDireitoDoObjeto();
            tamanhoBloco = 0;

        }

    }

    /**
     * Atualizo a posição dos objetos do jogo sempre que a cena for atualizada, se o herói foi pra direita a posição do objeto diminui, se para esquerda avança
     */
    protected void atualizaObjetosPeloCenario(){

        ArrayList<Objeto> objetosDoCenario = mundo.getObjetosDoCenario();

        if(mundo.oHeroi().estaIndoPraDireta() && mundo.possoAtualizarCenario()){
            for(Objeto objeto : objetosDoCenario){
                int limiteEsquedo = objeto.getX() - objeto.getImage().getWidth()/2;
                if(podeMoverObjetos()){
                    if(limiteEsquedo > 1){ // impede que ambos os métodos de movimentação de objetos sejam execitados juntos

                        objeto.move(mundo.TAMANHO_DO_QUADRO * -1);

                    }else{
                        tireOsPisosPelaEsquerda();
                    }
                }

            }
        }
        if(mundo.oHeroi().estaIndoPraEsquerda() && mundo.possoAtualizarCenario()){
            for(Objeto objeto : objetosDoCenario){
                if(podeMoverObjetos()){
                    int limiteDireito = objeto.getX() + objeto.getImage().getWidth()/2;
                    if(limiteDireito < 700){ // impede que ambos os métodos de movimentação de objetos sejam execitados juntos
                        objeto.move(mundo.TAMANHO_DO_QUADRO );}
                    else{
                        tireOsPisosPelaDireita(); 
                    }

                }

            }
        }

    }

    public boolean podeMoverObjetos(){
        return (mundo.getCiclo() % TAXA_INTERVALO_DE_ATUALIZACAO) == 0;
    }

}
