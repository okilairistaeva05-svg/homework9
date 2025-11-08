import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// =======================
// ======= ФАСАД =========
// =======================

// Подсистема 1: Телевизор
class TV {
    public void on() {
        System.out.println("Телевизор включен");
    }

    public void off() {
        System.out.println("Телевизор выключен");
    }

    public void setChannel(int channel) {
        System.out.println("Канал переключен на " + channel);
    }
}

// Подсистема 2: Аудиосистема
class AudioSystem {
    private int volume = 5;

    public void on() {
        System.out.println("Аудиосистема включена");
    }

    public void off() {
        System.out.println("Аудиосистема выключена");
    }

    public void setVolume(int volume) {
        this.volume = volume;
        System.out.println("Громкость установлена на " + volume);
    }

    public int getVolume() {
        return volume;
    }
}

// Подсистема 3: DVD-проигрыватель
class DVDPlayer {
    public void on() {
        System.out.println("DVD-проигрыватель включен");
    }

    public void play() {
        System.out.println("Воспроизведение DVD началось");
    }

    public void pause() {
        System.out.println("Пауза DVD");
    }

    public void stop() {
        System.out.println("Остановка DVD");
    }

    public void off() {
        System.out.println("DVD-проигрыватель выключен");
    }
}

// Подсистема 4: Игровая консоль
class GameConsole {
    public void on() {
        System.out.println("Игровая консоль включена");
    }

    public void startGame(String gameName) {
        System.out.println("Запуск игры: " + gameName);
    }

    public void off() {
        System.out.println("Игровая консоль выключена");
    }
}

// Фасад
class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    public void watchMovie() {
        System.out.println("\n🎬 Подготовка к просмотру фильма...");
        tv.on();
        audio.on();
        audio.setVolume(7);
        dvd.on();
        dvd.play();
        System.out.println("Фильм начался! 🍿");
    }

    public void stopMovie() {
        System.out.println("\n⏹ Остановка фильма...");
        dvd.stop();
        dvd.off();
        audio.off();
        tv.off();
    }

    public void playGame(String gameName) {
        System.out.println("\n🎮 Подготовка к запуску игры...");
        tv.on();
        audio.on();
        audio.setVolume(8);
        console.on();
        console.startGame(gameName);
    }

    public void listenToMusic() {
        System.out.println("\n🎵 Включение режима прослушивания музыки...");
        tv.on();
        audio.on();
        audio.setVolume(6);
        System.out.println("Музыка включена на телевизоре через аудиосистему!");
    }

    public void setVolume(int level) {
        audio.setVolume(level);
    }

    public void turnOffAll() {
        System.out.println("\n🔌 Выключение всей системы...");
        tv.off();
        audio.off();
        dvd.off();
        console.off();
    }
}


// ============================
// ======= КОМПОНОВЩИК ========
// ============================

abstract class FileSystemComponent {
    protected String name;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display(String indent);
    public abstract int getSize();
}

// Файл
class File extends FileSystemComponent {
    private int size;

    public File(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- Файл: " + name + " (" + size + " КБ)");
    }

    @Override
    public int getSize() {
        return size;
    }
}

// Папка
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void add(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
            System.out.println("Добавлено: " + component.name + " в " + name);
        } else {
            System.out.println("Компонент " + component.name + " уже существует в " + name);
        }
    }

    public void remove(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
            System.out.println("Удалено: " + component.name + " из " + name);
        } else {
            System.out.println("Компонент " + component.name + " не найден в " + name);
        }
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Папка: " + name);
        for (FileSystemComponent component : components) {
            component.display(indent + "   ");
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;
        for (FileSystemComponent component : components) {
            totalSize += component.getSize();
        }
        return totalSize;
    }
}


// =============================
// ======= КЛИЕНТСКИЙ КОД ======
// =============================

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите демонстрацию:");
        System.out.println("1 — Фасад (мультимедиа-система)");
        System.out.println("2 — Компоновщик (файловая система)");
        System.out.print("Ваш выбор: ");
        int choice = sc.nextInt();

        if (choice == 1) {
            runFacadeDemo();
        } else if (choice == 2) {
            runCompositeDemo();
        } else {
            System.out.println("Неверный выбор!");
        }
    }

    // Демонстрация паттерна ФАСАД
    public static void runFacadeDemo() {
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audio, dvd, console);

        homeTheater.watchMovie();
        homeTheater.stopMovie();
        homeTheater.playGame("GTA V");
        homeTheater.listenToMusic();
        homeTheater.setVolume(10);
        homeTheater.turnOffAll();
    }

    // Демонстрация паттерна КОМПОНОВЩИК
    public static void runCompositeDemo() {
        Directory root = new Directory("Root");
        Directory documents = new Directory("Документы");
        Directory images = new Directory("Изображения");
        Directory music = new Directory("Музыка");

        File file1 = new File("Реферат.docx", 120);
        File file2 = new File("Фото1.jpg", 350);
        File file3 = new File("Песня.mp3", 5000);

        documents.add(file1);
        images.add(file2);
        music.add(file3);

        root.add(documents);
        root.add(images);
        root.add(music);

        System.out.println("\n📂 Структура файловой системы:");
        root.display("");

        System.out.println("\nОбщий размер папки Root: " + root.getSize() + " КБ");
    }
}
