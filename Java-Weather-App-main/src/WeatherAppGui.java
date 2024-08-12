import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGui extends JFrame {

    private JSONObject weatherData;

    public WeatherAppGui() {
        super("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        // Text field for user input
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(20, 30, 360, 50);
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 22));
        searchTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(searchTextField);

        // Button for searching weather
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(390, 30, 60, 50);
        add(searchButton);

        // Label for weather condition image
        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(75, 100, 350, 200);
        weatherConditionImage.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionImage);

        // Label for temperature
        JLabel temperatureText = new JLabel("10°C");
        temperatureText.setBounds(0, 320, 500, 60);
        temperatureText.setFont(new Font("Arial", Font.BOLD, 50));
        temperatureText.setForeground(Color.BLUE);
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // Label for weather condition description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 380, 500, 40);
        weatherConditionDesc.setFont(new Font("Arial", Font.PLAIN, 30));
        weatherConditionDesc.setForeground(Color.DARK_GRAY);
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // Label for humidity image and text
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(60, 460, 60, 60);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(130, 470, 100, 50);
        humidityText.setFont(new Font("Arial", Font.PLAIN, 18));
        add(humidityText);

        // Label for windspeed image and text
        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(270, 460, 60, 60);
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(340, 470, 120, 50);
        windspeedText.setFont(new Font("Arial", Font.PLAIN, 18));
        add(windspeedText);

        // Action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve user input
                String userInput = searchTextField.getText();

                // Check if the input is empty
                if (userInput.replaceAll("\\s", "").length() <= 0) {
                    return;
                }

                // Get weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // Update weather condition image
                String weatherCondition = (String) weatherData.get("weather_condition");
                switch (weatherCondition) {
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }

                // Update temperature
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(String.format("%.1f°C", temperature));

                // Update weather condition description
                weatherConditionDesc.setText(weatherCondition);

                // Update humidity
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // Update windspeed
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
        });
    }

    // Method to load image from file
    private ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Could not find resource");
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherAppGui appGui = new WeatherAppGui();
            appGui.setVisible(true);
        });
    }
}
