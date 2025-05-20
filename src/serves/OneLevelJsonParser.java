package serves;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;

public class OneLevelJsonParser {


    private static JsonObject getJsonObj(String[] objFields) {
        JsonObject jsonObject = new JsonObject();
        String[] objFieldsValue;
        for (String string : objFields) {
            objFieldsValue = string.split(":");
            jsonObject.addProperty(
                    objFieldsValue[0].trim().replaceAll("\"", ""),
                    objFieldsValue[1].trim().replaceAll("\"", ""));

        }
        return jsonObject;
    }

    private static JsonArray getObjFields(String[] objBlocks) {
        JsonArray jsonArray = new JsonArray();
        for (String s : objBlocks) {
            String[] objFields = s.split(",");
            jsonArray.add(getJsonObj(objFields));
        }
        return jsonArray;
    }

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
        return getObjFields(objBlocks);
    }

}
