
/**
 * JFrameBreakingBad
 * 
 * @author Omar Antonio Carreón Medrano A01036074
 * @author Gabriel Salazar De Urquidi A01139126
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import javax.swing.JFrame;

public class JFrameBreakingBad extends JFrame implements Runnable,
 KeyListener {
    private Image imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private LinkedList lnkBloques;      // Colección de Bloques
    private int iNumBloques;            // Cantidad de bloques
    private int iVidas;                 // Cantidad de vidas
    private double dSlopeY;             //Double que mide como se mueve la bola
    private boolean bDisparada;        //Booleana que checa si se lanzo la bola
    private double dSlopeX;             //Double que mide como se mueve la bola
    private double dX;                  //Cuenta la posicion en X de la bola
    private double dY;                  //Cuenta la posicion en Y de la bola
    private Personaje perBarra;         // Objeto de la clase personaje (Barra)
    private Personaje perBloque;        // Objeto de la clase personaje (Bloque)
    private int iPosXBloque;            // Posicion en X del bloque
    private int iPosYBloque;            // Posicion en Y del bloque
    private boolean bKeyPressed;       // Booleana para cuando se presiona tecla
    private boolean bKeyReleased;       // Booleana para cuando se suelta tecla
    private int iDirBarra;              // Direccion de la barra
    private Personaje perBola;          //Objeto de la clase personaje (Bola)
    private int iVelocidadBola;         // Velocidad de la bola
    private int iDireccionBarra;        // Dirección de la barra
    private int iScore;                 // Score del juego
    private int iContBloques;            // Contador de bloques destruidos
    private boolean bNewGame;        // Booleana para saber si es un juego nuevo
    private boolean bGameOver;      // Booleana para saber si es game over
    private Image imaGameOver;          // Imagen de gameover
    private boolean bPausa;             // Booleana para cuando se pause juego
    private Image imaPausa;             // Imagen de pausa
    private SoundClip souSoundtrack;      // Objeto SoundClip de soundtrack
    private Image imaInicio;                        // Imagen de inicio
    private boolean bInicia; //Booleano para saber si remover pantalla de inicio


    
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
        setSize(501,750);
        // inicializa score
        iScore = 0;
        
        // inicializa booleana para saber si remover pantalla de inicio
        bInicia = false;
        
        // inicializa booleana para saber si es game over
        bGameOver = false;
        
        // inicializa booleana para pausar el juego
        bPausa = false;
        
        // inicializa booleana para saber si es juego nuevo y limpiar la lista
        bNewGame = true;
        if(!bNewGame)
            lnkBloques.clear();
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
        
        // inicializa numero de bloques destruidos
        iContBloques = 0;
        
        // inicializa posicion en X del bloque
        iPosXBloque = 50;
        
        // inicialeiza posicion en Y del bloque
        iPosYBloque = 50;
       
        //Inicializa la direccion en Y
        dSlopeY = -1.5;
        
        //Inicializa la direccion en X
        dSlopeX = -1.5;
        
        //inicializa la booleanaque checa si se disparó la bola
        bDisparada=false;
        
        //Inicializa las vidas
        iVidas = 1;
        // creo imagen de inicio
        URL iURL = this.getClass().getResource("inicio.jpg");
        imaInicio = Toolkit.getDefaultToolkit().getImage(iURL);           
        // se crea imagen de la barra
        URL urlImagenBarra = this.getClass().getResource("barra.png");
        // se crea la barra
	perBarra = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenBarra));
        // inicializa posicion de la barra
        perBarra.setX((getWidth() / 2) - (perBarra.getAncho()/2));
        perBarra.setY((getHeight() - perBarra.getAlto()));
        
        //se cre la imagen de la bola
        URL urlImagenBola = this.getClass().getResource("bola.gif");
        // se crea a la Bola
	perBola = new Personaje(0, 0,
                Toolkit.getDefaultToolkit().getImage(urlImagenBola));
        // inicializa posicion de la Bola
        perBola.setX(perBarra.getX() + perBarra.getAncho() / 2);
        perBola.setY(perBarra.getY() - perBola.getAlto());
        
        dX=perBola.getX();
        dY=perBola.getY();
        
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
        // creo imagen de Gameover
        imaGameOver = Toolkit.getDefaultToolkit()
                .getImage(this.getClass().getResource("gameover.jpg")); 
        // creo imagen de pausa
        URL pURL = this.getClass().getResource("pausa.png");
        imaPausa = Toolkit.getDefaultToolkit().getImage(pURL);  
        // creo el sonido de soundtrack
        souSoundtrack = new SoundClip("soundtrack.wav");
        souSoundtrack.setLooping(true);
        
           
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
        while (iVidas != 0) {
            if (!bPausa) {
                actualiza();
                checaColision();
                movimientoBola();

            }
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
        
        //Si se dispara la bola se actualiza el movimiento de la bola
        
        if( bInicia && bDisparada){
            dX += dSlopeX;
            dY += dSlopeY;
            perBola.setX((int)(dX));
            perBola.setY((int)(dY));
 
        }
        else if (!bDisparada) {
            perBola.setX(perBarra.getX() + ((perBarra.getAncho() / 2) - 
                    perBola.getAncho() / 2) );
            dX = perBola.getX();
        }
        if  (iContBloques == 25) {
            bGameOver = true;
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
        // si la barra llega al limite derecho
        if(perBarra.getX() + perBarra.getAncho() > getWidth()) {
            perBarra.setX(getWidth() - perBarra.getAncho());
            
            // si la barra llega al limite izquierdo
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
        URL urlImagenBackground = this.getClass().getResource("background.jpg");
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
        if (!bInicia) {
            g.drawImage(imaInicio,0,0,this);
        } 
            if (!bGameOver) {
                g.drawImage(perBarra.getImagen(), perBarra.getX(),
                        perBarra.getY(), this);
                g.drawImage(perBola.getImagen(), perBola.getX(),
                        perBola.getY(), this);            
                // dibuja la lista de bloques
                for (Object lnkBloque : lnkBloques) {
                    Personaje perBloque = (Personaje)lnkBloque;
                    //Dibuja la imagen de Susy en la posicion actualizada
                    g.drawImage(perBloque.getImagen(), perBloque.getX(),
                            perBloque.getY(), this);
                }            
                // Pinta el score
                g.setColor(Color.white);
                g.drawString("Score: " + iScore, 10,40);
                g.drawString("Vidas: " + iVidas, 100, 40);
            
            }
        
            if (bPausa) {
                g.drawImage(imaPausa,80,200, this);
            }
            if (iContBloques == iNumBloques || iVidas == 0) {
                bGameOver = true;
                souSoundtrack.stop();
            }
            if (bGameOver) {
                g.drawImage(imaGameOver,0,0,this);
                bDisparada = false;

            }
        
    }
        
    
    
    //Metodo que decide hacia donde se moverá la bola al colisiona
    public void movimientoBola() {
        
        // checa colision de la Bola con la pared del JFrame
        if(perBola.getX() <= 0) {  // si llega al limite izquierdo del JFrame
            dSlopeX *= -1;
        }
        // si llega al limite derecho
        if(perBola.getX() > getWidth() - perBola.getAncho()) { 
            dSlopeX *= -1; 
        }
        // si llega al limite superior
        if(perBola.getY() - (perBola.getAlto() / 2) <= 0) { 
            dSlopeY *= -1;
        }
        // si llega al limite inferior
        if(perBola.getY() > perBarra.getY() + perBarra.getAlto()) {  
            iVidas--;           // reduce una vida
            // reinicia la bola en su posicion inicial
            perBola.setY(perBarra.getY() - perBola.getAlto()); 
            // desactiva booleana de checa si se lanzó la bola
            bDisparada = false;
        }
        
        //Para cuando colisiona con la barra
        if(perBarra.colisiona(perBola) && dY + perBola.getAlto()
                <= perBarra.getY() + perBarra.getAlto() / 4){
            dSlopeY *= -1;
            if(perBola.getX() < perBarra.getX() + perBarra.getAncho() / 8){
                if(dSlopeX > 0){
                    dSlopeX*=-1;
                    dSlopeY=-1.5;
                }
                else{
                    if(dY > 0.5){
                        dSlopeY = 0.5;
                        dSlopeX = 1.5;  
                    }
                    else{
                        dSlopeY=-1.5;
                        dSlopeX=-1.5;
                    }
                }
            }
            if(perBola.getX() >= perBarra.getX() + perBarra.getAncho() / 8 && 
                    perBola.getX() < perBarra.getX() + perBarra.getAncho() / 3){
                if(dSlopeX > 0){
                    dSlopeX = 1.5;
                    dSlopeY = -1.5;
                }
                else{
                    dSlopeY/=2;
                    dSlopeX+=dSlopeX/4;
                }   
            }
            if(perBola.getX() < perBarra.getX() + (perBarra.getAncho() / 8) * 7 
                    && (perBola.getX() > perBarra.getX() + 
                    (perBarra.getAncho() / 3) * 2) ){
                if(dSlopeX < 0){
                    dSlopeX = -1.5;
                    dSlopeY = -1.5;
                }
                else{
                    dSlopeY/=2;
                    dSlopeX+=dSlopeX/4;
                }  
            }
            if(perBola.getX() > perBarra.getX() + (perBarra.getAncho() / 8) 
                    * 7){
                if(dSlopeX < 0){
                    if(dY > 0.5){
                        dSlopeX*=-1;
                    }
                    else{
                        dSlopeY=1.5;
                        dSlopeX=-1.5;
                    }
                }
                else{
                    if(dSlopeY>0.5){
                        dSlopeY/=4;
                        dSlopeX+=dSlopeX/2;
                    }
                    else{
                        dSlopeY=-1.5;
                        dSlopeX=1.5;
                    }
                }
            }
        }
        // Cuando la Bola colisiona con los Bloques
        for (int iI = 0; iI < lnkBloques.size(); iI++) {
            Personaje perBloque = (Personaje) lnkBloques.get(iI);
            
            if( perBola.colisiona(perBloque)) {
                if(perBola.getY() < perBloque.getY() | perBola.getY() > perBloque.getX() + perBloque.getAlto()) {
                    dSlopeY *= -1;
                    lnkBloques.remove(perBloque);
                    iContBloques++;
                } else {
                    dSlopeX *= -1;
                    lnkBloques.remove(perBloque);
                    iContBloques++; 
                }
                iScore += 10;
                
            }
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
        // si se presiona la flecha izquierda
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_LEFT) {
            bKeyPressed = true;
            iDirBarra = 1;
            bKeyReleased = false;
            
        }
        // si se presiona la flecha derecha
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
        // si se suelta la flecha izquierda
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_LEFT) {
            bKeyReleased = true;
            iDirBarra = 1;
            bKeyPressed = false;
        }
        // si se suelta la flecha derecha
        if (keyEvent.getKeyCode() ==  KeyEvent.VK_RIGHT) {
            bKeyReleased = true;
            iDirBarra =2;
            bKeyPressed = false;
        }     
        // si se presiona la tecla P
        if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
            if (!bGameOver) {
                bPausa =! bPausa;       // se pausa el juego
            }
        }
        // si presiona la tecla ENTER
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            bInicia = true;             // booleana inicio de juego se activa
            souSoundtrack.play();       // comienza la cancion
            bDisparada = false;
            

        }
        // si presiona la tecla SPACE
        if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE){
            bDisparada=true;            // inicia el movimiento de la bola
            bInicia = true;

        }
        // si presiona la tecla R
        if (keyEvent.getKeyCode() == KeyEvent.VK_R) {
            if (bGameOver) {
                bGameOver = false;                    
                init();     
                bDisparada = false;
            }
            
        }
        
    }

}