#version 300 es
precision mediump float;

uniform sampler2D uTexture;

out vec4 fragColor;

void main() {
    vec4 color = texture(uTexture, gl_PointCoord);
    fragColor = vec4(color.rgb, 1.0);
}