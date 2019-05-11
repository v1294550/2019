package org.cbr2019.task3;

/**
 * Вспомогательный класс для хранения пары координат (x, y) 
 */
public final class Point {

	private final int x;

	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	/**
	 * Возвращает массив из четырёх соседних клеток для данной клетки, на заданом растояние
	 * 
	 * @param offset расстояние до соседних точек
	 * 
	 * @return Массив точек
	 */
	public Point[] siblings(int offset) {
		return new Point[] { new Point(x + offset, y), new Point(x, y + offset), new Point(x - offset, y),
				new Point(x, y - offset) };
	}
	
	/**
	 * Возвращает соседние клетки на минимальном расстрояние
	 *  
	 * @return Массив точек
	 */
	public Point[] siblings() {
		return siblings(1);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		if(obj.getClass() != Point.class) {
			return false;
		}
		final Point p = (Point) obj;
		return x == p.x && y == p.y;
	}
	
	@Override
	public int hashCode() {
		return x ^ y;
	}
	
	@Override
	public String toString() {
		return x + " " + y;
	}
}
