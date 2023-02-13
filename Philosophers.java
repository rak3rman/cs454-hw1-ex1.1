import java.util.*;

public class Philosophers {

    private static int n;
    private static Peterson chop[];

    private static class Peterson {
        private volatile boolean[] flag = new boolean[2];
        private volatile int victim;

        public Peterson() {
            flag[0] = false;
            flag[1] = false;
        }

        private int convertId(int chopId, int philoId) {
            int right = 1;
            int left = 0;
            if (philoId == chopId) {
                philoId = right;
            } else {
                philoId = left;
            }
            return philoId;
        }

        public void lock(int chopId, int philoId) {
            int id = convertId(chopId, philoId);
            flag[id] = true;
            victim = id;

            System.out.println("Philo " + philoId + " locking chop " + id);

            while(flag[(id + 1) % 2] && victim == id) {}
        }

        public void unlock(int chopId, int philoId) {
            flag[convertId(chopId, philoId)] = false;
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
            chop[(id + 1) % n].lock((id + 1) % n, id);
            System.out.println("Philo " + id + " got left chopstick, trying to get right...");
            chop[id].lock(id, id);
            System.out.println("Philo " + id + " got right chopstick, monching...");
            try {Thread.sleep(1500);} catch (Exception e){}
            chop[id].unlock(id, id);
            chop[(id + 1) % n].unlock((id + 1) % n, id);
        }
    }

    public static void main(String[] args){
        // Gather user input on n number of philosophers
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of philosophers (int): ");
        n = sc.nextInt();
        System.out.println("Philosopher count: " + n);

        // Setup chopsticks array
        chop = new Peterson[n];
        for (int i = 0; i < n; i++) {
            chop[i] = new Peterson();
        }

        // Loop over n to create and start a thread for each philosopher
        for (int i = 0; i < n; i++) {
            Philo p = new Philo(i);
            p.start();
        }
    }
}