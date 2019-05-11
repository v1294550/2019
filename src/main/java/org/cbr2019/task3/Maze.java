package org.cbr2019.task3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * Класс описывающий лабиринт 
 */
public class Maze implements Layer {

	private static final Random RND = new Random();

	private final Layer.Output[] data;

	private final int width;

	private final int height;

	/**
	 * Координаты входа
	 */
	private Point start;

	/**
	 * Координаты выхода
	 */
	private Point exit;

	public Maze(int width, int height) {
		this.width = width;
		this.height = height;

		// Заполняем лабиринт пустым шаблоном всё заполнено стенами
		// кроме клеток которые всегда будут полом
		data = new Layer.Output[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((y < height - 1 && x < width - 1) && (y % 2 != 0 && x % 2 != 0)) {
					data[x + y * width] = Output.floor;
				} else {
					data[x + y * width] = Output.wall;
				}
			}
		}
		// Генерируем сам лабиринт
		generate();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Output getOutput(int x, int y) {
		return data[x + y * width];
	}

	/**
	 * Задаём новое значение для выбранной клетки
	 * 
	 * @param x координата 
	 * @param y координата
	 * @param value новое значение
	 */
	public void setOutput(int x, int y, Layer.Output value) {
		data[x + y * width] = value;
	}

	/**
	 * Получаем значение длы выбранной клетки
	 * 
	 * @param cell клетка
	 * @return текущие значение
	 */
	public Output getCell(Point cell) {
		return data[cell.getX() + cell.getY() * width];
	}

	/**
	 * Задаём новое значение для выбранной клетки
	 * 
	 * @param cell клетка
	 * @param value новое значение
	 */
	public void setCell(Point cell, Layer.Output value) {
		data[cell.getX() + cell.getY() * width] = value;
	}

	/**
	 * @return Клетка входа в лабиринит
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * @return Клетка выхода из лабиринта
	 */
	public Point getExit() {
		return exit;
	}

	private void generate() {
		// Набор уже посященых клеток
		// Объявляем как локальную переменную метода генерации, а не поле класса
		// для оптимизации использования памяти  
		final HashSet<Point> visited = new HashSet<Point>();
		// Стек с текущим путём
		final LinkedList<Point> stack = new LinkedList<>();
		// В качестве начальной точки выбираем верхний левый угол, с учётом стенок
		stack.push(new Point(1, 1));

		// Продолжаем строить лабиринт пока не исчерпали все варианты (не обошли все клетки) 
		while (!stack.isEmpty()) {
			final Point cell = stack.peek();
			visited.add(cell); // Отмечаем текущую клетку как уже отмеченную

			final Point next = getNext(visited, cell);
			if (next == null) {
				// Если нет по которой можно пройти дальше - возвращаемся назад
				stack.pop();
			} else {
				// Если клетка есть - добавляем её в текущий путь
				stack.push(next);

				// Убираем стенку между текущей и следующей клеткой 
				final int dx = next.getX() - cell.getX();
				final int dy = next.getY() - cell.getY();
				final int wx = cell.getX() + (dx != 0 ? dx / Math.abs(dx) : 0);
				final int wy = cell.getY() + (dy != 0 ? dy / Math.abs(dy) : 0);
				data[wx + wy * width] = Output.floor;
			}
		}

		// Выбраем в качестве входа и выхода случайные клетки (на позициях где не будет перегородки)
		// Для входа сверху лабиринта
		// Для выхода снизу лабиринта
		start = new Point(RND.nextInt((width - 1) / 2) * 2 + 1, 0);
		exit = new Point(RND.nextInt((width - 1) / 2) * 2 + 1, height - 1);

		data[start.getX() + start.getY() * width] = Output.floor;
		data[exit.getX() + exit.getY() * width] = Output.floor;
	}

	/**
	 * Поиск следующей клетки
	 * 
	 * Следующая клетка должна быть соседней от быранной (расчёт идёт "через" одну стенку между клетками)
	 * Данная клетка должна находиться в лабиринте и не быть ранее посещённой
	 *  
	 * @param visisted набор с уже посещёнными клетками
	 * @param cell текущая клетка
	 * @return одна случайная из доступных клеткок, или null если таких клеток нет (текущая клетка будет тупиком) 
	 */
	private Point getNext(Set<Point> visisted, Point cell) {
		ArrayList<Point> buffer = new ArrayList<Point>();
		for (Point c : cell.siblings(2)) { // Находим все соседние клетки (перепрыгиваем через стенку)
			if (c.getX() > 0 && c.getX() < width && c.getY() > 0 && c.getY() < height) { // Если она лежит в лабиринте
				if (!visisted.contains(c)) { // и ещё не посещена 
					buffer.add(c); // добавляем её в буфер
				}
			}
		}
		if (buffer.isEmpty()) {
			// Если буфер пуст - значит подходящих клеток нет
			return null;
		} else {
			// Иначе выбриаем случайную клетку
			return buffer.get(RND.nextInt(buffer.size()));
		}
	}
}
