import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Write a description of class Roteiro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Roteiro extends Actor
{
    private int tamanhoCenario;
    private World mundo;
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

    public Roteiro(World mundoAtual){
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
    
    public void tiraPiso(){
    
        mundo.removeObjects(mundo.getObjects(Pisoteria.class));
        
        
    }
    

    public void criaPiso(int pontoInicial){
         tiraPiso();
        int tamanhoBloco = 0;
        int xBloco = 0;
        int yBloco = 363;
        int controle = 0;
        while(controle < 700){
           
            try{
                
                if(mapaDoPiso.get(pontoInicial)){
                    tamanhoBloco++;
                }
            
            }catch(NullPointerException e){
            
                if(tamanhoBloco == 0 ){
                    xBloco +=4;
                }else{
                    Pisoteria p = new Pisoteria(tamanhoBloco,1);
                    xBloco = xBloco + p.getImage().getWidth()/2;
                    mundo.addObject(p, xBloco, yBloco);
                    tamanhoBloco = 0;
                }
                       
            }
                      
            
            pontoInicial++;
            controle++;
        }
        
        if(tamanhoBloco != 0 ){
            
            Pisoteria p = new Pisoteria(tamanhoBloco,1);
            xBloco = xBloco + p.getImage().getWidth()/2;
            mundo.addObject(p, xBloco, yBloco);
            tamanhoBloco = 0;
            
            }

    }

}
