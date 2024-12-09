public class Keyboard {
    public static void main(String[] args) {
        Breakingkeyboard keyboard = new Breakingkeyboard();

        // Проверка работоспособности методов
        System.out.println("Сломанные клавиши: " + keyboard.brokenKeys());
        keyboard.breakKey("a");
        System.out.println("Сломанные клавиши после поломки 'a': " + keyboard.brokenKeys());
        keyboard.fixKey("a");
        System.out.println("Сломанные клавиши после восстановления 'a': " + keyboard.brokenKeys());

        System.out.println("Можно ли напечатать 'Oleg': " + keyboard.canPrintWord("Oleg"));

        System.out.println("Необходимые клавиши для слова 'Oleg': " + keyboard.keysForWord("Oleg"));

        System.out.println("Печатаемые слова из строки 'Oleg sleep': " + keyboard.countPrintableWords("Oleg sleep"));

        System.out.println("Анализ напечатанного слова 'Ole0':");
        keyboard.analyzeTypedWord("Oleg", "Ole0");
        System.out.println("Сломанные клавиши после анализа: " + keyboard.brokenKeys());

        System.out.println("Переключение состояния клавиш 'Oleg':");
        keyboard.stringKeys("Oleg");
        System.out.println("Сломанные клавиши после переключения: " + keyboard.brokenKeys());

        // Проверка сломанных букв
        System.out.println("Есть сломанные буквы : " + keyboard.brokenLetters());

        // Проверка всех цифр
        System.out.println("Есть сломанные цифры: " + keyboard.brokenDigits());
    }
}


class Breakingkeyboard {
    private boolean[] keycondition; // массив для хранения состояния клавиш
    private  String[] keys; // массив для хранения символов клавиш

