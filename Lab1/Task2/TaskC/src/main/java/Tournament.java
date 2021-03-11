import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Tournament extends RecursiveTask<Integer> {
    private ArrayList<Integer> monks;
    private int beginIndex;
    private int endIndex;
    private static Random random = new Random();


    Tournament(ArrayList<Integer> monks, int beginIndex, int endIndex) {
        this.monks = monks;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    private static Integer fight(ArrayList<Integer> monks, int firstMonk, int secondMonk) {
        if (monks.get(firstMonk) == monks.get(secondMonk)) {
            return (random.nextInt(2) == 0 ? firstMonk : secondMonk);
        }
        return (monks.get(firstMonk) > monks.get(secondMonk) ? firstMonk : secondMonk);
    }

    @Override
    protected Integer compute() {
        if (endIndex - beginIndex == 1) {
            return fight(monks, beginIndex, endIndex);
        } else if (endIndex == beginIndex) {
            return beginIndex;
        }

        int midIndex = (beginIndex + endIndex) / 2;

        Tournament leftTournament = new Tournament(monks, beginIndex, midIndex);
        Tournament rightTournament = new Tournament(monks, midIndex + 1, endIndex);
        leftTournament.fork();
        rightTournament.fork();

        return fight(monks, leftTournament.join(), rightTournament.join());
    }



    public static void main(String[] args) {
        int monksNumber = 50;
        ArrayList<Integer> monks = new ArrayList<>(monksNumber);
        System.out.println("Powers:");
        for (int i = 0; i < monksNumber; ++i) {
            int power = random.nextInt(90) + 10;
            System.out.print(power + " ");
            monks.add(power);
        }
        System.out.println("\n");

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Tournament tournament = new Tournament(monks, 0, monks.size() - 1);
        int winnerMonk = forkJoinPool.invoke(tournament);
        System.out.println("Winner is " + winnerMonk + " with power " + monks.get(winnerMonk));
        System.out.println("From " + (winnerMonk % 2 == 0 ? "Huan In": "Huan Yan"));
    }
}
