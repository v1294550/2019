package org.cbr2019.task3;

/**
 * Базовый интерфейс слоя. Для вывода данных в консоль 
 *
 */
public interface Layer {
	
	/**
	 * Тип клетки
	 * 
	 */
	public enum Output {
		wall, floor, path;
	}

	/**
	 * @return Ширина слоя
	 */
	public int getWidth();

	/**
	 * @return Высота слоя
	 */
	public int getHeight();
	
	/**
	 * @return Тип клетки по заданым координатам
	 */
	public Output getOutput(int x, int y);
}
