package javaPlayExtras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javaPlay2.Scene;
import javaPlay2.TileInfo;
import tcc.ObjetoComMovimento;




public class CenarioComColisao {

    private Scene scene;

    private ArrayList<ObjetoComMovimento> objetosMovimento;

    public CenarioComColisao(String sceneFile) throws FileNotFoundException {
        this.scene = new Scene();

        try {
            this.scene.loadFromFile(sceneFile);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        this.objetosMovimento = new ArrayList<ObjetoComMovimento>();
    }

    public void adicionaObjeto(ObjetoComMovimento obj) {
        this.objetosMovimento.add(obj);
    }

    public void step(long timeElapsed) {
       
        for (ObjetoComMovimento obj : this.objetosMovimento) {
            if(obj.getAltura() !=0){
            System.out.println("fgg");
            }
            this.verificaColisao(obj);
        }
    }

    public void draw(Graphics g, int offsetx , int offsety) {
        this.scene.draw(g,offsetx,offsety);     
        //this.drawDebug(g);
    }

    public boolean temColisaoComTile(ObjetoComMovimento obj, int idTile){
        ArrayList<TileInfo> tiles = this.scene.getTilesFromRect(obj.getPontoMin(), obj.getPontoMax());
        for (TileInfo tile : tiles) {
            if ((tile.id == idTile) && obj.temColisao(tile.getRetangle())) {
                return true;
            }
        }
        return false;
    }

    private void drawDebug(Graphics g){
        for (ObjetoComMovimento obj : this.objetosMovimento) {
            Point objMin = new Point(obj.getX(), obj.getY());
            Point objMax = new Point(obj.getX() + obj.getLargura() - 1, obj.getY() + obj.getAltura() - 1);

            ArrayList<TileInfo> tiles = this.scene.getTilesFromRect(objMin, objMax);


            for (TileInfo tile : tiles) {
                if ((tile.id > 0) && obj.temColisao(tile.getRetangle())) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }
                g.drawRect(tile.min.x, tile.min.y, 32, 32);
            }
        }
    }

    private void verificaColisao(ObjetoComMovimento obj) {
        //Considera que não existe bloco abaixo

    if(this.scene == null){
        System.exit(1);
    }
        ArrayList<TileInfo> tiles = this.scene.getTilesFromRect(obj.getPontoMin(), obj.getPontoMax());
        DirecaoColisao direcao = null;

        for(TileInfo tile : tiles){
            if ( (tile.id > 0) && obj.temColisao(tile.getRetangle())) {            
                direcao = this.pegaDirecaoColisao(obj, tile);
                this.realizaColisao(obj, tile, direcao);                
            }
        }                
    }

    private DirecaoColisao pegaDirecaoColisao(ObjetoComMovimento obj, TileInfo tile) {
        /**
         * Para calcular  de ond evem a direção
         * verifica-se quais o ponto do Tile mais próximo do ponto central do objeto.
         */

        //Considera inicialmente que a colisão foi de cima para baixo
        DirecaoColisao direcao = DirecaoColisao.CIMA_PARA_BAIXO;
        double distancia = this.calculaDistancia(tile.getTopPoint(), obj.getPontoCentral());
        double distanciaTemp;

        distanciaTemp = this.calculaDistancia(tile.getBottomPoint(), obj.getPontoCentral());
        if(distanciaTemp < distancia ){
            direcao = DirecaoColisao.BAIXO_PARA_CIMA;
            distancia = distanciaTemp;
        }

        distanciaTemp = this.calculaDistancia(tile.getLeftPoint(), obj.getPontoCentral());
        if(distanciaTemp < distancia ){
            direcao = DirecaoColisao.ESQUERDA_PARA_DIREITA;
            distancia = distanciaTemp;
        }

        distanciaTemp = this.calculaDistancia( tile.getRightPoint(), obj.getPontoCentral() );
        if(distanciaTemp < distancia ){
            direcao = DirecaoColisao.DIREITA_PARA_ESQUERDA;
            distancia = distanciaTemp;
        }

        //Caso especial quando o objeto
        //por causa da força da gravidade
        //atravessa todo o tile.
        if (obj.getY() < tile.getCentralY() && obj.getMaxY() > tile.max.y && obj.getPontoCentral().x > tile.min.x && obj.getPontoCentral().x < tile.max.x ) {
            direcao = DirecaoColisao.CIMA_PARA_BAIXO;
        }

        return direcao;
    }

    private double calculaDistancia(Point ponto1, Point ponto2){
        int dx = ponto1.x - ponto2.x;
        int dy = ponto1.y - ponto2.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    private void realizaColisao(ObjetoComMovimento obj, TileInfo tile, DirecaoColisao direcao) {
        switch (direcao) {
            case CIMA_PARA_BAIXO:
                obj.setY(tile.min.y - obj.getAltura());
                break;
            case BAIXO_PARA_CIMA:
                obj.setY(tile.max.y);
               
                break;
            case ESQUERDA_PARA_DIREITA:                
                obj.setX(tile.min.x - obj.getLargura());
                break;
            case DIREITA_PARA_ESQUERDA:
                obj.setX(tile.max.x);
                break;
        }
    }
}
