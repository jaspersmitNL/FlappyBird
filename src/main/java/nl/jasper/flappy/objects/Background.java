package nl.jasper.flappy.objects;

import nl.jasper.flappy.GameObject;
import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Texture;
import org.joml.Vector3f;

public class Background extends GameObject {
    public Background(Mesh mesh, Texture texture) {
        super(mesh, texture);
    }

    public Background setPosition(Vector3f position) {
        this.position = position;
        return this;
    }

    @Override
    public void update(double deltaTime) {

        if (position.x < -2.0f) {
            position.x = 1.99f;
        }

        float speed = 0.2f;
        position.x -= (float) (speed * deltaTime);
    }
}
