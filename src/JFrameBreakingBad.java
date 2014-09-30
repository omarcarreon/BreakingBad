
/**
 * JFrameBreakingBad
 * 
 * @author Omar Antonio Carreón Medrano A01036074
 * @author Gabriel Salazar De Urquidi A01139126
 */
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class JFrameBreakingBad extends JFrame implements Runnable,
 KeyListener {
    private Image imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private LinkedList lnkBloques;      // Colección de Bloques
    private int iNumBloques;            // Cantidad de bloques
    private Personaje perBarra;         // Objeto de la clase personaje (Barra)
    private int iDireccionBarra;        // Dirección de la barra
    
    /**
     * JFrameBreakingBad
     * 
     * Construye un nuevo JFrame Breaking Bad
     */
    public JFrameBreakingBad() {
        init();
        start();
    }
    
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>JFrameBreakingBad</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>JFrameBreakingBad</code> y se definen 
     * funcionalidades.
     */
    public void init() {
        setSize(500,500);
        
        // se crea la lista encadenada de bloques
        lnkBloques = new LinkedList();
        
        // inicializa numero de bloques
        iNumBloques = 1;
        
        // se crea imagen de la barra
        URL urlImagenNena = this.getClass().getResource("barra.png");
        // se crea a Nena 
	perBarra = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenNena));
        // inicializa posicion de Nena
        perBarra.setX((getWidth() / 2) - (perBarra.getAncho() / 2));
        perBarra.setY((getHeight() - perBarra.getAlto()));
        
        /* inicializa dirección de la barra
         * 1 = izquierda
         * 2 = derecha
        */
        iDireccionBarra = 2;
 
        // se añade para que el teclado sea escuchado en el JFrame
        addKeyListener(this);
    }
    
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>JFrameBreakingBad</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>JFrameBreakingBad</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
    
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run() {
        while (true) {
            actualiza();
            checaColision();
            repaint();
            try {
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
        }
    }
    
    /**
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto Nena, Caminador y Corredor
     * 
     */
    public void actualiza() {
        // actualiza dirección de la barra
        if (iDireccionBarra == 1) {
            perBarra.izquierda();
        } else if (iDireccionBarra == 2) {
            perBarra.derecha();
        }
    }
    
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision de los objetos del
     * <code>JFrameBreakingBad</code>.
     * 
     */    
    public void checaColision() {
        // si llega al limite derecho
         if(perBarra.getX() + perBarra.getAncho() > getWidth()) {
            perBarra.setX(getWidth() - perBarra.getAncho());
            
            // si llega al limite izquierdo
        } else if(perBarra.getX() < 0) {
            perBarra.setX(0);
        }        
    }
        public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // crea de fondo la imagen del espacio
        URL urlImagenBackground = this.getClass().getResource("wallpaper.jpg");
        Image imaImagenBackground = 
                Toolkit.getDefaultToolkit().getImage(urlImagenBackground);

        // Despliego la imagen de fondo
        graGraficaApplet.drawImage(imaImagenBackground, 0, 0, 
                getWidth(), getHeight(), this);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint1(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }    
    
    public void paint1 (Graphics g) {
        if (perBarra != null) {
            g.drawImage(perBarra.getImagen(), perBarra.getX(),
                    perBarra.getY(), this);
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

            
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            iDireccionBarra = 2;
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            iDireccionBarra = 1;
        }
    }

}
