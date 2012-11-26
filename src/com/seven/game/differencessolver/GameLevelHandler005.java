package com.seven.game.differencessolver;


public class GameLevelHandler005 extends GameLevelHandler{

	@Override
	public void LevelRequest(long score) {
		// TODO Auto-generated method stub
		
		if(score>=3300 && score<3800)
		{
			setLevel(13);
		}
		else if (score>=4300 && score<4800) {
			setLevel(14);

		}
		else if (score>=5500) {
			setLevel(15);

		}
		
	}

}