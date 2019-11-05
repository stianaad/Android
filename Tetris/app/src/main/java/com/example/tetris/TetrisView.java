package com.example.tetris;

import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class TetrisView extends SurfaceView implements SurfaceHolder.Callback{
    public final static int INCREMENT=40;
    public final static int DOWN=0;
    public final static int UP=2;
    public final static int LEFT=1;
    public final static int RIGHT=3;
    public final static int LENGTH_TO_LINE = 1240;
    public final static int LENGTH_TO_LINE_TOUCH_DISTANCE = 1310;
    public final static int SQUARE = 0;
    public final static int I = 1;
    public final static int ITrans = 11;
    public final static int L = 2;
    public final static int LRot90 = 290;
    public final static int LRot180 = 2180;
    public final static int LRot270 = 2270;
    public final static int S = 3;
    public final static int SRot90 = 390;
    private int mWidth;
    private int mHeight;
    private int sizeY;
    private int sizeX;
    private TetrisThread mThread=null;
    private ArrayList<Posision> tetrisBrickPos = new ArrayList<Posision>();
    private int[][] closedBricks = null;
    private Random mRand=new Random();
    private boolean mInc=false;
    private int moving=DOWN;
    private int activeBrick;
    private int[][] activeBrickArray;
    private int[][] colorArray;
    private int activeColor;
    private String languageRotate;
    private String languageGameOver;
    private TetrisCallback tetrisCallback;
    private boolean gameOver = false;

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        mThread = new TetrisThread(holder, this);
        setFocusable(true); // make sure we get key events
        tetrisBrickPos.add(new Posision(INCREMENT,INCREMENT));//Snake with just head
        sizeY = LENGTH_TO_LINE/INCREMENT;
        sizeX = 30;//mWidth/INCREMENT;
        DisplayMetrics metrics = new DisplayMetrics();
        Log.i("WIDTHSTART", String.valueOf(mWidth));
        activeBrickArray = getRandomBrick();
        activeColor = getRandomColor();
        languageRotate = getResources().getString(R.string.rotate);
        languageGameOver = getResources().getString(R.string.gameOver);
    }
    public TetrisThread getThread() {
        return mThread;
    }

    public void updateLanguageText(String value, String gameOver){
        languageRotate = value;
        languageGameOver = gameOver;
    }

    public void startNytt() {
        if(gameOver){
            gameOver = false;
            mThread.setPaused(false);
        }
        activeBrickArray = getRandomBrick();
        closedBricks = new int[sizeY][sizeX];
        tetrisBrickPos.add(0, new Posision(INCREMENT,INCREMENT));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        boolean handeled = doKeyDown(keyCode, msg);
        if (handeled) return true;
        else return super.onKeyDown(keyCode, msg);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            Posision touch = new Posision((int)event.getX(0), (int)event.getY(0));
            touchMove(touch);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent msg){
        if(moving != DOWN) moving = DOWN;
        return true;
    }

    public boolean doKeyDown(int keyCode, KeyEvent msg){
        if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT || keyCode==KeyEvent.KEYCODE_DPAD_RIGHT
                || keyCode==KeyEvent.KEYCODE_DPAD_DOWN || keyCode==KeyEvent.KEYCODE_DPAD_UP) {
            if (keyCode==KeyEvent.KEYCODE_DPAD_LEFT) turn(LEFT);
            else if (keyCode==KeyEvent.KEYCODE_DPAD_RIGHT) turn(RIGHT);
            else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) turn(DOWN);
            else if (keyCode==KeyEvent.KEYCODE_DPAD_UP) turn(UP);
            return true;
        }
        return false;
    }

    private void touchMove(Posision posision) {
        //Log.i("POS", String.valueOf(posision.yPos));
        if(posision.xPos < 500 && posision.yPos < LENGTH_TO_LINE_TOUCH_DISTANCE) { // && posision.yPos < 500
            turn(LEFT);
        } else if(posision.xPos > 500 && posision.yPos <LENGTH_TO_LINE_TOUCH_DISTANCE) {
            turn(RIGHT);
        } else if( posision.yPos > LENGTH_TO_LINE_TOUCH_DISTANCE ){
            turn(UP);
        }

    }
    public void turn(int direction) {
        if (direction==LEFT) moving =LEFT;
        else if(direction==RIGHT) moving = RIGHT;
        else if(direction==DOWN) moving = DOWN;
        else if(direction==UP) {
            switch (activeBrick){
                case I:
                    activeBrick = ITrans;
                    activeBrickArray = TetrisPieces.ITransponert();
                    break;
                case ITrans:
                    activeBrick = I;
                    activeBrickArray = TetrisPieces.I();
                    break;
                case L:
                    activeBrick = LRot90;
                    activeBrickArray = TetrisPieces.LRot90();
                    break;
                case LRot90:
                    activeBrick = LRot180;
                    activeBrickArray = TetrisPieces.LRot180();
                    break;
                case LRot180:
                    activeBrick = LRot270;
                    activeBrickArray = TetrisPieces.LRot270();
                    break;
                case LRot270:
                    activeBrick = L;
                    activeBrickArray = TetrisPieces.L();
                    break;
                case S:
                    activeBrick = SRot90;
                    activeBrickArray = TetrisPieces.SRot90();
                    break;
                case SRot90:
                    activeBrick = S;
                    activeBrickArray = TetrisPieces.S();
                    break;
            }
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                       moving = DOWN;
                    }
                },
                300);
    }
    @Override
    public void onDraw(Canvas canvas){
        if (!mThread.isPaused()) {
            updateFigure();
        }
        canvas.drawColor(Color.BLACK);
        drawLine(canvas, 0,1500,1300);
        drawFigure(canvas);
        drawLine(canvas,0,1500,40);
        drawFinishedBricks(canvas);
        drawText(canvas,languageRotate, 300, 1450);
        if(gameOver){
            drawText(canvas, languageGameOver, 100, 600);
        }
    }

    public void drawText(Canvas canvas, String text, int x, int y) {
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, x,y, paint);
    }

    public void drawLine(Canvas canvas,int startX, int sluttX, int y) {
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.YELLOW);
        canvas.drawLine(startX, y, sluttX, y, paint);
    }


    public void drawFigure(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(activeColor);
        Posision start = new Posision(tetrisBrickPos.get(0));
        int startX = start.xPos;
        int startY = start.yPos;
        int[][] shape = activeBrickArray;
        for(int i=0; i<shape.length; i++){
            for(int j=0; j<shape[0].length; j++){
                if(shape[i][j] != 0) { // [0][0], [0, 1]
                    canvas.drawRect( new Rect(startX+j*(INCREMENT), startY+i*(INCREMENT), startX+j*(INCREMENT)+INCREMENT, startY+INCREMENT+i*(INCREMENT)), paint);
                }
            }
        }
    }

    private int getRandomColor(){
        int rand = new Random().nextInt(5);
        return getColor(rand);
    }

    private int getColor(int value){
        switch (value){
            case 0:
                return Color.LTGRAY;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.RED;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.YELLOW;
        }
        return Color.WHITE;
    }

    private int[][] getRandomBrick(){
        int rand = new Random().nextInt(4);
        int[][] shape= null;
        switch (rand){
            case 0:
                shape = TetrisPieces.firkant();
                activeBrick = SQUARE;
                break;
            case 1:
                shape = TetrisPieces.I();
                activeBrick=I;
                break;
            case 2:
                shape = TetrisPieces.L();
                activeBrick = L;
                break;
            case 3:
                shape = TetrisPieces.S();
                activeBrick = S;
                break;
        }
        return shape;
    }

    public void drawFinishedBricks(Canvas canvas) {
        Paint paint = new Paint();
        int startX = 0;
        int startY = 40;//880;
        for(int i=closedBricks.length-1; i>=0; i--){
            for(int j=0; j<closedBricks[0].length; j++){
                if(closedBricks[i][j] != 0) { // [0][0], [0, 1]
                    paint.setColor(colorArray[i][j]);
                    canvas.drawRect( new Rect(startX+j*(INCREMENT), startY+i*(INCREMENT), startX+j*(INCREMENT)+INCREMENT, startY+INCREMENT+i*(INCREMENT)), paint);
                }
            }
        }
    }

    private boolean existingBrickOnLeftOrRight(Posision pos, boolean right) {
        int xpos = (pos.xPos)/INCREMENT;
        int ypos = pos.yPos/INCREMENT;
        if(right) {
            int x = activeBrickArray[0].length;
            xpos+=(x-1);
            if(xpos == mWidth/INCREMENT){ // closedBricks[ypos][xpos] == 0
                return true;
            }
        }
        if(xpos < 0) {
            return true;
        }
        if(closedBricks[ypos][xpos] == 1){
            return true;
        }
        return false;
    }

    private void updateFigure(){
        Posision newPos = new Posision(tetrisBrickPos.get(0));
        tetrisBrickPos.add(1,newPos);
        advance(newPos);
        tetrisBrickPos.add(0,newPos);
        boolean crash = false;
        for (int i = 0; i < closedBricks.length; i++) {
            int xPos = (newPos.xPos / INCREMENT);
            int yPos = (newPos.yPos / INCREMENT);
            int width = closedBricks[i][xPos];
            int widthX2 = closedBricks[i][xPos + 1];
            int widthX3 = closedBricks[i][xPos + 2];
            int widthX4 = closedBricks[i][xPos + 3];

            /*int x = activeBrickArray[0].length;
            int y = activeBrickArray.length;
            for(int j = xPos; j <x ;j++){
                if(closedBricks[i+(y-1)][j] == 1){
                    crash = true;
                }
            }*/


            switch (activeBrick) {
                case SQUARE: // dinna fungere
                    if (i == closedBricks.length - 1 && newPos.yPos >= LENGTH_TO_LINE) {
                        crash = true;
                        break;
                    } else if (newPos.yPos >= (LENGTH_TO_LINE - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= (LENGTH_TO_LINE - widthX2 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case I: // dinna fungere
                    if (newPos.yPos >= (1160 - width * (INCREMENT * (closedBricks.length - i)))) {
                        crash = true;
                        break;
                    }
                    break;
                case ITrans: // dinna funke
                    if (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - widthX2 * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - widthX3 * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - widthX4 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case L: // dinna fungere
                    if (newPos.yPos >= (LENGTH_TO_LINE - INCREMENT - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= (LENGTH_TO_LINE - INCREMENT - widthX2 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        //newPos.yPos-=INCREMENT;
                        break;
                    }
                    break;
                case LRot90: // dinna fungere
                    if (newPos.yPos >= ((LENGTH_TO_LINE) - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= ((LENGTH_TO_LINE) - widthX2 * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= ((LENGTH_TO_LINE) - widthX3 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case LRot180: // dinna fungere
                    if (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= ((LENGTH_TO_LINE - INCREMENT) - widthX2 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case LRot270: // dinna fungere
                    if (newPos.yPos >= ((LENGTH_TO_LINE) - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - widthX2 * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= ((LENGTH_TO_LINE + INCREMENT) - widthX3 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case S: //  dinna funke bra
                    if ((newPos.yPos >= (LENGTH_TO_LINE - width * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= (LENGTH_TO_LINE - widthX2 * (INCREMENT * (closedBricks.length - i))))
                            || (newPos.yPos >= (LENGTH_TO_LINE + INCREMENT - widthX3 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
                case SRot90: // dinna fungere
                    if (newPos.yPos >= ((LENGTH_TO_LINE) - width * (INCREMENT * (closedBricks.length - i)))
                            || (newPos.yPos >= ((LENGTH_TO_LINE - INCREMENT) - widthX2 * (INCREMENT * (closedBricks.length - i))))) {
                        crash = true;
                        break;
                    }
                    break;
            }
        }
        if(crash){
            newPos.yPos-=INCREMENT;
            int startX = newPos.xPos/INCREMENT;
            int startY = (sizeY-1)-(LENGTH_TO_LINE-newPos.yPos)/INCREMENT;
            int y=0;
            int x=0;
            y = activeBrickArray.length;
            x = activeBrickArray[0].length;
            for (int j = startY; j < startY + y; j++) {
                for (int i = startX; i < startX + x; i++) {
                    if(activeBrickArray[j-startY][i-startX] == 1){
                        closedBricks[j][i] = 1;
                        colorArray[j][i] = activeColor;
                    }
                }
            }
            if(newPos.yPos == 40){
                mThread.setPaused(true);
                gameOver = true;
            }
            tetrisBrickPos.add(0, new Posision(INCREMENT,INCREMENT));
            //Log.i("Crash","X="+newPos.xPos+" Y="+newPos.yPos);
            activeBrickArray = getRandomBrick();
            activeColor = getRandomColor();
        }
    }

    public void advance(Posision head) {
        switch (moving) {
            case UP:
                head.yPos -= INCREMENT;
                break;
            case RIGHT:
                head.xPos += INCREMENT;
                if(existingBrickOnLeftOrRight(head, true)){
                    Log.i("EKSISTERER", "EKS");
                    head.xPos -=INCREMENT;
                }
                break;
            case DOWN:
                head.yPos += INCREMENT;
                break;
            case LEFT:
                head.xPos -= INCREMENT; // funke her
                if(existingBrickOnLeftOrRight(head, false)){
                    Log.i("EKSISTERER", "EKS");
                    head.xPos +=INCREMENT;
                }
                break;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        mWidth=width;
        mHeight=height;
        sizeX = (mWidth+5*INCREMENT)/INCREMENT;
        closedBricks = new int[sizeY][sizeX];
        colorArray = new int[sizeY][sizeX];
    }
    public void surfaceCreated(SurfaceHolder holder) {
        mThread.setRunning(true);
        mThread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
    public class Posision {
        private int xPos;
        private int yPos;
        public Posision(int x, int y) {
            xPos=x;
            yPos=y;
        }
        public Posision(Posision orig) {
            xPos=orig.xPos;
            yPos=orig.yPos;
        }
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Posision)) return false;
            Posision theOther = (Posision)other;
            return xPos==theOther.xPos && yPos==theOther.yPos;
        }
    }

    public interface TetrisCallback {
        public void onEvent(boolean value);
    }

    public void setGameCallback(TetrisCallback cb){
        this.tetrisCallback = cb;
    }

}
/*switch (activeBrick) {
                case SQUARE:
                    y = activeBrickArray.length;
                    x = activeBrickArray[0].length;
                    for (int j = startY; j < startY + y; j++) {
                        for (int i = startX; i < startX + x; i++) {
                            if(activeBrickArray[j-startY][i-startX] == 1){
                                closedBricks[j][i] = 1;
                            }
                        }
                    }
                    break;
                case I:
                    /*startY += 2;
                    for (int j = startY; j > startY - 4; j--) {
                        for (int i = startX; i < startX + 1; i++) {
                            closedBricks[j][i] = 1;
                        }
                    }
                    y = activeBrickArray.length;
                    x = activeBrickArray[0].length;
                    for (int j = startY; j < startY + y; j++) {
                        for (int i = startX; i < startX + x; i++) {
                            if(activeBrickArray[j-startY][i-startX] == 1){
                                closedBricks[j][i] = 1;
                            }
                        }
                    }
                    break;
                case ITrans:
                    startY -= 1;
                    for (int i = startX; i < startX + 4; i++) {
                        closedBricks[startY][i] = 1;
                    }
                    break;
                case L:
                    //startX += 1;
                    y = activeBrickArray.length;
                    x = activeBrickArray[0].length;
                    for (int j = startY; j < startY + y; j++) {
                        for (int i = startX; i < startX + x; i++) {
                            if(activeBrickArray[j-startY][i-startX] == 1){
                                closedBricks[j][i] = 1;
                            }
                        }
                    }
                    /*startY += 1;
                    for (int j = startY; j > startY - 3; j--) {
                        for (int i = startX; i < startX + 1; i++) {
                            if(j == startY) {
                                closedBricks[j][i+1] = 1;
                            }
                            closedBricks[j][i] = 1;
                        }
                    }
                    break;
                case LRot90:
                    //startY -= 1;
                    y = activeBrickArray.length;
                    x = activeBrickArray[0].length;
                    for (int j = startY; j < startY + y; j++) {
                        for (int i = startX; i < startX + x; i++) {
                            if(activeBrickArray[j-startY][i-startX] == 1){
                                closedBricks[j][i] = 1;
                            }
                        }
                    }
                    /*for (int i = startX; i < startX + 3; i++) {
                        if(i==startX+2){
                            closedBricks[startY-1][i] = 1;
                        }
                        closedBricks[startY][i] = 1;
                    }
                    break;
                case S:
                    for (int j = startY; j > startY - 2; j--) {
                        for (int i = startX; i < startX + 1; i++) {
                            if(j == startY){
                                closedBricks[j][i+1] = 1;
                                closedBricks[j][i] = 1;
                            } else if(j == startY-1){
                                closedBricks[j][i+2] = 1;
                                closedBricks[j][i+1] = 1;
                            }
                        }
                    }
                    break;
            }*/