package tcc;

/* 
esse codigo não é meu, peguei do site: http://www.java-gaming.org/topics/perlin-s-noise-in-2d/26394/view.html
a unica coisa que vou fazer é adicionar comentarios para tentar entender melhor
mto complexo pra mim '-'
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ValueNoise2D {

    private float[][] values;
    private float roughness;
    private float varyation;
    private Random rand;

    public static void main(String[] args) {
        System.out.println("Started.");
        int size = 128; // tamanho da imagem (1024x1024)
        ValueNoise2D pn2d = new ValueNoise2D(size, 0.2f, 5, 20000f, new Random());
        float[][] vals = pn2d.get();//retorna os valores do noise
        BufferedImage img = new BufferedImage(size + 1, size + 1, BufferedImage.TYPE_INT_ARGB);

        String sArray[] = new String[size + 1];
        
        for (int x = 0; x < vals.length; x++) {
            System.out.println("x: " + x);
            String s = "";
                sArray[x] = "";
            for (int y = 0; y < vals[x].length; y++) {
                String hexStr = "0x" + (Integer.toHexString(Color.GREEN.getRGB()));


                img.setRGB(x, y, ((int) vals[x][y]) | 0xFF721138);//comeca a desenhar a img

                if (img.getRGB(x, y) > 0xFF725f53) {
                    sArray[x] += "2,";
                } else if (img.getRGB(x, y) < 0xFF725f3d){
                    sArray[x] += "3,";
                } else {
                    sArray[x] += "1,";
                }

                /*
                 * na hora de colocar os tiles:
                 * 
                if(pixelValue > 0x888888) 
                place mountain
                if(pixelValue < 0x888888)
                place grass
                 * 
                 * pixelValue seria a cor do pixel
                 * a cada passagem do loop for, muda de pixel
                 * entao seria so usar esse mesmo loop
                 * http://www.java-gaming.org/topics/2d-tile-generation/26175/view.html
                 */
            }
        }
        try {
            //   try {
            limpaTxt(new File("texto.txt"));
        } catch (IOException ex) {
            Logger.getLogger(ValueNoise2D.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] a = new String[4];
        a[0] = "3";
        a[1] = "resources/tiles/tiles avulsos/grass.png";
        a[2] = "resources/tiles/tiles avulsos/water.png";
        a[3] = "resources/tiles/tiles avulsos/jungle_grass.png";
        saveTxtTeste(new File("texto.txt"), a, true);
        
        saveTxtTeste(new File("texto.txt"), sArray, true);
        //   } catch (IOException ex) {
        //       Logger.getLogger(ValueNoise2D.class.getName()).log(Level.SEVERE, null, ex);
        //  }

        try {
            saveImg(new File("Heightmap.png"), img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished.");
    }

    public ValueNoise2D(int size, float roughness, float varyation, float seed, Random rand) {
        this.roughness = roughness; //frequencia?
        this.varyation = varyation; //amplitude?
        this.rand = rand;

        int realsize = 1;
        while (realsize < size) {
            realsize *= 2;
        }
        realsize += 1;
        values = new float[realsize][realsize]; //cria um float com o tamanho 1025x1025

        int firststep = realsize / 2;
        values[0][0] = seed;
        values[realsize - 1][0] = seed;
        values[0][realsize - 1] = seed;
        values[realsize - 1][realsize - 1] = seed;
        values[0][firststep] = seed;
        values[firststep][0] = seed;
        values[firststep][firststep] = seed;
        values[firststep][realsize - 1] = seed;
        values[realsize - 1][firststep] = seed;

        noise(0, 0, realsize - 1);
    }

    private void noise(int x, int y, int step) {
        if (step > 1) {
            int halfStep = step / 2;
            float vary = (step * varyation) * roughness;

            values[x + halfStep][y] = values[x + halfStep][y] == 0f
                    ? vary(mid(values[x][y], values[x + step][y]), vary) : values[x + halfStep][y];

            values[x][y + halfStep] = values[x][y + halfStep] == 0f
                    ? vary(mid(values[x][y], values[x][y + step]), vary) : values[x][y + halfStep];

            values[x + step][y + halfStep] = values[x + step][y + halfStep] == 0f
                    ? vary(mid(values[x + step][y], values[x + step][y + step]), vary) : values[x + step][y + halfStep];

            values[x + halfStep][y + step] = values[x + halfStep][y + step] == 0f
                    ? vary(mid(values[x][y + step], values[x + step][y + step]), vary) : values[x + halfStep][y + step];

            values[x + halfStep][y + halfStep] = vary(mid(
                    values[x][y],
                    values[x + halfStep][y],
                    values[x + step][y],
                    values[x][y + halfStep],
                    values[x + step][y + halfStep],
                    values[x][y + step],
                    values[x + halfStep][y + step],
                    values[x + step][y + step]), vary);

            noise(x, y, halfStep);
            noise(x + halfStep, y, halfStep);
            noise(x, y + halfStep, halfStep);
            noise(x + halfStep, y + halfStep, halfStep);
        }
    }

    public float randRange(float range, Random rand) {
        return rand.nextBoolean() ? -rand.nextFloat() * range : rand.nextFloat() * range;
    }

    public float vary(float val, float vary) {
        return val + randRange(vary, rand);
    }

    public float mid(float... vals) {
        float sum = 0;
        for (int i = 0; i < vals.length; i++) {
            sum += vals[i];
        }
        return sum / vals.length;
    }

    public float[][] get() {
        return values;
    }

    public static void saveImg(File f, BufferedImage img) throws IOException {
        f.createNewFile();
        ImageIO.write(img, "PNG", f);
    }

//////    public static void saveTxt(File f, String text) throws IOException {
//////        Writer output = null;
//////        f.createNewFile();
//////        output = new BufferedWriter(new FileWriter(f));
//////        output.write(text);
//////        output.close();
//////    }
    public static void saveTxtTeste(File f, String[] linhas, boolean appendToFile) {

        PrintWriter pw = null;

        try {
            if (appendToFile) {
                //If the file already exists, start writing at the end of it.
                pw = new PrintWriter(new FileWriter(f, true));
            } else {
                pw = new PrintWriter(new FileWriter(f));
                //this is equal to:
                //pw = new PrintWriter(new FileWriter(filename, false));
            }
            for (int i = 0; i < linhas.length; i++) {
                pw.println(linhas[i]);
            }
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close the PrintWriter
            if (pw != null) {
                pw.close();
            }
        }
    }

    public static void limpaTxt(File f) throws IOException {
        Writer output = null;
        f.createNewFile();
        output = new BufferedWriter(new FileWriter(f));
        output.write("");
        output.close();
    }
}