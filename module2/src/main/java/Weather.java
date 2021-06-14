import java.util.Date;

public class Weather {
    int id;
    int regionId;
    Date date;
    float temperature;
    float precipitation;

    public Weather(int id, int regionId, Date date, float temperature, float precipitation) {
        this.id = id;
        this.regionId = regionId;
        this.date = date;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public Weather() {
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", regionId=" + regionId +
                ", date=" + date +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getRegionId() {
        return regionId;
    }

    public Date getDate() {
        return date;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPrecipitation() {
        return precipitation;
    }
}
