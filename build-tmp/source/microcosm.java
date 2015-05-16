import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.video.*; 
import java.awt.Frame; 
import java.awt.BorderLayout; 
import java.awt.Robot; 
import java.awt.Rectangle; 
import java.awt.AWTException; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class microcosm extends PApplet {

// -------------------------------------------------------------------------------- //
// ---------------- LIVE GLITCHY CINEMA ------------------------------------------- //
// -------------------------------------------------------------------------------- //
// --------- Code by: JM Escalante | realitat.com (m m x v ) ---------------------- //
// -------------------------------------------------------------------------------- //

/*
Journey history
 v 01 - a new turn to improve the capture performance behavior, AND IT WORKS!!! IT'S ALIVE!!!
 v 02 - First tests  --> pixelation, pixelNation LIVE CINEMA 1
 v 03 - Implementaion of previous modes, 6 so far.
 v 04 and 05 different new implementation technniques, ending with spatial hue/red/bright pixel placement
 v 06 - 
 * SPECIAL THANKS TO:
 Andres Cabrera
 and Kurt Kaminski (for pointing me out in the right screen capture, thread and shader direction).
 This project, developed under 201-A will be the foundation of an upcoming live performance
 */
// Switches
int trasladoY = 250; // This i: the Y position where the capture starts
Boolean kamindustries = false; // If we want to display the frameRate
// Working variables
int anchoCaptura, altoCaptura, anchoDisplay, altoDisplay;

PShader mainShader;
PGraphics gl;
PImage toDisplay;

// -------------------------------------------------------------------------------------------
public void setup() {
  // 1. Size operations
  // iniciarStage(640, 360, 1280, 720); // All captures and sizes operations are set up
  //  iniciarStage(640, 360, 640, 360); // All captures and sizes operations are set up
  // iniciarStage(1280, 720, 1280, 720); 
  iniciarStage(1056, 704, 1056, 704); 
  size(anchoDisplay, altoDisplay, P2D);
  
  /*NOTES:
   Captured: 720p/2 Render: 720p(FPS=  almost 60 fps  )
   */
  // 2. We start the screen capture (using threading) operations
  iniciarScreenCapture();


  // 3. We load the PShapes for efficiency
  loadShapes();

  gl = createGraphics(anchoDisplay, altoDisplay, P3D);
  toDisplay = createImage(anchoDisplay, altoDisplay, RGB);

  frame.setResizable(true);
  // cc_toggle = 0; // rgb cc mode
  cf = addControlFrame("src window ctrl", 200,350);
  mainShader = loadShader("shader.frag");

}
// -------------------------------------------------------------------------------------------
public void draw() {

  
  if (!memoria)  background(0);

  // Apply shader magic and output the main toDisplay PImage!!!
  injectShader();

  // Live Cinema stages -->
  // Need this condition to wait until capture is working
  if (capturada.width > 0 && capturada.height > 0){ 
    if (pixelation) pixelationShow();
    if (pixelNation) pixelNationShow();
    if (hairs) hairsShow(); 
    if (stripe) stripeShow();
    if (fallingWater) fallingWaterShow();
    if (polvox) polvoxShow();
    if (meh) mehShow();
    if (spore) sporeShow();
    if (sporeGrid) sporeGridShow();
    
    // Test the performance
    if (kamindustries) verificarFrameRate(); // To test performance

    // Mini Sample
    if (miniCaptura) drawMiniSample();
  }

  // resetShader();

}
// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 PIXELATION 59 fps
//SWITCHES
Boolean pixelation = false;
float pixelationResX = 30; // The resolution from which to scan and search for points
float pixelationResY = 15;
Boolean memoria = false;
// *
Boolean pixelation1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean pixelationStart() {

  return false;
}
// ----------------------------------------------------------------------------------------------------------------

public void pixelationShow() {
  // 0. We don't place the image, bear in mind this
  // 1. We ask if it is the first time
  if (pixelation1AVez) pixelation1AVez = pixelationStart();

  // 2. We will apply some threshold to the image
  // PImage pic = capturada;
  // PImage pic = toDisplay;
  //pic.filter(THRESHOLD, map(mouseX, 0, width, 0, 1));
  // pic.filter(THRESHOLD, random(0.6, 0.8));
  toDisplay.filter(THRESHOLD, random(0.6f, 0.8f));
  //capturada.filter(GRAY);


  // 3. We will cycle through each pixel
  for (int i = 0; i < pixelationResX; i++) {
    for (int j = 0; j < pixelationResY; j++) {
      // a. We get the position
      int x = floor(map( i, 0, pixelationResX, 0, anchoCaptura ));
      int y = floor(map( j, 0, pixelationResY, 0, altoCaptura));
      // b. we ask if it is white
      // if ( pic.get( x, y ) > -1.1 ) {
      if ( toDisplay.get( x, y ) > -1.1f ) {
        // fill(capturada.get( x, y)); // alternative method-->gives colored X'es
        fill(toDisplay.get( x, y));
        // c. We re-map the values
        x = floor(map( x, 0, anchoCaptura, 0, width));
        y = floor(map( y, 0, altoCaptura, 0, height ));

        //d. we draw
        //fill(255, 0, 0);

        noStroke();
        shapeMode(CENTER);
        pushMatrix();
        translate(x, y);
        rotate(radians(45));
        shape( Rect, 0, 0, 2, 15 );
        rotate(radians(-45));
        shape( Rect, 0, 0, 2, 15 );
        popMatrix();
      }
    }
  }
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 pixelNation
//SWITCHES

Boolean pixelNation = false;
float pixelNationResX = 24; // The resolution from which to scan and search for points // 20 works
float pixelNationResY = 10*2; // 10 works
float curtainRuidoYMin = 0.0002f;
float curtainRuidoYMax = 0.052f;
float curtainVelXMax = 12.59f;
float curtainVelXMin = 4.59f;
Boolean curtainPrendida = true;
float toleranciaX = 6; // This is how many surrounding squares will be activated on X
float toleranciaY = 56; // on Y
// *
float curtainCenterX, curtainCenterY, curtainCenterYD;
float curtainRuidoY, curtainVarY;
float curtainVelX = 8;
Boolean pixelNation1AVez = true;
Boolean [] pixelNationPrendidos;
float [] alphaPorcentajes, velAlpha;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public void recalcularAlphas() {
  for (int i = 0; i < velAlpha.length; i++) {
    //velAlpha[i] = random(0.005/15, 0.005*2);
  }
}
public Boolean pixelNationStart() {
  println(">> PIXELNATION STARTED!");

  // 1. We create a temporary Array
  Boolean [] pixelNationPrendidosT = new Boolean[PApplet.parseInt(pixelNationResX*pixelNationResY)];
  float [] alphaPorcentajesT = new float[PApplet.parseInt(pixelNationResX*pixelNationResY)];
  float [] velAlphaT = new float[PApplet.parseInt(pixelNationResX*pixelNationResY)];
  //2. We fill the Arrays
  for (int i = 0; i < pixelNationPrendidosT.length; i++) {
    pixelNationPrendidosT[i] = false;
    alphaPorcentajesT[i] = 0;
    velAlphaT[i] = 0.005f;
  }
  //3. Igualamos los arreglos
  pixelNationPrendidos = pixelNationPrendidosT;
  alphaPorcentajes = alphaPorcentajesT;
  velAlpha = velAlphaT;

  // 4. We create some noise
  curtainVarY = 0.0f;
  curtainRuidoY = random(curtainRuidoYMin, curtainRuidoYMax);

  //5. We set an original center in Y
  curtainCenterYD = random(height);

  // XX. We set the turned on to false
  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void curtainOperations() {
  // a. We calculate the X position
  curtainCenterX += curtainVelX;

  if (curtainPrendida) {
    // b. We calculate the Y position
    float toleranciaAlturaCurtain = 700;
    curtainVarY += curtainRuidoY;
    curtainCenterY = noise(curtainVarY)*toleranciaAlturaCurtain;
    curtainCenterY = map(curtainCenterY, 0, toleranciaAlturaCurtain, -toleranciaAlturaCurtain/2, toleranciaAlturaCurtain/2);
    curtainCenterY = curtainCenterYD +curtainCenterY;
    toleranciaY -= 1;
    // c. Let's draw an ellipse to see where we're at
    fill(0);
    //ellipse(curtainCenterX, curtainCenterY, 5, 5);
  } else {
    if (random(100)>99.9f) curtainPrendida = true;
  }
  //5. We ask if it is off stage
  if (curtainCenterX > width) {
    //Something should happen here
    curtainCenterX = 0;
    curtainCenterYD = random(height);
    curtainPrendida = false;
    toleranciaX = random(100, 900);
    toleranciaY = random(500, 1100);
    pixelNationResX = random( 4, 90 );
    curtainVelX = random(curtainVelXMin, curtainVelXMax);
    recalcularAlphas();
    if (random(10)>9) {
      pixelNationResY = random(2, 20);
    }
  }
}
// ----------------------------------------------------------------------------------------------------------------

public void pixelNationShow() {
  // 1. We ask if it is the first time
  if (pixelNation1AVez) pixelNation1AVez = pixelNationStart();

  // 0. We place the image
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);
  image(toDisplay, 0, 0, anchoDisplay, altoDisplay);

  // Mostrar operaciones de curtain
  curtainOperations();

  // 2. We will cycle through each pixel
  int item = 0;
  for (int i = 0; i < pixelNationResX; i++) {
    for (int j = 0; j < pixelNationResY; j++) {
      // 3. We check if this values are turned on (otherwise, why bother?)
      // if (pixelNationPrendidos[item]) {

      // a. We get the position
      int x = floor(map( i, 0, pixelNationResX, 0, anchoCaptura ));
      int y = floor(map( j, 0, pixelNationResY, 0, altoCaptura));

      // b. we calculate
      float ancho = width/pixelNationResX;
      float alto = height/pixelNationResY;

      // c. we get the color
      // color c = capturada.get( x, y);
      int c = toDisplay.get( x, y);
      fill(c);

      // c. We re-map the values
      x = floor(map( x, 0, anchoCaptura, 0, width));
      y = floor(map( y, 0, altoCaptura, 0, height ));
      x += ancho/2;
      y += alto/2;

      //d. we draw
      noStroke();
      shapeMode(CENTER);
      //fill(0, 0, 0, 220);
      //println("ancho = "+ancho+" alto= "+alto);
      //println("x = "+x+" y= "+y);
      int limiteY = PApplet.parseInt(map( mouseY, 0, height, 0, pixelNationResY )); 
      //if((j > limiteY-3) &&j < limiteY) shape( Rect, x, y, ancho, alto ); 
      if ( x < (curtainCenterX -toleranciaX) || x > (curtainCenterX +toleranciaX)) {
        //shape( Rect, x, y, ancho, alto );
      } else {
        if ( y < (curtainCenterY -toleranciaY) || y > (curtainCenterY +toleranciaY)) {
          //
          //alphaPorcentajes[i] = 0;
        } else {
          alphaPorcentajes[i] = 1;
          //shape( Rect, x, y, ancho, alto );
        }
      }

      fill(red(c), green(c), blue(c), 255* alphaPorcentajes[i]);

      shape( Rect, x, y, ancho+1, alto+1.7f );

      //ellipse(x, y, 10, 10);
      //shape(Rect, mouseX, mouseY, ancho, alto);
      //alphaPorcentajes[i] -= 0.005;
      alphaPorcentajes[i] -=  velAlpha[i];
      if (alphaPorcentajes[i] < 0) alphaPorcentajes[i] = 0;
      // We increase the items
      item++;
      //}
    } // <---- j ends
  } // <--- i ends
} // <--- show function edns


// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 MEH
//SWITCHES
Boolean meh = false;

// *
Boolean meh1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean mehStart() {

  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void mehShow() {
  // 1. We ask if it is the first time
  if (meh1AVez) meh1AVez = mehStart();

  //2. We show the image
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);
  image(toDisplay, 0, 0, anchoDisplay, altoDisplay);
}
// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 hairs
//SWITCHES
Boolean hairs = false;
int cabellosMax = 530; // We will never have more than these hairs // 230 works
int limiteNegro = 70; // The number from where to find dark values
float hairsVelAlphaMin = 2;
float hairsVelAlphaMax = 9;
float hairsSizeMin = 10;
float hairsSizeMax = 70;
Boolean cabellos = true;
Boolean cruces = false;
Boolean bubbles = false;

// *
Boolean hairs1AVez = true;
float [] hairsX, hairsY, hairsTamano, hairsTamanoD, hairsAlpha, hairsVelAlpha, hairsAngulos, despX, sentX, despY, sentY; // All values to control hairs
Boolean [] hairsPrendido; // To see which ones to display or are ready to be displayed.
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean hairsStart() {
  // 1. We start the Arrays
  float [] hairsXT = new float[cabellosMax];
  float [] hairsYT = new float[cabellosMax];
  float [] hairsTamanoT = new float[cabellosMax];
  float [] hairsTamanoDT = new float[cabellosMax];
  float [] hairsAlphaT = new float[cabellosMax];
  float [] hairsVelAlphaT = new float[cabellosMax];
  float [] hairsAngulosT = new float[cabellosMax];
  Boolean [] hairsPrendidoT  = new Boolean[cabellosMax];
  float [] despXT = new float[cabellosMax];
  float [] despYT = new float[cabellosMax];
  float [] sentXT = new float[cabellosMax];
  float [] sentYT = new float[cabellosMax];

  // 2. We fill them
  for (int i = 0; i < cabellosMax; i++) {
    hairsXT[i] = 0.0f;
    hairsYT[i] = 0.0f;
    hairsTamanoT[i] = 0;
    hairsTamanoDT[i] = random(hairsSizeMin, hairsSizeMax);
    hairsAlphaT[i] = 255;
    hairsVelAlphaT[i] = random(hairsVelAlphaMin, hairsVelAlphaMax);
    hairsPrendidoT[i] = false;
    hairsAngulosT[i] = random(360);
    despXT[i] = 0.0f;
    despYT[i] = 0.0f;
    sentXT[i] = 1;
    sentYT[i] = 1;
  }

  // 3. Los igualamos
  hairsX = hairsXT;
  hairsY = hairsYT;
  hairsTamano = hairsTamanoT;
  hairsTamanoD = hairsTamanoDT;
  hairsAlpha = hairsAlphaT;
  hairsVelAlpha = hairsVelAlphaT;
  hairsPrendido = hairsPrendidoT;
  hairsAngulos = hairsAngulosT;
  despX = despXT;
  despY = despYT;
  sentX = sentXT;
  sentY = sentYT;
  // 4. 
  println(">> HAIRS STARTED!");
  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void hairsShow() {
  // 1. We ask if it is the first time
  if (hairs1AVez) hairs1AVez = hairsStart();

  // 2. We show the captured pic
  // image(capturada, 0, 0, width, height);
  image(toDisplay, 0, 0, width, height);

  // 2. We assign a position if the hair is available to assign, if no, well... no :(
  for (int i = 0; i < cabellosMax; i++) {
    if (!hairsPrendido[i]) {

      // a. We get a new point
      int [] posiciones = asignarNuevaPosicion();
      // b. if that new point is valid we assign it
      if ( posiciones[2] == 1 ) {
        // c. WE START THE NEW HAIR!!!!!!!!!
        hairsX[i] = posiciones[0];
        hairsY[i] = posiciones[1];
        hairsPrendido[i] = true;
        hairsAlpha[i] = random(100, 255); // Totally visible
        hairsVelAlpha[i] = random(hairsVelAlphaMin, hairsVelAlphaMax);
        hairsTamano[i] = 0; // Change htis afterwards to work as friction
        hairsTamanoD[i] = random(hairsSizeMin, hairsSizeMax);
        hairsAngulos[i] = random(360);
        despX[i] = 0.0f;
        sentX[i] = 1;
        if (random(2)>1) sentX[i] = -1;
        sentY[i] = 1;
        if (random(2)>1) sentY[i] = -1;
      }
    }
  }

  // 4. We calculate everything that needs to be calculated for each individual hair
  for (int i = 0; i < cabellosMax; i++) {
    if (hairsPrendido[i]) { // We will only show the utrned on hairs
      // We reduce the Alpha
      hairsAlpha[i]-=  hairsVelAlpha[i];

      // We calculate the size
      hairsTamano[i] = calcularFriccion(hairsTamano[i], hairsTamanoD[i], 3);

      // If it is gone, no need to display it
      if (hairsAlpha[i] <= 0) hairsPrendido[i] = false;
    }
  }

  // 3. We show the hair at last, finally!
  for (int i = 0; i < cabellosMax; i++) {
    if (hairsPrendido[i]) { // We will only show the utrned on hairs

      //  propiedades de dibujo
      strokeWeight(random(1));
      // color c = capturada.get( floor(hairsX[i]), floor(hairsY[i]) );
      int c = toDisplay.get( floor(hairsX[i]), floor(hairsY[i]) );
      fill(red(c), green(c), blue(c), hairsAlpha[i]);
      stroke(red(c), green(c), blue(c), hairsAlpha[i]);
      //stroke(255, 0, 0, hairsAlpha[i]);

      // Now the different disualizations for this scenario
      // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593  cabellos
      if (cabellos) {
        pushMatrix();
        translate(hairsX[i], hairsY[i]);
        rotate(radians(hairsAngulos[i]));
        // The actual line
        line(0, 0, hairsTamano[i], 0);
        shapeMode(CENTER);
        noStroke();
        shape(Ellipse, 0, 0, 3, 3);
        shape(Ellipse, hairsTamano[i], 0, 3, 3);
        popMatrix();
      }
      // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593  slit scan
      if (cruces) {
        noStroke();
        shapeMode(CORNERS);
        float tamanoAlto = map( hairsTamano[i], 0, hairsTamanoD[i], hairsY[i], height );
        shape(Rect, hairsX[i], hairsY[i], hairsX[i]+0.5f, tamanoAlto);
        // Let's place the cross
        pushMatrix();
        translate( hairsX[i], hairsY[i]);
        rotate(radians(45));
        //shape(Rect,0, 0, 0+2, tamanoAlto*2); //diagonal
        shapeMode(CENTER);
        float largo = map( hairsTamano[i], 0, hairsTamanoD[i], 3, map( hairsTamanoD[i], hairsSizeMin, hairsSizeMax, 4, 20  ) );
        shape( Rect, 0, 0, 2, largo );
        rotate(radians(45+45));
        if (random(10)>2.7f)shape( Rect, 0, 0, 2, largo );
        popMatrix();
      }
      // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593  slit scan
      if (bubbles) {
        despX[i] += 0.5f*sentX[i];
        despY[i] += 0.5f*sentY[i];
        //despX[i] = map(hairsTamano[i], 0, hairsTamanoD[i],0, 1000);
        //despY[i] = map(hairsTamano[i], 0, hairsTamanoD[i],0, 900);
        noStroke();
        shapeMode(CENTER);
        float tamanoRadio = map( hairsTamano[i], 0, hairsTamanoD[i], 0, random(5));
        shape(Ellipse, hairsX[i]+despX[i], hairsY[i]+despY[i], tamanoRadio, tamanoRadio);
        shape(Ellipse, random(hairsX[i]-2, hairsX[i]+2), hairsY[i], tamanoRadio, tamanoRadio);
      }
    }
  }
}
// ----------------------------------------------------------------------------------------------------------------
// This function returns a 3 point arrays with a dark spot in the picture (low brightness)
// first two 0 and 1 x and y position, the third one (pos2) is a swith 1 or 0
// This switch is implemented in case the image is totally blank/white
public int [] asignarNuevaPosicion() {
  // 1. Variables to avoid infinite loops 
  Boolean encontrePosicion = false;
  int intentosMaximos = 500; // 1000 standard

  // 2. We create a new array and fill the swtich to off
  int [] posiciones = new int[3];
  posiciones[0] = 0;
  posiciones[1] = 0;
  posiciones[2] = 0;

  // 3. We start the cycle
  for (int i = 0; !encontrePosicion && i < intentosMaximos; i++) {
    // a. A new random position
    float xAzar = floor(random(capturada.width));
    float yAzar = floor(random(capturada.height));

    // b. we load the pixels
    // PImage capturadaT = capturada;
    // PImage capturadaT = toDisplay;
    // capturadaT.loadPixels();

    // c. We get one pixel
    // float pixel = brightness(capturadaT.pixels[ int(yAzar*capturadaT.width+xAzar) ]);
    float pixel = brightness(capturada.pixels[ PApplet.parseInt(yAzar*toDisplay.width+xAzar) ]);

    // d We ask if it is black enough
    if (pixel < limiteNegro) {
      // WE trasnlate the positions to the actual width, bear that in mind
      posiciones[0] = floor(map(xAzar, 0, anchoCaptura, 0, anchoDisplay));
      posiciones[1] = floor(map(yAzar, 0, altoCaptura, 0, altoDisplay));
      posiciones[2] = 1; // This is just a switch
      encontrePosicion = true; // The for loop must be turned off by now
      //println("ECONTRE!!!!");
    }

    // Z
    // capturadaT.updatePixels();
  }
  // 4. Values are returnes
  return posiciones;
}


// --------------------------------------------
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 stripe
//SWITCHES
Boolean stripe = false;
int cantidadStripes = 7;
int tiritasMin = 50; // 150 def.
int tiritasMax = 180; //280 def.
float alturaStripeMin = 5;
float alturaStripeMax = 23;
// *
Boolean stripe1AVez = true;
Stripe [] stripes;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean stripeStart() {
  //1. WE create 1 new array
  Stripe [] stripesT = new Stripe[cantidadStripes];

  // 2. WE fill it
  for (int i = 0; i < cantidadStripes; i++) {
    //stripesT[i] = new Stripe(0, width, height/5 *2, height/5 *3, false);
    float posY = random(height);
    float alt = random(alturaStripeMin, alturaStripeMax);
    stripesT[i] = new Stripe(0, width, posY-alt, posY+alt, false);
  }

  // 3. Igualamos arrelgos
  stripes = stripesT;

  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void stripeShow() {
  // 1. We ask if it is the first time
  if (stripe1AVez) stripe1AVez = stripeStart();

  // Apply the shader!!
  injectShader();

  // 2. We show the captured pic
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);
  image(toDisplay, 0, 0, anchoDisplay, altoDisplay);

  // 3. We show the stripes
  for (int i = 0; i < cantidadStripes; i++) {
    stripes[i].mostrar();
    if (stripes[i].todasMuertas()) {
      float posY = random(height);
      float alt = random(alturaStripeMin, alturaStripeMax);
      Stripe b = new Stripe(0, width, posY-alt, posY+alt, false);
      stripes[i] = b;
    }
  }

}
// ------------------------------------------------------------------------------------------------------------------

class Stripe {
  // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 
  // Propiedades
  float x1, x2, y1, y2; // The square
  float ancho, alto;
  int cantidad, sentido; // How many tiritas?
  Boolean empezarArriba, modoApagando; // Where this will start
  Boolean [] vivas; // M'as radical o no
  Boolean [] prendidas; // Para moverse
  Boolean [] yaNaci, yaMori, puedoMorir; // To see if they must be grown
  float [] tamanoD, tamano; // So they grow with friciton
  int c;
  // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 
  // CONSTRUCTORS
  Stripe(float x1, float x2, float y1, float y2, Boolean empezarArriba) {
    // 1. Normal declaration of variables
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.ancho = x2-x1;
    this.alto = y2-y1;
    this.cantidad = floor(random(tiritasMin, tiritasMax));
    // 2. We declare the new Arrays
    vivas = new Boolean[cantidad];
    prendidas = new Boolean[cantidad];
    yaNaci = new Boolean[cantidad];
    yaMori = new Boolean[cantidad];
    puedoMorir = new Boolean[cantidad];
    tamanoD = new float[cantidad];
    tamano = new float[cantidad];
    // 3. We fil the Arrays
    for (int i = 0; i < cantidad; i++) {
      vivas[i] = true; // It will always be true, cause they are alive!!!
      prendidas[i] = false; 
      yaNaci[i] = false; // We must animate their birth
      yaMori[i] = false; // How can they die if they havent been born yet?!
      puedoMorir[i] = false;
      tamanoD[i] = alto;
      tamano[i] = 0.0f; // They start in zero (so they can grow)
    }
    this.empezarArriba = empezarArriba;
    this.sentido = -1;
    if (empezarArriba) this.sentido = 1;
    this.c = color(255, 0, 0);
    this.modoApagando = false;
  }
  // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 
  //METHODS
  public void mostrar() {
    // We will display the stripes if at least one is alive
    if (!todasMuertas()) {
      mostrarStripes();
    } else {
      //println("TODAS ESTAN MUERTAS!");
    }
  }
  // -----------------------------------
  public void mostrarStripes() {
    // 1. Test rect
    //fill(0, 0, 0, 160);
    rectMode(CORNERS);
    //rect(x1, y1, x2, y2);

    // 2. We'll tell them if they have not been born yet, born!
    Boolean yaEncendi = true;
    if (!todasNacidas()) {
      for (int i = 0; i < cantidad && yaEncendi; i++) {
        if (!yaNaci[i] && random(100)>95.8f) {
          yaNaci[i] = true;
          //yaEncendi = false;
          //println("prendi la: "+i);
        }
      }
    }

    // 3. We calculate the positions
    for (int i = 0; i < cantidad && !modoApagando; i++) {
      if (yaNaci[i] && !yaMori[i]) { // Again to make them appear graually
        tamano[i] = calcularFriccion( tamano[i], tamanoD[i], 10 );
        if (tamano[i] == tamanoD[i]) prendidas[i] = true; // This is to prepare them for the disappearance
      }
    }

    // 4. We display each element
    for (int i = 0; i < cantidad; i++) {
      if (yaNaci[i]) {
        // 1. We get the actual X
        float x = map(i, 0, cantidad, x1, x2);

        // 2. We get the Y
        float y = y1;
        if (!empezarArriba)  y = y2;

        // 3. We set the width
        float anchoStripe = 5;
        anchoStripe = ancho/cantidad;

        // 2. We get the color for the first bar
        float deseoX = map( x, 0, anchoDisplay, 0, anchoCaptura );
        float deseoY = map( y+(tamano[i]*sentido), 0, altoDisplay, 0, altoCaptura);
        // c = capturada.get(floor(deseoX), floor(deseoY));
        c = toDisplay.get(floor(deseoX), floor(deseoY));

        // 3. We set the display properties
        noStroke();
        fill(c);

        // 4. We put the 1st shape, finally!
        shapeMode(CORNERS);
        shape(Rect, x- anchoStripe, y, x +anchoStripe, y+(tamano[i]*sentido));

        // 5. WE put the 2nd Shape
        deseoY = map( y-(tamano[i]*sentido), 0, altoDisplay, 0, altoCaptura);
        // c = capturada.get(floor(deseoX), floor(deseoY));
        c = toDisplay.get(floor(deseoX), floor(deseoY));
        fill(c);

        // 6. We put the second shape
        shape(Rect, x- anchoStripe, y, x +anchoStripe, y-(tamano[i]*sentido));
      } // <-- yaNaci question
    } // < --- Individual for ends

    // 5. We ask if all of them are alive, so we kill them all (this is to agro)
    if (todasPrendidas() && !modoApagando) {
      // println("TODAS ESTAN PRENDIDAS");
      modoApagando = true;
    }

    // 6. They ask for permission to die
    // 2. We'll tell them if they have not been born yet, born!
    Boolean turnoMorir = true;
    if (modoApagando) {
      for (int i = 0; i < cantidad && turnoMorir; i++) {
        if (!puedoMorir[i] && random(100)>95.8f) {
          puedoMorir[i] = true;
          turnoMorir = false;
          //println("prendi la: "+i);
        }
      }
    }

    // 7. We ask if we can shut it down, if so, we will
    for (int i = 0; i < cantidad && modoApagando; i++) {
      if (!yaMori[i] && puedoMorir[i]) { // We will only deal with the alive ones
        tamano[i] = calcularFriccion( tamano[i], 0, 5 );
        if (tamano[i] == 0) yaMori[i] = true; // This is, it's all over now baby blue
      }
    }
  }
  // -----------------------------------------------------
  public Boolean todasNacidas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!yaNaci[i]) todas = false;
    }
    return todas;
  }
  // -----------------------------------------------------
  public Boolean todasPrendidas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!prendidas[i]) todas = false;
    }
    return todas;
  }
  // -----------------------------------------------------
  public Boolean todasMuertas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!yaMori[i]) todas = false;
    }
    return todas;
  }
  // \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593
} // <-- Stripe class ends
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 fallingWater
//SWITCHES
Boolean fallingWater = false;
// WaterFall just takes the pixels and distorts them to the bottom
int densidadWaterFall = 140; // How many pixel density we will use
float [] yWater, speedWater, sentidoWater;
float speedWaterMin = 1.2f;
float speedWaterMax = 1.20000000001f;
// *
Boolean fallingWater1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean fallingWaterStart() {
  // 1. We start some temporary Arrays
  float [] yWaterT = new float[densidadWaterFall];
  float [] speedWaterT = new float[densidadWaterFall];
  float [] sentidoWaterT = new float[densidadWaterFall];

  // 2. We fill those Arrays
  for (int i = 0; i < densidadWaterFall; i++) {
    yWaterT[i] = height;
    yWaterT[i] = map(i, 0, densidadWaterFall, 0, height);
    speedWaterT[i] = random( speedWaterMin, speedWaterMax );
    //speedWaterT[i] = i*0.1;
    sentidoWaterT[i] = -1; // Because it will decrease since it starts at the middle of the screen
  }

  // 3. Igualamos los arreglos
  yWater = yWaterT;
  speedWater = speedWaterT;
  sentidoWater = sentidoWaterT;

  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void fallingWaterShow() {
  // 1. We ask if it is the first time
  if (fallingWater1AVez) fallingWater1AVez = fallingWaterStart();

  // Apply the shader!!
  // injectShader();

  // 2. We show the image
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);
  image(toDisplay, 0, 0, anchoDisplay, altoDisplay);
  
  //  //We start Cycling
  for (int i = 0; i < densidadWaterFall/1; i++) {
    // a. We calculate the positions 
    yWater[i] += speedWater[i]*sentidoWater[i];
    // b. We ask if it is lower than zero
    if (yWater[i] < 0 || yWater[i] > height) sentidoWater[i] *= -1;

    // c. We calculate the X position
    float x = map(i, 0, densidadWaterFall, 0, width);

    // d. We calculate the width of this rect
    float ancho = 9;
    //ancho = width/densidadWaterFall * 2;

    // e. We will get a color data from an image
    int deseoX = floor(map(i, 0, densidadWaterFall, 0, capturada.width));
    int deseoY = floor(map(yWater[i], 0, height, 0, capturada.height));
    // color c = capturada.get(deseoX, deseoY);
    int c = toDisplay.get(deseoX, deseoY);

    // f. We draw something upside down
    noStroke();
    fill(255, 255, 255, 230);
    fill(c);
    fill(red(c), green(c), blue(c), 230);
    if (random(10)>9.9f) fill(c);
    shapeMode(CORNERS);
    if (yWater[i]<height/2) shape(Rect, x, 0, x+ancho, yWater[i]); // D

    // g. We draw something diagonally
    pushMatrix();
    //fill(c);
    //fill(255,255,255,90);
    translate(x+ancho, yWater[i]);
    //rotate(radians(yWater[i]/10));
    rotate(radians(45));
    //if (random(10) >5) rotate(radians(90));
    rectMode(CORNER);
    if (x < width/2) shape(Rect, 0, 0, ancho, width);
    popMatrix();

    // h. half image
    //PImage temp = capturada.get(100, 100, 100, 100 );
    //image(temp, 0, 0);
  }
}


// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 polvox
//SWITCHES
Boolean polvox = false;
Boolean ocultar = true;
Dust [] polvos; // All of the Dust particles
int cantidadSurcos = 155; // 55 seems to work, 255 fully populated
float grosorMax = 3.81f;
float [] xLinea, yLinea, velLinea, alphaLinea, velAlphaOla, velOlaLinea, sentidoLinea, anguloLinea;
Boolean [] surcoPrendido;
float [] angulosPosiblesLinea = {
  90+45, 90, 45
};
float velSurcosMin = 3.52f;
float velSurcosMax = 26;
float alphaInicioMin = 150;
float alphaInicioMax = 250;
float velAlphaMin = 5.51f;
float velAlphaMax = 22; // 2 workds
float velLineaMin = 1.5f; // 1.5 works
float velLineaMax = 9;//7 original

// *
Boolean polvox1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean polvoxStart() {
  
    // We start the Arrays
  xLinea = new float[cantidadSurcos];
  yLinea = new float[cantidadSurcos];
  velLinea = new float[cantidadSurcos];
  alphaLinea = new float[cantidadSurcos]; // El alpha de inicio
  velAlphaOla = new float[cantidadSurcos];
  velOlaLinea = new float[cantidadSurcos]; 
  sentidoLinea = new float[cantidadSurcos]; 
  anguloLinea = new float[cantidadSurcos];
  surcoPrendido = new Boolean[cantidadSurcos];

  // We fill them
  for (int i = 0; i < cantidadSurcos; i++) {
    xLinea[i] = 0;
    sentidoLinea[i] = 1;
    yLinea[i] = random( height);
    velLinea[i] = random(velSurcosMin, velSurcosMax);
    alphaLinea[i] = random(alphaInicioMin, alphaInicioMax); // Alpha inicio
    //alphaLinea[i] = 255;
    velAlphaOla[i] = random(velAlphaMin, velAlphaMax);
    //velAlphaOla[i] = random(1, 7);
    velOlaLinea[i] = random( velLineaMin, velLineaMax );
    //velOlaLinea[i] = 1;
    anguloLinea[i] = angulosPosiblesLinea[floor(random(angulosPosiblesLinea.length))];
    surcoPrendido[i] = true;
  }

  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void polvoxShow() {
  // 1. We ask if it is the first time
  if (polvox1AVez) polvox1AVez = polvoxStart();
  
  // Apply the shader!!
  // injectShader();

  // 2. We show the image
  // if(ocultar) image(capturada, 0, 0, anchoDisplay, altoDisplay);
  if(ocultar) image(toDisplay, 0, 0, anchoDisplay, altoDisplay);
  
  // 1. Mostramos y calculamos los surcos
  for (int i = 0; i < cantidadSurcos; i++) {
    // 1. We calculate the X Position
    xLinea[i] += velLinea[i]*sentidoLinea[i];
    //2. We ask if it's beyond  our borders
    if (xLinea[i] > width) {
      yLinea[i] = random(height);
      sentidoLinea[i] = -1;
      surcoPrendido[i] = false;
    } else if (xLinea[i] < 0) {
      sentidoLinea[i] = 1;
      surcoPrendido[i] = false;
    }

    //Experimentos del prendido
    //surcoPrendido[i] = true;
    if (!surcoPrendido[i] && random(100)> 99.9f) surcoPrendido[i] = true;

    // 3. We draw the line (just to be sure where we're at, perhaps comment it afterwards)
    stroke(0, 0, 0, 10);
    fill(0);
    //strokeWeight(1);
    //ellipse( xLinea[i], yLinea[i], 4, 4);

    // 4. And now we add a new Dust
    if (surcoPrendido[i]) crearDust( xLinea[i], random(yLinea[i]-9, yLinea[i]+9), alphaLinea[i], velAlphaOla[i], velOlaLinea[i], anguloLinea[i], grosorMax);
  }

  /*
  // Va la linea con todo y su aumento
   xLinea += random(4,14.8); // 4.8 works
   stroke(0, 0, 0, 10);
   strokeWeight(1);
   line( 0, yLinea, xLinea, yLinea );
   if (xLinea > width){
   xLinea = 0;
   yLinea = random(height); 
   }
   
   // Aumentamos sin sentido
   crearDust( xLinea, random(yLinea-5, yLinea+5 ));
   crearDust( xLinea, random(yLinea-5, yLinea+5 ));;
   */


  // Va el dust
  for (int i = 1; i < cantidadDust; i++ ) {
    if (polvos[i].getVivo()) polvos[i].mostrar();
  }
}
// -------------------------------------------------- Dust Creation 
int contadorDust = 0;
int cantidadDust = 0; // It will always be 0 at the start // 55 seems
public void crearDust(float x, float y, float alphaInicio, float velAlpha, float velX, float angulo, float grosor) {
  // We start the Array if it is Null
  if (cantidadDust == 0) polvos = new Dust[1];

  // 2. We create a new object on specific coordinates
  Dust b = new Dust(x, y, alphaInicio, velAlpha, velX, angulo, grosor);

  // 3. We append it to the Array or start 
  //if(cantidadDust == 1000){
  polvos = (Dust[]) append (polvos, b);
  //}else{
  //  polvos[i] = 
  //}

  // 1. We add the counter
  cantidadDust++;
}

// --------------------------------------------------- Dust Class
class Dust {
  // Propiedades -----------------------------------------------
  float x, y, aumentoX; // The starting points
  float angulo, alpha, grosor, alphaInicio, velAlpha, velX;
  int c;
  Boolean vivo;
  // Constructors----------------------------------------------
  Dust(float x, float y, float alphaInicio, float velAlpha, float velX, float angulo, float grosor) {

    this.x = x;
    this.y = y;
    //this.angulo = 90; // Her we can specify a different one
    //if (random(2)>1) this.angulo = -90;
    //this.angulo = 45;
    this.angulo = angulo;
    this.aumentoX = 0;
    // this.c = capturada.get( int(x), int(y) );
    this.c = toDisplay.get( PApplet.parseInt(x), PApplet.parseInt(y) );
   // this.c = color(random(255), random(255), 0);
    this.alpha = 255;
    this.alpha= alphaInicio;
    this.vivo = true;
    this.grosor = grosor;
    this.velAlpha = velAlpha;
    this.velX = velX;
  }

  // Getters/Setters------------------------------------------------
  public Boolean getVivo() {
    return this.vivo;
  }

  // Methods----------------------------------------------------------
  public void mostrar() {

    // 1.We calculate the values
    this.aumentoX += random(velX/2, velX); // 6 seems to work
    this.alpha -= velAlpha;

    // 2. We translate
    pushMatrix();
    translate(x, y);
    //angulo += 0.0001;
    //if (random(10)>7) angulo -= 0.0001;
    rotate(radians(angulo));

    // 3. We draw
    stroke(red(c), green(c), blue(c), alpha);
    strokeWeight(grosor);
    strokeCap(SQUARE);
    // line( 0, 0, aumentoX, 0 );

    // 3. Espinitas
    int segmentosEspinitas = 2;
    for (int i = 0; i < segmentosEspinitas; i++) {
      float inter = aumentoX/segmentosEspinitas;
      float inicio = i*inter;
      float fin= (i+1)*inter;
      float grosorT = map(i, 0, 10, grosor, 0);
      strokeWeight(grosorT);
      //line( inicio, 0, fin-(inter*0), 0 );
    }


    // 3. Shape alternative test
    int deseoX = floor(map(x, 0, anchoDisplay, 0, anchoCaptura));
    int deseoY = floor(map(y, 0, altoDisplay, 0, altoCaptura));
    // c = capturada.get( deseoX, deseoY) ;
    c = toDisplay.get( deseoX, deseoY) ;
    fill(red(c), green(c), blue(c), alpha);

    noStroke();
    shapeMode(CORNER);
    shape(Rect, 0, 0, aumentoX, grosor);
    //fill(255, 255, 255, alpha);
    shapeMode(CENTER);
    //shape(Ellipse, aumentoX, 0, 20, 20);

    // 4. Vertex (fail)
    //fill(255, 255, 255, 10);
    noStroke();
    shapeMode(CENTER);
    // shape( Ellipse, 0, 0, 2, 2 );

    // 4. WE check if the alpha is still in place
    if (alpha <= 0) vivo = false; // We die, stop

    popMatrix();
  }
}
// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 spore
/*
The spore scenario is an attempt to make point cloudds (not exactly)
 and pushing GPU power to it's limits
 */
//SWITCHES
Boolean sporeGrid = false;

// *
Boolean sporeGrid1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean sporeGridStart() {


  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void sporeGridShow() {
  // 1. We ask if it is the first time
  if (spore1AVez) spore1AVez = sporeStart();

  //2. We show the image
  //image(capturada, 0, 0, anchoDisplay, altoDisplay);

  // 3. Now points that will appear like a led screen
  int reng = altoDisplay/7;
  int col = anchoDisplay/7;
  int cantLed = reng*col;
  // println( ">> Cant led = "+cantLed );
  for (int i = 1; i < col; i++) {
    float interX = width/col;
    float x = interX*i;
    for (int j = 1; j < reng; j++) {
      float interY = height/reng;
      float y = interY*j;

      stroke(255);

      int xc = PApplet.parseInt(map( x, 0, width, 0, anchoCaptura ));
      int yc = PApplet.parseInt(map( y, 0, height, 0, altoCaptura ));

      // color c = capturada.get( xc, yc );
      int c = toDisplay.get( xc, yc );
      float bright_val = red(c)+green(c)+blue(c);
      stroke(red(c), green(c), blue(c), bright_val*2.f);
      strokeWeight(6);
      strokeCap(SQUARE);
      noFill();
      point(x+(interX/2), y+(interY/2));
    }
  }

  // 4. Test points
  int cantidadGas = 1000;
  for (int i = 0; i < cantidadGas; i++) {
    float x = random(width);
    float y = random(height);

    int xc = PApplet.parseInt(map( x, 0, width, 0, anchoCaptura ));
    int yc = PApplet.parseInt(map( y, 0, height, 0, altoCaptura ));

    // color c = capturada.get( xc, yc );
    int c = toDisplay.get( xc, yc );

    stroke(0);
    stroke(c);
    strokeWeight(3);
    strokeCap(SQUARE);
    point(x, y);
  }
}

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 spore
// This function now aims to the leds
//SWITCHES
Boolean negro = true;
int intervaloDivisionLeds = 7; // This can be changed 7 def.
int rengLeds, colLeds, cantLeds;
float [] xLeds, xLedsD, xLedsO; // actual, deseo, original
float [] yLeds, yLedsD, yLedsO;
int it = 0;
float interXLed, interYLed;
Boolean saturationMode = true;
Boolean brightnessMode = true;
Boolean hueMode = true;
//
Boolean spore = true;
// *
Boolean spore1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
public Boolean sporeStart() {
  // 1. We set a quantity
  rengLeds = altoDisplay/intervaloDivisionLeds;
  colLeds = anchoDisplay/intervaloDivisionLeds;
  cantLeds = rengLeds*colLeds;

  // 2. We create new empty Arrays
  float [] xLedsT = new float[ cantLeds ];
  float [] xLedsDT = new float[ cantLeds ];
  float [] xLedsOT = new float[ cantLeds ];
  float [] yLedsT = new float[ cantLeds ];
  float [] yLedsDT = new float[ cantLeds ];
  float [] yLedsOT  = new float[ cantLeds ];

  // 3. We fill them
  it = 0; // it de items
  for (int i = 0; i < colLeds; i++) {
    // 3 a - we establish x properties
    interXLed = width/colLeds;
    float x = interXLed*i;
    for (int j = 0; j < rengLeds; j++) {
      // 3 - b we establish y properties
      interYLed = height/rengLeds;
      float y = interYLed*j;

      // 3 - c we put in on the array
      xLedsT[it] = x; // actual is the same as the original
      xLedsDT[it] = 0;
      xLedsOT[it] = x;

      yLedsT[it] = y; // actual is the same as the original
      yLedsDT[it] = 0;
      yLedsOT[it] = y;

      // 3 - d
      // WE add the items
      it++;
    }
  }

  // 4. Los igualamos
  xLeds = xLedsT;
  xLedsD = xLedsDT;
  xLedsO = xLedsOT;
  yLeds = yLedsT;
  yLedsD = yLedsDT;
  yLedsO  = yLedsOT;

  // 5. We started!!!!
  return false;
}
// ----------------------------------------------------------------------------------------------------------------
public void sporeShow() {
  // 1. We ask if it is the first time /////////////////////////////////////////////////////////////////////////////
  if (spore1AVez) spore1AVez = sporeStart();

  // Apply the shader!!
  // injectShader();

  // 2. We show the image (no thank you, because we want this effect)...or should we? //////////////////////////////
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);
  if (negro) {
    fill(0, 0, 0, 3);
    noStroke();
    rectMode(CORNERS);
    rect(0, 0, width, height);
  }

  // 3. We calculate the positions ////////////////////////////////////////////////////////////////////////////////
  it = 0; // it de items
  // a. we load the pixels
  // PImage capturadaT = capturada;
  // capturadaT.loadPixels();

  for (int i = 0; i < colLeds; i++) {
    for (int j = 0; j < rengLeds; j++) {
      // b. WE see which pixel we want
      int xc = PApplet.parseInt(map( xLedsO[it], 0, width, 0, anchoCaptura-1 ));
      int yc = PApplet.parseInt(map( yLedsO[it], 0, height, 0, altoCaptura-1 ));

      // c. We get one pixel
      float pixel = 0.0f;
      // if (saturationMode) pixel = brightness(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (saturationMode) pixel = brightness(toDisplay.pixels[ PApplet.parseInt(yc*toDisplay.width+xc) ]);
      // if (brightnessMode) pixel = brightness(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (brightnessMode) pixel = brightness(toDisplay.pixels[ PApplet.parseInt(yc*toDisplay.width+xc) ]);
      // if (hueMode) pixel = hue(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (hueMode) pixel = hue(toDisplay.pixels[ PApplet.parseInt(yc*toDisplay.width+xc) ]);

      // d. WE will translate this pixel Y value to height
      yLedsD[it] = map( pixel, 0, 255, 0, height );

      // e. We make the friction operations
      yLeds[it] = calcularFriccion( yLeds[it], yLedsD[it], 20 );

      // yLedsD[it] = map( pixel, 0, 255, -10, 10 );
      //yLeds[it] = calcularFriccion( yLeds[it],  yLeds[it]+yLedsD[it], 20 );
      // WE add the items
      it++;
    }
  }

  // 4. We display them / ///////////////////////////////////////////////////////////////////////////////////////
  it = 0; // it de items
  for (int i = 0; i < colLeds; i++) {
    for (int j = 0; j < rengLeds; j++) {

      // We get the color (TRICKY PART, the original one?) we get the color form the ORIGINAL coordiantes
      int xc = PApplet.parseInt(map( xLedsO[it], 0, width, 0, anchoCaptura ));
      int yc = PApplet.parseInt(map( yLedsO[it], 0, height, 0, altoCaptura ));
      // color c = capturada.get( xc, yc );
      int c = toDisplay.get( xc, yc );

      // We put the item, finally!
      stroke(red(c), green(c), blue(c), 150);
      strokeWeight(random(8)); // 7 original
      strokeCap(SQUARE);
      noFill();
      // int dx = floor(random(14));
      point(xLeds[it]+(interXLed/2), yLeds[it]+(interYLed/2)); // WE draw it to the ACTUAL coordinates

      // WE add the item
      it++;
    }
  } // <-- display fork ends
}

private ControlP5 cp5;

ControlFrame cf;

int capture_window_posX = 0;
int capture_window_posY = 0;
int capture_window_width = 1150;
int capture_window_height = 480;
int pos_window_x_start = 0;
int pos_window_y_start = 0;
boolean update_capture_window = true;
boolean update_sliders = true;

float ui_red = 0.f;
float ui_green = 0.f;
float ui_blue = 0.f;
float ui_brightness = 0.f;
float ui_contrast = 0.f;
float ui_hue = 0.f;
float ui_saturation = 0.f;
float ui_sharpen = 0.f;
float ui_niceContrast = 0.f;
int ui_sortBlackVal = -10000000;
int ui_sortBrightVal = 60;
int ui_sortWhiteVal = -6000000;
float ui_party = 0.f;

float ui_red_target = 0.f;
float ui_green_target = 0.f;
float ui_blue_target = 0.f;
float ui_brightness_target = 0.f;
float ui_contrast_target = 0.f;
float ui_hue_target = 0.f;
float ui_saturation_target = 0.f;
float ui_sharpen_target = 0.f;
float ui_niceContrast_target = 0.f;
int ui_sortBlackVal_target = -10000000;
int ui_sortBrightVal_target = 60;
int ui_sortWhiteVal_target = -6000000;
float ui_party_target = 0.f;

int update_shader = 0;

// CheckBox checkbox_cc;
CheckBox checkbox_fx;
CheckBox checkbox_sort;
CheckBox checkbox_party;
CheckBox checkbox_updateShader;
int cp5_mx = 30;
int cp5_my = 10;

// int cc_toggle; 
// int[] fx_toggle; 
int[] sort_toggle;
boolean sort_masterToggle = false;
int party_toggle;

// S O R T I N G
//MODE:
//0 -> black
//1 -> bright
//2 -> white
int sort_mode = 0;
int loops = 1;
int blackValue = -10000000;
int brigthnessValue = 60;
int whiteValue = -6000000;
int row = 0;
int column = 0;
boolean saved = false;

PFont font1 = createFont("monaco", 10, true);


///////////////////////////////////////////////////////////////////////
// C O N T R O L   F R A M E
///////////////////////////////////////////////////////////////////////
// Sliders n stuff
///////////////////////////////////////////////////////////////////////

public ControlFrame addControlFrame(String theName, int theWidth, int theHeight) {
  Frame f = new Frame(theName);
  ControlFrame p = new ControlFrame(this, theWidth, theHeight);
  f.add(p);
  p.init();
  f.setTitle(theName);
  f.setSize(p.w, p.h);
  f.setLocation(100, 100);
  f.setResizable(false);
  f.setVisible(true);
  return p;
}

// the ControlFrame class extends PApplet, so we 
// are creating a new processing applet inside a
// new frame with a controlP5 object loaded
public class ControlFrame extends PApplet {

  int w, h;

  int cf_bgColor = 100;
  
  public void setup() {
    size(w, h);
    frameRate(25);
    cp5 = new ControlP5(this);

    ///////////////////////////////////////////////////////////////////////
    // T O G G L E S
    ///////////////////////////////////////////////////////////////////////
    checkbox_sort = cp5.addCheckBox("checkbox_sort")
                      .setPosition(cp5_mx-14-10, 180+cp5_my)
                      .addItem("sort_black_mode", 0)
                      .addItem("sort_value_mode", 0)
                      .addItem("sort_white_mode", 0)
                      .setItemsPerRow(1)
                      .hideLabels()
                      ;
    checkbox_party = cp5.addCheckBox("checkbox_party")
                      .setPosition(cp5_mx-14-10, 250+cp5_my)
                      .addItem("party_mode", 0)
                      .setItemsPerRow(1)
                      .hideLabels()
                      ;
    checkbox_updateShader = cp5.addCheckBox("checkbox_updateShader")
                      .setPosition(cp5_mx-14-10, 220+cp5_my)
                      .addItem("update shader", 0)
                      .setItemsPerRow(1)
                      // .hideLabels()
                      ;                    

    ///////////////////////////////////////////////////////////////////////
    // R E S E T   B U T T O N S
    ///////////////////////////////////////////////////////////////////////
    cp5.addButton("red_reset") 
        .setPosition(PApplet.parseInt(cp5_mx-12),70+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("green_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),80+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("blue_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),90+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("brightness_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),110+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("contrast_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),120+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("saturation_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),130+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("hue_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),140+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sharpen_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),150+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("niceContrast_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),160+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_black_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),180+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_value_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),190+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_white_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),200+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("party_reset")
        .setPosition(PApplet.parseInt(cp5_mx-12),250+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;

    ///////////////////////////////////////////////////////////////////////
    // S L I D E R S
    ///////////////////////////////////////////////////////////////////////
    cp5.addSlider("red")
      .setPosition(cp5_mx,70+cp5_my)
      .setRange(-1.0f, 1.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("green")
      .setPosition(cp5_mx,80+cp5_my)
      .setRange(-1.0f, 1.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("blue")
      .setPosition(cp5_mx,90+cp5_my)
      .setRange(-1.0f, 1.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("brightness")
      .setPosition(cp5_mx,110+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("contrast")
      .setPosition(cp5_mx,120+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("saturation")
      .setPosition(cp5_mx,130+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("hue")
      .setPosition(cp5_mx,140+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("sharpen")
      .setPosition(cp5_mx,150+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("niceContrast")
      .setPosition(cp5_mx,160+cp5_my)
      .setRange(-100, 100.0f)
      .setValue(0.0f)
      ;
    cp5.addSlider("sort: black")
      .setPosition(cp5_mx,180+cp5_my)
      .setRange(-20000000, -6000000)
      .setValue(-10000000)
      ;
    cp5.addSlider("sort: value")
      .setPosition(cp5_mx,190+cp5_my)
      .setRange(0.001f, 255)
      .setValue(60)
      ;
    cp5.addSlider("sort: white")
      .setPosition(cp5_mx,200+cp5_my)
      .setRange(-10000000, 0)
      .setValue(-6000000)
      ;
    cp5.addSlider("party")
      .setPosition(cp5_mx,250+cp5_my)
      .setRange(0.f, 1.f)
      .setValue(0.0f)
      ;


  }

  public void controlEvent(ControlEvent theEvent) {

    ///////////////////////////////////////////////////////////////////////
    // A D J U S T M E N T   S L I D E R S
    ///////////////////////////////////////////////////////////////////////
    if (theEvent.isFrom(cp5.getController("red"))) {
      ui_red_target = (theEvent.getController().getValue());
      update_sliders=true;
    }
    if (theEvent.isFrom(cp5.getController("green"))) {
      ui_green_target = (theEvent.getController().getValue());
    }
    if (theEvent.isFrom(cp5.getController("blue"))) {
      ui_blue_target = (theEvent.getController().getValue());
    } 
    if (theEvent.isFrom(cp5.getController("brightness"))) {
      float b = theEvent.getController().getValue();
      ui_brightness_target = b / 100.0f;
    }
    if (theEvent.isFrom(cp5.getController("contrast"))) {
      float c = theEvent.getController().getValue();
      ui_contrast_target = c / 100.0f;
      // if (fx_toggle[1]==0) ui_contrast = pow((100 + c) / 100, 2.0);
      // else if (fx_toggle[1]==1) ui_contrast = c / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("hue"))) {
      float b = theEvent.getController().getValue();
      ui_hue_target = b / 100.0f;
    }
    if (theEvent.isFrom(cp5.getController("saturation"))) {
      float b = theEvent.getController().getValue();
      ui_saturation_target = b / 100.0f;
    }
    if (theEvent.isFrom(cp5.getController("sharpen"))) {
      float b = theEvent.getController().getValue();
      ui_sharpen_target = b / 100.0f;
    }
    if (theEvent.isFrom(cp5.getController("niceContrast"))) {
      float b = theEvent.getController().getValue();
      ui_niceContrast_target = b / 100.0f;
    }

    if (theEvent.isFrom(cp5.getController("sort: black"))) {
      ui_sortBlackVal = PApplet.parseInt((theEvent.getController().getValue()));
    }
    if (theEvent.isFrom(cp5.getController("sort: value"))) {
      ui_sortBrightVal = PApplet.parseInt((theEvent.getController().getValue()));
    }
    if (theEvent.isFrom(cp5.getController("sort: white"))) {
      ui_sortWhiteVal = PApplet.parseInt((theEvent.getController().getValue()));
    }

    if (theEvent.isFrom(cp5.getController("party"))) {
      float b = theEvent.getController().getValue();
      ui_party = b / 100.0f;
    }

    ///////////////////////////////////////////////////////////////////////
    // R E S E T   B U T T O N S
    ///////////////////////////////////////////////////////////////////////
    if (theEvent.isFrom(cp5.getController("red_reset"))) {
      cp5.getController("red").setValue(0.0f);
      ui_red_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("green_reset"))) {
      cp5.getController("green").setValue(0.0f);
      ui_green_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("blue_reset"))) {
      cp5.getController("blue").setValue(0.0f);
      ui_blue_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("brightness_reset"))) {
      cp5.getController("brightness").setValue(0.0f);
      ui_brightness_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("contrast_reset"))) {
      cp5.getController("contrast").setValue(0.0f);
      ui_contrast_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("hue_reset"))) {
      cp5.getController("hue").setValue(0.0f);
      ui_hue_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("saturation_reset"))) {
      cp5.getController("saturation").setValue(0.0f);
      ui_saturation_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("sharpen_reset"))) {
      cp5.getController("sharpen").setValue(0.0f);
      ui_sharpen_target = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("niceContrast_reset"))) {
      cp5.getController("niceContrast").setValue(0.0f);
      ui_niceContrast = 0.0f;
    }
    if (theEvent.isFrom(cp5.getController("sort_black_reset"))) {
      cp5.getController("sort: black").setValue(-10000000);
      ui_sortBlackVal = -10000000;
    }
    if (theEvent.isFrom(cp5.getController("sort_value_reset"))) {
      cp5.getController("sort: value").setValue(60);
      ui_sortBrightVal = 60;
    }
    if (theEvent.isFrom(cp5.getController("sort_white_reset"))) {
      cp5.getController("sort: white").setValue(-6000000);
      ui_sortWhiteVal = -6000000;
    }
    if (theEvent.isFrom(cp5.getController("party_reset"))) {
      cp5.getController("party").setValue(0.0f);
      ui_party = 0.0f;
    }

    ///////////////////////////////////////////////////////////////////////
    // M O R E   T O G G L E S
    ///////////////////////////////////////////////////////////////////////
    
    if (theEvent.isFrom(checkbox_party)) {
        party_toggle = (int)checkbox_party.getArrayValue()[0];
      }

    if (theEvent.isFrom(checkbox_sort)) {
      int incoming_0 = (int)checkbox_sort.getArrayValue()[0];
      int incoming_1 = (int)checkbox_sort.getArrayValue()[1];
      int incoming_2 = (int)checkbox_sort.getArrayValue()[2];
      if (incoming_0 != sort_toggle[0]) {
        sort_toggle[0] = (int)checkbox_sort.getArrayValue()[0];
        sort_toggle[1] = 0;
        sort_toggle[2] = 0;
        checkbox_sort.setArrayValue(PApplet.parseFloat(sort_toggle));
        sort_mode = 0;
      }
      else if (incoming_1 != sort_toggle[1]) {
        sort_toggle[0] = 0;
        sort_toggle[1] = (int)checkbox_sort.getArrayValue()[1];
        sort_toggle[2] = 0;
        checkbox_sort.setArrayValue(PApplet.parseFloat(sort_toggle));
        sort_mode = 1;
      }
      else if (incoming_2 != sort_toggle[2]) {
        sort_toggle[0] = 0;
        sort_toggle[1] = 0;
        sort_toggle[2] = (int)checkbox_sort.getArrayValue()[2];
        checkbox_sort.setArrayValue(PApplet.parseFloat(sort_toggle));
        sort_mode = 2;
      }
      int x = 0;
      for (int i=0; i<3; i++){
        if (sort_toggle[i] > 0) x++;
      }
      if (x<=0) sort_masterToggle = false;
      else if (x>0) sort_masterToggle = true;
    }
    
    if (theEvent.isFrom(checkbox_updateShader)) {
      update_shader = (int)checkbox_updateShader.getArrayValue()[0];
    }

  }



  public void draw() {
    background(cf_bgColor);
    // textFont(font1);
    // text(fps, 5,10);
  }
  
  private ControlFrame() {
  }

  public ControlFrame(Object theParent, int theWidth, int theHeight) {
    parent = theParent;
    w = theWidth;
    h = theHeight;
  }


  public ControlP5 control() {
    return cp5;
  }
  
  ControlP5 cp5;
  Object parent;
}
// ------------------------------------------------------------------------------------------ //
//----------------------- GRAL SKETCH FUNCTIONS --------------------------------------------- //
// ----------------------------------------------------------------------------------------- //


// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 PSHAPEs
// -----------------------------------------------------------------------------------------------> shapes
PShape Rect, Line, Ellipse; // Same function as Processing but with a CAPITAL letter, huh? 
// * 
public void loadShapes() {
  // RECT, ELLIPSE AND LINE SHAPE
  Rect = createShape(RECT, 0, 0, 8, 8);
  Ellipse = createShape(ELLIPSE, 0, 0, 8, 8);

  // We disable all the styles ( so we can change it on the go, in real time)
  Rect.disableStyle(); 
  Ellipse.disableStyle();
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 STAGE/CAPTURE SIZE
public void iniciarStage(int anchoC, int altoC, int anchoS, int altoS) { 
  anchoCaptura = anchoC;
  altoCaptura = altoC;
  anchoDisplay = anchoS;
  altoDisplay = altoS;
  println(">> STAGE STARTED AT: "+anchoCaptura+"(c:"+anchoDisplay+") x "+ altoDisplay+"(c:"+altoC+")");
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 FRAMERATE VERIFICATION
// Tip by Kurt from Kamindustries
public void verificarFrameRate() {
  // 1. We put a rect
  fill(0);
  noStroke();
  rectMode(CORNERS);
  rect( width-40, 0+13, width, 0 );

  // 2. We put the actual frameRate
  // We are not going to display it every frame (for CPU efficiency), that's the reason of the 'if'
  if (frameCount%4==0) {
    fill(255);
    text(frameRate, width - 39, 11);
  }
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 
public void apagarTodos() {
  // 1. We turn them all off
  pixelation = false;
  pixelNation = false;
  hairs = false;
  stripe = false;
  fallingWater = false;
  polvox = false;
  meh = false;
  spore = false;
  sporeGrid = false;
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 
// \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 \u2593 Friction Calculation
public float calcularFriccion(float actual, float deseo, float coeficiente) {

  // 0. Local variables we'll use
  float intervalo = 0;
  float resultado = 0.0f;

  // 1. We check which value is bigger
  if (actual > deseo) intervalo = actual - deseo;
  if (deseo > actual) intervalo = deseo - actual;

  // 2. We calculate the friction operations
  intervalo /= coeficiente;

  // 3. We add all of the data
  if (intervalo > 0.001f) {
    if (actual > deseo) resultado = actual - intervalo;
    if (deseo > actual) resultado = actual + intervalo;
  } else {
    resultado = deseo;
  }

  // 4. We return the number
  return resultado;
}

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 MINI SAMPLE
Boolean miniCaptura = false;
public void drawMiniSample() {
  // 1. WE draw the captured
  image(capturada, 10, height-105, anchoCaptura/4, altoCaptura/4);
  // 2. We draw the rectangle
  rectMode(CORNER);
  noFill();
  stroke(0);
  strokeWeight(3);
  rect(10, height-105, anchoCaptura/4, altoCaptura/4);
  //stroke(255);
  // strokeWeight(1.3);
  //rect(10, height-105, anchoCaptura/4, altoCaptura/4);
  
}
// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

///////////////////////////////////////////////////////////////////////
// EASE IN
///////////////////////////////////////////////////////////////////////
public float EaseIn(float _value, float _target, float _speed){
  float x = _value;
  float d = _target - _value;
  x = d * _speed;
  return x;
}


///////////////////////////////////////////////////////////////////////
// U P D A T E   S H A D E R   V A R S
///////////////////////////////////////////////////////////////////////
float ease_speed = 0.01f;
public void updateShaderVariables(){


  // easing animation for variables
  ui_red += EaseIn(ui_red, ui_red_target, ease_speed);
  ui_green += EaseIn(ui_green, ui_green_target, ease_speed);
  ui_blue += EaseIn(ui_blue, ui_blue_target, ease_speed);
  ui_brightness += EaseIn(ui_brightness, ui_brightness_target, ease_speed);
  ui_contrast += EaseIn(ui_contrast, ui_contrast_target, ease_speed);
  ui_hue += EaseIn(ui_hue, ui_hue_target, ease_speed);
  ui_saturation += EaseIn(ui_saturation, ui_saturation_target, ease_speed);
  ui_sharpen += EaseIn(ui_sharpen, ui_sharpen_target, ease_speed);
  ui_niceContrast += EaseIn(ui_niceContrast, ui_niceContrast_target, ease_speed);

  // set shader vaiables 
  mainShader.set("ui_red", ui_red);
  mainShader.set("ui_green", ui_green);
  mainShader.set("ui_blue", ui_blue);
  mainShader.set("ui_brightness", ui_brightness);
  mainShader.set("ui_contrast", ui_contrast);
  mainShader.set("ui_hue", ui_hue);
  mainShader.set("ui_saturation", ui_saturation);
  mainShader.set("ui_sharpen", ui_sharpen);
  mainShader.set("ui_niceContrast", ui_niceContrast);
  // mainShader.set("ui_sortBlackVal", ui_sortBlackVal);
  // mainShader.set("ui_sortBrightVal", ui_sortBrightVal);
  // mainShader.set("ui_sortWhiteVal", ui_sortWhiteVal);
  mainShader.set("ui_party", ui_party);
  mainShader.set("ttime", PApplet.parseFloat(millis())*.0001f);

  update_sliders=false;
}

///////////////////////////////////////////////////////////////////////
// I N J E C T   S H A D E R
///////////////////////////////////////////////////////////////////////

public void injectShader(){
  // update shader with slider settings and turn it on
  updateShaderVariables();

  gl.beginDraw();
    gl.shader(mainShader);
    gl.image(capturada, 0, 0, anchoDisplay, altoDisplay);
  gl.endDraw();

  // send color corrected image to glitch functions
  toDisplay = gl.get();
}

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 
// ----------------------------------------------------------------------------------- //
//----------------------- INTERACTION ------------------------------------------------ //
// ----------------------------------------------------------------------------------- //


public void keyPressed() {

  // ----------------------------- G L O B A L ----------------------------------------------------
  if (key == 's') save( "CAPTURES/BITMAPS/LiveCinema_"+year()+""+month()+""+day()+"_"+hour()+""+minute()+""+second()+".png" );

  // ----------------------------- TURNING ON/OFF Stage
  if (key == '1') {
    apagarTodos();
    pixelation = true;
  }
  if (key == '2') {
    apagarTodos();
    pixelNation = true;
  }
  if (key == '3') {
    apagarTodos();
    hairs = true;
  }
  if (key == '4') {
    apagarTodos();
    stripe = true;
  }
  if (key == '5') {
    apagarTodos();
    fallingWater = true;
  }
  if (key == '6') {
    apagarTodos();
    polvox = true;
  }
  if (key == '0') {
    apagarTodos();
    meh = true;
  }

  if (key == '7') {
    apagarTodos();
    sporeGrid = true;
  }
  if (key == '8') {
    apagarTodos();
    spore = true;
  }
  // -----------------
  if (key == 'q') {
    cabellos = true;
    cruces = false;
    bubbles = false;
    // 
    saturationMode = true;
    hueMode = false;
    brightnessMode = false;
  }
  if (key == 'w') {
    cabellos = false;
    cruces = true;
    bubbles = false;
    //
    saturationMode = false;
    hueMode = true;
    brightnessMode = false;
  }
  if (key == 'e') {
    //
    cabellos = false;
    cruces = false;
    bubbles = true;
    //
    saturationMode = false;
    hueMode = false;
    brightnessMode = true;
  }
  if (key == 'o') {
    ocultar = !ocultar;
  }
  if (key == 'n') {
    negro = !negro;
  }
  if (key == 'm') {
    memoria = !memoria;
  }
  if (key == 'i') miniCaptura = !miniCaptura;
  if (key =='x') {
    // Prendemos de nuevo los surcos

    for (int i = 0; i < cantidadSurcos; i++) {
      surcoPrendido[i] = true;
    }
  }
}
// ----------------------------------------------------------------------------------- //
//----------------------- SCREEN CAPTURE --------------------------------------------- //
// ----------------------------------------------------------------------------------- //
/*
Thist tab deals with all of the Screen Capture functions.
 Taken from: https://github.com/onformative/ScreenCapturer/issues/2
 Screen Capturer developed by: Doeke Wartena
 */

// IMPORTANT VARIABLES:
// We import all of the Java functions we will use







Capture cam; // webcam object
PImage capturada; // This is where we'll sotre all captured data.

// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 START CAPTURE
// Starting screen capture ---------------------------------------------------------
public void iniciarScreenCapture() {

  // Print a a list of connected cameras
  String[] cameras = Capture.list();
  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(i + ": " + cameras[i]);
    }
    
    // cam = new Capture(this, cameras[0]); //CamTwist, the dSLR
    cam = new Capture(this, cameras[12]); //Webcam on my Mac Air
    cam.start();     
  }


  // 1. We start the blank image (recipient)
  capturada = createImage( anchoCaptura, altoCaptura, RGB );
  capturada = cam;

  // 2. We start the thread
  thread( "threadScreenCapture" );
}


// \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588 CAPTURE THREAD
boolean pausar_captura = false; // This will always be turned off
public void threadScreenCapture() {

  // 3. Feedback:
  println(">> ScreenCapture thread: ON");
  // Robot robot; // (should be in the java class

  while (!pausar_captura) {

    // Update at 25fps (1000 milliseconds/40 = 25)
    if (millis()%40==0) {
      cam.read();
      capturada = cam; // forward webcam to rest of code


      // NO MORE TRY AND CATCH SINCE WE ARENT SCREEN GRABBIN!!
      // try {
        
        // load image into capture object

        // capturada = new PImage(0, 0, anchoCaptura, altoCaptura);

        // robot = new Robot(); // We start the robot
        // We get the image (at last)!!!!
        // capturada = new PImage(robot.createScreenCapture(new Rectangle(0, trasladoY, anchoCaptura, altoCaptura)));
       // println(capturada.get(floor(random(capturada.width)),floor(random(capturada.height)))); // Random pixel data
      // }
      // catch (AWTException e) {
      // capturada = new PImage(null);
        // println(">> HOLY, FATAL ERROR: --->"+e);
      // }

    } // <--- if ends
  } // <---- while ends
} // <--- thread ends
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--hide-stop", "microcosm" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
