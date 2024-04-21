#version 330 core
layout(location = 0) in vec3 position;
layout(location = 1) in vec2 texCoord;

out vec3 FragPos;
out vec2 TexCoord;

uniform mat4 projection;
uniform mat4 transformation;


void main() {
    FragPos = position;
    TexCoord = texCoord;
    gl_Position = projection * transformation * vec4(position, 1.0);
}