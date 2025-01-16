package analytics;

import java.util.HashMap;
import java.util.Map;

public abstract class AnalyticsMetric {
    protected Map<Integer, Integer> userMetrics = new HashMap<>(); // Kullanıcı ID ve metrik değeri

    public abstract void recordEvent(int userId);

    public int getMetricForUser(int userId) {
        return userMetrics.getOrDefault(userId, 0);
    }
}
