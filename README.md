### Программа выводящая лабиринт и путь через него в консоль

После запуска главного класса выводит в консоль:

1. Сгенерированный лабиринт заданого размера
2. Две пустых строки
3. Ранее сгенерированный лабиринт и путь через него отмеченный символом: *

#### Запуск
По умолчанию размер лабиринта 20 на 20

Если передать при запуске два аргумента они будут использованны в качестве размера лабиринта, пример: java -jar test.jar 12 24
 
Создаст и выведет в консоль лабиринт замером 12 на 24

Аргументы должны соответвовать следующим условиям: Быть не менее 2-х и иметь чётное значение

#### Особенность генерации

Заданый замер считаеться без учёта внешних и внутренних стенок, то есть фактический размер лабиринта по умолчанию будет 41

Данный размер вычисляется как 2 * N + 1, где N вводимый размер лабиринта