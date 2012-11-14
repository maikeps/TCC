/*
 * Scene
 */
package tcc;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author VisionLab/PUC-Rio
 */
public class Scene {

    private Image backDrop;
    private Image[] tiles;
    private ArrayList tileLayer;
    private ArrayList overlays;
    private int drawStartX = 0;
    private int drawStartY = 0;
    private final int MAX_SLEEP_COUNT = 30;

    public void loadFromFile(String sceneFile) throws InterruptedException, FileNotFoundException, IOException, NullPointerException, Exception {
        tileLayer = new ArrayList();
        overlays = new ArrayList();

        BufferedReader input = new BufferedReader(new FileReader(new File(sceneFile)));

        //first read the number of tile images
        String line = input.readLine();

        int numOfTileImages = Integer.parseInt(line, 10);

        tiles = new Image[numOfTileImages];

        int count;

        for (int i = 0; i < numOfTileImages; i++) {
            //read each tile image name
            line = input.readLine();

            try {
                tiles[i] = new Image(line);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recurso não encontrado em Scene: " + ex.getMessage());
                System.exit(1);
            }
            //  tiles[i] = Toolkit.getDefaultToolkit().getImage(line);

            count = 0;
            while (tiles[i].getWidth() == -1) {
                Thread.sleep(1);

                count++;

                if (count == MAX_SLEEP_COUNT) {
                    throw new Exception("image could not be loaded: " + line);
                }
            }
        }

        //now read the tile set map until the final
        //character is found "%"
        String endTileSet = "%";

        line = input.readLine();

        while (line.equals(endTileSet) != true) {
            ArrayList tileLine = new ArrayList();

            String[] tileIndices = line.split(",");

            for (int i = 0; i < tileIndices.length; i++) {
                tileLine.add(Integer.parseInt(tileIndices[i]));
            }

            tileLayer.add(tileLine);

            line = input.readLine();
        }

        //now read the backdrop file
        line = input.readLine();
        /*
        //        backDrop = new Image(line);
        //    backDrop = new Image("resources/Heightmap.png");
        backDrop = Toolkit.getDefaultToolkit().getImage(line);
        System.out.println(backDrop.getHeight(null)+"Scene");
        count = 0;
        while (backDrop.getWidth(null) == -1) {
        Thread.sleep(1);
        
        count++;
        
        if (count == MAX_SLEEP_COUNT) {
        throw new Exception("image could not be loaded");
        }
        }
         * 
         * */
    }

    public void addOverlay(GameObject overlay) {
        overlays.add(overlay);
    }

    public void removeOverlay(GameObject overlay) {
        overlays.remove(overlay);
    }

    public void setDrawStartPos(int drawStartX, int drawStartY) {
        this.drawStartX = drawStartX;
        this.drawStartY = drawStartY;
    }

    public void draw(GameContainer gc, StateBasedGame game, Graphics g, int offsetx, int offsety, int xPlayer, int yPlayer) {
        //first clear the scene
        g.setColor(Color.black);
        
        //first draw the backdrop
        int startDrawX = drawStartX;
        int startDrawY = drawStartY;
        g.translate(offsetx, offsety);
        
        //now draw the tile set
        int tileWidth = tiles[0].getWidth();
        int tileHeight = tiles[0].getHeight();

        int line = 0;
        int drawY = startDrawY;

        do {
            ArrayList tileLine = (ArrayList) tileLayer.get(line);

            int drawX = startDrawX;

            for (int c = 0; c < tileLine.size(); c++, drawX += tileWidth) {
                int idx = (Integer) tileLine.get(c);

                if (idx == 0) {
                    continue;
                }

                if (Math.abs(drawX - xPlayer) <= gc.getWidth() / 2 + 25 && Math.abs(drawY - yPlayer) <= gc.getHeight() / 2 + 25) {
                    g.drawImage(tiles[idx - 1], drawX, drawY);
                }
            }

            drawY += tileHeight;
            line++;

        } while (line < tileLayer.size());

        //finally draw the overlays
        for (int i = 0; i < overlays.size(); i++) {
            GameObject element = (GameObject) overlays.get(i);

            element.render(gc, game, g);
        }
    }

    public ArrayList<TileInfo> getTilesFromRect(Point min, Point max) {
        ArrayList<TileInfo> v = new ArrayList<TileInfo>();


        int startDrawX = drawStartX;
        int startDrawY = drawStartY;

        int tileWidth = tiles[0].getWidth();
        int tileHeight = tiles[0].getHeight();

        int line = 0;
        int drawY = startDrawY;

        do {
            ArrayList tileLine = (ArrayList) tileLayer.get(line);

            int drawX = startDrawX;

            for (int c = 0; c < tileLine.size(); c++, drawX += tileWidth) {
                TileInfo tile = new TileInfo();

                tile.id = (Integer) tileLine.get(c);
                tile.min.x = drawX;
                tile.min.y = drawY;
                tile.max.x = drawX + tileWidth - 1;
                tile.max.y = drawY + tileHeight - 1;

                if ((min.x > tile.max.x) || (max.x < tile.min.x)) {
                    continue;
                }
                if ((min.y > tile.max.y) || (max.y < tile.min.y)) {
                    continue;
                }

                v.add(tile);
            }

            drawY += tileHeight;
            line++;

        } while (line < tileLayer.size());

        return v;
    }

    public void step(GameContainer gc, StateBasedGame game, long timeElapsed) {
        for (int i = 0; i < overlays.size(); i++) {
            GameObject element = (GameObject) overlays.get(i);

            element.update(gc, game, (int) timeElapsed);
            //element.step(timeElapsed);
        }
    }

    public int getWidth() {
        return this.tileLayer.size() * this.tiles[0].getWidth();
    }

    public int getHeight() {
        //arrumar, pois esta igual ao de cima
        //funciona enquanto o mapa for quadrado
        return this.tileLayer.size() * this.tiles[0].getHeight();
    }
}
