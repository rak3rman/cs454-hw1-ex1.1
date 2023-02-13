# cs454-hw1-ex1.1

# Homework 1, Exercise 1.1

This implementation of the dining philosophers problem by E.W. Dijkstra is built on Java and a modified Peterson lock algorithm to support concurrency.
Two philosophers are not allowed to hold the same chopstick at the same time, never reaches a state of deadlock, is starvation-free (as proven in the textbook, Peterson lock), and supports n philosophers.
In order to enter the critical section of the Peterson lock, the lock algorithm on each chopstick must be aware of each other chopstick, as well as which philosophers would like to eat.
If we allow each chopstick to be aware of only it's state and not of the state of other chopsticks, then we could enter a state of deadlock (impling starvation) where one chopstick could be picked up and never put down.
Therefore, we only obtain a lock when both chopsticks to the right and left of the philosopher is available (we don't pick up one then the other = deadlock).
After the philosopher is done eating, we simply unlock the chopsticks and remove the philosopher flag stating that he is willing to eat.
The program follows this logic until all philosophers have eaten at least once (but this algorithm supports eating and thinking forever).

Run Java class using the following command.
Expects n number of philosophers as input.

```
javac Philosophers.java && java Philosophers
```