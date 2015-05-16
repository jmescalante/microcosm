// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// ██████████████████████████████████████████████████████████████████████████████████████████ spore
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
Boolean sporeGridStart() {


  return false;
}
// ----------------------------------------------------------------------------------------------------------------
void sporeGridShow() {
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

      int xc = int(map( x, 0, width, 0, anchoCaptura ));
      int yc = int(map( y, 0, height, 0, altoCaptura ));

      // color c = capturada.get( xc, yc );
      color c = toDisplay.get( xc, yc );
      float bright_val = red(c)+green(c)+blue(c);
      stroke(red(c), green(c), blue(c), bright_val*2.);
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

    int xc = int(map( x, 0, width, 0, anchoCaptura ));
    int yc = int(map( y, 0, height, 0, altoCaptura ));

    // color c = capturada.get( xc, yc );
    color c = toDisplay.get( xc, yc );

    stroke(0);
    stroke(c);
    strokeWeight(3);
    strokeCap(SQUARE);
    point(x, y);
  }
}

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ spore
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
Boolean sporeStart() {
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
void sporeShow() {
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
      int xc = int(map( xLedsO[it], 0, width, 0, anchoCaptura-1 ));
      int yc = int(map( yLedsO[it], 0, height, 0, altoCaptura-1 ));

      // c. We get one pixel
      float pixel = 0.0;
      // if (saturationMode) pixel = brightness(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (saturationMode) pixel = brightness(toDisplay.pixels[ int(yc*toDisplay.width+xc) ]);
      // if (brightnessMode) pixel = brightness(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (brightnessMode) pixel = brightness(toDisplay.pixels[ int(yc*toDisplay.width+xc) ]);
      // if (hueMode) pixel = hue(capturadaT.pixels[ int(yc*capturadaT.width+xc) ]);
      if (hueMode) pixel = hue(toDisplay.pixels[ int(yc*toDisplay.width+xc) ]);

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
      int xc = int(map( xLedsO[it], 0, width, 0, anchoCaptura ));
      int yc = int(map( yLedsO[it], 0, height, 0, altoCaptura ));
      // color c = capturada.get( xc, yc );
      color c = toDisplay.get( xc, yc );

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
