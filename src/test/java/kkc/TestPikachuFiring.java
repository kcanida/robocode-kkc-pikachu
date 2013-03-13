package kkc;

import static org.junit.Assert.assertTrue;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IBulletSnapshot;
import robocode.control.testing.RobotTestBed;

/**
 * Tests that the Pikachu robot fires bullets of varying power.  The power
 * of a bullet is dependent on the distance the enemy robot is from Pikachu.
 * 
 * @author Kellie Canida
 *
 */
public class TestPikachuFiring extends RobotTestBed {
  
  //True if Pikachu has fired a bullet with power of 2.0
  boolean firePower2 = false;
  // True if Pikachu has fired a bullet with power of 1.5
  boolean firePower1AndHalf = false;
  // True if Pikachu has fired a bullet with power 1.0
  boolean firePower1 = false;
  
  /**
   * Specifies that RamFire and Pikachu are to be matched up in this test case.
   * 
   * @return The comma-delimited list of robots in this match.
   */
  @Override 
  public String getRobotNames() {
    return "sample.RamFire, kkc.Pikachu";
  }
  
  /**
   * This test runs for 30 rounds.
   * 
   * @return The number of rounds. 
   */
  @Override 
  public int getNumRounds() {
    return 30;
  }

  /**
   * At the end of each turn, checks the power of all bullets from Pikachu. Checks that 
   * Pikachu's bullets vary in power based on its distance away from the enemy robot. 
   * 
   * @param event Info about the current state of the battle.
   */
  @Override 
  public void onTurnEnded (TurnEndedEvent event) {

    // All active bullets belong to Pikachu since Corners does not fire.
    IBulletSnapshot bullets[] = event.getTurnSnapshot().getBullets();
    double bulletPower;
    
    for (int i = 0; i < bullets.length; i++) {
    
      bulletPower = bullets[i].getPower();
      
      if (bulletPower > 1.5 && bullets[i].getOwnerIndex() == 1) {
        firePower2 = true;
      }
      else if (bulletPower > 1 && bullets[i].getOwnerIndex() == 1) {
        firePower1AndHalf = true;
      }
      else if (bulletPower > 0 && bullets[i].getOwnerIndex() == 1) {
        firePower1 = true;
      }
    }   
  }
  /**
   * After running all matches, determine if Pikachu bullets vary in power.
   * 
   * @param event Details about the completed battle.
   */
  @Override 
  public void onBattleCompleted(BattleCompletedEvent event) {
    assertTrue("Bullet Power of 2.0", firePower2);
    assertTrue("Bullet Power of 1.5", firePower1AndHalf);
    assertTrue("Bullet Power of 1.0", firePower1);
  }
}
