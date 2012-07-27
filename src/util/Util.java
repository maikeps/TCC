package util;


import java.util.Random;

public class Util {

    static public void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            System.out.println("Erro: " + ex);
        }
    }

    static public int random(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }
    
    static public double calculaAngulo(int xAlvo, int xPersonagem, int yAlvo, int yPersonagem) {

        /*
         * xAlvo e yAlvo é onde o player clica com o mouse
         * xPersonagem e yPersonagem é onde o player esta atualmente
         */
        
        //se calculo estiver errado trocar x2 e y2 por xPonto e yPonto
        int x2 = xAlvo;
        int y2 = yAlvo;

        int xPonto = xPersonagem;
        int yPonto = yPersonagem;
        double quadrante = 1;
        double anguloTotal = 0;


        double b = Math.abs(yPonto - y2);
        double c = Math.abs(xPonto - x2);
        //double b = 50;
        //double c = 26;
        double a = Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2));

        double sen = b / a;
        double cos = c / a;

        double tg = sen / cos;


        double angulo = Math.toDegrees(Math.atan(tg));

        //verificacao do quadrante
        if (x2 > xPonto && y2 < yPonto) {
            quadrante = 1.0;
            anguloTotal = angulo;
        }
        if (x2 < xPonto && y2 < yPonto) {
            quadrante = 2.0;
            anguloTotal = 180 - angulo;
        }
        if (x2 < xPonto && y2 > yPonto) {
            quadrante = 3.0;
            anguloTotal = 180 + angulo;
        }
        if (x2 >= xPonto && y2 >= yPonto) {
            quadrante = 4.0;
            anguloTotal = 360 - angulo;
        }


        int startX = 100;
        int startY = 500;
        double endX = startX + 50 * Math.cos(Math.toRadians(anguloTotal * (-1)));
        double endY = startY + 50 * Math.sin(Math.toRadians(anguloTotal * (-1)));

        return anguloTotal;
        
    }
    
    //static public void animacao(int miliseconds)
}
