package com.seven.game.differencessolver;


public class GameLevelHandler004 extends GameLevelHandler{

	@Override
	public void LevelRequest(long score) {
		// TODO Auto-generated method stub
		
		if(score>=1800 && score<2100)
		{
			setLevel(10);
		}
		else if (score>=2400 && score<2700) {
			setLevel(11);

		}
		else if (score>=3000 && score<3300) {
			setLevel(12);

		}
		else if (score>=3300) {
			if(mLevelHandler!=null)mLevelHandler.LevelRequest(score);
		}
		
	}

}
