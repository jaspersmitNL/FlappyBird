package nl.jasper.flappy.render;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int program, vertex, fragment;

    private final Map<String, Integer> uniforms = new HashMap<>();


    public Shader(String vertexSrc, String fragmentSrc) {
        program = glCreateProgram();
        vertex = glCreateShader(GL_VERTEX_SHADER);
        fragment = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertex, vertexSrc);
        glShaderSource(fragment, fragmentSrc);

        compile(vertex, vertexSrc);
        compile(fragment, fragmentSrc);

        glAttachShader(program, vertex);
        glAttachShader(program, fragment);

        glLinkProgram(program);

        glDetachShader(program, vertex);
        glDetachShader(program, fragment);

    }


    public void bind() {
        glUseProgram(program);
    }

    public int getUniform(String name) {
        if (uniforms.containsKey(name)) {
            return uniforms.get(name);
        }
        int location = glGetUniformLocation(program, name);
        if (location == -1) {
            throw new RuntimeException("Could not find uniform: " + name);
        }
        uniforms.put(name, location);
        return location;
    }

    public void setUniform(String name, int value) {
        glUniform1i(getUniform(name), value);
    }

    public void setUniform(String name, float value) {
        glUniform1f(getUniform(name), value);
    }

    public void setUniform(String name, Vector2f value) {
        glUniform2f(getUniform(name), value.x, value.y);
    }

    public void setUniform(String name, Vector3f value) {
        glUniform3f(getUniform(name), value.x, value.y, value.z);
    }
    public void setUniform(String name, Matrix4f value) {
        glUniformMatrix4fv(getUniform(name), false, value.get(new float[16]));
    }

    private void compile(int shader, String src) {
        glCompileShader(shader);
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile shader: " + glGetShaderInfoLog(shader));
            System.err.println(src);
            System.exit(1);
        }
    }
}
