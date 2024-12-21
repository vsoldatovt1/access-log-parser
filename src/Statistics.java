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
    private HashMap<String, Integer> osStatistics;
    private HashMap<String, Double> osStatisticByPercent;



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
        this.osStatistics = new HashMap<String,Integer>();

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

        try { osStatistics.put(logEntry.getUserAgent().getOs(), osStatistics.get(logEntry.getUserAgent().getOs())+1); } catch (Exception e) {
            osStatistics.put(logEntry.getUserAgent().getOs(),0);
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

    public HashMap<String, Double> getOsStatisticByPercent() {
        this.osStatisticByPercent = new HashMap<>();
        int sumOfAllOs = osStatistics.get("Windows NT") + osStatistics.get("Mac OS X") + osStatistics.get("Linux") + osStatistics.get("Unknown os");
        double percentofWindows = (double) osStatistics.get("Windows NT") / sumOfAllOs;
        double percentOfMacOS = (double) osStatistics.get("Mac OS X") / sumOfAllOs;
        double percentOfUnknownOS = (double) osStatistics.get("Linux") / sumOfAllOs;
        double percentOfLinux = (double) osStatistics.get("Unknown os") / sumOfAllOs;
        this.osStatisticByPercent.put("Windows NT",percentofWindows);
        this.osStatisticByPercent.put("Mac OS X",percentOfMacOS);
        this.osStatisticByPercent.put("Linux",percentOfLinux);
        this.osStatisticByPercent.put("Unknown os",percentOfUnknownOS);
        return osStatisticByPercent;
    }
}
