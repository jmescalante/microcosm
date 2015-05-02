// -------------------------------------------------------------------------------------------------------- //
//----------------------- Subtle video effects for live cinema -------------------------------------------- //
// -------------------------------------------------------------------------------------------------------- //
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ hairs
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
Boolean hairsStart() {
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
    hairsXT[i] = 0.0;
    hairsYT[i] = 0.0;
    hairsTamanoT[i] = 0;
    hairsTamanoDT[i] = random(hairsSizeMin, hairsSizeMax);
    hairsAlphaT[i] = 255;
    hairsVelAlphaT[i] = random(hairsVelAlphaMin, hairsVelAlphaMax);
    hairsPrendidoT[i] = false;
    hairsAngulosT[i] = random(360);
    despXT[i] = 0.0;
    despYT[i] = 0.0;
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
void hairsShow() {
  // 1. We ask if it is the first time
  if (hairs1AVez) hairs1AVez = hairsStart();

  // 2. We show the captured pic
  image(capturada, 0, 0, width, height);

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
        despX[i] = 0.0;
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
      color c = capturada.get( floor(hairsX[i]), floor(hairsY[i]) );
      fill(red(c), green(c), blue(c), hairsAlpha[i]);
      stroke(red(c), green(c), blue(c), hairsAlpha[i]);
      //stroke(255, 0, 0, hairsAlpha[i]);

      // Now the different disualizations for this scenario
      // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓  cabellos
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
      // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓  slit scan
      if (cruces) {
        noStroke();
        shapeMode(CORNERS);
        float tamanoAlto = map( hairsTamano[i], 0, hairsTamanoD[i], hairsY[i], height );
        shape(Rect, hairsX[i], hairsY[i], hairsX[i]+0.5, tamanoAlto);
        // Let's place the cross
        pushMatrix();
        translate( hairsX[i], hairsY[i]);
        rotate(radians(45));
        //shape(Rect,0, 0, 0+2, tamanoAlto*2); //diagonal
        shapeMode(CENTER);
        float largo = map( hairsTamano[i], 0, hairsTamanoD[i], 3, map( hairsTamanoD[i], hairsSizeMin, hairsSizeMax, 4, 20  ) );
        shape( Rect, 0, 0, 2, largo );
        rotate(radians(45+45));
        if (random(10)>2.7)shape( Rect, 0, 0, 2, largo );
        popMatrix();
      }
      // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓  slit scan
      if (bubbles) {
        despX[i] += 0.5*sentX[i];
        despY[i] += 0.5*sentY[i];
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
int [] asignarNuevaPosicion() {
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
    PImage capturadaT = capturada;
    capturadaT.loadPixels();

    // c. We get one pixel
    float pixel = brightness(capturadaT.pixels[ int(yAzar*capturadaT.width+xAzar) ]);

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
    capturadaT.updatePixels();
  }
  // 4. Values are returnes
  return posiciones;
}


// --------------------------------------------
// █████████████████████████████████████████████████████████████████████████████████████████████████████████████████ stripe
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
Boolean stripeStart() {
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
void stripeShow() {
  // 1. We ask if it is the first time
  if (stripe1AVez) stripe1AVez = stripeStart();

  // Apply the shader!!
  injectShader();

  // 2. We show the captured pic
  image(capturada, 0, 0, anchoDisplay, altoDisplay);

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
  // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ 
  // Propiedades
  float x1, x2, y1, y2; // The square
  float ancho, alto;
  int cantidad, sentido; // How many tiritas?
  Boolean empezarArriba, modoApagando; // Where this will start
  Boolean [] vivas; // M'as radical o no
  Boolean [] prendidas; // Para moverse
  Boolean [] yaNaci, yaMori, puedoMorir; // To see if they must be grown
  float [] tamanoD, tamano; // So they grow with friciton
  color c;
  // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ 
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
      tamano[i] = 0.0; // They start in zero (so they can grow)
    }
    this.empezarArriba = empezarArriba;
    this.sentido = -1;
    if (empezarArriba) this.sentido = 1;
    this.c = color(255, 0, 0);
    this.modoApagando = false;
  }
  // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ 
  //METHODS
  void mostrar() {
    // We will display the stripes if at least one is alive
    if (!todasMuertas()) {
      mostrarStripes();
    } else {
      //println("TODAS ESTAN MUERTAS!");
    }
  }
  // -----------------------------------
  void mostrarStripes() {
    // 1. Test rect
    //fill(0, 0, 0, 160);
    rectMode(CORNERS);
    //rect(x1, y1, x2, y2);

    // 2. We'll tell them if they have not been born yet, born!
    Boolean yaEncendi = true;
    if (!todasNacidas()) {
      for (int i = 0; i < cantidad && yaEncendi; i++) {
        if (!yaNaci[i] && random(100)>95.8) {
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
        c = capturada.get(floor(deseoX), floor(deseoY));

        // 3. We set the display properties
        noStroke();
        fill(c);

        // 4. We put the 1st shape, finally!
        shapeMode(CORNERS);
        shape(Rect, x- anchoStripe, y, x +anchoStripe, y+(tamano[i]*sentido));

        // 5. WE put the 2nd Shape
        deseoY = map( y-(tamano[i]*sentido), 0, altoDisplay, 0, altoCaptura);
        c = capturada.get(floor(deseoX), floor(deseoY));
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
        if (!puedoMorir[i] && random(100)>95.8) {
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
  Boolean todasNacidas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!yaNaci[i]) todas = false;
    }
    return todas;
  }
  // -----------------------------------------------------
  Boolean todasPrendidas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!prendidas[i]) todas = false;
    }
    return todas;
  }
  // -----------------------------------------------------
  Boolean todasMuertas() {
    Boolean todas = true;
    for (int i = 0; i < cantidad; i++) {
      if (!yaMori[i]) todas = false;
    }
    return todas;
  }
  // ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓ ▓
} // <-- Stripe class ends
