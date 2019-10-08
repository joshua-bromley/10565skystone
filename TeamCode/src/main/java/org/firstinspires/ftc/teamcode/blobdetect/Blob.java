package org.firstinspires.ftc.teamcode.blobdetect;

public class Blob {
    static int id;
    int x, y;
    int width, height;
    int color;

    public Blob(int id, int x, int y, int width, int height, int color){
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void set(int id, int x, int y, int width, int height, int color){
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }



}
