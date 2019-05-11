package org.cbr2019.task3;

public class Main {
	
	private static final int MIN_SIZE = 2;

	/**
	 * Размер по умолчанию
	 */
	private static final int DEFAULT_SIZE = 20;

	public static void main(String[] args) {
		int width = DEFAULT_SIZE;
		int height = DEFAULT_SIZE;
		
		// Если есть параметры используем их в качестве размера
		if (args != null && args.length > 0) {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		}
		
		// Проверям выбранные размеры на соотвтветвие условиям:
		// Минимальный размер стороны - 2 клетки
		// Размер должен быть чётным 
		if (width < MIN_SIZE || width % 2 != 0) {
			throw new IllegalArgumentException(Integer.toString(width));
		}
		if (height < MIN_SIZE || height % 2 != 0) {
			throw new IllegalArgumentException(Integer.toString(height));
		}
		
		// Пересчитываем размер с учётом стенок между клетками
		width = width * 2 + 1;
		height = height * 2 + 1;
		
		// Создаём лабиринт
		final Maze maze = new Maze(width, height);
		
		// Выводим лабиринт
		print(maze);
		
		System.out.println();
		System.out.println();
		
		// Строим путь через лабиринт
		final Path path = Path.find(maze);
				
		// Выводим лабиринт и путь поверх него
		print(new ComplexLayer(path, maze));
	}
	
	private static void print(Layer layer)  {
		int width = layer.getWidth();
		int height = layer.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Layer.Output out = layer.getOutput(x, y);
				if(out == null) {
					System.out.append('?');
				}
				switch (out) {
				case floor:
					System.out.append('0');
					break;
				case wall:
					System.out.append('1');
					break;
				case path:
					System.out.append('*');
					break;
				default:
					break;
				}
			}
			System.out.println();
		}
		System.out.flush();
	}
}
