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
    
    private int tamanhoCenario;
    private int filetaAtual = 1;
    private Mundo1 mundo;
    private Map<Integer,ArrayList<Elenco>> roteiro = new HashMap<Integer,ArrayList<Elenco>> ();
    //private Map<Integer,Boolean> mapaDoPiso = new HashMap<Integer,Boolean> ();
    private Map<Integer,Elenco> mapaDoPiso = new HashMap<Integer,Elenco> ();
    private Pisoteria ultimoPiso;
    private int ultimoQuadro;

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
        for(Elenco scriptDoAtor : Elenco){

            try{
                Class clazz = Class.forName(scriptDoAtor.nomeDaClasse());
                Actor novoAtor  = (Actor) clazz.newInstance();
                mundo.addObject(novoAtor, scriptDoAtor.getX(), scriptDoAtor.getY());

            }catch(Exception e){

                System.out.println("Erro Desconhecido no roteiro ao tentar adicionar um novo ator no mundo." );
                e.printStackTrace();

            }

        }
    }

    protected void atualizaPiso(int quadro){

        if(mapaDoPiso.get(quadro) != null ){

            if(ultimoPiso == null){
                //varrer o array pra encontrar o tamanho total do piso. AQUIIII
                ultimoPiso = new Pisoteria(mapaDoPiso.get(quadro).getLarguraTotal(),quadro);
                mundo.addObject(ultimoPiso, 698, 363); 
                ultimoQuadro = quadro;
            }
            
        }

    }
    
    public void addPiso(int pontoInicial,int pontoFinal){
        int tamanho = pontoFinal - pontoInicial;
        while(pontoInicial < pontoFinal){
            mapaDoPiso.put(pontoInicial, new Elenco(tamanho));
            //mapaDoPiso.put(pontoInicial, true);
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
     * Solicita ao Pisoteria que coloque os pisos em cena pelo lado direito.
     */
    protected void coloqueOsPisosPelaDireita(){

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

                if(mapaDoPiso.get(pontoInicial) != null){
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
                
                objeto.moveSeParaEsquerda();
            }
        }
        if(mundo.oHeroi().estaIndoPraEsquerda() && mundo.possoAtualizarCenario()){
            for(Objeto objeto : objetosDoCenario){
                objeto.moveSeParaDireita();

            }
        }

    }

    
}
