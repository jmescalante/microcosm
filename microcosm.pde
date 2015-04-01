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


// -------------------------------------------------------------------------------------------
void setup() {
  // 1. Size operations
  // iniciarStage(640, 360, 1280, 720); // All captures and sizes operations are set up
  //  iniciarStage(640, 360, 640, 360); // All captures and sizes operations are set up
  iniciarStage(1280, 720, 1280, 720); 
  size(anchoDisplay, altoDisplay, P2D);
  /*NOTES:
   Captured: 720p/2 Render: 720p(FPS=  almost 60 fps  )
   */
  // 2. We start the screen capture (using threading) operations
  iniciarScreenCapture();

  // 3. We load the PShapes for efficiency
  loadShapes();
}
// -------------------------------------------------------------------------------------------
void draw() {
  if (!memoria)  background(0);
  // 1. We show the captured image
  //capturada.filter(THRESHOLD, 0.4);
  // image(capturada, 0, 0, anchoDisplay, altoDisplay);

  // 2. Live Cinema stages -->
  if (pixelation) pixelationShow();
  if (pixelNation) pixelNationShow();
  if (hairs) hairsShow();
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
