package nl.jasper.flappy.objects;

import nl.jasper.flappy.GameObject;
import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Shader;
import nl.jasper.flappy.render.Texture;

public class Bird extends GameObject {

    private float velocity = 0;
    private float gravity = 0.05f;

    public Bird(Mesh mesh, Texture texture) {
        super(mesh, texture);
        this.scale.set(0.25);
    }



    public void jump() {
        this.velocity = 1.5f;
    }

    @Override
    public void update(double deltaTime) {
        this.velocity -= gravity;
        this.position.y += velocity * deltaTime;
    }

    @Override
    public void render(Shader shader) {
        super.render(shader);
    }
}
