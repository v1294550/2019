package org.cbr2019.task3;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Путь через лабиринт 
 *
 */
public class Path implements Layer {

	/**
	 * Путь через лабиринт храним как неупорядоченный набор клеток
	 */
	private final HashSet<Point> data;

	/**
	 * Поиск пусти через лабиринт
	 * @param maze Лабиринт 
	 * @return Построенный путь
	 */
	public static Path find(Maze maze) {
		final int width = maze.getWidth();
		final int height = maze.getHeight();
		
		// Набор ранее посещённых клеток
		final HashSet<Point> visisted = new HashSet<>();
		
		// Стек с путём
		final LinkedList<Point> stack = new LinkedList<>();
		stack.push(maze.getStart()); // Размещаем начало пути в начале лабиринта
				
		while (!stack.peek().equals(maze.getExit())) { //Ищим путь пока не достигнем выхода
			final Point point = stack.peek();
			visisted.add(point); // Отмечаем текущею клетку 	
			
			Point next = null;
			for (Point p : point.siblings()) { // Перебираем все соседние клетки
				// Если она лежит в пределах лабиринта
				if (p.getX() > 0 && p.getX() < width && p.getY() > 0 && p.getY() < height) {
					// И при этом не посещена нами ранее, а так-же являетеся проходимым полом
					if(!visisted.contains(p) && maze.getCell(p) == Output.floor) {
						// Выбираем её как следующею
						next = p;
						break;	
					}
				}
			}
			if(next == null) {
				// Если мы не можем сделать шаг, так-как подходящей клетки нет
				// Мы находимся в тупике или все клетки рядом уже помечаны как ведущие в тупики
				// То возвращаемся по своему пути назад 
				stack.pop();
			} else {
				// Иначе делаем шаг на выбранную клетку
				stack.push(next);
			}
		}
		// Возвращаем путь в виде готового объекта 
		return new Path(stack);
	}
	
	public Path(Collection<Point> source) {
		data = new HashSet<Point>(source);
	}
	
	@Override
	public int getWidth() {
		return data.stream().mapToInt(Point::getX).max().orElse(0);
	}
	
	@Override
	public int getHeight() {
		return data.stream().mapToInt(Point::getY).max().orElse(0);
	}
	
	@Override
	public Output getOutput(int x, int y) {
		return data.contains(new Point(x, y)) ? Output.path : null;
	}
}
