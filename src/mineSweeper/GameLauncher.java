package mineSweeper;

public class GameLauncher {
	CreateMine newMine;
	CreateUI newUI;
	public GameLauncher(){
		newUI = new CreateUI();
		while(true){
			if(newUI.judgeWin() == true || newUI.judgeLose() == true){
				newUI.resetGame();
				newUI = new CreateUI();
			}
		}
	}
}
