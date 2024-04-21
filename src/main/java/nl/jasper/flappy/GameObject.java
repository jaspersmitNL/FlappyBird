package nl.jasper.flappy;

import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Shader;
import nl.jasper.flappy.render.Texture;
import org.joml.Vector3f;

public abstract class GameObject {

    private Mesh mesh;
    private Texture texture;

    public Vector3f position = new Vector3f();
    public float rotation = 0;
    public Vector3f scale = new Vector3f(1);


    public GameObject(Mesh mesh, Texture texture) {
        this.mesh = mesh;
        this.texture = texture;
    }


    public void render(Shader shader) {
        shader.setUniform("transformation", MathUtil.getTransformationMatrix(position, rotation, scale));
        texture.bind();
        mesh.bind();
        mesh.render();

    }

    public void update(double deltaTime) {

    }





}
