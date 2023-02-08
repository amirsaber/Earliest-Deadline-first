
package earliest_deadline_first;

/**
 *
 * @author Amir Zamoura
 */
class Process {

    int time;
    int period;
    String name;
    String state = "ready";
    int nextDeadLine;
    int pir;
    int progress = 0;
}
