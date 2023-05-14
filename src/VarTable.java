public class VarTable {
    public static boolean isRunning;

    static Game game = new Game();

    public boolean getRunState() {
        return isRunning;
    }

    public void setRunState(boolean state) {
        isRunning = state;
    }
}
