package org.cbr2019.task3;

import java.util.stream.Stream;

/**
 * Класс для отображения нескольких слоёв наложеных друг на друга 
 */
public class ComplexLayer implements Layer {

	private final Layer[] layers;

	public ComplexLayer(Layer... layers) {
		if (layers == null) {
			this.layers = new Layer[0];
		} else {
			this.layers = new Layer[layers.length];
			System.arraycopy(layers, 0, this.layers, 0, layers.length);
		}
	}

	public int getWidth() {
		return Stream.of(layers).mapToInt(Layer::getWidth).max().orElseThrow(IllegalStateException::new);
	}

	public int getHeight() {
		return Stream.of(layers).mapToInt(Layer::getHeight).max().orElseThrow(IllegalStateException::new);
	}

	@Override
	public Output getOutput(int x, int y) {
		for (Layer layer : layers) {
			final Output out = layer.getOutput(x, y);
			if (out != null) {
				return out;
			}
		}
		return null;
	}
}
