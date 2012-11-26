package com.seven.game.differencessolver;


import org.alljoyn.bus.sample.chat.R;
import android.content.Context;


public class GetGameTitlesClass {
	
	Context mContext;
	public GetGameTitlesClass(Context mContext)
	{
		this.mContext=mContext;
	}

	public String  getTitles(long score)
	{
		int level=1000;

		String titleString="";
		
		
		GameLevelHandler level001=new GameLevelHandler001();
		GameLevelHandler level002=new GameLevelHandler002();
		GameLevelHandler level003=new GameLevelHandler003();
		GameLevelHandler level004=new GameLevelHandler004();
		GameLevelHandler level005=new GameLevelHandler005();

		level001.setLevelHandler(level002);
		level002.setLevelHandler(level003);
		level003.setLevelHandler(level004);
		level004.setLevelHandler(level005);
	
		level001.LevelRequest(score);
		
		level=level001.getLevel();
		
		switch (level) {
		case 1:
			titleString=mContext.getString(R.string.guess_title_play_01); 
			break;

		case 2:
			titleString=mContext.getString(R.string.guess_title_play_02); 
			break;
		case 3:
			titleString=mContext.getString(R.string.guess_title_play_03); 
			break;
			
		case 4:
			titleString=mContext.getString(R.string.guess_title_play_04); 
			break;
		case 5:
			titleString=mContext.getString(R.string.guess_title_play_05); 
			break;
			
		case 6:
			titleString=mContext.getString(R.string.guess_title_play_06); 
			break;
			
		case 7:
			titleString=mContext.getString(R.string.guess_title_play_07); 
			break;
		case 8:
			titleString=mContext.getString(R.string.guess_title_play_08); 
			break;
			
		case 9:
			titleString=mContext.getString(R.string.guess_title_play_09); 
			break;
		case 10:
			titleString=mContext.getString(R.string.guess_title_play_10); 
			break;
		case 11:
			titleString=mContext.getString(R.string.guess_title_play_11); 
			break;
			
		case 12:
			titleString=mContext.getString(R.string.guess_title_play_12); 
			break;
			
			
		case 13:
			titleString=mContext.getString(R.string.guess_title_play_13); 
			break;
			
			
		case 14:
			titleString=mContext.getString(R.string.guess_title_play_14); 
			break;
			
		case 15:
			titleString=mContext.getString(R.string.guess_title_play_15); 
			break;
			
		default:
			titleString=mContext.getString(R.string.guess_title_play_default); 
			break;
		}
		return titleString;
	}
	
	
}
