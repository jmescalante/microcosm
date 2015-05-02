// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ fallingWater
//SWITCHES
Boolean fallingWater = false;
// WaterFall just takes the pixels and distorts them to the bottom
int densidadWaterFall = 140; // How many pixel density we will use
float [] yWater, speedWater, sentidoWater;
float speedWaterMin = 1.2;
float speedWaterMax = 1.20000000001;
// *
Boolean fallingWater1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
Boolean fallingWaterStart() {
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
void fallingWaterShow() {
  // 1. We ask if it is the first time
  if (fallingWater1AVez) fallingWater1AVez = fallingWaterStart();

  // Apply the shader!!
  // injectShader();

  // 2. We show the image
  image(capturada, 0, 0, anchoDisplay, altoDisplay);
  
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
    color c = capturada.get(deseoX, deseoY);

    // f. We draw something upside down
    noStroke();
    fill(255, 255, 255, 230);
    fill(c);
    fill(red(c), green(c), blue(c), 230);
    if (random(10)>9.9) fill(c);
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


// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ polvox
//SWITCHES
Boolean polvox = false;
Boolean ocultar = true;
Dust [] polvos; // All of the Dust particles
int cantidadSurcos = 155; // 55 seems to work, 255 fully populated
float grosorMax = 3.81;
float [] xLinea, yLinea, velLinea, alphaLinea, velAlphaOla, velOlaLinea, sentidoLinea, anguloLinea;
Boolean [] surcoPrendido;
float [] angulosPosiblesLinea = {
  90+45, 90, 45
};
float velSurcosMin = 3.52;
float velSurcosMax = 26;
float alphaInicioMin = 150;
float alphaInicioMax = 250;
float velAlphaMin = 5.51;
float velAlphaMax = 22; // 2 workds
float velLineaMin = 1.5; // 1.5 works
float velLineaMax = 9;//7 original

// *
Boolean polvox1AVez = true;
// ----------------------------------------------------------------------------------------------------------------
// All array functions needed before the first run
Boolean polvoxStart() {
  
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
void polvoxShow() {
  // 1. We ask if it is the first time
  if (polvox1AVez) polvox1AVez = polvoxStart();
  
  // Apply the shader!!
  // injectShader();

  // 2. We show the image
  if(ocultar) image(capturada, 0, 0, anchoDisplay, altoDisplay);
  
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
    if (!surcoPrendido[i] && random(100)> 99.9) surcoPrendido[i] = true;

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
void crearDust(float x, float y, float alphaInicio, float velAlpha, float velX, float angulo, float grosor) {
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
  color c;
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
    this.c = capturada.get( int(x), int(y) );
   // this.c = color(random(255), random(255), 0);
    this.alpha = 255;
    this.alpha= alphaInicio;
    this.vivo = true;
    this.grosor = grosor;
    this.velAlpha = velAlpha;
    this.velX = velX;
  }

  // Getters/Setters------------------------------------------------
  Boolean getVivo() {
    return this.vivo;
  }

  // Methods----------------------------------------------------------
  void mostrar() {

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
    c = capturada.get( deseoX, deseoY) ;
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
