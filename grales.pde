// ------------------------------------------------------------------------------------------ //
//----------------------- GRAL SKETCH FUNCTIONS --------------------------------------------- //
// ----------------------------------------------------------------------------------------- //


// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ PSHAPEs
// -----------------------------------------------------------------------------------------------> shapes
PShape Rect, Line, Ellipse; // Same function as Processing but with a CAPITAL letter, huh? 
// * 
void loadShapes() {
  // RECT, ELLIPSE AND LINE SHAPE
  Rect = createShape(RECT, 0, 0, 8, 8);
  Ellipse = createShape(ELLIPSE, 0, 0, 8, 8);

  // We disable all the styles ( so we can change it on the go, in real time)
  Rect.disableStyle(); 
  Ellipse.disableStyle();
}
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ STAGE/CAPTURE SIZE
void iniciarStage(int anchoC, int altoC, int anchoS, int altoS) { 
  anchoCaptura = anchoC;
  altoCaptura = altoC;
  anchoDisplay = anchoS;
  altoDisplay = altoS;
  println(">> STAGE STARTED AT: "+anchoCaptura+"(c:"+anchoDisplay+") x "+ altoDisplay+"(c:"+altoC+")");
}
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ FRAMERATE VERIFICATION
// Tip by Kurt from Kamindustries
void verificarFrameRate() {
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
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
void apagarTodos() {
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
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
// ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ Friction Calculation
float calcularFriccion(float actual, float deseo, float coeficiente) {

  // 0. Local variables we'll use
  float intervalo = 0;
  float resultado = 0.0;

  // 1. We check which value is bigger
  if (actual > deseo) intervalo = actual - deseo;
  if (deseo > actual) intervalo = deseo - actual;

  // 2. We calculate the friction operations
  intervalo /= coeficiente;

  // 3. We add all of the data
  if (intervalo > 0.001) {
    if (actual > deseo) resultado = actual - intervalo;
    if (deseo > actual) resultado = actual + intervalo;
  } else {
    resultado = deseo;
  }

  // 4. We return the number
  return resultado;
}

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ MINI SAMPLE
Boolean miniCaptura = false;
void drawMiniSample() {
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
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
///////////////////////////////////////////////////////////////////////
// U P D A T E   S H A D E R   V A R S
///////////////////////////////////////////////////////////////////////
void updateShaderVariables(){
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
    mainShader.set("cc_mode",   cc_toggle);
    mainShader.set("brightness_mode",   fx_toggle[0]);
    mainShader.set("contrast_mode",     fx_toggle[1]);
    mainShader.set("hue_mode",          fx_toggle[2]);
    mainShader.set("saturation_mode",   fx_toggle[3]);
    mainShader.set("sharpening_mode",   fx_toggle[4]);
    mainShader.set("niceContrast_mode", fx_toggle[5]);
    mainShader.set("party_mode",   party_toggle);
    mainShader.set("ttime", float(millis())*.0001);

    update_sliders=false;
}
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
///////////////////////////////////////////////////////////////////////
// I N J E C T   S H A D E R
///////////////////////////////////////////////////////////////////////
// Apply the shader to the PImage
///////////////////////////////////////////////////////////////////////

void injectShader(){
  // update shader with slider settings
  updateShaderVariables();
  // apply the shader
  shader(mainShader);
}

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
