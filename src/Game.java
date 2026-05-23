
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game extends JPanel implements KeyListener, Runnable{
    Random rand = new Random();

    Musuh[] musuh = new Musuh[25];

    boolean playerHidup = true;
    int xPlayer = 960;
    int yPlayer = 540;
    int playerSize = 50;
    int gerakPlayerX = 5;
    int gerakPlayerY = 5;

    public Game(){
        for(int i = 0 ; i < 25 ; i++){
            int gerakY = 5;
            int gerakX = 5;
            int positionX = rand.nextInt(1920);
            int positionY = rand.nextInt(1080);
            int size = rand.nextInt(41) + 10;
            int randomMoveX = rand.nextInt(2);
            int randomMoveY = rand.nextInt(2);
            Color warna= new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            if(randomMoveX == 0){
                gerakX = -gerakX;
            }
            if(randomMoveY == 0){
                gerakY = -gerakY;
            }
            musuh[i] = new Musuh(positionX, positionY, size, gerakX, gerakY, true, warna);
        }
        setFocusable(true);
        addKeyListener(this);

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.RED);
        if(playerHidup){
            g.fillOval(xPlayer, yPlayer, playerSize, playerSize);
        }else{
            System.exit(0);
        }
        for(int i = 0 ; i < 25 ; i++){
            if(musuh[i].hidup){
                g.setColor(musuh[i].warna);
                g.fillOval(musuh[i].positionX, musuh[i].positionY, musuh[i].size, musuh[i].size);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(playerSize > 60){
            gerakPlayerX = 4;
            gerakPlayerY = 4;
        }
        if(playerSize > 70){
            gerakPlayerX = 3;
            gerakPlayerY = 3;
        }
        if(playerSize > 80){
            gerakPlayerX = 2;
            gerakPlayerY = 2;
        }

        if(key == KeyEvent.VK_W){
            //Naik
            yPlayer-=gerakPlayerY;
            if(yPlayer < 0){
                yPlayer = 0;
            }
        }else if(key == KeyEvent.VK_S){
            //Turun
            yPlayer+=gerakPlayerY;
            if(yPlayer > 1090-playerSize){
                yPlayer = 1090-playerSize;
            }
        }else if(key == KeyEvent.VK_A){
            //Kiri
            xPlayer-=gerakPlayerX;
            if(xPlayer < 0){
                xPlayer = 0;
            }
        }else if(key == KeyEvent.VK_D){
            //Kanan
            xPlayer+=gerakPlayerX;
            if(xPlayer+playerSize > 1920-playerSize){
                xPlayer = 1920-playerSize;
            }
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void run() {
        
        while(true){
            // ini adalah apa yang di ulang oleh program
            for(int i = 0 ; i < 25 ; i++){
                if(musuh[i].hidup){
                    if(musuh[i].size > 60){
                        musuh[i].gerakX = 4;
                        musuh[i].gerakY = 4;
                    }
                    if(musuh[i].size > 70){
                        musuh[i].gerakX = 3;
                        musuh[i].gerakY = 3;
                    }
                    if(musuh[i].size > 80){
                        musuh[i].gerakX = 2;
                        musuh[i].gerakY = 2;
                    }
                }
            }
            for(int i = 0 ; i < 25 ; i++){
                if(musuh[i].hidup){
                    musuh[i].positionX+=musuh[i].gerakX;
                    musuh[i].positionY+=musuh[i].gerakY;
                }
            }
    
            repaint();

            for(int  i = 0 ; i < 25 ; i++){
                if(musuh[i].positionX < 0 || musuh[i].positionX > 1920-musuh[i].size) musuh[i].gerakX = -musuh[i].gerakX;
                if(musuh[i].positionY < 0 || musuh[i].positionY > 1080-musuh[i].size) musuh[i].gerakY = -musuh[i].gerakY;
            }

            //hitbox (kalau kena langsung hilang, ga harus kena titik tengah)
            Rectangle player = new Rectangle(xPlayer , yPlayer, playerSize, playerSize);
            Rectangle[] enemy = new Rectangle[25];
            for(int  i = 0 ; i < 25 ; i++){
                enemy[i] = new Rectangle(musuh[i].positionX , musuh[i].positionY, musuh[i].size, musuh[i].size);
            }

            for(int  i = 0 ; i < 25 ; i++){
                if(player.intersects(enemy[i]) && musuh[i].hidup){
                    if(musuh[i].size <= playerSize){
                        musuh[i].hidup = false;
                        int tambah = musuh[i].size/10;
                        playerSize+=tambah;
                    }
                    if(playerSize < musuh[i].size){
                        playerHidup = false;
                        int tambah = playerSize/10;
                        musuh[i].size+=tambah;
                    }
                }
                for(int j = 0 ; j < 25 ; j++){
                    if(musuh[i].hidup && musuh[j].hidup && enemy[i].intersects(enemy[j])){
                        if(musuh[i].size > musuh[j].size){
                            musuh[j].hidup = false;
                            int tambahEnemy = musuh[j].size/10;
                            musuh[i].size += tambahEnemy;
                        }
                        if(musuh[j].size > musuh[i].size){
                            musuh[i].hidup = false;
                            int tambahEnemy = musuh[i].size/10;
                            musuh[j].size += tambahEnemy;
                        }
                    }
                }
            }
    
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    
    
}
