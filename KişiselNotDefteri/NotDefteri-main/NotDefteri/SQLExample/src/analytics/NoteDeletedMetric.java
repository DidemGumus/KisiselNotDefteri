package analytics;

public class NoteDeletedMetric extends AnalyticsMetric {
    @Override
    public void recordEvent(int userId) {
        userMetrics.put(userId, userMetrics.getOrDefault(userId, 0) + 1);
    }
}
