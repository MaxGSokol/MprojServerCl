package output;

import runnable.DataOutputer;
import serves.OutputDataMarks;

import java.util.TreeMap;

/**
 * Интерфейс {@code FileOutputType} реализуется в классах отвечающих за различный вывод данных.
 * <p>Обеспечивает связывание этих классов для работы фабрики в классе {@link  DataOutputer}.
 */

public interface FileOutputType {
    /**
     * Переопределяется в классе реализующем интерфейс в соответствии с логикой и форматом вывода
     * данных.
     *
     * @param map - {@code HashMap<String, String>} содержит данные для вывода.
     */
    void outputData(TreeMap<OutputDataMarks, String> map);

}
