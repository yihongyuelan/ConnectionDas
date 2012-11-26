package com.seven.game.differencessolver;


public class GameLevelHandler003 extends GameLevelHandler{

	@Override
	public void LevelRequest(long score) {
		// TODO Auto-generated method stub
		
		if(score>=800 && score<900)
		{
			setLevel(7);
		}
		else if (score>=1000 && score<1200) {
			setLevel(8);

		}
		else if (score>=1400 && score<1600) {
			setLevel(9);

		}
		else if (score>=1800) {
			if(mLevelHandler!=null)mLevelHandler.LevelRequest(score);
		}
		
	}

}
