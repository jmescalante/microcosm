import controlP5.*;
private ControlP5 cp5;

ControlFrame cf;

int capture_window_posX = 0;
int capture_window_posY = 0;
int capture_window_width = 1150;
int capture_window_height = 480;
int pos_window_x_start = 0;
int pos_window_y_start = 0;
boolean update_capture_window = true;
boolean update_sliders = true;

float ui_red = 0.;
float ui_green = 0.;
float ui_blue = 0.;
float ui_brightness = 0.;
float ui_contrast = 0.;
float ui_hue = 0.;
float ui_saturation = 0.;
float ui_sharpen = 0.;
float ui_niceContrast = 0.;
int ui_sortBlackVal = -10000000;
int ui_sortBrightVal = 60;
int ui_sortWhiteVal = -6000000;
float ui_party = 0.;

float ui_red_target = 0.;
float ui_green_target = 0.;
float ui_blue_target = 0.;
float ui_brightness_target = 0.;
float ui_contrast_target = 0.;
float ui_hue_target = 0.;
float ui_saturation_target = 0.;
float ui_sharpen_target = 0.;
float ui_niceContrast_target = 0.;
int ui_sortBlackVal_target = -10000000;
int ui_sortBrightVal_target = 60;
int ui_sortWhiteVal_target = -6000000;
float ui_party_target = 0.;

int update_shader = 0;

// CheckBox checkbox_cc;
CheckBox checkbox_fx;
CheckBox checkbox_sort;
CheckBox checkbox_party;
CheckBox checkbox_updateShader;
int cp5_mx = 30;
int cp5_my = 10;

// int cc_toggle; 
// int[] fx_toggle; 
int[] sort_toggle;
boolean sort_masterToggle = false;
int party_toggle;

// S O R T I N G
//MODE:
//0 -> black
//1 -> bright
//2 -> white
int sort_mode = 0;
int loops = 1;
int blackValue = -10000000;
int brigthnessValue = 60;
int whiteValue = -6000000;
int row = 0;
int column = 0;
boolean saved = false;

PFont font1 = createFont("monaco", 10, true);


///////////////////////////////////////////////////////////////////////
// C O N T R O L   F R A M E
///////////////////////////////////////////////////////////////////////
// Sliders n stuff
///////////////////////////////////////////////////////////////////////

ControlFrame addControlFrame(String theName, int theWidth, int theHeight) {
  Frame f = new Frame(theName);
  ControlFrame p = new ControlFrame(this, theWidth, theHeight);
  f.add(p);
  p.init();
  f.setTitle(theName);
  f.setSize(p.w, p.h);
  f.setLocation(100, 100);
  f.setResizable(false);
  f.setVisible(true);
  return p;
}

// the ControlFrame class extends PApplet, so we 
// are creating a new processing applet inside a
// new frame with a controlP5 object loaded
public class ControlFrame extends PApplet {

  int w, h;

  int cf_bgColor = 100;
  
