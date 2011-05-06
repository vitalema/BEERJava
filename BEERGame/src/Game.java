
public class Game {
	Player currentPlayer;
	View currentView;
	boolean isMuted;
	boolean isPaused;
	
	public Game(Player p, View v){
		currentPlayer = p;
		currentView = v;
		isMuted = false;
		isPaused = false;
	}
	public View getCurrentView() {
		return this.currentView;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	void changeView(View v){
		currentView = v;
		}
	void pauseGame(){
		isPaused = true;
	}
	void unpauseGame(){
		isPaused = false;
	}
	void muteGame(){
		isMuted = true;
	}
	void unmuteGame(){
		isMuted = false;
	}
	
	Boolean isMuted(){
		return isMuted;
	}
	
	Boolean isPaused(){
		return isPaused;
	}
}
