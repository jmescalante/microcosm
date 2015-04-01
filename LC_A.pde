// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ PIXELATION 59 fps
//SWITCHES
Boolean pixelation = false;
float pixelationResX = 30; // The resolution from which to scan and search for points
float pixelationResY = 15;
Boolean memoria = false;
// *
Boolean pixelation1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
Boolean pixelationStart() {

  return false;
}
// ----------------------------------------------------------------------------------------------------------------

void pixelationShow() {
  // 0. We don't place the image, bear in mind this
  // 1. We ask if it is the first time
  if (pixelation1AVez) pixelation1AVez = pixelationStart();

  // 2. We will apply some threshold to the image
  PImage pic = capturada;
  //pic.filter(THRESHOLD, map(mouseX, 0, width, 0, 1));
  pic.filter(THRESHOLD, random(0.6, 0.8));
  //capturada.filter(GRAY);


  // 3. We will cycle through each pixel
  for (int i = 0; i < pixelationResX; i++) {
    for (int j = 0; j < pixelationResY; j++) {
      // a. We get the position
      int x = floor(map( i, 0, pixelationResX, 0, anchoCaptura ));
      int y = floor(map( j, 0, pixelationResY, 0, altoCaptura));
      // b. we ask if it is white
      if ( pic.get( x, y ) > -1.1 ) {
        fill(capturada.get( x, y));
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
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ pixelNation
//SWITCHES

Boolean pixelNation = false;
float pixelNationResX = 24; // The resolution from which to scan and search for points // 20 works
float pixelNationResY = 10*2; // 10 works
float curtainRuidoYMin = 0.0002;
float curtainRuidoYMax = 0.052;
float curtainVelXMax = 12.59;
float curtainVelXMin = 4.59;
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
void recalcularAlphas() {
  for (int i = 0; i < velAlpha.length; i++) {
    //velAlpha[i] = random(0.005/15, 0.005*2);
  }
}
Boolean pixelNationStart() {
  println(">> PIXELNATION STARTED!");

  // 1. We create a temporary Array
  Boolean [] pixelNationPrendidosT = new Boolean[int(pixelNationResX*pixelNationResY)];
  float [] alphaPorcentajesT = new float[int(pixelNationResX*pixelNationResY)];
  float [] velAlphaT = new float[int(pixelNationResX*pixelNationResY)];
  //2. We fill the Arrays
  for (int i = 0; i < pixelNationPrendidosT.length; i++) {
    pixelNationPrendidosT[i] = false;
    alphaPorcentajesT[i] = 0;
    velAlphaT[i] = 0.005;
  }
  //3. Igualamos los arreglos
  pixelNationPrendidos = pixelNationPrendidosT;
  alphaPorcentajes = alphaPorcentajesT;
  velAlpha = velAlphaT;

  // 4. We create some noise
  curtainVarY = 0.0;
  curtainRuidoY = random(curtainRuidoYMin, curtainRuidoYMax);

  //5. We set an original center in Y
  curtainCenterYD = random(height);

  // XX. We set the turned on to false
  return false;
}
// ----------------------------------------------------------------------------------------------------------------
void curtainOperations() {
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
    if (random(100)>99.9) curtainPrendida = true;
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

void pixelNationShow() {
  // 1. We ask if it is the first time
  if (pixelNation1AVez) pixelNation1AVez = pixelNationStart();

  // 0. We place the image
  image(capturada, 0, 0, anchoDisplay, altoDisplay);

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
      color c = capturada.get( x, y);
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
      int limiteY = int(map( mouseY, 0, height, 0, pixelNationResY )); 
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

      shape( Rect, x, y, ancho+1, alto+1.7 );

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


// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ 

// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ MEH
//SWITCHES
Boolean meh = false;

// *
Boolean meh1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
Boolean mehStart() {

  return false;
}
// ----------------------------------------------------------------------------------------------------------------
void mehShow() {
  // 1. We ask if it is the first time
  if (meh1AVez) meh1AVez = mehStart();
  
  //2. We show the image
  image(capturada, 0, 0, anchoDisplay, altoDisplay);
}
