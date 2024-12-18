import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> urls;
    private HashMap<String, Double> osStatistic;
    private int countOfEdgeBrowser;
    private int countOfChromeBrowser;
    private int countOfSafariBrowser;
    private int countOfOperaBrowser;
    private int countOfUnknownBrowsers;
    private int countOfFirefoxBrowser;


    public Statistics() {
        this.totalTraffic = 0;
        this.urls = new HashSet<>();
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.osStatistic = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (logEntry.getDateRequest().isBefore(minTime)) {
            this.minTime = logEntry.getDateRequest();
        }

        if (logEntry.getDateRequest().isAfter(maxTime)) {
            this.maxTime = logEntry.getDateRequest();
        }
        if (logEntry.getResponseCode() == 200) {
            System.out.println("yes");
            urls.add(logEntry.getIpAddr());
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Chrome")) {
            countOfChromeBrowser++;
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Edge")) {
            countOfEdgeBrowser++;
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Safari")) {
            countOfSafariBrowser++;
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Opera")) {
            countOfOperaBrowser++;
        }

        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Firefox")) {
            countOfFirefoxBrowser++;
        }


        if (logEntry.getUserAgent().getBrowser().equalsIgnoreCase("Unknown browser")) {
            countOfUnknownBrowsers++;
        }
    }

    public int getTrafficRate() {
        int hours = (int) Duration.between(minTime, maxTime).toHours();
        return totalTraffic / hours;
    }

    public HashSet<String> getUrls() {
        return this.urls;
    }

    public HashMap<String, Double> getOsStatistic() {
        int sumOfAllBrowsers = countOfUnknownBrowsers + countOfOperaBrowser + countOfSafariBrowser + countOfEdgeBrowser
                + countOfChromeBrowser + countOfFirefoxBrowser;
        double percentOfChrome = (double) countOfChromeBrowser / sumOfAllBrowsers;
        double percentOfFirefox = (double) countOfFirefoxBrowser / sumOfAllBrowsers;
        double percentOfOpera = (double) countOfOperaBrowser / sumOfAllBrowsers;
        double percentOfSafari = (double) countOfSafariBrowser / sumOfAllBrowsers;
        double percentOfEdge = (double) countOfEdgeBrowser / sumOfAllBrowsers;
        double percentOfUnknownBrowsers = (double) countOfUnknownBrowsers / sumOfAllBrowsers;
        this.osStatistic.put("Chrome", percentOfChrome);
        this.osStatistic.put("Firefox", percentOfFirefox);
        this.osStatistic.put("Opera", percentOfOpera);
        this.osStatistic.put("Safari", percentOfSafari);
        this.osStatistic.put("Edge", percentOfEdge);
        this.osStatistic.put("Unknown browsers", percentOfUnknownBrowsers);
        return this.osStatistic;
    }
}
