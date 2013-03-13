package kkc;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * Targeting class tests to make sure that the Pikachu robot is
 * aiming the gun at the scanned enemy robot. To do this it must 
 * rotate the gun the right amount so that the heading of the radar
 * and the heading of the gun are equal. 
 * 
 * @author Kellie Canida
 *
 */
public class TestPikachuTargeting extends RobotTestBed {
  
  private boolean targeted = false;

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
   * At the end of each turn, checks the distance between Pikachu and 
   * Corners robot. If the distance is ever less than 50 pixels, set 
   * keepsDistanceFromRobot to false.
   * 
   * @param event Info about the current state of the battle.
   */
  @Override 
  public void onTurnEnded (TurnEndedEvent event) {
    
    // Get a snapshot of Pikachu at the end of turn
    IRobotSnapshot robot = event.getTurnSnapshot().getRobots()[1];
    
    //Get Pikachu's gun and radar heading
    double gunHeading = robot.getGunHeading();
    double radarHeading = robot.getRadarHeading();
    
    //Check if gun and radar heading are equal after turn
    if (gunHeading == radarHeading) {
      targeted = true;
    }
    
    
  }
  
  /**
   * After running all matches, determine if Pikachu gets to close to enemy robot.
   * 
   * @param event Details about the completed battle.
   */
  @Override 
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Robot is targeting enemy", targeted);   
  }
}
