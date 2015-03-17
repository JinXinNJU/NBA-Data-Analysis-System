package vo;

import enums.Teams;

public class PlayerBasicStatsVO {
	//
	String name;                                     //姓名
	Teams team;                                      //所属球队
	double games;                                    //参赛场数
	double gamesStarting;                            //先发场数
	String minutes;                                  //在场时间
	double rebounds;                                 //篮板数
	double assists;                                  //助攻数
	double fieldGoalPercentage;                      //命中率
	double threePointFieldGoalPercentage;            //三分命中率
	double freeThrowPercentage;                      //罚球命中率
	double offensiveRebounds;                        //进攻（篮板）数
	double defensiveRebounds;                        //防守(篮板)数
	double steals;                                   //抢断数
	double blocks;                                   //盖帽数
	double turnovers;                                //失误数
	double personalFouls;                              //犯规数
	double points;                                   //个人得分
	
	public PlayerBasicStatsVO(String name, Teams team, double games, double gamesStarting, 
			String minutes, double rebounds, double assists, double fieldGoalsPercentage, 
			double threePointFieldGoalsPercentage, double freeThrowPercentage, 
			double offensiveRebounds, double defensiveRebounds, double steals, 
			double blocks, double turnovers, double personalFouls, double points){
		this.name = name;
		this.team = team;
		this.games = games;
		this.gamesStarting = gamesStarting;
		this.minutes = minutes;
		this.rebounds = rebounds;
		this.assists = assists;
		this.fieldGoalPercentage = fieldGoalsPercentage;
		this.threePointFieldGoalPercentage = threePointFieldGoalsPercentage;
		this.freeThrowPercentage = freeThrowPercentage;
		this.offensiveRebounds = offensiveRebounds;
		this.defensiveRebounds = defensiveRebounds;
		this.steals = steals;
		this.blocks = blocks;
		this.turnovers = turnovers;
		this.personalFouls = personalFouls;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public Teams getTeam() {
		return team;
	}

	public double getGames() {
		return games;
	}

	public double getGamesStarting() {
		return gamesStarting;
	}

	public double getRebounds() {
		return rebounds;
	}

	public double getAssists() {
		return assists;
	}

	public String getMinutes() {
		return minutes;
	}

	public double getFieldGoalPercentage() {
		return fieldGoalPercentage;
	}

	public double getThreePointFieldGoalPercentage() {
		return threePointFieldGoalPercentage;
	}

	public double getFreeThrowPercentage() {
		return freeThrowPercentage;
	}

	public double getOffensiveRebounds() {
		return offensiveRebounds;
	}

	public double getDefensiveRebounds() {
		return defensiveRebounds;
	}

	public double getSteals() {
		return steals;
	}

	public double getBlocks() {
		return blocks;
	}

	public double getTurnovers() {
		return turnovers;
	}

	public double getPersonalFouls() {
		return personalFouls;
	}

	public double getPoints() {
		return points;
	}
	
	
	
}
