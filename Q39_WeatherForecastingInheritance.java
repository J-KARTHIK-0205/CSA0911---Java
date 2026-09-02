class Forecast {
    protected String date;
    protected double temperature;

    public Forecast(String date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }
}

class SunnyForecast extends Forecast {
    private double uvIndex;

    public SunnyForecast(String date, double temperature, double uvIndex) {
        super(date, temperature);
        this.uvIndex = uvIndex;
    }

    public void predict() {
        System.out.println(date + ": Sunny, " + temperature + "C, UV Index: " + uvIndex);
    }
}

class RainyForecast extends Forecast {
    private double rainfallMm;

    public RainyForecast(String date, double temperature, double rainfallMm) {
        super(date, temperature);
        this.rainfallMm = rainfallMm;
    }

    public void predict() {
        System.out.println(date + ": Rainy, " + temperature + "C, Rainfall: " + rainfallMm + "mm");
    }
}

public class Q39_WeatherForecastingInheritance {
    public static void main(String[] args) {
        SunnyForecast sunny = new SunnyForecast("2026-08-30", 32.0, 8.5);
        RainyForecast rainy = new RainyForecast("2026-08-31", 24.0, 15.0);

        sunny.predict();
        rainy.predict();
    }
}
