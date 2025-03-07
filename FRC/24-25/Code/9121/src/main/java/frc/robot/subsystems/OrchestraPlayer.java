package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.Orchestra;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.List;

public class OrchestraPlayer extends SubsystemBase {
    private final Orchestra orchestra;
    private final List<TalonFX> instruments;

    public OrchestraPlayer(List<TalonFX> motors, String songFile) {
        this.instruments = motors;
        this.orchestra = new Orchestra();

        // Add instruments to orchestra
        for (TalonFX motor : instruments) {
            orchestra.addInstrument(motor);
        }

        // Load the song file
        orchestra.loadMusic(songFile);
    }

    public void playSong() {
        orchestra.play();
    }

    public void stopSong() {
        orchestra.stop();
    }
}
