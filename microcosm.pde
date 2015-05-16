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
void setup() {
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
void draw() {

  // // testing not grabbing image in separate thread
  // if (cam.available() == true) {
  //   cam.read();
  // }
  // capturada = cam; // forward webcam to rest of code

  
  if (!memoria)  background(0);
  // 1. We show the captured image
  //capturada.filter(THRESHOLD, 0.4);
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);

  // Apply shader magic and output the main toDisplay PImage!!!
  injectShader();

  // 2. Live Cinema stages -->
  //    Need this condition to wait until capture is working
  if (capturada.width > 0 && capturada.height > 0){ 
    if (pixelation) pixelationShow();
    if (pixelNation) pixelNationShow();
    if (hairs) hairsShow(); // suuuper slow on my lil machine
    if (stripe) stripeShow();
    if (fallingWater) fallingWaterShow();
    if (polvox) polvoxShow();
    if (meh) mehShow();
    if (spore) sporeShow();
    if (sporeGrid) sporeGridShow();
    // X. We test the performance
    if (kamindustries) verificarFrameRate(); // To test performance

    // 3. Mini Sample
    if (miniCaptura) drawMiniSample();
  }

  resetShader();

}
