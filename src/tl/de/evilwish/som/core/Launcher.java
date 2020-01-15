package tl.de.evilwish.som.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import tl.de.evilwish.som.gfx.Model;
import tl.de.evilwish.som.gfx.Texture;
import tl.de.evilwish.som.shaders.Shader;

public class Launcher {

	public static void main(String[] args) {
		//TEST RUN
		if (!glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW!");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		long window = glfwCreateWindow(1600, 900, "Shadow of Mine", 0, 0);

		if (window == 0) {
			throw new IllegalStateException("Failed to create window!");
		}

		glfwShowWindow(window);

		glfwMakeContextCurrent(window);

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);

		float[] vertices = new float[] { 
				-0.5f, 0.5f, 0, // Top Left     0
				0.5f, 0.5f, 0, // TOP RIGHT     1
				0.5f, -0.5f, 0, // BOTTOM RIGHT 2
				-0.5f, -0.5f, 0, // BOTTOM LEFT 3
		};

		float[] texture = new float[] {
				0, 0, 
				1, 0, 
				1, 1,
				0, 1
		};
		
		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};

		Model model = new Model(vertices, texture, indices);
		Shader shader = new Shader("shader");
		Texture tex = new Texture(".//res//PathAndObjects.png");

		while (!glfwWindowShouldClose(window)) {

			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwSetWindowShouldClose(window, true);
			}

			glfwPollEvents();

			glClear(GL_COLOR_BUFFER_BIT);

			shader.bind();
			shader.setUniform("sampler", 0);
			tex.bind(0);
			model.render();

			glfwSwapBuffers(window);
		}

		
		
		glfwTerminate();

	}

}
