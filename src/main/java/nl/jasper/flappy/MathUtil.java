package nl.jasper.flappy;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MathUtil {


    public static Matrix4f getProjectionMatrix(int width, int height) {

        float aspectRatio = (float) width / height;
        float left = -aspectRatio;
        float right = aspectRatio;
        float bottom = -1;
        float top = 1;

        return new Matrix4f().ortho2D(left, right, bottom, top);
    }

    public static Matrix4f getTransformationMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
        return new Matrix4f().translate(position).rotateX((float) Math.toRadians(rotation.x)).rotateY((float) Math.toRadians(rotation.y)).rotateZ((float) Math.toRadians(rotation.z)).scale(scale);
    }

}
