// ----------------------------------------------------------------------------------- //
//----------------------- SCREEN CAPTURE --------------------------------------------- //
// ----------------------------------------------------------------------------------- //
/*
Thist tab deals with all of the Screen Capture functions.
 Taken from: https://github.com/onformative/ScreenCapturer/issues/2
 Screen Capturer developed by: Doeke Wartena
 */

// IMPORTANT VARIABLES:
// We import all of the Java functions we will use
import processing.video.*;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.AWTException;

Capture cam; // webcam object
PImage capturada; // This is where we'll sotre all captured data.

// ████████████████████████████████████████████████████████████████████████████████████████ START CAPTURE
// Starting screen capture ---------------------------------------------------------
void iniciarScreenCapture() {

  // get a a list of connected cameras
  String[] cameras = Capture.list();
  
  if (cameras.length == 0) {
    println("There are no cameras available for capture.");
    exit();
  } else {
    println("Available cameras:");
    for (int i = 0; i < cameras.length; i++) {
      println(i + ": " + cameras[i]);
    }
    
    // use the first camera from the list
    // cam = new Capture(this, cameras[0]);
    cam = new Capture(this, cameras[12]);
    cam.start();     
  }


  // 1. We start the blank image (recipient)
  capturada = createImage( anchoCaptura, altoCaptura, RGB );
  capturada = cam;
  // 2. We start the thread
  thread( "threadScreenCapture" );
}


// ████████████████████████████████████████████████████████████████████████████████████████ CAPTURE THREAD
boolean pausar_captura = false; // This will always be turned off
void threadScreenCapture() {
  // 3. Feedback:
  println(">> ScreenCapture thread: ON");

  // W1 create this object that retrieves constatly information
  // Robot robot; // (should be in the java class

  // This loop will request a screen capture image
  while (!pausar_captura) {

    // We insert some modulo magic, so it won't crash
    if (millis()%40==0) {
      // try {
        
        // load image into capture object
        // if (cam.available() == true) {
          cam.read();
        // }
        capturada = cam; // forward webcam to rest of code
        // capturada = new PImage(0, 0, anchoCaptura, altoCaptura);

        // robot = new Robot(); // We start the robot
        // We get the image (at last)!!!!
        // capturada = new PImage(robot.createScreenCapture(new Rectangle(0, trasladoY, anchoCaptura, altoCaptura)));
       // println(capturada.get(floor(random(capturada.width)),floor(random(capturada.height)))); // Random pixel data
      // }
      // catch (AWTException e) {
      // capturada = new PImage(null);
        // println(">> HOLY, FATAL ERROR: --->"+e);
      // }
    } // <--- if ends
  } // <---- while ends
} // <--- thread ends
