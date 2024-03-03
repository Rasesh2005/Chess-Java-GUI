import java.util.Timer;
import java.util.TimerTask;

public class ChessTimer {
    private Timer timer;
    private long startTime;
    private long elapsedTime;
    private int finalTime = 0;
    private boolean isRunning;

    public ChessTimer() {
        timer = new Timer();
        isRunning = false;
    }

    public void start(Display gameDisplay) {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    finalTime++;
                    gameDisplay.repaint();
                    
                    // System.out.println(getTime(finalTime));
                }
            }, 0, 1000); // Update elapsed time every second
            // finalTime += elapsedTime;
            isRunning = true;
        }
    }

    public void stop() {
        timer.cancel();
        isRunning = false;
        timer = new Timer();

    }

    public void reset() {
        stop();
        elapsedTime = 0;
    }

    public long getElapsedTimeInSeconds() {
        return finalTime;
    }
    static String getTime(int sec)
{
    
    int minutes = 0;
    int seconds = 0;

    if (sec >= 60)                
    {
        minutes = sec / 60;
        seconds = sec % 60;
    }
    //if we have just seconds
    else if (sec < 60)
    {
        minutes = 0;
        seconds = sec;
    }
    
    String strMins; 
    String strSecs; 

    if(seconds < 10)
    	strSecs = "0" + Integer.toString(seconds);
    else
    	strSecs = Integer.toString(seconds);
   
    if(minutes < 10)
   	    strMins = "0" + Integer.toString(minutes);
    else
	    strMins = Integer.toString(minutes);
    	
    String time = strMins + ":" + strSecs;
    return time;
    }
}
