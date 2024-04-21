package nl.jasper.flappy.objects;

import nl.jasper.flappy.GameObject;
import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Shader;
import nl.jasper.flappy.render.Texture;

public class Bird extends GameObject {

    private float velocity = 0;
    private float gravity = 0.05f;

    public boolean isDead = false;

    public Bird(Mesh mesh, Texture texture) {
        super(mesh, texture);
        this.scale.set(0.25);
        this.position.z = 1;
    }



    public void jump() {
        this.velocity = 1.5f;
    }

    @Override
    public void update(double deltaTime) {

        if (isDead) {
            return;
        }

        this.velocity -= gravity;

        //clamp velocity
        if (velocity < -1.5f) {
            velocity = -1.5f;
        }

        this.position.y += velocity * deltaTime;
        this.rotation = (float) Math.toDegrees(Math.atan(velocity / 1.5f)) / 3;


        if (position.y > 1) {
            position.y = 1;
            velocity = 0;
        }

        if (position.y < -1) {
            isDead = true;
        }




    }

    @Override
    public void render(Shader shader) {
        super.render(shader);
    }


    public boolean hitsPipe(Pipe pipe) {
        return false;
    }

}
