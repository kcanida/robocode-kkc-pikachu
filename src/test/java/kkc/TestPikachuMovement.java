package kkc;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * Tests that Pikachu robot will change position when the enemy robot fires
 * a bullets(energy drops by a small amount).
 * 
 * @author Kellie Canida
 *
 */
public class TestPikachuMovement extends RobotTestBed {

  //True if Pikachu changed position after bullet is fired from enemy
  boolean movedAway = false;
  //Energy level of enemy from previous turn
  double previousEnemyEnergy = 100;
  //Current energy level of enemy
  double currentEnemyEnergy = 100; 
  //first turn of pikachu robot
  boolean firstTurn = true;
  //Coordinates of Pikachu from previous turn
  double xPrevious = 0;
  double yPrevious = 0;
  
  /**
   * Specifies that Walls and Pikachu are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override 
  public String getRobotNames() {
    return "sample.Walls,kkc.Pikachu";
  }
  
  /**
   * This test runs for 20 rounds.
   * 
   * @return The number of rounds. 
   */
  @Override 
  public int getNumRounds() {
    return 20;
  }
  
  /**
   * Battles are not deterministic so robots do not always start in the same initial position. 
   * 
   * @return false if battles are not deterministic, true if battles are deterministic.
   */
  @Override 
  public boolean isDeterministic() {
    return false;
  }
  
  /**
   * At the end of each turn, checks the to see if our robot changed position when the enemy's
   * energy level dropped by a small amount.  Pikachu should move away when it assumes the enemy
   * just fired a bullet at it.  
   * 
   * @param event Info about the current state of the battle.
   */
  @Override 
  public void onTurnEnded (TurnEndedEvent event) {
    
    // Get the snapshot of the robot Pikachu 
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];
    
    //Get the snapshot of Walls robot
    IRobotSnapshot enemy = event.getTurnSnapshot().getRobots()[0];
    
    //Get enemies energy
    currentEnemyEnergy = enemy.getEnergy();
    
    // Get robot's current position
    double xPosition = robot.getX();
    double yPosition = robot.getY();
    
    //Calculate if enemy energy dropped by 3.0 or less after Pikachu's 
    //first turn so coordinates are initialized
    if ((previousEnemyEnergy - currentEnemyEnergy <= 3.0) && 
       (previousEnemyEnergy - currentEnemyEnergy > 0) && firstTurn != true) {
      
      if (xPosition != xPrevious && yPosition != yPrevious) {
        movedAway = true;
      }
    } 
    else {
      //update all previous variables with the current values
      xPrevious = xPosition;
      yPrevious = yPosition;
      previousEnemyEnergy = currentEnemyEnergy;
    }
    
    firstTurn = false;
  }
  
  /**
   * After running all matches, determine if Pikachu changed position after enemy fired a bullet.
   * 
   * @param event Details about the completed battle.
   */
  @Override 
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Check Movement", movedAway);
 
  }

}
