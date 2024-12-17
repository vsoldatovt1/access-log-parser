import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;


    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
    }

    public void addEntry (LogEntry logEntry) {
        totalTraffic+=logEntry.getResponseSize();
        if (logEntry.getDateRequest().isBefore(minTime)) {
            this.minTime = logEntry.getDateRequest();
        }

        if (logEntry.getDateRequest().isAfter(maxTime)) {
            this.maxTime = logEntry.getDateRequest();
        }
    }

    public int getTrafficRate() {
        int hours = (int) Duration.between(minTime,maxTime).toHours();
        return totalTraffic/hours;
    }




}
