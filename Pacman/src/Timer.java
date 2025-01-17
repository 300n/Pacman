public class Timer {
    long timeStart;
    long timeEnd;
    long timeStop;
    long timeStop_f;
    
    
    public Timer() {}
            
    public void start() {
        this.timeStart = System.currentTimeMillis();
    }
    public long get_Time() {
        this.timeStop = System.currentTimeMillis();
        return this.timeStop-this.timeStart;
    }
    public void Stop_Timer() {
        this.timeStop_f = get_Time();
    }
    public void start_again() {
        this.timeStart = this.timeStop_f;
    }
}
