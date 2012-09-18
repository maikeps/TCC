package javaPlayExtras;

import java.awt.Dimension;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay2.Imagem;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class Splash extends JWindow {

    Image imagemCarregando; //a imagem a carregar
    String src; //o caminho da imagem a carregar
    AbsoluteLayout absoluto; //nao sei :)
    AbsoluteConstraints absImage, absBarra, absString; //nao sei :)
    ImageIcon image; //imagem de fundo
    JLabel jLabel; //lugar pra colocar a imagem
    JLabel string; //mensagem que aparece ao carregar
    JProgressBar barra; //barra de progresso
    int max; //maximo da barra

    public Splash(Image img, int max, String src) {

        this.src = src;
        this.imagemCarregando = img;
        this.max = max;
        //this.max = 50;

////////////        //max da barra relativo ao tamanho da imagem a ser carregada
////////////        //arrumar, ta indo tudo zero '-'
////////////        //this.max = img.getHeight(null) *img.getWidth(null) /10;
////////////        System.out.println("----");
////////////        System.out.println(this.max);
////////////        System.out.println("Largura: " + img.getWidth(null) + " | Altura: "+ img.getHeight(null));
////////////        
        //cria os objetos necessarios
        absoluto = new AbsoluteLayout();
        absImage = new AbsoluteConstraints(0, 0);
        absBarra = new AbsoluteConstraints(0, 300);
        absString = new AbsoluteConstraints(50, 325);
        jLabel = new JLabel();
        image = new ImageIcon("resources/Title.png");
        jLabel.setIcon(image);
        barra = new JProgressBar();
        barra.setMaximum(this.max);
        barra.setPreferredSize(new Dimension(600, 5));

        string = new JLabel("carregando a imagem " + src);

        //adiciona os itens à tela
        this.getContentPane().setLayout(absoluto);
        this.getContentPane().add(jLabel, absImage);
        this.getContentPane().add(string, absString);
        this.getContentPane().add(barra, absBarra);
        this.pack();
        this.setVisible(true);
        //mostra a splashScreen no centro
        setLocationRelativeTo(null);


        this.carrega();
        this.setVisible(false);

    }

    //arrumar, a imagem REALMENTE carrega, mas na hora de jogar, carrega de novo
    public void carrega() {
        //contador
        int i = 0;
        while (i < this.max) {

            barra.setValue(i);
            i++;
            //espera um certo tempo antes de continuar
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
            }

            //se a imagem ja for carregada apos encher a barra, termina o carregamento
            if (!(this.imagemCarregando.getWidth(null) == -1)) {
                //estranho, por que sempre termina de carrega logo no primeiro loop
                i = this.max;
            }
        }

        //se a imagem ainda nao estiver carregada, tem algo errado
        //entao mostra uma msg de erro e passa para aprox img
        //nao funciona a segunda parte '-'
        if ((this.imagemCarregando.getWidth(null) == -1)) {
            
//////            i = this.max;
//////        } else { //senao, inicia o carregamento de novo
            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem "+this.src+", a imagem sera carregada novamente(nao vai nao, arrumar esse erro)");
            return;
            //this.carrega();
        }
    }

    //teste
    public Splash() {
        absoluto = new AbsoluteLayout();
        absImage = new AbsoluteConstraints(0, 0);
        absBarra = new AbsoluteConstraints(0, 300);
        jLabel = new JLabel();
        image = new ImageIcon("resources/Title.png");
        jLabel.setIcon(image);
        barra = new JProgressBar();
        barra.setMaximum(this.max);
        barra.setPreferredSize(new Dimension(600, 5));
        this.getContentPane().setLayout(absoluto);
        this.getContentPane().add(jLabel, absImage);
        this.getContentPane().add(barra, absBarra);
        this.pack();
        this.setVisible(true);
        setLocationRelativeTo(null);
    }
}
