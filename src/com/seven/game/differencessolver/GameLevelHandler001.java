package com.seven.game.differencessolver;


public  class GameLevelHandler001 extends GameLevelHandler{

	@Override
	public void LevelRequest(long score) {
		// TODO Auto-generated method stub
		
		if(score>=0 && score<100)
		{
			setLevel(1);
		}
		else if (score>=100 && score<250) {
			setLevel(2);

		}
		else if (score>=250 && score<400) {
			setLevel(3);

		}
		else if (score>=400) {
			if(mLevelHandler!=null)mLevelHandler.LevelRequest(score);
		}
		
	}
	

}
