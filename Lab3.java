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
                out.println(listOfWorkers[i].getFirstName() + " " + listOfWorkers[i].getSecondName() + " - " + listOfWorkers[i].getLevelOfWorker());
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
        double perf = Math.pow(10, 10);
        int id = 0;
        for (int i = 0; i < listOfWorkers.length; i++) {
            if ((listOfWorkers[i].getCountOfTasks()/(double)listOfWorkers[i].getLevelOfWorker()) < perf) {
                perf = listOfWorkers[i].getCountOfTasks()/(double)listOfWorkers[i].getLevelOfWorker();
                id = i;
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
}

public class Lab3 {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
    public static void main(String[] args) throws IOException {
        EAS company = new EAS("Some Cool Company");
        Worker w1 = new Worker("Матвей", "Худяков");
        Worker w2 = new Worker("Яна", "Ант");
        Worker w3 = new Worker("Ян", "Ант");
        Worker w4 = new Worker("Циц", "Худяков");
        Worker w5 = new Worker("Aa", "Худяков");
        Worker w6 = new Worker("Aa", "Худякова");
        Worker w7 = new Worker("Борис", "Бритва");

        company.add(w1);
        company.add(w2);
        company.add(w3);
        company.add(w4);
        company.add(w5);
        company.add(w6);
        company.add(w7);
        company.addTask(w1);
        company.addTask(w7);
        company.addTask(w7);
        company.levelUp(w1);
        company.addTaskWithDifficult(w1, 2);
        company.list();
        company.kickWorker("Яна", "Ант");
        company.list();
        company.add(w2);
        company.list();
        company.kickBadWorkers(2);
        company.levelUpBestWorkers();
        company.list();
    }
}