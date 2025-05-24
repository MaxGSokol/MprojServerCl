package output;

import runnable.DataOutputer;
import serves.ClientData;

/**
 * Интерфейс {@code FileOutputType} реализуется в классах отвечающих за различный вывод данных.
 * <p>Обеспечивает связывание этих классов для работы фабрики в классе {@link  DataOutputer}.
 */

public interface FileOutputType {
    /**
     * Переопределяется в классе реализующем интерфейс в соответствии с логикой и форматом вывода
     * данных.
     *
     * @param clientData Объект {@code POJO} класса хранящий данные.
     */
    void outputData(ClientData clientData);

}
