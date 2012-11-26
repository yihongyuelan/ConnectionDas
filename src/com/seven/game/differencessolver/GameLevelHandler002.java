package com.seven.game.differencessolver;


public class GameLevelHandler002 extends GameLevelHandler{

	@Override
	public void LevelRequest(long score) {
		// TODO Auto-generated method stub
		
		if(score>=400 && score<450)
		{
			setLevel(4);
		}
		else if (score>=450 && score<500) {
			setLevel(5);

		}
		else if (score>=500 && score<600) {
			setLevel(6);

		}
		else if (score>=700) {
			if(mLevelHandler!=null)mLevelHandler.LevelRequest(score);
		}
		
	}
}
