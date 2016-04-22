import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.System;
import java.util.*;
abstract class Game1 extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {
//the main game loop thread
private Thread gameloop;
//internal list of sprites
private LinkedList _sprites;
public LinkedList sprites() { return _sprites; }
//screen and double buffer related variables
private BufferedImage backbuffer;
private Graphics2D g2d;
private int screenWidth, screenHeight;
//keep track of mouse position and buttons
private Point2D mousePos = new Point2D(0,0);
private boolean mouseButtons[] = new boolean[4];
//frame rate counters and other timing variables
private int _frameCount = 0;
private int _frameRate = 0;
private int desiredRate;
private long startTime = System.currentTimeMillis();
//local applet object
public Applet applet() { return this; }
//game pause state
private boolean _gamePaused = false;
public boolean gamePaused() { return _gamePaused; }
public void pauseGame() { _gamePaused = true; }
public void resumeGame() { _gamePaused = false; }
//declare the game event methods that sub-class must implement
abstract void gameStartup();
abstract void gameTimedUpdate();
abstract void gameRefreshScreen();
abstract void gameShutdown();
abstract void gameKeyDown(int keyCode);
abstract void gameKeyUp(int keyCode);
abstract void gameMouseDown();
abstract void gameMouseUp();
abstract void gameMouseMove();
abstract void spriteUpdate(AnimatedSprite sprite);
abstract void spriteDraw(AnimatedSprite sprite);
abstract void spriteDying(AnimatedSprite sprite);
abstract void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2);

public double movex;
public double movey;
public boolean thrust;
public int buttonPressed=0;
public boolean autofire=false;
public boolean mCont=false;
public int afinc=5;
public double radians = 0;
public double shipX;
public double shipY;
public boolean bgOn = true;

final double ACCELERATION = 0.1;//0.05

/*****************************************************
* constructor
*****************************************************/
public Game1(int frameRate, int width, int height) {
desiredRate = frameRate;
screenWidth = width;
screenHeight = height;
}
//return g2d object so sub-class can draw things
public Graphics2D graphics() { return g2d; }
//current frame rate
public int frameRate() { return _frameRate; }
//mouse buttons and movement
public boolean mouseButton(int btn) { return mouseButtons[btn]; }
public Point2D mousePosition() { return mousePos; }
/*****************************************************
* applet init event method
*****************************************************/
public void init() {
//create the back buffer and drawing surface
backbuffer = new BufferedImage(screenWidth, screenHeight,
BufferedImage.TYPE_INT_RGB);
g2d = backbuffer.createGraphics();
//create the internal sprite list
_sprites = new LinkedList<AnimatedSprite>();
//start the input listeners
addKeyListener(this);
addMouseListener(this);
addMouseMotionListener(this);
//this method implemented by sub-class
gameStartup();
}
/*****************************************************
* applet update event method
*****************************************************/
public void update(Graphics g) {
//calculate frame rate
_frameCount++;
if (System.currentTimeMillis() > startTime + 1000) {
startTime = System.currentTimeMillis();
_frameRate = _frameCount;
_frameCount = 0;
//once every second all dead sprites are deleted
purgeSprites();
}
//this method implemented by sub-class
gameRefreshScreen();
//draw the internal list of sprites
if (!gamePaused()) {
drawSprites();
}
//redraw the screen
paint(g);
}
/*****************************************************
* applet window paint event method
*****************************************************/
public void paint(Graphics g) {
g.drawImage(backbuffer, 0, 0, this);
}
/*****************************************************
* thread start event - start the game loop running
*****************************************************/
public void start() {
gameloop = new Thread(this);
gameloop.start();
}
/*****************************************************
* thread run event (game loop)
*****************************************************/
public void run() {
//acquire the current thread
Thread t = Thread.currentThread();
//process the main game loop thread
while (t == gameloop) {
try {
//set a consistent frame rate
Thread.sleep(1000 / desiredRate);
}
catch(InterruptedException e) {
e.printStackTrace();
}
//update the internal list of sprites
if (!gamePaused()) {
updateSprites();
testCollisions();
}
//allow main game to update if needed
gameTimedUpdate();
//refresh the screen
repaint();
}
}
/*****************************************************
* thread stop event
*****************************************************/
public void stop() {
//kill the game loop
gameloop = null;
//this method implemented by sub-class
gameShutdown();
}

/*****************************************************
* increase the thrust of the ship based on facing angle
*****************************************************/
public void applyThrust() {
//the ship is always the first sprite in the linked list
AnimatedSprite ship = (AnimatedSprite)sprites().get(0);
//up arrow adds thrust to ship (1/10 normal speed)
ship.setMoveAngle(ship.faceAngle() - 90);
//calculate the X and Y velocity based on angle
double velx = ship.velocity().X();
velx += calcAngleMoveX(ship.moveAngle()) * ACCELERATION;
if (velx < -10) velx = -10;
else if (velx > 10) velx = 10;
double vely = ship.velocity().Y();
vely += calcAngleMoveY(ship.moveAngle()) * ACCELERATION;
if (vely < -10) vely = -10;
else if (vely > 10) vely = 10;
ship.setVelocity(new Point2D(velx, vely));
}

public void applyBackwardsThrust() {
//the ship is always the first sprite in the linked list
AnimatedSprite ship = (AnimatedSprite)sprites().get(0);
//up arrow adds thrust to ship (1/10 normal speed)
ship.setMoveAngle(ship.faceAngle() - 90);
//calculate the X and Y velocity based on angle
double velx = ship.velocity().X();
velx -= calcAngleMoveX(ship.moveAngle()) * ACCELERATION;
if (velx < -10) velx = -10;
else if (velx > 10) velx = 10;
double vely = ship.velocity().Y();
vely -= calcAngleMoveY(ship.moveAngle()) * ACCELERATION;
if (vely < -10) vely = -10;
else if (vely > 10) vely = 10;
ship.setVelocity(new Point2D(velx, vely));
}

