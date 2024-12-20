import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

//создаем класс EAS (Employer Accounting System)
class EAS {
    public static PrintStream out = System.out; // Для вывода сообщений на экран
    private Worker[] listOfWorkers = new Worker[0]; // Создаем список сотрудников
    private String companyName; // Создаём название компании

    // Создать компанию
    public EAS(String companyName) {
        this.companyName = companyName;
    }
    // Если нет имени компании - сообщить, что нужно назвать
    public EAS() {
        out.println("Нужно указать навзание компании!");
    }
    // Вывод списка сотрудников на экран
    public void list() {
        if (listOfWorkers.length == 0) { // Если сотрудников нет:
            out.println("В компании `" + companyName + "` нет сотрудников!");
        }
        else { // Иначе выводим
            out.println("Сотрудники компании " + companyName + ":");
            for (int i = 0; i < listOfWorkers.length; i++) {
                out.println(listOfWorkers[i].getFirstName() + " " + listOfWorkers[i].getSecondName() + " - Должность: " + listOfWorkers[i].getLevelOfWorker() + ", Заданий: " + listOfWorkers[i].getCountOfTasks());
            }
        }
    }
    
    // Метод сортировки пузырьком для строк
    static Worker[] bubbleSort(Worker[] array) {
        int n = array.length; // Длина массива
        // Внешний цикл для прохода по всем элементам
        for (int i = 0; i < n - 1; i++) {
            // Внутренний цикл для сравнения соседних элементов
            for (int j = 0; j < n - i - 1; j++) {
                // Сравниваем по имени, если имена одинаковые, то сравниваем по фамилии
                if (isGreater(array[j].getFirstName(), array[j + 1].getFirstName()) || (array[j].getFirstName().equals(array[j + 1].getFirstName()) && isGreater(array[j].getSecondName(), array[j + 1].getSecondName()))) {
                    // Меняем местами, если порядок неверный
                    Worker temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        // Возвращаем массив
        return array;
    }
    // Метод для сравнения двух строк
    static boolean isGreater(String str1, String str2) {
        // Минимальная длина
        int minLength = Math.min(str1.length(), str2.length());
        // Сравниваем символы строк
        for (int i = 0; i < minLength; i++) {
            if (str1.charAt(i) > str2.charAt(i)) {
                return true; // str1 'больше', чем str2
            }
            else {
                if (str1.charAt(i) < str2.charAt(i)) {
                    return false; // str1 'меньше', чем str2
                } 
            }
        }
        // Если все символы равны, то возвращаем строку с большей длинной
        if (str1.length() > str2.length()) {
            return true;
        }
        return false;
    }
    
    // Добавляем работника
    public void add(Worker worker) {
        Worker[] newList = new Worker[listOfWorkers.length+1]; // Создаём новый массив
        if (listOfWorkers.length == 0) { // Если список сотрудников пуст
            newList[0] = worker;
            listOfWorkers = newList;
            out.println("Новый сотрудник: " + worker.getFirstName() + " " + worker.getSecondName() + " теперь имеет должность `1` и `0` выполненных заданий");
        }
        else { // Если не пуст
            boolean alreadyExist = false; // Если такие Имя и Фамилия уже существуют:
            for (int i = 0; i < listOfWorkers.length; i++) {
                if (worker.getFirstName().equals(listOfWorkers[i].getFirstName()) && worker.getSecondName().equals(listOfWorkers[i].getSecondName())) {
                    alreadyExist = true;
                    out.println("Сотрудник `" + worker.getFirstName() + " " + worker.getSecondName() + "` уже работает!");
                }
            }
            if (alreadyExist == false) { // Если не существует:
                for (int i = 0; i < listOfWorkers.length; i++) {
                    newList[i] = listOfWorkers[i]; // Перекладываем элементы из старого массива в новый
                }
                newList[listOfWorkers.length] = worker; // Добавляем нового сотрудника
                listOfWorkers = bubbleSort(newList); // сортируем по алфавиту
                out.println("Новый сотрудник: " + worker.getFirstName() + " " + worker.getSecondName() + " теперь имеет должность `1` и `0` выполненных заданий");
            }
        }
    }
    // Добавление задачи
    public void addTask(Worker worker) {
        worker.addTask();
        out.println("Теперь у `"+worker.getFirstName()+" "+worker.getSecondName()+"` выполнено заданий: " + worker.getCountOfTasks());
    }
    // Повышение
    public void levelUp(Worker worker) {
        worker.levelUp();
        out.println("У `"+worker.getFirstName()+" "+worker.getSecondName()+"` - новая должность: " + worker.getLevelOfWorker());
    }
    // Добавление задачи от должности
    public void addTaskWithDifficult(Worker worker, int difficultLevel) {
        if ((int)worker.getLevelOfWorker() >= difficultLevel) {
            worker.addTask();
            out.println("Сотрудник `"+worker.getFirstName()+" "+worker.getSecondName()+"` выполнил задание со сложностью: " + difficultLevel + ". Теперь у него выполненно задач: " + worker.getCountOfTasks());
        }
        else {
            out.println("У `"+worker.getFirstName()+" "+worker.getSecondName()+"` слишкмо низкая должность: " + worker.getCountOfTasks() + ". Минимально необходимая: " + difficultLevel);
        }
    }
    // Уволить сотруника
    public void kickWorker(String firstName, String secondName) {
        int workerID = -3;
        for (int i = 0; i < listOfWorkers.length; i++) {
            // Если такой сотрудник существует, то запомниаем его id
            if (listOfWorkers[i].getFirstName().equals(firstName) && listOfWorkers[i].getSecondName().equals(secondName)) {
                workerID = i;
            }
        }
        if (workerID == -3) { // Если не существует
            out.println("Сотрудника `"+firstName+" "+secondName+"` не существует!");
        }
        else { // Если такой сотрудник существует - увольняем
            listOfWorkers[workerID].reset();
            int k = 0;
            Worker[] newList = new Worker[listOfWorkers.length-1]; // Новый список
            for (int i = 0; i < listOfWorkers.length-1; i++) {
                if (i == workerID) { // Когда нашли id, делаем `сдвиг`
                    k = 1;
                }
                newList[i] = listOfWorkers[i+k];
            }
            listOfWorkers = newList;
            out.println("Сотрудник: `"+firstName+" "+secondName+"` уволен!");
        }
    }
    // Уволить сотрудников с низкой производительностью
    public void kickBadWorkers(int tasks) {
        int countWorkers = 0; // Кол-во уволенных
        int startLen = listOfWorkers.length; // Изначальная длина списка
        for (int i = 0; i < startLen; i++) {
            if (listOfWorkers[i].getCountOfTasks() < tasks) { // Выполенных задач меньше заданного порога
                String firstName = listOfWorkers[i].getFirstName();
                String secondName = listOfWorkers[i].getSecondName();
                this.kickWorker(firstName, secondName);
                countWorkers++; startLen--; i--;
            }
        }
        // Вывод информации
        if (countWorkers == 0) {
            out.println("Сотрудники не были уволены!");
        }
        else {
            if (listOfWorkers.length == 0) {
                out.println("Все сотрудники были уволены!");
            }
            else {
                out.println("Было уволено сотрудников: " + countWorkers);
            }
        }
    }
    // Повышение лучших сотрудников
    public void levelUpBestWorkers() {
        int maxTask = -1;
        for (int i = 0; i < listOfWorkers.length; i++) { // Ищем наибольшее количесвто выполненных заданий
            if (listOfWorkers[i].getCountOfTasks() > maxTask) {
                maxTask = listOfWorkers[i].getCountOfTasks();
            }
        }
        for (int i = 0; i < listOfWorkers.length; i++) { // Если число выполенных задач равно макс., то повышаем
            if (listOfWorkers[i].getCountOfTasks() == maxTask) {
                this.levelUp(listOfWorkers[i]);
            }
        }
    }
    // Понижение сотрудников с низким отношением долдности к производительности (level Down Worker With Bad Perfomance)
    public void levelDownWWBP() {
        double perf = Math.pow(10, 10); // perfomance
        int id = 0;
        for (int i = 0; i < listOfWorkers.length; i++) { // Поиск id сотрудника с наименьшим отношением
            if ((listOfWorkers[i].getCountOfTasks()/(double)listOfWorkers[i].getLevelOfWorker()) < perf) {
                perf = listOfWorkers[i].getCountOfTasks()/(double)listOfWorkers[i].getLevelOfWorker();
                id = i; // Получем id самого ленивого
            }
        }
        // Проверки
        if (listOfWorkers.length == 1) { // Если длина списка 1, то этот сотрудник самый ленивый и соседей нет
            if (listOfWorkers[id].getLevelOfWorker() == 1) { // Если должность уже минимальная
                out.println("У сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` уже минимальная должность!");
            }
            else { // Если должность не минимальная
                listOfWorkers[id].levelDown(); // Понижение
                out.println("Понижение сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` до должности: " + listOfWorkers[id].getLevelOfWorker());
            }            
        }
        else {
            if (id == 0) { // Если больше 1 сотрудника и самый ленивый - первый
                if (listOfWorkers[id].getLevelOfWorker() == 1) { // Если должность уже минимальная
                    out.println("У сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` уже минимальная должность!");
                }
                else { // Если должность не минимальная
                    listOfWorkers[id].levelDown(); // Понижение
                    out.println("Понижение сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` до должности: " + listOfWorkers[id].getLevelOfWorker());
                }
                if (listOfWorkers[id+1].getLevelOfWorker() == 1) { // Тоже самое для соседа
                    out.println("У соседнего сотрудника `" + listOfWorkers[id+1].getFirstName() + " " + listOfWorkers[id+1].getSecondName() + "` уже минимальная должность!");
                }
                else {
                    listOfWorkers[id+1].levelDown();
                    out.println("Понижение соседнего сотрудника `" + listOfWorkers[id+1].getFirstName() + " " + listOfWorkers[id+1].getSecondName() + "` до должности: " + listOfWorkers[id+1].getLevelOfWorker());
                }
            }
            else { // Если больше 1 сотрудника и самый ленивый - последний
                if (id == listOfWorkers.length-1) {
                    if (listOfWorkers[id].getLevelOfWorker() == 1) { // Если должность уже минимальная
                        out.println("У сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` уже минимальная должность!");
                    }
                    else { // Если должность не минимальная
                        listOfWorkers[id].levelDown(); // Понижение
                        out.println("Понижение сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` до должности: " + listOfWorkers[id].getLevelOfWorker());
                    }
                    if (listOfWorkers[id-1].getLevelOfWorker() == 1) { // Всё тоже самое
                        out.println("У соседнего сотрудника `" + listOfWorkers[id-1].getFirstName() + " " + listOfWorkers[id-1].getSecondName() + "` уже минимальная должность!");
                    }
                    else {
                        listOfWorkers[id-1].levelDown();
                        out.println("Понижение соседнего сотрудника `" + listOfWorkers[id-1].getFirstName() + " " + listOfWorkers[id-1].getSecondName() + "` до должности: " + listOfWorkers[id-1].getLevelOfWorker());
                    }                    
                }
                else { // Если ленивый сотрудник не первый и не последний
                    if (listOfWorkers[id].getLevelOfWorker() == 1) { // Если должность уже минимальная
                        out.println("У сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` уже минимальная должность!");
                    }
                    else { // Если должность не минимальная
                        listOfWorkers[id].levelDown(); // Понижение
                        out.println("Понижение сотрудника `" + listOfWorkers[id].getFirstName() + " " + listOfWorkers[id].getSecondName() + "` до должности: " + listOfWorkers[id].getLevelOfWorker());
                    }
                    if (listOfWorkers[id+1].getLevelOfWorker() == 1) { // Всё тоже самое для следующего соседа
                        out.println("У соседнего сотрудника `" + listOfWorkers[id+1].getFirstName() + " " + listOfWorkers[id+1].getSecondName() + "` уже минимальная должность!");
                    }
                    else {
                        listOfWorkers[id+1].levelDown();
                        out.println("Понижение соседнего сотрудника `" + listOfWorkers[id+1].getFirstName() + " " + listOfWorkers[id+1].getSecondName() + "` до должности: " + listOfWorkers[id+1].getLevelOfWorker());
                    }
                    if (listOfWorkers[id-1].getLevelOfWorker() == 1) { // Всё тоже самое для предыдущего соседа
                        out.println("У соседнего сотрудника `" + listOfWorkers[id-1].getFirstName() + " " + listOfWorkers[id-1].getSecondName() + "` уже минимальная должность!");
                    }
                    else {
                        listOfWorkers[id-1].levelDown();
                        out.println("Понижение соседнего сотрудника `" + listOfWorkers[id-1].getFirstName() + " " + listOfWorkers[id-1].getSecondName() + "` до должности: " + listOfWorkers[id-1].getLevelOfWorker());
                    }                      
                }
            }
        }   
    }
    // Доска почёта
    public void honorBoard() {
        // Переменные для хранения информации (id и состояние поиска)
        int id1 = 0; boolean find1 = false;
        int id2 = 0; boolean find2 = false;
        int id3 = 0; 
        double max = 0;
        if (listOfWorkers.length < 3) { // Если сотрудников < 3
            out.println("В компании меньше 3-х сотрудников!");
        }
        else {
            for (int i = 0; i < listOfWorkers.length; i++) { // Поиск 1-го, 2-го, 3-го места
                double perf = listOfWorkers[i].getCountOfTasks() / (double)listOfWorkers[i].getLevelOfWorker(); // Производительность
                if (perf > max && find1 == false) { // Пока id1 не найден
                    id1 = i; max = perf;
                }
                if (perf > max && find1 == true && i != id1 && find2 == false) { // Пока id2 не найден
                    id2 = i; max = perf;
                }
                if (perf > max && find1 == true && i != id1 && find2 == true && i != id2) { // Пока id3 не найден
                    id3 = i; max = perf;
                }
                if (i == listOfWorkers.length-1) { // Когда доходим до конца списка
                    if (find1 == true && find2 == false) { // Если первый найден и второй не найден
                        find2 = true;
                        i = -1; max = 0;
                    }
                    if (find1 == false) { // Если не первый найден
                        find1 = true;
                        i = -1; max = 0;
                    }
                }
            }
            if (id3 == 0) { // Это значит, что как минимум 1 работник ничего не делал
                out.println("В компании нет 3-х сотрудников с не нулевым количеством выполенных заданий!");
            }
            else { // Вывод топ-3
                out.println("Три лучших сотрудника компании `" + this.companyName + "`: ");
                out.println("1. " + listOfWorkers[id1].getFirstName() + " " + listOfWorkers[id1].getSecondName());
                out.println("2. " + listOfWorkers[id2].getFirstName() + " " + listOfWorkers[id2].getSecondName());
                out.println("3. " + listOfWorkers[id3].getFirstName() + " " + listOfWorkers[id3].getSecondName());
            }
        }
    }
    // Увольнение или понижение сотрудников с минимальной производительностью
    public void punishWorstWorkers() {
        int countOfTasks = (int)Math.pow(10,10); // Минимальное количесвто задач
        for (int i = 0; i < listOfWorkers.length; i++) { // Поиск наименьшего кол-ва
            if (listOfWorkers[i].getCountOfTasks() < countOfTasks) {
                countOfTasks = listOfWorkers[i].getCountOfTasks();
            }
        }
        for (int i = 0; i < listOfWorkers.length; i++) { // Поиск и "наказание" сотрудников с наимельшим кол-вом задач
            if (listOfWorkers[i].getCountOfTasks() == countOfTasks) {
                if (listOfWorkers[i].getLevelOfWorker() == 1) { // Если должность минимальная - увольняем
                    String fN = listOfWorkers[i].getFirstName();
                    String sN = listOfWorkers[i].getSecondName();
                    this.kickWorker(fN, sN);
                    i--;
                }
                else { // Иначе понижаем
                    listOfWorkers[i].levelDown();
                    out.println("Понижение сотрудника `" + listOfWorkers[i].getFirstName() + " " + listOfWorkers[i].getSecondName() + "` до должности: " + listOfWorkers[i].getLevelOfWorker());
                }
            }
        }
    }
}

// Класс работник (сотрудник)
class Worker {
    public static PrintStream out = System.out; // Для вывода сообщений на экран
    private String firstName; // Переменная для Имени
    private String secondName; // Переменная для Фамилии
    private int levelOfWorker; // Переменная для должности
    private int countOfTasks; // Переменная для заданий

    // Для нового сотрудника
    public Worker(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.levelOfWorker = 1;
        this.countOfTasks = 0;
    }
    // Если нет элементов
    public Worker() {
        out.println("Нужно указать Имя и Фамилию!");
    }
    // Если 1 элемент
    public Worker(String firstName) {
        out.println("Нужно указать Имя и Фамилию!");
    }
    // Возвращаем имя
    public String getFirstName() {
        return firstName;
    }
    // Возвращаем фамилию
    public String getSecondName() {
        return secondName;
    }
    // Возвращаем должность
    public int getLevelOfWorker() {
        return levelOfWorker;
    }
    // Возвращаем задания
    public int getCountOfTasks() {
        return countOfTasks;
    }
    // Добавляем задания
    public void addTask() {
        countOfTasks++;
    }
    // Повышение уровня
    public void levelUp() {
        levelOfWorker++;
    }
    // Понижение уровня
    public void levelDown() {
        if (levelOfWorker > 1) {
            levelOfWorker--;
        }
    }
    public void reset() {
        levelOfWorker = 1;
        countOfTasks = 0;
    }
}

public class Lab3 {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
    public static void main(String[] args) throws IOException {
        // Создание компании
        EAS company = new EAS("Some Cool Company");
        // Создание работников
        Worker w1 = new Worker("Иван", "Олегович");
        Worker w2 = new Worker("Яна", "Ант");
        Worker w3 = new Worker("Хлоя", "Дмитриевна");
        Worker w4 = new Worker("Дарья", "Ионановна");
        Worker w5 = new Worker("Антон", "Антонов");
        Worker w6 = new Worker("Олег", "Манго");
        Worker w7 = new Worker("Борис", "Безверь");
        // Добавление работников в компанию
        company.add(w1);
        company.add(w2);
        company.add(w3);
        company.add(w4);
        company.add(w5);
        company.add(w6);
        company.add(w7); out.println();
        // Выводим список сотрудников
        company.list(); out.println();
        // Раздача задач
        company.addTask(w1);
        company.addTask(w1);
        company.addTask(w1);
        company.addTask(w2);
        company.addTask(w2);
        company.addTask(w2);
        company.addTask(w3);
        company.addTask(w4);
        company.addTask(w6);
        company.addTask(w7);
        company.addTask(w7); out.println();
        // Повышаем
        company.levelUp(w7);
        company.levelUp(w7);
        company.levelUp(w1);
        company.levelUp(w1);
        company.levelUp(w2);
        company.levelUp(w4); out.println();
        // Даём задание со сложностью
        company.addTaskWithDifficult(w1, 7);
        company.addTaskWithDifficult(w1, 3);
        company.addTaskWithDifficult(w7, 2); out.println();
        // Увольняем работника
        company.kickWorker("Такого", "Нет");
        company.kickWorker("Антон", "Антонов"); out.println();
        // Опять выводим
        company.list(); out.println();
        // Увольняем работников с низкой производительностью
        company.kickBadWorkers(2); out.println();
        // Новый список
        company.list(); out.println();
        // Повышаем лучших
        company.levelUpBestWorkers(); out.println();
        // Понижаем плохих
        company.levelDownWWBP(); out.println();
        // Сотрудник + список
        company.add(w5);
        company.add(w4);
        company.list(); out.println();
        // Доска почёта
        company.honorBoard(); out.println();
        // Наказываем худших
        company.punishWorstWorkers();
    }
}