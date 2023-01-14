import java.awt.*;
import java.io.IOException;
import java.net.*;
import javax.imageio.*;

public class MyImage {

    private URL link;
    private Image picture;
    private String landmark;
    
    public MyImage(URL link, String landmark){
        this.link = link;
        this.landmark = landmark;
    }

    public void drawMe(Graphics g, int x, int y){
        try{
            this.picture = ImageIO.read(link);
            Image scaledPicture = picture.getScaledInstance(300, 250, Image.SCALE_DEFAULT);

            g.drawImage(scaledPicture, x, y, null);
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public String getLandmark(){
        return landmark;
    }

    public String getURL(){
        String temp = link.toString();
        return temp;
    }

    @Override
    public String toString(){
        return link.toString() + "," + landmark;
    }
}
