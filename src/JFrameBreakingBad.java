
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
    private int iRenBloques;            // Cantidad de renglones de bloques
    private Personaje perBarra;         // Objeto de la clase personaje (Barra)
    private Personaje perBloque;        // Objeto de la clase personaje (Bloque)
    private int iDireccionBarra;        // Dirección de la barra
    private int iBloques;               // Numero de bloques
    
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
        setSize(500,750);
        
        // se crea la lista encadenada de bloques
        lnkBloques = new LinkedList();
        
        // inicializa numero de bloques
        iNumBloques = 5;
        
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
        iDireccionBarra = 1;
        // ciclo para crear de 5 a 10 caminadores
        for (int iI = 1; iI <= iNumBloques; iI++) {      
            URL urlImagenCaminador = 
                    this.getClass().getResource("bloque.png");
            // se crea Bloque
            perBloque = new Personaje(0,0,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
            // se posiciona a caminador afuera del applet del lado superior
            perBloque.setX((iI*80));
            perBloque.setY(getHeight()/2 );
            lnkBloques.add(perBloque);  // agrega caminador a coleccion

        }        
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
            
            // dibuja la lista de bloques
            for (Object lnkBloque : lnkBloques) {
                Personaje perBloque = (Personaje)lnkBloque;
                //Dibuja la imagen de Susy en la posicion actualizada
                g.drawImage(perBloque.getImagen(), perBloque.getX(),
                        perBloque.getY(), this);
            }            
        } else {
            //Da un mensaje mientras se carga el dibujo	
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**
     * keyPressed
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo se maneja el evento que se genera al presionar una tecla.
     * @param keyEvent es el <code>evento</code> que se genera al presionar una
     * tecla.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_LEFT) {
            perBarra.setX(perBarra.getX() - 10);
        }
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            perBarra.setX(perBarra.getX() + 10);
        }        
        
    }
    
    /**
     * keyReleased
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo se maneja el evento que se genera al soltar una tecla.
     * @param keyEvent es el <code>evento</code> que se genera al soltar 
     * una tecla.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_LEFT) {
            perBarra.setX(perBarra.getX());
        }
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            perBarra.setX(perBarra.getX());
        }        
        
    }

}