  public void setup() {
    size(w, h);
    frameRate(25);
    cp5 = new ControlP5(this);

    ///////////////////////////////////////////////////////////////////////
    // T O G G L E S
    ///////////////////////////////////////////////////////////////////////
    checkbox_sort = cp5.addCheckBox("checkbox_sort")
                      .setPosition(cp5_mx-14-10, 180+cp5_my)
                      .addItem("sort_black_mode", 0)
                      .addItem("sort_value_mode", 0)
                      .addItem("sort_white_mode", 0)
                      .setItemsPerRow(1)
                      .hideLabels()
                      ;
    checkbox_party = cp5.addCheckBox("checkbox_party")
                      .setPosition(cp5_mx-14-10, 250+cp5_my)
                      .addItem("party_mode", 0)
                      .setItemsPerRow(1)
                      .hideLabels()
                      ;
    checkbox_updateShader = cp5.addCheckBox("checkbox_updateShader")
                      .setPosition(cp5_mx-14-10, 220+cp5_my)
                      .addItem("update shader", 0)
                      .setItemsPerRow(1)
                      // .hideLabels()
                      ;                    

    ///////////////////////////////////////////////////////////////////////
    // R E S E T   B U T T O N S
    ///////////////////////////////////////////////////////////////////////
    cp5.addButton("red_reset") 
        .setPosition(int(cp5_mx-12),70+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("green_reset")
        .setPosition(int(cp5_mx-12),80+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("blue_reset")
        .setPosition(int(cp5_mx-12),90+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("brightness_reset")
        .setPosition(int(cp5_mx-12),110+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("contrast_reset")
        .setPosition(int(cp5_mx-12),120+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("saturation_reset")
        .setPosition(int(cp5_mx-12),130+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("hue_reset")
        .setPosition(int(cp5_mx-12),140+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sharpen_reset")
        .setPosition(int(cp5_mx-12),150+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("niceContrast_reset")
        .setPosition(int(cp5_mx-12),160+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_black_reset")
        .setPosition(int(cp5_mx-12),180+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_value_reset")
        .setPosition(int(cp5_mx-12),190+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("sort_white_reset")
        .setPosition(int(cp5_mx-12),200+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;
    cp5.addButton("party_reset")
        .setPosition(int(cp5_mx-12),250+cp5_my)
        .setSize(9,9)
        .setLabelVisible(false)
        ;

    ///////////////////////////////////////////////////////////////////////
    // S L I D E R S
    ///////////////////////////////////////////////////////////////////////
    cp5.addSlider("red")
      .setPosition(cp5_mx,70+cp5_my)
      .setRange(-1.0, 1.0)
      .setValue(0.0)
      ;
    cp5.addSlider("green")
      .setPosition(cp5_mx,80+cp5_my)
      .setRange(-1.0, 1.0)
      .setValue(0.0)
      ;
    cp5.addSlider("blue")
      .setPosition(cp5_mx,90+cp5_my)
      .setRange(-1.0, 1.0)
      .setValue(0.0)
      ;
    cp5.addSlider("brightness")
      .setPosition(cp5_mx,110+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("contrast")
      .setPosition(cp5_mx,120+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("saturation")
      .setPosition(cp5_mx,130+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("hue")
      .setPosition(cp5_mx,140+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("sharpen")
      .setPosition(cp5_mx,150+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("niceContrast")
      .setPosition(cp5_mx,160+cp5_my)
      .setRange(-100, 100.0)
      .setValue(0.0)
      ;
    cp5.addSlider("sort: black")
      .setPosition(cp5_mx,180+cp5_my)
      .setRange(-20000000, -6000000)
      .setValue(-10000000)
      ;
    cp5.addSlider("sort: value")
      .setPosition(cp5_mx,190+cp5_my)
      .setRange(0.001, 255)
      .setValue(60)
      ;
    cp5.addSlider("sort: white")
      .setPosition(cp5_mx,200+cp5_my)
      .setRange(-10000000, 0)
      .setValue(-6000000)
      ;
    cp5.addSlider("party")
      .setPosition(cp5_mx,250+cp5_my)
      .setRange(0., 1.)
      .setValue(0.0)
      ;


  }

  void controlEvent(ControlEvent theEvent) {

    ///////////////////////////////////////////////////////////////////////
    // A D J U S T M E N T   S L I D E R S
    ///////////////////////////////////////////////////////////////////////
    if (theEvent.isFrom(cp5.getController("red"))) {
      ui_red_target = (theEvent.getController().getValue());
      update_sliders=true;
    }
    if (theEvent.isFrom(cp5.getController("green"))) {
      ui_green_target = (theEvent.getController().getValue());
    }
    if (theEvent.isFrom(cp5.getController("blue"))) {
      ui_blue_target = (theEvent.getController().getValue());
    } 
    if (theEvent.isFrom(cp5.getController("brightness"))) {
      float b = theEvent.getController().getValue();
      ui_brightness_target = b / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("contrast"))) {
      float c = theEvent.getController().getValue();
      ui_contrast_target = c / 100.0;
      // if (fx_toggle[1]==0) ui_contrast = pow((100 + c) / 100, 2.0);
      // else if (fx_toggle[1]==1) ui_contrast = c / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("hue"))) {
      float b = theEvent.getController().getValue();
      ui_hue_target = b / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("saturation"))) {
      float b = theEvent.getController().getValue();
      ui_saturation_target = b / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("sharpen"))) {
      float b = theEvent.getController().getValue();
      ui_sharpen_target = b / 100.0;
    }
    if (theEvent.isFrom(cp5.getController("niceContrast"))) {
      float b = theEvent.getController().getValue();
      ui_niceContrast_target = b / 100.0;
    }

    if (theEvent.isFrom(cp5.getController("sort: black"))) {
      ui_sortBlackVal = int((theEvent.getController().getValue()));
    }
    if (theEvent.isFrom(cp5.getController("sort: value"))) {
      ui_sortBrightVal = int((theEvent.getController().getValue()));
    }
    if (theEvent.isFrom(cp5.getController("sort: white"))) {
      ui_sortWhiteVal = int((theEvent.getController().getValue()));
    }

    if (theEvent.isFrom(cp5.getController("party"))) {
      float b = theEvent.getController().getValue();
      ui_party = b / 100.0;
    }

    ///////////////////////////////////////////////////////////////////////
    // R E S E T   B U T T O N S
    ///////////////////////////////////////////////////////////////////////
    if (theEvent.isFrom(cp5.getController("red_reset"))) {
      cp5.getController("red").setValue(0.0);
      ui_red_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("green_reset"))) {
      cp5.getController("green").setValue(0.0);
      ui_green_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("blue_reset"))) {
      cp5.getController("blue").setValue(0.0);
      ui_blue_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("brightness_reset"))) {
      cp5.getController("brightness").setValue(0.0);
      ui_brightness_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("contrast_reset"))) {
      cp5.getController("contrast").setValue(0.0);
      ui_contrast_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("hue_reset"))) {
      cp5.getController("hue").setValue(0.0);
      ui_hue_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("saturation_reset"))) {
      cp5.getController("saturation").setValue(0.0);
      ui_saturation_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("sharpen_reset"))) {
      cp5.getController("sharpen").setValue(0.0);
      ui_sharpen_target = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("niceContrast_reset"))) {
      cp5.getController("niceContrast").setValue(0.0);
      ui_niceContrast = 0.0;
    }
    if (theEvent.isFrom(cp5.getController("sort_black_reset"))) {
      cp5.getController("sort: black").setValue(-10000000);
      ui_sortBlackVal = -10000000;
    }
    if (theEvent.isFrom(cp5.getController("sort_value_reset"))) {
      cp5.getController("sort: value").setValue(60);
      ui_sortBrightVal = 60;
    }
    if (theEvent.isFrom(cp5.getController("sort_white_reset"))) {
      cp5.getController("sort: white").setValue(-6000000);
      ui_sortWhiteVal = -6000000;
    }
    if (theEvent.isFrom(cp5.getController("party_reset"))) {
      cp5.getController("party").setValue(0.0);
      ui_party = 0.0;
    }

    ///////////////////////////////////////////////////////////////////////
    // M O R E   T O G G L E S
    ///////////////////////////////////////////////////////////////////////
    
    if (theEvent.isFrom(checkbox_party)) {
        party_toggle = (int)checkbox_party.getArrayValue()[0];
      }

    if (theEvent.isFrom(checkbox_sort)) {
      int incoming_0 = (int)checkbox_sort.getArrayValue()[0];
      int incoming_1 = (int)checkbox_sort.getArrayValue()[1];
      int incoming_2 = (int)checkbox_sort.getArrayValue()[2];
      if (incoming_0 != sort_toggle[0]) {
        sort_toggle[0] = (int)checkbox_sort.getArrayValue()[0];
        sort_toggle[1] = 0;
        sort_toggle[2] = 0;
        checkbox_sort.setArrayValue(float(sort_toggle));
        sort_mode = 0;
      }
      else if (incoming_1 != sort_toggle[1]) {
        sort_toggle[0] = 0;
        sort_toggle[1] = (int)checkbox_sort.getArrayValue()[1];
        sort_toggle[2] = 0;
        checkbox_sort.setArrayValue(float(sort_toggle));
        sort_mode = 1;
      }
      else if (incoming_2 != sort_toggle[2]) {
        sort_toggle[0] = 0;
        sort_toggle[1] = 0;
        sort_toggle[2] = (int)checkbox_sort.getArrayValue()[2];
        checkbox_sort.setArrayValue(float(sort_toggle));
        sort_mode = 2;
      }
      int x = 0;
      for (int i=0; i<3; i++){
        if (sort_toggle[i] > 0) x++;
      }
      if (x<=0) sort_masterToggle = false;
      else if (x>0) sort_masterToggle = true;
    }
    
    if (theEvent.isFrom(checkbox_updateShader)) {
      update_shader = (int)checkbox_updateShader.getArrayValue()[0];
    }

  }



  public void draw() {
    background(cf_bgColor);
    // textFont(font1);
    // text(fps, 5,10);
  }
  
  private ControlFrame() {
  }

  public ControlFrame(Object theParent, int theWidth, int theHeight) {
    parent = theParent;
    w = theWidth;
    h = theHeight;
  }


  public ControlP5 control() {
    return cp5;
  }
  
  ControlP5 cp5;
  Object parent;
}