package nl.jasper.flappy.objects;

import nl.jasper.flappy.FlappyBird;
import nl.jasper.flappy.GameObject;
import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Texture;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class Pipe extends GameObject {

    private FlappyBird flappyBird;

    public Pipe(FlappyBird flappyBird, Mesh mesh, Texture texture) {
        super(mesh, texture);
        this.flappyBird = flappyBird;
    }

    public Pipe setPosition(Vector3d position) {
        this.position = position;
        return this;
    }
    public Pipe setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        float speed = 0.5f;

        this.position.x -= (float) (speed * deltaTime);
        System.out.println("Updating pipe");
    }
}
