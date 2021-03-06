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
PShader mirrorShader;
PGraphics gl;
// PGraphics gl_scale;
PImage toDisplay;
PImage toResize;
// PImage dualDisplay;
// PImage foo_test;

int capture_height = 1056;
int capture_width = 704;
int frame_num = 0;

boolean sketchFullScreen() {
  return true;
}

// -------------------------------------------------------------------------------------------
void setup() {
  // 1. Size operations
  // iniciarStage(640, 360, 1280, 720); // All captures and sizes operations are set up
  //  iniciarStage(640, 360, 640, 360); // All captures and sizes operations are set up
  // iniciarStage(1280, 720, 1280, 720); 
  iniciarStage(1056, 704, 1056, 704); //previous working one 5/22
  // iniciarStage(2800, 1060, 2800, 1060); 
  size(anchoDisplay, altoDisplay, P2D);


  // 3. We load the PShapes for efficiency
  loadShapes();

  gl = createGraphics(anchoDisplay, altoDisplay, P2D);
  // gl_scale = createGraphics(anchoDisplay, altoDisplay, P2D);
  toResize = createImage(capture_width, capture_height, RGB);
  toDisplay = createImage(anchoDisplay, altoDisplay, RGB);
  // foo_test = createImage(anchoDisplay, altoDisplay, RGB);

  // frame.setResizable(true);
  // cc_toggle = 0; // rgb cc mode
  cf = addControlFrame("src window ctrl", 200,350);
  mainShader = loadShader("shader.frag");
  mirrorShader = loadShader("mirror.frag");

  /*NOTES:
   Captured: 720p/2 Render: 720p(FPS=  almost 60 fps  )
   */
  // 2. We start the screen capture (using threading) operations
  iniciarScreenCapture();

}
// -------------------------------------------------------------------------------------------
void draw() {

  if (!memoria)  background(0);

  // Apply shader magic and output the color corrected toDisplay PImage!!!
  injectShader();

  // if (frame_num % 48 == 0){
  //   mirrorShader = loadShader("mirror.frag");
  // }
  // shader(mirrorShader);


  // Live Cinema stages -->
  // Need the first condition to wait until capture image has data to play with
  if (toDisplay.width > 0 && toDisplay.height > 0){ 
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

    frame_num++;
  }

}
