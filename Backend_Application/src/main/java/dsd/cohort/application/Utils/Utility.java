package dsd.cohort.application.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dsd.cohort.application.ingredient.IngredientEntity;
import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.ingredient.IngredientService;

@Component
public class Utility {

    private DecimalFormat df = new DecimalFormat("#.00");

    private IngredientRepository ingredientRepository;

    private IngredientService ingredientService;

    private long lastCallTime;

    public Utility(IngredientRepository ingredientRepository, IngredientService ingredientService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
        this.lastCallTime = System.currentTimeMillis() / 1000;
    }

    public String consolidateString(List<String> list) {

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }

    public List<String> tokenizeString(String str) {

        // slice string into a list of string tokens that are 255 characters or less
        List<String> tokens = new ArrayList<>();
        int index = 0;
        while (index < str.length()) {
            int endIndex = Math.min(index + 255, str.length());
            tokens.add(str.substring(index, endIndex));
            index = endIndex;
        }

        return tokens;
    }

    public JsonNode stringToJson(String response) throws JsonProcessingException, JsonMappingException {
        // declare variables for parsing
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        // parse response to json
        jsonNode = mapper.readTree(response);

        return jsonNode;
    }

    public boolean compareTime(long time) {

        if (time - 60 > lastCallTime) {
            lastCallTime = time;
            return true;
        }

        return false;
    }
}
