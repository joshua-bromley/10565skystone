package org.firstinspires.ftc.teamcode.Test;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Auto.VuforiaLib_SkyStone;

@Config
@Autonomous (name="StoneIDTest", group = "Test")
public class StoneIDTest extends LinearOpMode {

    VuforiaLib_SkyStone mvLib = new VuforiaLib_SkyStone();
    @Override
    public void runOpMode(){
        mvLib.init(this, null);
        mvLib.start();

        while(true) {
            telemetry.addData("X: ", mvLib.getFieldPosition().get(0));
        }

    }
}
