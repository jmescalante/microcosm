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
  // image(capturada, 10, height-105, anchoCaptura/4, altoCaptura/4);
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
// EASE IN
///////////////////////////////////////////////////////////////////////
float EaseIn(float _value, float _target, float _speed){
  float x = _value;
  float d = _target - _value;
  x = d * _speed;
  return x;
}


///////////////////////////////////////////////////////////////////////
// U P D A T E   S H A D E R   V A R S
///////////////////////////////////////////////////////////////////////
float ease_speed = 0.01;
void updateShaderVariables(){

  if (update_shader != 0) {
    if (frame_num % 48 == 0){
      mainShader = loadShader("shader.frag");
    }
  }

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
  mainShader.set("ttime", float(millis())*.0001);

  update_sliders=false;
}

///////////////////////////////////////////////////////////////////////
// I N J E C T   S H A D E R
///////////////////////////////////////////////////////////////////////

void injectShader(){
  // update shader with slider settings and turn it on
  updateShaderVariables();
  // gl.beginDraw();
  //   gl.shader(mainShader);
  //   gl.image(capturada, anchoDisplay/4, 0, anchoDisplay/2, altoDisplay); // image, x position, y position, width, height
  //   gl.shader(mirrorShader);
  //   gl.image(gl.get(), 0, 0, anchoDisplay, altoDisplay);
  // gl.endDraw();
  gl.beginDraw();
    gl.shader(mainShader);
    gl.image(toResize, 0, 0, anchoDisplay, altoDisplay); // image, x position, y position, width, height
    // gl.shader(mirrorShader);
    // gl.image(gl.get(), 0, 0, anchoDisplay, altoDisplay);
  gl.endDraw();

  // gl_mirror.beginDraw();
  //   gl_mirror.shader(mirrorShader);
  //   gl_mirror.image(gl.get(), 0, 0, anchoDisplay, altoDisplay);
  // gl_mirror.endDraw();

  // toResize = gl.get();
  // toResize.resize(0, 1060);

  // send color corrected image to glitch functions
  toDisplay = gl.get();
  // toDisplay = foo_test;

  // toResize.resize(capture_width, capture_height);
}

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 
