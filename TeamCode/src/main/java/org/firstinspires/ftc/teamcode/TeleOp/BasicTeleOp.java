package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Config
@TeleOp(name="Basic TeleOP", group = "TeleOp")
public class BasicTeleOp extends OpMode {

    DcMotor[] driveMotors = new DcMotor[4];
    DcMotor[] intakeMotors = new DcMotor[2];
    DcMotor liftMotor = null;
    DcMotor armMotor = null;
    Servo claw = null;

    double xVelocity, yVelocity, angle, magnitude;
    double flPower, frPower, blPower, brPower, rotation, maxPower;

    double armPower, intakePower;

    boolean isClawGrabbing = false, bool = false;

    @Override
    public void init(){
        driveMotors[0] = hardwareMap.dcMotor.get("br");
        driveMotors[1] = hardwareMap.dcMotor.get("bl");
        driveMotors[2] = hardwareMap.dcMotor.get("fr");
        driveMotors[3] = hardwareMap.dcMotor.get("fl");

        intakeMotors[0] = hardwareMap.dcMotor.get("lintake");
        intakeMotors[1] = hardwareMap.dcMotor.get("rintake");

        liftMotor = hardwareMap.dcMotor.get("lift");
        armMotor = hardwareMap.dcMotor.get("arm");

        claw = hardwareMap.servo.get("claw");

        driveMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[3].setDirection(DcMotorSimple.Direction.REVERSE);
        intakeMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);

        driveMotors[0].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveMotors[1].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveMotors[2].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driveMotors[3].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop(){
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
            claw.setPosition(.85);
        } else {
            claw.setPosition(.25);
        }

        driveMotors[0].setPower(brPower);
        driveMotors[1].setPower(blPower);
        driveMotors[2].setPower(frPower);
        driveMotors[3].setPower(flPower);

        intakeMotors[0].setPower(intakePower);
        intakeMotors[1].setPower(intakePower);

        armMotor.setPower(armPower);

    }

}
