#version 330 core

in vec3 FragPos;
in vec2 TexCoord;
out vec4 FragColor;
uniform sampler2D tex;
uniform vec3 color;

void main() {
    FragColor = texture(tex, TexCoord);
//    FragColor = vec4(color, 1.0);

}