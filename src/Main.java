import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Job> jobs = new ArrayList<>();
        // The jobs array list stores the process inputted by the user.
        ArrayList<Job> queue = new ArrayList<>();
        // If there is a process currently using the CPU then incoming process will be place in the queue.

        Job ongoingJob = null;
        int ongoingJobEstimatedCompletionTime = 0;
        int currentTime = 0;

        while (true) {
            // Ask user to input the job name, arrival time and burst time. For example: A 2 3 where A is the process's
            // name, 2 is the process's arrival time and 3 is the process's burst time.
            System.out.print("Enter (job name, arrival time, burst time) or type 'confirm ' to finish: ");
            Scanner scanner = new Scanner(System.in);
            String[] input = scanner.nextLine().split(" ");

            if ("confirm".equals(input[0])) {
                break;
            }
            jobs.add(new Job(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2])));
        }

        while (true) {
            for (int i = 0; i < jobs.size(); i++) {
                Job currJob = jobs.get(i);
                if (currJob.arrivalTime == currentTime) {
                    queue.add(jobs.remove(i));
                    // Decrement i so that it does not skip other process. It happens when two process has the same
                    // arrival time.
                    i--;
                }
            }
            if (ongoingJobEstimatedCompletionTime == currentTime) {
                if (ongoingJob != null) {
                    // A job has finish executing, calculate and output the result.
                    calcTurnAroundTimeAndWaitingTime(ongoingJob, ongoingJobEstimatedCompletionTime);
                    ongoingJob = null;
                }
                if (!queue.isEmpty()) {
                    // Get a process from the queue and calculate its estimated completion time.
                    ongoingJob = queue.remove(0);
                    ongoingJobEstimatedCompletionTime = ongoingJob.burstTime + currentTime;
                }
            }
            if (ongoingJob == null) {
                ongoingJobEstimatedCompletionTime++;
            }
            currentTime++;
            if (queue.isEmpty() && jobs.isEmpty()) {
                if (ongoingJob != null) {
                    // A job has finish executing, calculate and output the result.
                    calcTurnAroundTimeAndWaitingTime(ongoingJob, ongoingJobEstimatedCompletionTime);
                }
                break;
            }
        }
    }
    private static void calcTurnAroundTimeAndWaitingTime(Job job, int ongoingJobEstimatedCompletionTime) {
        job.completionTime = ongoingJobEstimatedCompletionTime;
        job.turnAroundTime = ongoingJobEstimatedCompletionTime - job.arrivalTime;
        job.waitingTime = job.turnAroundTime - job.burstTime;
        System.out.println("Job: " + job.jobName + ", Completion time: " + job.completionTime + ", Turn around time: " +
                job.turnAroundTime + ", Waiting time: " + job.waitingTime);
    }
}