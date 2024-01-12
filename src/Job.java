public class Job {
    String jobName;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnAroundTime;
    int waitingTime;

    public Job(String jobName, int arrivalTime, int burstTime) {
        this.jobName = jobName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }
}
