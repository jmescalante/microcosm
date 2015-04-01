// ----------------------------------------------------------------------------------- //
//----------------------- INTERACTION ------------------------------------------------ //
// ----------------------------------------------------------------------------------- //


void keyPressed() {

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
