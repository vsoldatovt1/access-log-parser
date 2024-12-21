import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> urls;
    private HashMap<String, Integer> browserStatistic;
    private HashMap<String, Double> browserStatisticByPercent;


    public Statistics() {
        this.totalTraffic = 0;
        this.urls = new HashSet<>();
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.browserStatistic = new HashMap<String, Integer>() {{
            put("Chrome", 0);
            put("Edge", 0);
            put("Safari", 0);
            put("Opera", 0);
            put("Firefox", 0);
            put("UnknownBrowser", 0);
        }};
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (logEntry.getDateRequest().isBefore(minTime)) {
            this.minTime = logEntry.getDateRequest();
        }

        if (logEntry.getDateRequest().isAfter(maxTime)) {
            this.maxTime = logEntry.getDateRequest();
        }
        if (logEntry.getResponseCode() == 404) {
            System.out.println("yes");
            urls.add(logEntry.getIpAddr());
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Chrome")) {
            browserStatistic.put("Chrome", browserStatistic.get("Chrome") + 1);
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Edge")) {
            browserStatistic.put("Edge", browserStatistic.get("Edge") + 1);
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Safari")) {
            browserStatistic.put("Safari", browserStatistic.get("Safari") + 1);
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Opera")) {
            browserStatistic.put("Opera", browserStatistic.get("Opera") + 1);
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Firefox")) {
            browserStatistic.put("Firefox", browserStatistic.get("Firefox") + 1);
        }


        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Unknown browser")) {
            browserStatistic.put("UnknownBrowser", browserStatistic.get("UnknownBrowser") + 1);
        }
    }

    public int getTrafficRate() {
        int hours = (int) Duration.between(minTime, maxTime).toHours();
        return totalTraffic / hours;
    }

    public HashSet<String> getUrls() {
        return this.urls;
    }

    public HashMap<String, Double> getBrowserStatisticByPercent() {
        this.browserStatisticByPercent = new HashMap<>();
        int sumOfAllBrowsers = browserStatistic.get("UnknownBrowser") + browserStatistic.get("Opera") + browserStatistic.get("Safari") + browserStatistic.get("Edge") + browserStatistic.get("Chrome") + browserStatistic.get("Firefox");
        double percentOfChrome = (double) browserStatistic.get("Chrome") / sumOfAllBrowsers;
        double percentOfFirefox = (double) browserStatistic.get("Firefox") / sumOfAllBrowsers;
        double percentOfOpera = (double) browserStatistic.get("Opera") / sumOfAllBrowsers;
        double percentOfSafari = (double) browserStatistic.get("Safari") / sumOfAllBrowsers;
        double percentOfEdge = (double) browserStatistic.get("Edge") / sumOfAllBrowsers;
        double percentOfUnknownBrowsers = (double) browserStatistic.get("UnknownBrowser") / sumOfAllBrowsers;
        this.browserStatisticByPercent.put("Chrome", percentOfChrome);
        this.browserStatisticByPercent.put("Firefox", percentOfFirefox);
        this.browserStatisticByPercent.put("Opera", percentOfOpera);
        this.browserStatisticByPercent.put("Safari", percentOfSafari);
        this.browserStatisticByPercent.put("Edge", percentOfEdge);
        this.browserStatisticByPercent.put("Unknown browsers", percentOfUnknownBrowsers);
        return this.browserStatisticByPercent;
    }
}
