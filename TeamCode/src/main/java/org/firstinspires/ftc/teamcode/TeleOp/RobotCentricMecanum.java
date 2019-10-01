package org.firstinspires.ftc.teamcode._TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode._Libs.AutoLib;
import org.firstinspires.ftc.teamcode._Libs.hardware.MecanumHardware;
import org.firstinspires.ftc.teamcode._Libs.mathFunctions;

@TeleOp(name="Robot Centric Mecanum")

public class RobotCentricMecanum extends OpMode {
    MecanumHardware robot =  new MecanumHardware();

    double xVelocity, yVelocity, angle, magnitude;
    double flPower, frPower, blPower, brPower, rotation, maxPower;

    double armPower, intakePower;

    boolean isClawGrabbing = false, bool = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        maxPower = 0.7;

        xVelocity = Math.pow(gamepad1.left_stick_x, 3);
        yVelocity = Math.pow(gamepad1.left_stick_y, 3);

        xVelocity = Range.clip(xVelocity, -1, 1);
        yVelocity = Range.clip(yVelocity, -1, 1);

        intakePower = -Math.pow(gamepad2.left_stick_y, 3);

        if(Math.abs(gamepad1.right_stick_x) > Math.abs(gamepad1.right_stick_y)) {
            armPower = 0;
            rotation = -Math.pow(gamepad1.right_stick_x, 3);
        } else {
            armPower = -Math.pow(gamepad1.right_stick_y, 3);
            rotation = 0;
        }

        angle = Math.atan2(xVelocity, yVelocity) - Math.PI;
        magnitude = Math.sqrt(Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2));

        flPower = magnitude * Math.sin(-angle + Math.PI / 4) - rotation;
        frPower = magnitude * Math.cos(-angle + Math.PI / 4) + rotation;
        blPower = magnitude * Math.cos(-angle + Math.PI / 4) - rotation;
        brPower = magnitude * Math.sin(-angle + Math.PI / 4) + rotation;

        if(Math.abs(flPower) > maxPower)
            maxPower = Math.abs(flPower);
        if(Math.abs(frPower) > maxPower)
            maxPower = Math.abs(frPower);
        if(Math.abs(blPower) > maxPower)
            maxPower = Math.abs(blPower);
        if(Math.abs(brPower) > maxPower)
            maxPower = Math.abs(brPower);

        flPower = flPower / maxPower;
        frPower = frPower / maxPower;
        blPower = blPower / maxPower;
        brPower = brPower / maxPower;

        if (gamepad1.a && bool == false){
            isClawGrabbing = !isClawGrabbing;
            bool = true;
        } else {
            bool = false;
        }
        if (isClawGrabbing) {
            robot.Claw.setPosition(.85);
        } else {
            robot.Claw.setPosition(.25);
        }

        robot.fl.setPower(flPower);
        robot.fr.setPower(frPower);
        robot.bl.setPower(blPower);
        robot.br.setPower(brPower);

        robot.arm1.setPower(armPower);
        robot.arm2.setPower(armPower);

        robot.intake1.setPower(intakePower);
        robot.intake2.setPower(intakePower);

        telemetry.addData("front left/right power:", "%.2f %.2f", flPower, frPower);
        telemetry.addData("back left/right power:", "%.2f %.2f", blPower, brPower);
        telemetry.addData("heading:", "%.2f", angle * 180 / Math.PI);
        telemetry.addData("max power", "%.2f", maxPower);
        telemetry.addData("arm power","%.2f", armPower);
        telemetry.addData("arm power","%.2f", intakePower);
    }

    @Override
    public void stop() {

    }
}
