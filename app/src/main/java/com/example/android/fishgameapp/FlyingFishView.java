package com.example.android.fishgameapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class FlyingFishView extends View
{

    private Bitmap fish[] = new Bitmap[2];

    private int fishX=10,fishY,fishSpeed,canvasWidth,canvasHeight;

    private Boolean touch = false;
    private Bitmap backgroundImage;


    private int redX,redY,redSpeed = 50;
    private Paint redPaint = new Paint();



    private int yellowX,yellowY,yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private int score,lifeCounterofFish;

    private Paint greenPaint= new Paint();
    private int greenX,greenY,greenSpeed = 25;

    private Paint blackPaint= new Paint();
    private int blackX,blackY,blackSpeed = 50;





    private Paint scorePaint= new Paint();

    private Bitmap life[] =new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        fish [0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish [1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY = 550;
        lifeCounterofFish=3;
        score = 0;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();



        canvas.drawBitmap(backgroundImage,0,0,null);

        int minFishY= fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if(fishY<minFishY)
        {
            fishY=minFishY;
        }
        if(fishY> maxFishY)
        {
            fishY=maxFishY;
        }
        fishSpeed = fishSpeed+2;

        if(touch)
        {
            canvas.drawBitmap(fish[1],fishX,fishY,null);
            touch = false;

        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY,null);

        }



        yellowX = yellowX - yellowSpeed;
        if(hitBallChecker(yellowX,yellowY))
        {
            score = score + 10;
            yellowX = - 100;

        }

        if(yellowX < 0 )
        {
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY))+ minFishY ;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);



        greenX = greenX - greenSpeed;
        if(hitBallChecker(greenX,greenY))
        {

            score = score + 25;
            greenX = - 100;
        }

        if(greenX < 0 )
        {
            greenX = canvasWidth +21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY))+ minFishY ;
        }
        canvas.drawCircle(greenX,greenY,35,greenPaint);


        blackX = blackX - blackSpeed;
        if(hitBallChecker(blackX,blackY))
        {
            score = score + 75;

            blackX = - 100;
        }

        if(blackX < 0 )
        {
            blackX = canvasWidth +21;
            blackY = (int) Math.floor(Math.random() * (maxFishY - minFishY))+ minFishY ;
        }
        canvas.drawCircle(blackX,blackY,10,blackPaint);



        redX = redX - redSpeed;
        if(hitBallChecker(redX,redY))
        {


            lifeCounterofFish--;
            redX = - 100;

            if(lifeCounterofFish == 0)
            {
                Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT).show();
                Intent gameoverIntent = new Intent (getContext(),GameOverActivity.class);
                gameoverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                gameoverIntent.putExtra("score",score);
                getContext().startActivity(gameoverIntent);
            }
        }

        if(redX < 0 )
        {
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY))+ minFishY ;
        }
        canvas.drawCircle(redX,redY,25,redPaint);



        canvas.drawText("Score :"+score,20, 60 ,scorePaint);

        for (int i =0 ;i<3 ; i++)
        {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if(i < lifeCounterofFish)
            {
                canvas.drawBitmap(life[0],x,y,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x,y,null);
            }
        }






    }


    public boolean hitBallChecker(int x, int y)
    {

        if(fishX < x && x< (fish[0].getWidth() +fishX) && fishY < y && y <(fishY + fish[0].getHeight()) )
        {

            return true;
        }

        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
            if(event.getAction() == MotionEvent.ACTION_DOWN)
            {
                touch = true;
                fishSpeed = -25;
            }
            return  true;
    }
}