    public Breakingkeyboard() {
        keycondition = new boolean[63]; // 26+26+10 + 1
        keys = new String[63];
        int i = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            keys[i++] = c + "";
            keycondition[i - 1] = true; // Все клавиши исправны
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            keys[i++] = c + "";
            keycondition[i - 1] = true; // Все клавиши исправны
        }
        for (char c = '0'; c <= '9'; c++) {
            keys[i++] = c + "";
            keycondition[i - 1] = true; // Все клавиши исправны
        }
        keys[62] = "SHIFT";
        keycondition[62] = true;
    }

    // Метод для поиска индекса клавиши
    private int getKeyIndex(String key) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1; // Если клавиша не найдена
    }

    // 1. Вывод сломанных клавиш
    public String brokenKeys() {
        String brokenKeys = ""; // Начинаем с пустой строки
        boolean[] seen = new boolean[26]; // Массив для отслеживания уникальных символов (a-z)

        for (int i = 0; i < keycondition.length; i++) {
            if (!keycondition[i]) {
                char c = keys[i].charAt(0); // Получаем символ

                // Проверяем, является ли символ буквой a-z или A-Z
                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                    int index;

                    // Используем if для определения индекса
                    if (c >= 'a' && c <= 'z') {
                        index = c - 'a'; // Если строчная буква, получаем индекс
                    } else { // Если заглавная буква
                        index = c - 'A'; // Получаем индекс заглавной буквы
                    }

                    if (!seen[index]) { // Проверяем, был ли этот символ уже добавлен
                        seen[index] = true; // Отмечаем, что символ был добавлен
                        brokenKeys += (char) (index + 'a') + ", "; // Добавляем символ в нижнем регистре
                    }
                } else {
                    // Если это цифра или другой символ, просто добавляем его
                    brokenKeys += c + ", "; // Добавляем символ без изменения
                }
            }
        }

        // Проверяем состояние клавиши SHIFT
        if (!keycondition[62]) {
            brokenKeys += "Сломан SHIFT"; // Добавляем сообщение о сломанной клавише SHIFT
        }

        // Убираем последний запятую и пробел, если есть сломанные клавиши
        if (brokenKeys.length() > 0) {
            // Убираем последнюю запятую и пробел, если они есть
            if (brokenKeys.length() > 2) {
                brokenKeys = brokenKeys.substring(0, brokenKeys.length() - 2);
            }
        } else {
            return "Нет сломанных клавиш";
        }
        return brokenKeys;
    }

    // 2. Сломать клавишу
    public void breakKey(String key) {
        int i = getKeyIndex(key);
        if (i != -1) {
            keycondition[i] = false;
        }
    }
    // 3. Проверка возможности напечатать символ
    public boolean canPrint(String key){
        int i = getKeyIndex(key);
        return  i!=-1 && keycondition[i];
    }
    // 4. Восстановление клавиши
    public void fixKey(String key) {
        int i = getKeyIndex(key);
        if (i != -1) {
            keycondition[i] = true;
        }
    }
    // 5. Проверка возможности напечатать слово
    public boolean canPrintWord(String word) {
        for (char c : word.toCharArray()) {
            if (c >= 'A' && c <= 'Z') { // Проверяем, является ли символ заглавным
                char lowerKey = (char) (c + 32); // Преобразуем в строчную букву
                if (!canPrint("SHIFT") || !canPrint(String.valueOf(lowerKey))) {
                    return false; // Если Shift сломан или соответствующая строчная буква сломана
                }
            } else {
                if (!canPrint(String.valueOf(c))) { // Если строчная буква сломана
                    return false;
                }
            }
        }
        return true; // Все буквы могут быть напечатаны
    }
    // 6. Проверка сломанных букв
    public boolean brokenLetters() {
        for (int i = 0; i < 26; i++) { // Проверяем только буквы a-z
            if (!keycondition[i]) {
                return true;
            }
        }
        return false;
    }
    // 7. Проверка всех цифр
    public boolean brokenDigits() {
        for (int i = 52; i < 62; i++) { // Проверяем только цифры 0-9
            if (keycondition[i]) {
                return false;
            }
        }
        return true;
    }
    // 8. Необходимые клавиши для слова
    public String keysForWord(String word) {
        String requiredKeys = ""; // Строка для хранения необходимых клавиш

        boolean requiresShift = false; // Переменная для проверки необходимости Shift
        for (char c : word.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                requiresShift = true; // Если символ заглавный, требуется Shift
            }
            if (!canPrint(String.valueOf(c))) {
                requiredKeys += c + ", "; // Добавляем к строке, если клавиша сломана
            }
        }
        // Добавляем Shift в требуемые клавиши, если он нужен
        if (requiresShift && !canPrint("SHIFT")) {
            requiredKeys += "SHIFT, ";
        }
        if (requiredKeys.length() > 0) {
            requiredKeys = requiredKeys.substring(0, requiredKeys.length() - 2); // Убираем последнюю запятую
            return requiredKeys; // Возвращаем список сломанных клавиш
        } else {
            return "null"; // Если все клавиши исправны
        }
    }
    // 9. Печатаемые слова из строки
    public int countPrintableWords(String text) {
        int count = 0;
        String currentWord = ""; // Для хранения текущего слова
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i); // Получаем текущий символ
            if (c == ' ') { // Если пробел, проверяем слово
                if (canPrintWord(currentWord)) {
                    count++; // Увеличиваем счетчик, если слово можно напечатать
                }
                currentWord = ""; // Сбрасываем текущее слово
            } else {
                currentWord += c; // Добавляем символ к текущему слову
            }
        }
        // Проверяем последнее слово
        if (canPrintWord(currentWord)) {
            count++;
        }
        return count;
    }
    // 10. Анализ напечатанного слова
    public void analyzeTypedWord(String original, String printed) {
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i); // Получаем символ оригинала
            if (printed.indexOf(c) == -1) { // Если символ не напечатан
                breakKey(String.valueOf(c)); // Ломаем клавишу
            } else {
                fixKey(String.valueOf(c)); // Восстанавливаем клавишу
            }
        }
    }
    // 11. Восстановление или поломка строки
    public void stringKeys(String ke) {
        for (int i = 0; i < ke.length(); i++) {
            char c = ke.charAt(i);
            for (int j = 0; j < keys.length; j++) {
                // Сравниваем без учета регистра
                if (keys[j].equalsIgnoreCase(String.valueOf(c))) {
                    keycondition[j] = !keycondition[j]; // Переключаем состояние клавиши
                    break;
                }
            }
        }
    }

}