import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Converter implements ActionListener {
    private Window view;
    private String key = "dc89635a28f52945354c";
    private ObjectMapper objectMapper = new ObjectMapper();

    public Converter (Window view){
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String from  = view.getjComboBox1().toString();
        String to = view.getjComboBox2().toString();
        float value = Integer.parseInt(view.getjTextField());
        float convertion = getCurrencyValue(from, to);
        float  result = value * convertion;
        view.setjLabel(String.format("Value: %.2f", result));
    }

    public float getCurrencyValue(String from, String to) {
        float value = 0;

        try {
            URL url = new URL("https://free.currconv.com/api/v7/convert?q=" + from + "_" + to + "&compact=ultra&apiKey=" + key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            String currency = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()
                    )
            ).lines().collect(Collectors.joining());

            JsonNode jsonNode = objectMapper.readTree(currency);
            value = jsonNode.get(from+"_"+to).floatValue();

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    public ArrayList<String> getAllCurrencies() {
        ArrayList<String> currencyList = new ArrayList<String>();
        try {
            URL url = new URL("https://free.currconv.com/api/v7/currencies?apiKey=" + key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            String currency = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()
                    )
            ).lines().collect(Collectors.joining());

            connection.disconnect();

            JsonNode jsonNode = objectMapper.readTree(currency);
            for(JsonNode json : jsonNode.get("results"))
                currencyList.add(json.get("id").toString());
            Collections.sort(currencyList);
        } catch (MalformedURLException e) {
            currencyList.add("API is not working!");
            e.printStackTrace();
        } catch (IOException e) {
            currencyList.add("API is not working!");
            e.printStackTrace();
        }
        return currencyList;
    }
}
