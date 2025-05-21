package serves;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Считывает файл формата {@code json} записанного в формате {@link JsonArray}
 * и возвращает его в виде массива {@code JsonArray}.
 */
public class OneLevelJsonParser {

    /**
     * Считывает данные. Подвергает частичному форматированию. Преобразует результат в массив строк {@code String[]}.
     *<p> В операторе {@code return} работает метод {@link #getJsonArray(String[] objBlocks)}
     * параметром которому подаёт созданный массив.
     *
     * @param bufferedReader - объект {@link BufferedReader}.
     * @return Возвращает {@link JsonArray} - массив данных записанных в формате
     * {@code jsonArray} библиотеки {@code gson-2.13}.
     * Который в свою очередь является результатом работы метода {@code getJsonArray}.
     *<p> В случае отсутствия данных в файле, возвращает новый массив {@code new JsonArray()}.
     * @throws IOException
     *
     * @see #getJsonArray(String[])
     * @see #getJsonObj(String[])
     */
    public static JsonArray parseOneLevelJsonToArray(BufferedReader bufferedReader) throws IOException {
        String jsonFileToString;
        StringBuilder stringBuilder = new StringBuilder();
        while ((jsonFileToString = bufferedReader.readLine()) != null) {
            if (jsonFileToString.equals("[") || jsonFileToString.equals("]") ||
                    jsonFileToString.trim().equals("{") || jsonFileToString.trim().equals("}")) {
                continue;
            }
            stringBuilder.append(jsonFileToString);
        }
        String objBlock = stringBuilder.toString();
        if (objBlock.isEmpty()) {
            return new JsonArray();
        }
        String[] objBlocks = objBlock.split("},");
        return getJsonArray(objBlocks);
    }

    /**
     * Принимает в качестве параметра массив строк {@code String[]},
     * отформатированных до строк содержащих ключ и значение.
     * <ul>
     *
     *  <li> Отделяет ключ от значения и формирует из них новый объект типа {@link JsonObject}.
     *
     *  <li> Работает внутри метода {@link #getJsonArray(String[])}
     *
     * </ul>
     * @param objFields - Массив строк {@code String[]}.
     *
     * @return - Возвращает новый объект {@code JsonObject}.
     *
     *  @see #getJsonArray(String[])
     */
    private static JsonObject getJsonObj(String[] objFields) {
        JsonObject jsonObject = new JsonObject();
        String[] objFieldsValue;
        for (String string : objFields) {
            objFieldsValue = string.split(":");
            jsonObject.addProperty(
                    objFieldsValue[0].trim().replaceAll("\"", "")
                            .replaceAll("\\{","").replaceAll("\\[",""),
                    objFieldsValue[1].trim().replaceAll("\"", ""));

        }
        return jsonObject;
    }

    /**
     * Принимает в качестве параметра массив строк {@code String[]},
     * полученных в процессе работы метода {@link #parseOneLevelJsonToArray(BufferedReader)}.
     * <ul>
     *
     *  <li> Преобразует их в массив строк {@code String[]} содержащих ключ - значение.
     *
     *  <li> Передаёт массив работающему внутри методу {@link #getJsonObj(String[])}.
     *
     *  <li> Получает в результате его работы объект {@code JsonObject}.
     *
     *  <li> Создаёт из объектов {@code JsonObject} массив {@code JsonArray}.
     *
     * </ul>
     * Работает в операторе {@code return} метода {@code  parseOneLevelJsonToArray}.
     *
     * @param objBlocks - Массив строк {@code String[]}.
     * @return Массив - {@code  JsonArray}.
     *
     * @see #parseOneLevelJsonToArray(BufferedReader)
     * @see #getJsonObj(String[])
     */
    private static JsonArray getJsonArray(String[] objBlocks) {
        JsonArray jsonArray = new JsonArray();
        for (String s : objBlocks) {
            String[] objFields = s.split(",");
            jsonArray.add(getJsonObj(objFields));
        }
        return jsonArray;
    }

}
