package nl.jasper.flappy;

import nl.jasper.flappy.objects.Background;
import nl.jasper.flappy.objects.Bird;
import nl.jasper.flappy.render.Mesh;
import nl.jasper.flappy.render.Shader;
import nl.jasper.flappy.render.Texture;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class Main {

    public static int WIDTH = 1080;
    public static int HEIGHT = 720;

    public boolean playing = false;


    private long window;
    private Shader basicShader;
    private Bird bird;

    private List<GameObject> gameObjects = new ArrayList<>();


    private Mesh backgroundMesh;
    private Texture backgroundTexture;

    private List<Background> backgrounds = new ArrayList<>();


    private void setup() {


        this.basicShader = new Shader(readFile("assets/shaders/basic.vert.glsl"), readFile("assets/shaders/basic.frag.glsl"));


        this.bird = new Bird(new Mesh(new float[]{
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        }, new int[]{
                0, 1, 3,
                3, 1, 2
        }, new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        }), new Texture("assets/bird.png"));

        bird.position.set(0, 0, 1);

        this.gameObjects.add(bird);

        this.backgroundMesh = new Mesh(new float[]{
                -0.5f, 1, 0,
                -0.5f, -1, 0,
                0.5f, -1, 0,
                0.5f, 1, 0
        }, new int[]{
                0, 1, 3,
                3, 1, 2
        }, new float[]{
                0, 1,
                0, 0,
                1, 0,
                1, 1
        });
        this.backgroundTexture = new Texture("assets/bg.png");


        float overlap = 0.05f;
        for (int i = -1; i <= 2; i++) {
            float positionX = i - overlap;
            this.backgrounds.add(new Background(backgroundMesh, backgroundTexture).setPosition(new Vector3f(positionX, 0, 0)));
        }



    }


    private void render() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);





        basicShader.bind();
        basicShader.setUniform("projection", MathUtil.getProjectionMatrix(WIDTH, HEIGHT));

        for (Background background : backgrounds) {
            background.render(basicShader);
        }


        for (GameObject object : gameObjects) {
            object.render(basicShader);
        }

    }

    private void update(double deltaTime) {
        for (Background background : backgrounds) {
            background.update(deltaTime);
        }

        if (playing) {

            for (GameObject object : gameObjects) {
                object.update(deltaTime);
            }
        }
    }


    public void start() {
        GLFWErrorCallback.createPrint(System.err);
        glfwInit();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        this.window = glfwCreateWindow(WIDTH, HEIGHT, "FlappyBird", 0, 0);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();

        //center window on screen
        int screenWidth = glfwGetVideoMode(glfwGetPrimaryMonitor()).width();
        int screenHeight = glfwGetVideoMode(glfwGetPrimaryMonitor()).height();
        glfwSetWindowPos(window, (screenWidth - WIDTH) / 2, (screenHeight - HEIGHT) / 2);
        glfwSetKeyCallback(window, (l, key, scan, action, mods) -> handleKeyEvent(key, action));
        glfwSetWindowSizeCallback(window, (l, width, height) -> {
            WIDTH = width;
            HEIGHT = height;
        });

        setup();
        glfwShowWindow(window);


        double lastTime = glfwGetTime();


        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            glViewport(0, 0, WIDTH, HEIGHT);


            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTime;
            render();
            update(deltaTime);

            glfwSwapBuffers(window);
            lastTime = currentTime;

        }

    }


    private void handleKeyEvent(int key, int action) {

        if (action == GLFW_PRESS) {
            if (key == GLFW_KEY_ESCAPE) {
                glfwSetWindowShouldClose(window, true);
                return;
            }
            if (key == GLFW_KEY_ENTER) {
                playing = !playing;
            }
            if (key == GLFW_KEY_SPACE && playing) {
                bird.jump();
            }
        }


    }

    private boolean isKeyDown(int key) {
        int status = glfwGetKey(window, key);
        return status == GLFW_PRESS || status == GLFW_REPEAT;
    }

    private String readFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        new Main().start();
    }
}