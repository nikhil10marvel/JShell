package util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @see BufferedImage
 * @since 2.3.1
 * @author Nikhil
 */
public class SplashFrame{
    
    public HashMap<Integer, Splash> map = new HashMap<>();
    static LinkedList<Splash> list = new LinkedList<>();
    Dimension splash_dimension;
    
    public SplashFrame(int width, int height){
        splash_dimension = new Dimension(width, height);
    }
    
    public JFrame frame(){
        JFrame jframe = new JFrame();
        
        jframe.setSize(splash_dimension);
        jframe.setResizable(false);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setUndecorated(true);
        
        // Order map to list
        map.entrySet().stream().forEach((entry) -> {
            list.add(entry.getKey(), entry.getValue());
        });
        
        return jframe;
    }
    
    public void show(long delay){
        JFrame frame = frame();
        list.get(0).apply(frame);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        for(int x = 1; x < list.size(); x ++){
            Splash splash = list.get(x);
            splash.apply(frame);
        }
        
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(SplashFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        frame.setVisible(false);
        frame.dispose();
    }
    
    public static class Splash {
        
        public static Splash load(BufferedImage image, long delay){
            return new Splash(image, delay);
        }
        
        BufferedImage image;
        long delay;
        
        Splash(BufferedImage display, long delay){
            image = display;
            this.delay = delay;
        }
        
        protected void apply(JFrame frame){
            try {
                Thread.sleep(delay);
                frame.getContentPane().add(new JLabel(new ImageIcon(image)));
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static class BufferedImageLoader {
        
        public static BufferedImage load(InputStream is){
            BufferedImage image = null;
            try {
                image = ImageIO.read(is);
            } catch (IOException ex) {
                Logger.getLogger(SplashFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return image;
        }
        
        public static BufferedImage load(File file){
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(SplashFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return image;
        }
        
        public static BufferedImage load(byte[] imagedata){
            ByteArrayInputStream stream = new ByteArrayInputStream(imagedata, 0, imagedata.length);
            BufferedImage image = null;
            try {
                image = ImageIO.read(stream);
            } catch (IOException ex) {
                Logger.getLogger(SplashFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            return image;
        }
        
    }
    
}
