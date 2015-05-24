#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

// internal variables
uniform sampler2D texture;
varying vec4 vertColor;
varying vec4 vertTexCoord;

// my variables



///////////////////////////////////////////////////////////////////////
// F U N C T I O N S
///////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////


void main() {

  vec2 coord = vertTexCoord.st;

  // S T R E T C H
  // if (coord.s > .745) coord.s = .745;
  // if (coord.s < .255) coord.s = .255;


  if (coord.s > .745) {
    float right = abs(.745 - coord.s);
    coord.s = .745 - right;
  }
  if (coord.s < .255) {
    float left = abs(.255 - coord.s);
    coord.s = .255 + left;
  }

  vec4 col = texture2D(texture, coord);  

  gl_FragColor = col;



}
