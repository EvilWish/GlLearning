package tl.de.evilwish.som.shaders;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Shader {

	private int program;
	private int vs; // Vertex Shader
	private int fs; // Fragment Shader

	public Shader(String filename) {
		program = glCreateProgram();

		// VERTEX SHADER
		vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, readFile(filename + ".vs"));
		glCompileShader(vs);

		// Shader Fehler abfrage
		if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(vs));
			System.exit(1);
		}

		// FRAGMENT SHADER
		fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, readFile(filename + ".fs"));
		glCompileShader(fs);

		// Shader Fehler abfrage
		if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
			System.err.println(glGetShaderInfoLog(fs));
			System.exit(1);
		}

		glAttachShader(program, vs);
		glAttachShader(program, fs);

		// ATTRIBUTES
		glBindAttribLocation(program, 0, "vertices");
		glBindAttribLocation(program, 1, "textures");

		// Programm Linken
		glLinkProgram(program);

		// Link Fehler abfrage
		if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}

		// Programm validate
		glValidateProgram(program);

		// Validate Fehler abfrage
		if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
			System.err.println(glGetProgramInfoLog(program));
			System.exit(1);
		}

	}

	public void bind() {
		glUseProgram(program);
	}

	public void setUniform(String name, int value) {
		int location = glGetUniformLocation(program, name);

		if (location != -1)
			glUniform1i(location, value); // i weil es ein Integer value ist

	}

	private String readFile(String filename) {
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("./shaders/" + filename)));
			String line;
			while ((line = br.readLine()) != null) {
				string.append(line);
				string.append("\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return string.toString();

	}

}
