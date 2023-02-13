import java.util.*;

public class Philosophers {

    private static int n;
    private static Peterson chop;

    private static class Peterson {

        private volatile HashSet<Integer> flags = new HashSet<Integer>();
        private volatile HashMap<Integer, Integer> victs = new HashMap<Integer, Integer>();

        public void lock(int philoId) {
            // Set flags (philo is interested in monching)
            flags.add(Integer.valueOf(philoId));

            // Set victims
            victs.put(Integer.valueOf(philoId), Integer.valueOf(philoId));
            victs.put(Integer.valueOf((philoId + 1) % n), Integer.valueOf(philoId));

            // Determine position of right and left chopsticks
            Integer right = (philoId == 0) ? Integer.valueOf(n -1) : Integer.valueOf(philoId -1);
            Integer left = Integer.valueOf((philoId + 1) % n);

            // Only enter critical section when both right and left chopsticks are available
            while((flags.contains(right) && victs.get(philoId) == philoId) ||
                  (flags.contains(left) && victs.get((philoId + 1) % n) == philoId)) {}
        }

        public void unlock(int philoId) {
            flags.remove(Integer.valueOf(philoId));
        }
    }

    private static class Philo extends Thread{
        private int id;

        public Philo(int Id){
            id = Id;
        }

        public void run(){
            System.out.println("Philo " + id + " is thinking for 1.5 secs...");
            try {Thread.sleep(1500);} catch (Exception e){}

            System.out.println("Philo " + id + " wants to munch, trying to get 2 chopsticks...");
            chop.lock(id);

            System.out.println("Philo " + id + " got both chopsticks, monching...");
            try {Thread.sleep(1500);} catch (Exception e){}

            System.out.println("Philo " + id + " finished monching...");
            chop.unlock(id);
        }
    }

    public static void main(String[] args){
        // Gather user input on n number of philosophers
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of philosophers (int): ");
        n = sc.nextInt();
        System.out.println("Philosopher count: " + n);

        // Setup chopsticks array
        chop = new Peterson();

        // Loop over n to create and start a thread for each philosopher
        for (int i = 0; i < n; i++) {
            Philo p = new Philo(i);
            p.start();
        }
    }
}