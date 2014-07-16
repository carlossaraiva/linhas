import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import beads.*; 
import java.util.Arrays; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class linhas extends PApplet {


 

Linha linha1 = new  Linha(300/2, 400/2, 400, 5, true);
Linha linha2 = new  Linha(200, 300, 400, 5, true);
LThread thread1 = new LThread(100,"linha1", linha1);
LThread thread2 = new LThread(200,"linha1", linha2);


public void setup(){
  size(400, 300);
  background(255);
  frameRate(60);
 
  thread1.start();
  thread2.start();

}

public void draw(){
  background(255);
  fill(0);

  int a = thread1.getCount();
  text(a,10,50);
  thread1.draw();
  int b = thread2.getCount();
  text(b,10,150);
  thread2.draw();
}
// iport beads,*;

// class Audio{

//   float baseFrequency = 200.0f;
//   int sineCount = 10;

//   WavePlayer sineTone;
//   Glide frequency;
//   Gain gain;


//   Audio(WavePlayer tone, float frequency){



//   }



// }

//   AudioContext ac;

//   WavePlayer freqModulator = new WavePlayer(ac, 1000, Buffer.SINE);
//   Function function = new Function(freqModulator){
//   	public float calculate(){
//   		return x[0] * 500.0 + 700.0;
//   	}
//   };
//   WavePlayer wp = new WavePlayer(ac, function, Buffer.SINE);
//   Gain g = new Gain(ac, 1, 0.1);
//   g.addInput(wp);
//   ac.out.addInput(g);
//   ac.start();

// }

//   ac = new  AudioContext(); 
class LThread extends Thread{
		boolean running;
		int wait;
		String id;
		int count;
		Linha linha;

	LThread(int w, String s, Linha linha){
		wait = w;
		running = false;
		id = s;
		count = 0;
		this.linha = linha;
	}

	public void start(){
		running = true;
		println("Starting thread (will execute every " + wait + " milliseconds.");
		println("Linha desenhando");
		super.start();		
	}

	public void run(){
		while(running){
			println(id + ": " + count);
			count++;
			println(linha.getVelocity());
			this.linha.update();
	 		try{
	 			sleep((long)(wait));
	 		}catch (Exception e){
	 		}
	 	}
	println("The thread is dead!");

}
	public void draw(){
		this.linha.draw();		
	}

	public void quit(){
		println("Quiting");
		running = false;
		interrupt();
	}

	public int getCount(){
		return count;
	}
}
class Linha{
  
  private float y, len;
  private boolean alive;
  private boolean blink;
  private float velocity;
  private float alpha;
  private float aux;
  
  Linha(float x, float y, int len, float velocity){
    this.y = y;
    this.len = len;
    this.alive = true;
    this.blink = true;
    this.velocity = velocity;
    this.alpha = 0;
  }
  
  Linha(float x, float y, int len, float velocity, boolean blink){
    this.y = y;
    this.len = len;
    this.alive = true;
    this.velocity = velocity;
    this.alpha = 0;
    this.blink = blink;
  }    

  public void setBlink(boolean blink){
    this.blink = blink;  
  }
  
  public boolean getBlink(){
      return this.blink;
  }

  public float getY(){
    return this.y;
  }
  
  public float[] getPosition(){
    return new float[] {this.y};
  }

  public void setAlive(boolean alive){
    this.alive = alive;
  }

  public boolean getAlive(){
    return this.alive;
  }
  
  public float  getVelocity(){
    return this.velocity;
  }

  public void updateY(float y){
        this.y = y;
      
  }

  public void update(){
    if(this.alive){   
      if(this.blink){
        this.aux = blink(this.alpha);
        println("chegou no A");
      }
    }
    else{
        this.aux = 255;
        println("cheogou no B");
    }

  }
   
  public void draw(){
    stroke(0, 0, 0, this.aux);
    line(this.y, 0, this.y,len);
    println(this.y  + "" + len);
    println("alpha: " + this.aux + " velocity: " + this.velocity);  
  }
  
  private float blink(float alpha){
    this.alpha += this.velocity;
    if(this.alpha > 255 || this.alpha < 0){    
      this.velocity = -this.velocity;       
    }
  return this.alpha;
  }  
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "linhas" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
