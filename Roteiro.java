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

    //private int tamanhoCenario;
    //private int filetaAtual = 1;
    private Mundo1 mundo;
    private Map<Integer,ArrayList<Elenco>> roteiro = new HashMap<Integer,ArrayList<Elenco>> ();
    private Map<Integer,Elenco> mapaDoPiso = new HashMap<Integer,Elenco> ();
   

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
    private boolean temNovoAtor(){

        try{
            return !roteiro.get(mundo.KMAtual()).isEmpty();
        }catch(NullPointerException e){

            return false;
        }

    }

    /**
     * Verifica e adiciona os atores que devem entrar em cena
     */
    public void coloqueOsAtoresEmAcao()  {
        
        if(temNovoAtor()){
            ArrayList<Elenco> Elenco = roteiro.get(mundo.KMAtual());
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
    }

    protected void coloqueOsPisosEmAcao(){
        if (mundo.KMAtual() > 400){
               int tt =1; 
               
           }
        
        
        if(mapaDoPiso.get(mundo.KMAtual()) != null ){

           
            Pisoteria piso = new Pisoteria(mapaDoPiso.get(mundo.KMAtual()).getLarguraTotal(),mundo.KMAtual());
            mundo.addObject(piso, 698, 363); 
         

        }

    }

    public void addPiso(int pontoInicial,int pontoFinal){
        int tamanho = pontoFinal - pontoInicial;

        Elenco ator = new Elenco(698,363,"Pisoteria");
        ator.setLarguraTotal(tamanho);

        mapaDoPiso.put(pontoInicial, ator);

    }
    
    /*
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

    */

}
