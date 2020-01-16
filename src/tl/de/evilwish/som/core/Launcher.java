package tl.de.evilwish.som.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import tl.de.evilwish.som.gfx.Camera;
import tl.de.evilwish.som.gfx.Model;
import tl.de.evilwish.som.gfx.Texture;
import tl.de.evilwish.som.gfx.Window;
import tl.de.evilwish.som.shaders.Shader;

public class Launcher {

	public static void main(String[] args) {

		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}

		Window win = new Window();
		win.setSize(1600, 900);
		win.createWindow("Shadow of Mine");

		glfwMakeContextCurrent(win.getWindow());
		GL.createCapabilities();

		Camera camera = new Camera(win.getWidth(), win.getHeight());
		glEnable(GL_TEXTURE_2D);

		float[] vertices = new float[] { -0.5f, 0.5f, 0, // Top Left 0
				0.5f, 0.5f, 0, // TOP RIGHT 1
				0.5f, -0.5f, 0, // BOTTOM RIGHT 2
				-0.5f, -0.5f, 0, // BOTTOM LEFT 3
		};

		float[] texture = new float[] { 0, 0, 1, 0, 1, 1, 0, 1 };

		int[] indices = new int[] { 0, 1, 2, 2, 3, 0 };

		Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Texture tex = new Texture(".//res//PathAndObjects.png");

		Matrix4f scale = new Matrix4f().translate(new Vector3f(100, 0, 0)).scale(512);

		Matrix4f target = new Matrix4f();

		camera.setPosition(new Vector3f(-100, 0, 0));

		double frame_cap = 1.0 / 60.0;

		double frame_time = 0;
		double frames = 0;

		double time = Timer.getTime();
		double unprocessed = 0;

		while (!win.shouldClose()) {

			boolean can_render = false;
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			time = time_2;

			while (unprocessed >= frame_cap) {
				unprocessed -= frame_cap;
				can_render = true;

				target = scale;
				if (win.getInput().isKeyPressed(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(win.getWindow(), true);
				}

				win.update();
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}

			if (can_render) {
				glClear(GL_COLOR_BUFFER_BIT);
				shader.bind();
				shader.setUniform("sampler", 0);
				shader.setUniform("projection", camera.getProjection().mul(target));
				tex.bind(0);
				model.render();
				win.swapBuffers();

				frames++;
			}
		}

		glfwTerminate();

	}

}
