
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
    private int iNumBloques;            // Número de bloques
    private Personaje perBarra;         // Objeto de la clase personaje (Barra)
    private Personaje perBloque;        // Objeto de la clase personaje (Bloque)
    private int iPosXBloque;            // Posicion en X del bloque
    private int iPosYBloque;            // Posicion en Y del bloque
    private boolean bKeyPressed;       // Booleana para cuando se presiona tecla
    private boolean bKeyReleased;       // Booleana para cuando se suelta tecla
    private int iDirBarra;              // Direccion de la barra
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
        // hago el JFrame de tamaño 500,750
        setSize(500,750);
        
        // inicializa booleana para cuando se presiona tecla
        bKeyPressed = false;
        // inicializa booleana para cuando se suelta tecla
        bKeyReleased = false;
 
        /* incializa direccion de la barra
         * 1 = Izquierda
         * 2 = Derecha
        */
        iDirBarra = 2;
        
        // se crea la lista encadenada de bloques
        lnkBloques = new LinkedList();
        
        // inicializa numero de bloques
        iNumBloques = 25;
        
        // inicializa posicion en X del bloque
        iPosXBloque = 50;
        
        // inicialeiza posicion en Y del bloque
        iPosYBloque = 50;
       
        // se crea imagen de la barra
        URL urlImagenNena = this.getClass().getResource("barra.png");
        // se crea la barra
	perBarra = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenNena));
        // inicializa posicion de la barra
        perBarra.setX((getWidth() / 2) - (perBarra.getAncho()/2));
        perBarra.setY((getHeight() - perBarra.getAlto()));
        
        // ciclo para crear los bloques
        for (int iI = 1; iI <= iNumBloques; iI++) { 
            URL urlImagenCaminador = 
                    this.getClass().getResource("bloque.png");
            // se crea Bloque
            perBloque = new Personaje(iPosXBloque,iPosYBloque,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
            iPosXBloque += perBloque.getAncho();
            
            lnkBloques.add(perBloque); // agrega el bloque a la lista encadenada
            if (iI % 5 == 0) {          // si ya se han creado 5 bloques
                iPosXBloque = 50;       // la posicion en X se reinicia 
                iPosYBloque += perBloque.getAlto(); // aumenta la posicion en Y 
                                                    // para crear nuevo renglon
            }            
            
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

        if (bKeyPressed && iDirBarra == 1) {
            perBarra.setX(perBarra.getX() - 10);
        }
        if (bKeyPressed && iDirBarra == 2) {
            perBarra.setX(perBarra.getX() + 10);
            
        }
        if (bKeyReleased && iDirBarra == 1) {
            perBarra.setX(perBarra.getX());
           
        }
        if (bKeyReleased && iDirBarra == 2) {
            perBarra.setX(perBarra.getX());
          
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

    /**
     * paint1
     * 
     * Metodo sobrescrito de la clase <code>JFrameBreakingBad</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     * 
     */        
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
            bKeyPressed = true;
            iDirBarra = 1;
            bKeyReleased = false;
            
        }
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            bKeyPressed = true;
            iDirBarra = 2;
            bKeyReleased = false;
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
            bKeyReleased = true;
            iDirBarra = 1;
            bKeyPressed = false;
        }
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            bKeyReleased = true;
            iDirBarra =2;
            bKeyPressed = false;
        }     
           
        
    }

}
