package businessLogic.playersBL;

import po.PlayerStatsPO;
import enums.Position;
import enums.Teams;

public class BasicPlayerStats {
	//球员的直接统计数据
	String name;
	Teams team;
	Integer games;                           //参赛场数
	Integer gamesStarting;                   //先发场数
	Position position;                       //位置
	Double minutes;                          //在场时间
	Double fieldGoalsMade;                   //投篮命中数
	Double fieldGoalsAttempted;              //投篮出手数
	Double threePointFieldGoalsMade;         //三分命中数
	Double threePointFieldGoalsAttempted;    //三分出手数
	Double freeThrowsMade;                   //罚球命中数
	Double freeThrowsAttempted;              //罚球出手数
	Double offensiveRebounds;                //进攻篮板数
	Double defensiveRebounds;                //防守篮板数
	Double rebounds;                         //总篮板数
	Double assists;                          //助攻数
	Double steals;                           //抢断数
	Double blocks;                           //盖帽数
	Double turnovers;                        //失误数
	Double personalFouls;                    //犯规数
	Double points;                           //个人得分

	public BasicPlayerStats(){
		
	}
	
	public BasicPlayerStats(PlayerStatsPO po, int games, int gamesStarting, Teams team){
		this.name = po.name();
		this.games = games;
		this.gamesStarting = gamesStarting;
	}

	public String name() {
		return name;
	}

	public Integer games() {
		return games;
	}

	public Integer gamesStarting() {
		return gamesStarting;
	}

	public Position position() {
		return position;
	}

	public Double minutes() {
		return minutes;
	}

	public Double fieldGoalsMade() {
		return fieldGoalsMade;
	}

	public Double fieldGoalsAttempted() {
		return fieldGoalsAttempted;
	}

	public Double threePointFieldGoalsMade() {
		return threePointFieldGoalsMade;
	}

	public Double threePointFieldGoalsAttempted() {
		return threePointFieldGoalsAttempted;
	}

	public Double freeThrowsMade() {
		return freeThrowsMade;
	}

	public Double freeThrowsAttempted() {
		return freeThrowsAttempted;
	}

	public Double offensiveRebounds() {
		return offensiveRebounds;
	}

	public Double defensiveRebounds() {
		return defensiveRebounds;
	}

	public Double rebounds() {
		return rebounds;
	}

	public Double assists() {
		return assists;
	}

	public Double steals() {
		return steals;
	}

	public Double blocks() {
		return blocks;
	}

	public Double turnovers() {
		return turnovers;
	}

	public Double personalFouls() {
		return personalFouls;
	}

	public Double points() {
		return points;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGames(Integer games) {
		this.games = games;
	}

	public void setGamesStarting(Integer gamesStarting) {
		this.gamesStarting = gamesStarting;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setMinutes(Double minutes) {
		this.minutes = minutes;
	}

	public void setFieldGoalsMade(Double fieldGoalsMade) {
		this.fieldGoalsMade = fieldGoalsMade;
	}

	public void setFieldGoalsAttempted(Double fieldGoalsAttempted) {
		this.fieldGoalsAttempted = fieldGoalsAttempted;
	}

	public void setThreePointFieldGoalsMade(Double threePointFieldGoalsMade) {
		this.threePointFieldGoalsMade = threePointFieldGoalsMade;
	}

	public void setThreePointFieldGoalsAttempted(
			Double threePointFieldGoalsAttempted) {
		this.threePointFieldGoalsAttempted = threePointFieldGoalsAttempted;
	}

	public void setFreeThrowsMade(Double freeThrowsMade) {
		this.freeThrowsMade = freeThrowsMade;
	}

	public void setFreeThrowsAttempted(Double freeThrowsAttempted) {
		this.freeThrowsAttempted = freeThrowsAttempted;
	}

	public void setOffensiveRebounds(Double offensiveRebounds) {
		this.offensiveRebounds = offensiveRebounds;
	}

	public void setDefensiveRebounds(Double defensiveRebounds) {
		this.defensiveRebounds = defensiveRebounds;
	}

	public void setRebounds(Double rebounds) {
		this.rebounds = rebounds;
	}

	public void setAssists(Double assists) {
		this.assists = assists;
	}

	public void setSteals(Double steals) {
		this.steals = steals;
	}

	public void setBlocks(Double blocks) {
		this.blocks = blocks;
	}

	public void setTurnovers(Double turnovers) {
		this.turnovers = turnovers;
	}

	public void setPersonalFouls(Double personalFouls) {
		this.personalFouls = personalFouls;
	}

	public void setPoints(Double points) {
		this.points = points;
	}
	
	
}
