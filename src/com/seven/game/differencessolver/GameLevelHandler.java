package com.seven.game.differencessolver;



public abstract class GameLevelHandler {

	public GameLevelHandler mLevelHandler;
	private int level=0;
	public int getLevel() {
		if(level!=0)
			return level;
		else {
			return mLevelHandler.getLevel();
		}	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLevelHandler(GameLevelHandler handler)
	{
		mLevelHandler=handler;
	}
	
	public abstract void LevelRequest(long score);

}
