package tcc;

import java.util.ArrayList;
import org.newdawn.slick.Image;

public class Animacao {

    protected ArrayList<Image> imagens;
    protected int interval = 0;
    protected int frame = 0;
    protected long initialTime;

    public Animacao(int _interval) {
        imagens = new ArrayList<Image>();
        interval = _interval;
        initialTime = System.currentTimeMillis();
    }

    public void update() {
        long now = System.currentTimeMillis();
        if ((now - initialTime) > interval) {
            swapFrame();
            initialTime = now;
        }
    }

    public void render(int x, int y, float scale, boolean centered) {
        if (centered) {
            this.getImage().draw(x-this.getImage().getWidth()/2, y-this.getImage().getHeight()/2, scale);
        } else {
            this.getImage().draw(x, y, scale);
        }
    }

    public void add(Image img) {
        imagens.add(img);
    }

    public void setImages(ArrayList<Image> imgs) {
        this.imagens = imgs;
    }

    public void clear() {
        this.imagens.clone();
    }

    private void swapFrame() {
        frame++;
        if (frame > (imagens.size() - 1)) {
            frame = 0;
        }
    }

    public Image getImage() {
        return imagens.get(frame);
    }
}
