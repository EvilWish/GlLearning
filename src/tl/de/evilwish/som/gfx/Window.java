package tl.de.evilwish.som.gfx;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWVidMode;

import tl.de.evilwish.som.utils.Input;

public class Window {

	private long window;

	private int width, height;
	private boolean fullscreen;

	private Input input;

	public Window() {
		setSize(640, 480);
		setFullscreen(false);
	}

	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

		if (window == 0)
			throw new IllegalStateException("Failed to create Window!");

		if (!fullscreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
			glfwShowWindow(window);

			input = new Input(window);
		}

	}

	public void update() {
		input.update();
		glfwPollEvents();
	}

	public void swapBuffers() {

		glfwSwapBuffers(window);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window) != false;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
