import java.awt.Color;

public class Musuh {
    int positionX;
    int positionY;
    int size;
    int gerakX;
    int gerakY;
    boolean hidup;
    Color warna;
    public Musuh(int positionX, int positionY, int size, int gerakX, int gerakY, boolean hidup, Color warna) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.size = size;
        this.gerakX = gerakX;
        this.gerakY = gerakY;
        this.hidup = hidup;
        this.warna = warna;
    }


}