public void mouseControl(AnimatedSprite sprite){
	Cursor crosshairCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
	setCursor(crosshairCursor);
	radians = (((Math.atan((movey - shipY)/(movex - shipX)))*180)/Math.PI)+90; // radians calculation formula
	sprite.setFaceAngle(radians);
	    if (movex-shipX<0) 
	    { 
	        sprite.setFaceAngle(sprite.faceAngle()-180); 
	    } 
	    
		if(thrust){
		switch(buttonPressed){
			case 1:
					applyThrust();
			break;
			case 2:
				
			break;
			case 3:
					applyBackwardsThrust();
			break;
		}
	}
}



/*****************************************************
* key listener events
*****************************************************/
public void keyTyped(KeyEvent k) { }
public void keyPressed(KeyEvent k) {
gameKeyDown(k.getKeyCode());
}
public void keyReleased(KeyEvent k) {
gameKeyUp(k.getKeyCode());
}
/*****************************************************
* checkButtons stores the state of the mouse buttons
*****************************************************/
private void checkButtons(MouseEvent e) {
switch(e.getButton()) {
case MouseEvent.BUTTON1:
mouseButtons[1] = true;
mouseButtons[2] = false;
mouseButtons[3] = false;
buttonPressed=1;
break;
case MouseEvent.BUTTON2:
mouseButtons[1] = false;
mouseButtons[2] = true;
mouseButtons[3] = false;
buttonPressed=2;
break;
case MouseEvent.BUTTON3:
mouseButtons[1] = false;
mouseButtons[2] = false;
mouseButtons[3] = true;
buttonPressed=3;
break;
}
}
/*****************************************************
* mouse listener events
*****************************************************/
public void mousePressed(MouseEvent e) {
checkButtons(e);
mousePos.setX(e.getX());
mousePos.setY(e.getY());
thrust=true;
gameMouseDown();
}
public void mouseReleased(MouseEvent e) {
checkButtons(e);
mousePos.setX(e.getX());
mousePos.setY(e.getY());
thrust=false;
gameMouseUp();
}
public void mouseMoved(MouseEvent e) {
checkButtons(e);
mousePos.setX(e.getX());
mousePos.setY(e.getY());
//movex=e.getX();
//movey=e.getY();
gameMouseMove();
}
public void mouseDragged(MouseEvent e) {
checkButtons(e);
mousePos.setX(e.getX());
mousePos.setY(e.getY());
gameMouseDown();
gameMouseMove();
}
public void mouseEntered(MouseEvent e) {
mousePos.setX(e.getX());
mousePos.setY(e.getY());
gameMouseMove();
}
public void mouseExited(MouseEvent e) {
mousePos.setX(e.getX());
mousePos.setY(e.getY());
gameMouseMove();
}
//this event is not needed
public void mouseClicked(MouseEvent e) { }
/*****************************************************
* X and Y velocity calculation functions
*****************************************************/
protected double calcAngleMoveX(double angle) {
return (double)(Math.cos(angle * Math.PI / 180));
}
protected double calcAngleMoveY(double angle) {
return (double) (Math.sin(angle * Math.PI / 180));
}
/*****************************************************
* update the sprite list from the game loop thread
*****************************************************/
protected void updateSprites() {
for (int n=0; n < _sprites.size(); n++) {
AnimatedSprite spr = (AnimatedSprite) _sprites.get(n);
if (spr.alive()) {
spr.updatePosition();
spr.updateRotation();
spr.updateAnimation();
spriteUpdate(spr);
spr.updateLifetime();/////////////////////////////////////////////////////////////////////
if (!spr.alive()) {
spriteDying(spr);
}
}
}
}
/*****************************************************
* perform collision testing of all active sprites
*****************************************************/
protected void testCollisions() {
//iterate through the sprite list, test each sprite against
//every other sprite in the list
for (int first=0; first < _sprites.size(); first++) {
//get the first sprite to test for collision
AnimatedSprite spr1 = (AnimatedSprite) _sprites.get(first);
if (spr1.alive()) {
//look through all sprites again for collisions
for (int second = 0; second < _sprites.size(); second++) {
//make sure this isn�t the same sprite
if (first != second) {
//get the second sprite to test for collision
AnimatedSprite spr2 = (AnimatedSprite)
_sprites.get(second);
if (spr2.alive()) {
if (spr2.collidesWith(spr1)) {
spriteCollision(spr1, spr2);
break;
}
else
spr1.setCollided(false);/////////////////////////////////////////////////////////////////////
}
}
}
}
}
}
/*****************************************************
* draw all active sprites in the sprite list
* sprites lower in the list are drawn on top
*****************************************************/
protected void drawSprites() {
//draw sprites in reverse order (reverse priority)
for (int n=0; n<_sprites.size(); n++) {
AnimatedSprite spr = (AnimatedSprite) _sprites.get(n);
if (spr.alive()) {
spr.updateFrame();/////////////////////////////////////////////////////////////////////////
spr.transform();
spr.draw();
spriteDraw(spr); //notify player
}
}
}
/*****************************************************
* once every second during the frame update, this method
* is called to remove all dead sprites from the linked list
*****************************************************/
private void purgeSprites() {
for (int n=0; n < _sprites.size(); n++) {
AnimatedSprite spr = (AnimatedSprite) _sprites.get(n);
if (!spr.alive()) {
_sprites.remove(n);
}
}
}
}