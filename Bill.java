import java.awt.*;
import java.util.*;
import java.lang.System;
import java.awt.event.*;
public class Bill extends Game {
 static int FRAMERATE = 60;
 static int SCREENWIDTH = 1200;
 static int SCREENHEIGHT = 700;
 final double MAINROTATION = 5.0;
 /////////////////////////////////////////////////////////////// speed variables
 double MAIN_SPEED = 4;
 int WEAPON_SPEED = 12;
 int ENEMY_WEAPON_SPEED = 10;
 double ENEMY_SPEED_X = 0.5;
 double ENEMY_SPEED_Y = 3;
 int coolDown=0; // changes the rate of fire (higher=slower)
 //////////////////////////////////////////////////////////////////////////////
 //sprite state values
 final int STATE_NORMAL = 0;
 final int STATE_COLLIDED = 1;
 /////////////////////////////////weapon types
 final int PENCIL = 0;
 final int MULTI_SHOT = 1;
 final int CD = 2;
 final int SCISSORS = 3;
 final int STAPLER = 4;
 final int BULLET = 5;
 final int MINE = 6;
 // enemy weapons
 final int FOOTBALL = 0;
 final int SHOE = 1;
 final int TEST = 2;
 ///////////////////////////////weapon variables
 int weaponType = 0;
 int maxWeapons = 0;
 int enemyWeaponType = 0;
 int maxEnemyWeapons = 3;
 int enemyCoolDown=0;
 double damage = 1;
 int WEAPON_LIFESPAN = 90;
 int ENEMY_WEAPON_LIFESPAN = 90;
 int CD_AMMO = 3000;
 int SCISSORS_AMMO = 80;
 int STAPLER_AMMO = 400;
 int BULLET_AMMO = 40;
 int MULTI_SHOT_AMMO = 60;
 int MINE_AMMO = 50;

 int[] ammo={
  999999,
  MULTI_SHOT_AMMO,
  CD_AMMO,
  SCISSORS_AMMO,
  STAPLER_AMMO,
  BULLET_AMMO,
  MINE_AMMO
 };
 int reloadAmmo[] = new int[ammo.length];
 //sprite types
 final int SPRITE_MAIN = 1;
 //final int SPRITE_ARM = 9;
 final int SPRITE_ENEMY_BALLERINA = 10;
 final int SPRITE_ENEMY_GHOST = 11;
 final int SPRITE_ENEMY_FBP = 12;
 final int SPRITE_ENEMY_TEACHER = 13;
 final int SPRITE_ENEMY_PACMAN = 14;
 final int SPRITE_ENEMY_TEST = 15;
 final int SPRITE_ENEMY_GHOST_DYING = 16;
 final int GHOST=0;
 final int TEACHER=1;
 final int BALLERINA=2;
 final int FBP=3;
 final int PACMAN=4;
 int enemyType;
 int[] incEnWep={0,0,0,0};
 final int SPRITE_WEAPON = 100;
 final int SPRITE_ENEMY_WEAPON = 101;
 final int SPRITE_EXPLOSION = 200;
 //game states
 final int GAME_MENU = 1;
 final int GAME_RUNNING = 2;
 final int GAME_PAUSED = 3;
 final int GAME_OVER = 4;
 final int GAME_CTRLSCREEN = 5;
 final int GAME_OBJSCREEN = 6;
 final int BOSS1 = 7;
 final int BOSS2 = 8;
 final int BOSS3 = 9;
 int currentLevel=1;
 //various toggles
 boolean showBounds = false;
 boolean collisionTesting = true;
 int bg=0;
 int frontLawn=0;
 int titleScreen=1;
 int gameOver=2;
 int objective=3;
 int controls=4;
 int pause=5;
 int bloody=6;
 //define the images used in the game
 ImageEntity[] background = new ImageEntity[8];
 ImageEntity arrow;
 ImageEntity[] weaponImage = new ImageEntity[10];
 ImageEntity[] enemyWeaponImage = new ImageEntity[5];
 ImageEntity[] ghosts = new ImageEntity[6];
 ImageEntity[] enemies = new ImageEntity[10];
 ImageEntity[] explosions = new ImageEntity[5];
 ImageEntity[] mainImage = new ImageEntity[5];
 double maxMainHealth = 50;
 double mainHealth = maxMainHealth;
 double maxhouseHP = 20;
 double houseHP = maxhouseHP;
 int shield = 20;
 int score = 0;
 int highscore = 0;
 int cdinc=0;
 public double radians = 0.0;
 public int jitterNum=4;
 boolean billWalking=false;
 boolean billThrowing=false;
 boolean throwIt=false;
 boolean auto=true;
 boolean thrownOnce=false;
 boolean bgOn=true;
 boolean laserGuide=false;
 boolean createEnemy=true;
 int gameState = GAME_MENU;
 Random rand = new Random();
 long collisionTimer = 0;
 //some key input tracking variables
 boolean keyLeft, keyRight, keyUp, keyDown, keyFire, clickShield;
 //sound effects and music
 // MidiSequence music = new MidiSequence();
 Point2D center = new Point2D(SCREENHEIGHT/2,SCREENWIDTH/2);
 double enemyX;
 double enemyY;
 int count=0;
 // screen shake variables
 int bgX=0;
 int bgY=0;
 int shakeDuration=10;//10
 int shakeCount=shakeDuration;
 int shakeSize=10;//5
 boolean shake=false;
 boolean mainAlive=true;
 boolean armDrawn=false;
 double mainXLeftCorner;
 double mainYLeftCorner;
 //....................1..2..3..4.5.6..7..8..9
 int[] enemyCount = {0,10,20,35,5,8,12,25,15,30};
 int reloadEC[] = new int[enemyCount.length];
 boolean countFinished=false;
 double start,start0=System.currentTimeMillis();
// double start0=System.currentTimeMillis();
 int enemySprites=0;
 int weaponSprites=0;
 int gameOverCount=0;
 boolean clear=false;
 boolean alreadyNotified=false;
 int anCount=0;
 boolean flash=true;
 int flct=10;
 int enemy=0;
 int choice=0;
 boolean firstRun=true;
 boolean drawBlood=false;
 boolean mainHit=false;
 ////////////////////////^ END OF VARIABLES ^//////////////////////////////

 public Bill() {
  //call base Game class constructor
  super(FRAMERATE, SCREENWIDTH, SCREENHEIGHT);
 }

 void gameStartup() {
     background[0] = new ImageEntity(this);
     background[0].load("FrontLawn.png");
     background[1] = new ImageEntity(this);
     background[1].load("TitleScreen.png");
     background[2] = new ImageEntity(this);
     background[2].load("backgroundgameover.png");
     background[3] = new ImageEntity(this);
     background[3].load("backgroundobjective.png");
     background[4] = new ImageEntity(this);
     background[4].load("backgroundcontrols.png");
     background[5] = new ImageEntity(this);
     background[5].load("backgroundpause.png");
     background[6] = new ImageEntity(this);
     background[6].load("frontlawnbloody.png");
     
     arrow = new ImageEntity(this);
     arrow.load("arrow.png");
     //load main images
     mainImage[0] = new ImageEntity(this);
     mainImage[0].load("billstanding.png");
     mainImage[1] = new ImageEntity(this);
     mainImage[1].load("billwalking.png");
     mainImage[3] = new ImageEntity(this);
     mainImage[3].load("billarmfix.png");
     //load explosion images
  	 explosions[0] = new ImageEntity(this);
   	 explosions[0].load("explosion0.png");
 	 explosions[1] = new ImageEntity(this);
 	 explosions[1].load("explosion1.png");
     //load the enemy sprite image
	 ghosts[0] = new ImageEntity(this);
	 ghosts[0].load("inky.png");
	 ghosts[1] = new ImageEntity(this);
	 ghosts[1].load("blinky.png");
	 ghosts[2] = new ImageEntity(this);
	 ghosts[2].load("pinky.png");
	 ghosts[3] = new ImageEntity(this);
	 ghosts[3].load("clyde.png");
	 ghosts[5] = new ImageEntity(this);
	 ghosts[5].load("ghostdying.png");
     enemies[0] = new ImageEntity(this);
     enemies[0].load("FootballPlayer.png");
     enemies[1] = new ImageEntity(this);
     enemies[1].load("bearballerina.png");
     //load the enemy weapon sprite images
     enemyWeaponImage[FOOTBALL] = new ImageEntity(this);
     enemyWeaponImage[FOOTBALL].load("football.png");
     enemyWeaponImage[SHOE] = new ImageEntity(this);
     enemyWeaponImage[SHOE].load("shoe.png");
     enemyWeaponImage[TEST] = new ImageEntity(this);
     enemyWeaponImage[TEST].load("Test.png");
     //load the weapon sprite images
     weaponImage[STAPLER] = new ImageEntity(this);
     weaponImage[STAPLER].load("stapler.png");
     weaponImage[CD] = new ImageEntity(this);
     weaponImage[CD].load("CD.png");
     weaponImage[SCISSORS] = new ImageEntity(this);
     weaponImage[SCISSORS].load("scissors.png");
     weaponImage[PENCIL] = new ImageEntity(this);
     weaponImage[PENCIL].load("pencil.png");
     weaponImage[MINE] = new ImageEntity(this);
     weaponImage[MINE].load("laptop.png");
  	 weaponImage[BULLET] = new ImageEntity(this);
     weaponImage[BULLET].load("powerup_gun.png");
     weaponImage[MULTI_SHOT] = new ImageEntity(this);
     weaponImage[MULTI_SHOT].load("pencil.png");
     Cursor crosshairCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
     setCursor(crosshairCursor);
     addBillInit();
	 //set reload arrays equal to original arrays
     for(int i=0; i<ammo.length; i++){reloadAmmo[i]=ammo[i];}
 	 for(int i=0; i<enemyCount.length; i++){reloadEC[i]=enemyCount[i];}
 }

 private void resetGame() {
     AnimatedSprite main = (AnimatedSprite) sprites().get(0);
     clear=true;
     main.setState(STATE_NORMAL);
     main.setPosition(new Point2D(1300, 350));
     mainHealth=maxMainHealth;
     //reset variables
     currentLevel=1;
     maxWeapons=0;
     weaponType=0;
     score = 0;
     houseHP=maxhouseHP;
     // reset ammo
   	 for(int i=0; i<ammo.length; i++){
 		ammo[i]=reloadAmmo[i];
 	 }
  	 // reset level counters
  	 	for(int i=0; i<enemyCount.length; i++){
 		enemyCount[i]=reloadEC[i];
 	 }
 	 gameOverCount+=1;
 }
 
 public void clearEnemySprites(AnimatedSprite spr){
 		if(isEnemy(spr.spriteType())){//||spr.spriteType()==SPRITE_ENEMY_WEAPON||spr.spriteType()==SPRITE_WEAPON){
 			spr.setAlive(false);
 			if(isEnemy(spr.spriteType()))enemySprites-=1;
 		}
 		if(enemySprites<=0)clear=false;
 }
 /*****************************************************
 * gameTimedUpdate event passed by game engine
 *****************************************************/
 void gameTimedUpdate() {
  checkInput();
  if (!gamePaused()&&(mainHealth<=0||houseHP<=0)) {
   resetGame();
   gameState = GAME_OVER;
   firstRun=false;
  }
 }

 /*****************************************************
 * gameRefreshScreen event passed by game engine
 *****************************************************/
 void gameRefreshScreen() {
     Graphics2D g2d = graphics();
     if(shake){shakeScreen();}
      g2d.drawImage(background[bg].getImage(),bgX,bgY,SCREENWIDTH-1,SCREENHEIGHT-1,this);
      if(drawBlood){g2d.drawImage(background[bloody].getImage(),0,0,SCREENWIDTH-1,SCREENHEIGHT-1,this);}

     if (gameState == GAME_MENU) {
     	bg=titleScreen;
     }

     else if (gameState == GAME_RUNNING) {

     	 bg=frontLawn;
       	 drawInventory(g2d);
         g2d.setFont(new Font("Verdana", Font.BOLD, 18));
         g2d.setColor(Color.WHITE);
         g2d.drawString("LEVEL "+currentLevel,25,20);
         //g2d.drawString("enemySprites: "+enemySprites, 25, 60);
         drawMainHealthBar(800,5,200,10);
         g2d.drawString("Bill",775,15);
         drawhouseHPBar(650,5,100,10);
         g2d.drawString("House",600,15);
   		 drawCoolDown(g2d);
         if(laserGuide){g2d.setColor(Color.RED);g2d.drawLine((int)mainX,(int)mainY,(int)moveX,(int)moveY);}
  		 //spawnRandEnemy();
         if(auto){
             autoThrow();
             cdinc++;
             if(cdinc>coolDown)
              cdinc=coolDown;
         }
		////////////////////////////////////LEVELS////////////////////////////////////////

         	switch(currentLevel){
	         	case 1://level 1
		         	runLevel(1,0,0,1000);
	         	break;

	         	case 2://level 2
					runLevel(1,0,0,600);
	         	break;

	         	case 3://level 3
	         		maxWeapons=1;
	         		if(!alreadyNotified){newWeaponNotify(g2d);}
					runLevel(1,0,0,300);
	         	break;

	         	case 4://level 4
					runLevel(2,0,0,4000);
	         	break;

	         	case 5://level 5
	         		maxWeapons=2;
					if(!alreadyNotified){newWeaponNotify(g2d);}
					runLevel(2,0,0,1000);

	         	break;

	         	case 6://level 6
					runLevel(2,0,0,600);
	         	break;

	         	case 7://level 7
	         		maxWeapons=3;
	         		if(!alreadyNotified){newWeaponNotify(g2d);}
					runLevel(2,1,0,300);
	         	break;

	         	case 8://level 8
					runLevel(3,0,0,1000);
	         	break;

	         	case 9://level 9
	         		maxWeapons=4;
	         		if(!alreadyNotified){newWeaponNotify(g2d);}
					runLevel(2,3,0,500);
	         	break;

	         	case 10://level 10
					runLevel(1,2,3,100);
	         	break;
         	}

     	/////////////////////////////////END LEVELS///////////////////////////////////////
     }

     if (gameState == GAME_PAUSED){
		bg=pause;
     }

     if (gameState == GAME_OVER) {
         bg=gameOver;
     }

     if (gameState == GAME_CTRLSCREEN) {
         bg=controls;
     }

     if (gameState == GAME_OBJSCREEN) {
         bg=objective;
     }
 }

 /*****************************************************
 * gameShutdown event passed by game engine
 *****************************************************/
 void gameShutdown() {
//  music.stop();
 }
 /*****************************************************
 * spriteUpdate event passed by game engine
 *****************************************************/
 public void spriteUpdate(AnimatedSprite sprite) {
  AnimatedSprite main = new AnimatedSprite(this, graphics());
  //seperately set enemy properties
      switch(sprite.spriteType()) {
      	case SPRITE_ENEMY_BALLERINA:
          enemyType=BALLERINA;
          //enemyWeaponType=SHOE;
          //enemyCoolDown=300;
          break;
         case SPRITE_ENEMY_GHOST:
          enemyType=GHOST;
          break;
         case SPRITE_ENEMY_FBP:
          enemyType=FBP;
          enemyCoolDown=180;
          enemyWeaponType=FOOTBALL;
          break;
         case SPRITE_ENEMY_TEACHER:
          enemyType=TEACHER;
          enemyCoolDown=100;
          enemyWeaponType=TEST;
          break;
     }
     switch(sprite.spriteType()) {
         case SPRITE_MAIN:
           Point2D mainpos = new Point2D(main.position().X(),main.position().Y());
             mainX=sprite.center().X();
             mainY=sprite.center().Y();
			 mainXLeftCorner = sprite.position().X();
			 mainYLeftCorner = sprite.position().Y();

             mouseControl(sprite);
             if(gameState!=GAME_MENU||gameState!=GAME_CTRLSCREEN||gameState!=GAME_OBJSCREEN){stopAtEdge(sprite);}

             if(billWalking){
              addBillWalking(sprite);
             }else addBillStanding(sprite);
			AnimatedSprite arm = new AnimatedSprite(this, graphics());

             //if(billThrowing==true&&armDrawn==false){
              //addBillThrowing(sprite);
              //armDrawn=true;
             //}

             break;
             /*
         case SPRITE_ARM:
			mouseControl(sprite);
         	sprite.setPosition(new Point2D(mainXLeftCorner,mainYLeftCorner));

         	if(billThrowing==false){
         		sprite.setAlive(false);
         		armDrawn=false;
         	}
         	break;
			*/
         case SPRITE_WEAPON:
          applyWeaponProp(weaponType);
          switch(enemyWeaponType){
           case FOOTBALL:
            ENEMY_WEAPON_SPEED=5;
            ENEMY_WEAPON_LIFESPAN=300;
            break;
           case SHOE:
            ENEMY_WEAPON_SPEED=8;
            ENEMY_WEAPON_LIFESPAN=100;
            break;
           case TEST:
            ENEMY_WEAPON_SPEED=12;
            ENEMY_WEAPON_LIFESPAN=100;
            break;
          }
    break;
         case SPRITE_ENEMY_BALLERINA:
         case SPRITE_ENEMY_GHOST:
         case SPRITE_ENEMY_FBP:
         case SPRITE_ENEMY_TEACHER:
           enemyX = sprite.center().X();
           enemyY = sprite.center().Y();
             autoMove(sprite);
             if(sprite.spriteType()!=SPRITE_ENEMY_GHOST&&
             	sprite.spriteType()!=SPRITE_ENEMY_BALLERINA){
	             //sprite faces bill at all times
	             faceMain();
	             sprite.setFaceAngle(radians);
             }
             //occasionally reverse direction
             if(rand.nextInt(1000)>997){
              reverseDirY(sprite);
             }
             //fire enemy weapon
             if(sprite.spriteType()==SPRITE_ENEMY_FBP||
             	sprite.spriteType()==SPRITE_ENEMY_TEACHER){
	             if(incEnWep[enemyType]>=enemyCoolDown){
	              incEnWep[enemyType]=0;
	              fireEnemyWeapon(sprite);
	             }
	             else
	              incEnWep[enemyType]++;
             }

             if(sprite.spriteType()==SPRITE_ENEMY_BALLERINA){
             	sprite.setFaceAngle(sprite.faceAngle()+10);
             	if(sprite.faceAngle()>360)
             		sprite.setFaceAngle(0);
             }

             break;
         case SPRITE_ENEMY_GHOST_DYING:
         	autoMoveReverse(sprite);

         	if(sprite.position().X()<=1){
         		sprite.setAlive(false);
         		spawnGhost(sprite.position());
         	}

         	break;
         case SPRITE_EXPLOSION:
   if (sprite.currentFrame() == sprite.totalFrames()-1) {
    sprite.setAlive(false);
  }
       break;
     }
     // kill all sprites that are off screen
     if(gameState!=GAME_MENU){
      killOffScreen(sprite);
	      if(sprite.position().X()>=SCREENWIDTH&&isEnemy(sprite.spriteType())){
	     	houseHP-=1;
	     }
     }
		if(clear)clearEnemySprites(sprite);
 }

 public void applyWeaponProp(int weaponType){
      switch(weaponType){
       case PENCIL:
        coolDown=12;
        damage=1;
        WEAPON_SPEED=12;
        WEAPON_LIFESPAN=90;
        jitterNum=0;
        break;
       case CD:
        coolDown=0;
        damage=0.3;
        WEAPON_SPEED=12;
        WEAPON_LIFESPAN=90;
        jitterNum=4;
        break;
       case SCISSORS:
        coolDown=12;
        damage=0.5;
        WEAPON_SPEED=12;
        WEAPON_LIFESPAN=90;
        jitterNum=0;
        break;
       case STAPLER:
        coolDown=2;
        damage=2;
        WEAPON_SPEED=18;
        WEAPON_LIFESPAN=90;
        jitterNum=20;
        break;
       case BULLET:
        coolDown=20;
        damage=30;
        WEAPON_SPEED=50;
        WEAPON_LIFESPAN=90;
        jitterNum=0;
        break;
       case MULTI_SHOT:
        coolDown=20;
        damage=1;
        WEAPON_SPEED=12;
        WEAPON_LIFESPAN=90;
        jitterNum=0;
        break;
       case MINE:
        coolDown=50;
        damage=100;
        WEAPON_SPEED=0;
        WEAPON_LIFESPAN=3000;
        jitterNum=0;
        break;
      }
 }

 public void spriteDraw(AnimatedSprite sprite) {
     if (showBounds) {
         if (sprite.collided())
             sprite.drawBounds(Color.RED);
         else
             sprite.drawBounds(Color.BLUE);
     }
     if(isEnemy(sprite.spriteType())){
      drawHealthBar(sprite,(int)sprite.position().X(),(int)sprite.position().Y());
     }
 }

 private boolean isEnemy(int spriteType) {
     switch(spriteType) {
         case SPRITE_ENEMY_BALLERINA:
         case SPRITE_ENEMY_GHOST:
         case SPRITE_ENEMY_FBP:
         case SPRITE_ENEMY_TEACHER:
         case SPRITE_ENEMY_GHOST_DYING:
             return true;
         default:
             return false;
     }
 }

 /*****************************************************
 * spriteDying event passed by game engine
 * called after a sprites age reaches its lifespan
 * at which point it will be killed off, and then removed from
 * the linked list. you can cancel the purging process here.
 *****************************************************/
 public void spriteDying(AnimatedSprite sprite) {
  //currently no need to revive any sprites
 }
 /*****************************************************
 * spriteCollision event passed by game engine
 *****************************************************/
 public void spriteCollision(AnimatedSprite spr1, AnimatedSprite spr2) {
     if (!collisionTesting) return;
     //figure out what type of sprite has collided
     switch(spr1.spriteType()) {
         case SPRITE_WEAPON:
             if (isEnemy(spr2.spriteType())) { // weapon collides with enemy
	             if(weaponType!=SCISSORS) spr1.setAlive(false);
		             spr2.setHealth(spr2.health()-damage);
		             if(spr2.health()<=0){
		             	if(spr2.spriteType()!=SPRITE_ENEMY_GHOST){
		                 shake=true;
		                 startExplosion0(spr2.position());
		                 spr2.setAlive(false);enemySprites-=1;
		             	}
		             	else{
		             		spr2.setAlive(false);
		             		spawnGhostDying(spr2.position());
		             	}
		             }
             }
             break;

         case SPRITE_ENEMY_WEAPON:
              if(spr2.spriteType()==SPRITE_MAIN) {
	               mainHealth=mainHealth-1;
	               startExplosion1(spr1.position());
	               shake=true;
	               spr1.setAlive(false);
	               mainHit=true;
             	}
         case SPRITE_MAIN:

             break;
         case SPRITE_ENEMY_BALLERINA:
         if(spr2.spriteType()==SPRITE_MAIN) {
		               mainHealth=mainHealth-0.1;
		               shake=true;
		               mainHit=true;
	             	}
	         break;
         case SPRITE_ENEMY_GHOST:
         case SPRITE_ENEMY_FBP:
         case SPRITE_ENEMY_TEACHER:
	         if(spr2.spriteType()==SPRITE_MAIN) {
		               mainHealth=mainHealth-0.05;
		               mainHit=true;
		               shake=true;
	             	}
             break;
     }
 }

 /*****************************************************
 * A key is pressed
 *****************************************************/

 public void gameKeyDown(int keyCode) {
     switch(keyCode) {
         case KeyEvent.VK_A:
             keyLeft = true;
             break;
         case KeyEvent.VK_D:
             keyRight = true;
             break;
         case KeyEvent.VK_W:
             keyUp = true;
             break;
         case KeyEvent.VK_S:
             keyDown = true;
             break;
         case KeyEvent.VK_F:
             //auto=!auto;
             break;
         case KeyEvent.VK_L:
             laserGuide=!laserGuide;
             break;
         case KeyEvent.VK_ENTER:

	         if(firstRun){
	              if (gameState==GAME_MENU) {
	             	gameState = GAME_CTRLSCREEN;
	             	//bg=controls;
		         }

		          else if (gameState==GAME_CTRLSCREEN) {
	             	gameState = GAME_OBJSCREEN;
	             	//bg=objective;
		         }
		          else if (gameState==GAME_OBJSCREEN) {
	             	gameState = GAME_RUNNING;
		         }	
	         }
	         else{
		         if (gameState==GAME_OVER) {
		            gameState = GAME_MENU;
		         }
		         
		         else if (gameState==GAME_MENU) {
	             	gameState = GAME_RUNNING;
		         }	
	         }

             break;
         case KeyEvent.VK_ESCAPE:
             if (gameState == GAME_RUNNING) {
              pauseGame();
              gameState=GAME_PAUSED;
         }
          else{
              resumeGame();
              gameState=GAME_RUNNING;
          }
             break;
         case KeyEvent.VK_B:
             //toggle bounding rectangles
             showBounds =!showBounds;
             break;
         case KeyEvent.VK_C:
             //toggle collision testing
            // collisionTesting =!collisionTesting;
            //clear=true;
             break;
         case KeyEvent.VK_E:
         if(createEnemy){
          //   spawnGhost(new Point2D(0, moveY));
         }
         createEnemy=false;
             break;
         case KeyEvent.VK_R:
         if(createEnemy){
             //spawnFBP(new Point2D(0, moveY));enemySprites+=1;
         }
         break;
         case KeyEvent.VK_T:
         if(createEnemy){
             spawnBallerina(new Point2D(0, moveY));
         }
         break;
         case KeyEvent.VK_Q:
             bgOn=!bgOn;
             if(gameState==GAME_RUNNING){
	             if(bgOn){bg=frontLawn;}
	             else
	                 bg=pause;
             }
             break;
         case KeyEvent.VK_SHIFT:///////////////////////////////////////////////////////
         	 //speedChanged=true;
         break;
         case KeyEvent.VK_EQUALS:
          currentLevel+=1;
          clear=true;
         break;
         case KeyEvent.VK_MINUS:
          currentLevel-=1;
          clear=true;
         break;
     }
 }

 /*****************************************************
 * A key is released
 *****************************************************/

 public void gameKeyUp(int keyCode) {
     switch(keyCode) {
         case KeyEvent.VK_A:
             keyLeft = false;
             billWalking=false;
             break;
         case KeyEvent.VK_D:
             keyRight = false;
             billWalking=false;
             break;
         case KeyEvent.VK_W:
             keyUp = false;
             billWalking=false;
             break;
         case KeyEvent.VK_S:
             keyDown = false;
             billWalking=false;
             break;
         case KeyEvent.VK_E:
             createEnemy=true;
             billWalking=false;
             break;
         case KeyEvent.VK_SHIFT:
         	 //speedChanged=false;
             break;
     }
 }

 /*****************************************************
 * mouse events passed by game engine
 *****************************************************/
 public void gameMouseDown() {
     moveX=mousePosition().X();
     moveY=mousePosition().Y();
     if(gameState != GAME_MENU){
     	billThrowing=true;
         if(auto && buttonPressed==1){throwIt=true;}
         else if(!auto && buttonPressed==1){
             if(!thrownOnce){
                 fireWeapon();
                 thrownOnce=true;
             }
         }
     }
 }
 public void gameMouseUp() {
     moveX=mousePosition().X();
     moveY=mousePosition().Y();
     throwIt=false;
     thrownOnce=false;
     billThrowing=false;
 }
 public void gameMouseMove() {
     moveX=mousePosition().X();
     moveY=mousePosition().Y();
 }

 public void gameMouseWheel(){
  boolean reset=true;
   if(notches<0){weaponType-=1;}
   else{weaponType+=1;}
   if(weaponType<0){weaponType=0;reset=false;}
   if(weaponType>maxWeapons){weaponType=maxWeapons;reset=false;}
   applyWeaponProp(weaponType);
   if(reset)
    cdinc=coolDown;
 }

 public void autoThrow(){
     if(throwIt){
         if(cdinc>=coolDown)
         {
             fireWeapon();
             cdinc=0;
         }
     }
 }

 public void changeSpeed(){//////////////////////////////////////////
 	desiredRate=10;
 }

 /*****************************************************
 * process keys that have been pressed
 *****************************************************/
 public void checkInput() {
     if (gameState != GAME_RUNNING) return;
     //the main is always the first sprite in the linked list
     AnimatedSprite main = (AnimatedSprite)sprites().get(0);
     if(!outOfBounds(main)){
	     if (keyLeft) {
	      billWalking=true;
	         main.setPosition(new Point2D(main.position().X()-MAIN_SPEED,main.position().Y()));
	     }
	     else if (keyRight) {
	      billWalking=true;
	         main.setPosition(new Point2D(main.position().X()+MAIN_SPEED,main.position().Y()));
	     }
	     if (keyUp) {
	      billWalking=true;
	         main.setPosition(new Point2D(main.position().X(),main.position().Y()-MAIN_SPEED));
	     }
	     if (keyDown) {
	      billWalking=true;
	         main.setPosition(new Point2D(main.position().X(),main.position().Y()+MAIN_SPEED));
	     }
    } 
 }

 public int jitter(){
 	int jn=0;

 	if(jitterNum!=0){
 		if(jitterNum<0) jitterNum=0;
	 	jn = rand.nextInt(jitterNum);
	 	if(rand.nextInt(10)<5)
	 		jn= 0-jn;
	 	else
	 		jn= 0+jn;
 	}
 	return jn;
 }

 /*****************************************************
 * fire a weapon from the mains position and orientation
 *****************************************************/
 public void fireWeapon() {
   //subtract ammo
   if(ammo[weaponType]>0){
    ammo[weaponType]--;
      //create the new weapon sprite
      AnimatedSprite[] weapons = new AnimatedSprite[10];
       weapons[0] = stockWeapon();
       adjustDirection(weapons[0], jitter());
       sprites().add(weapons[0]);
      if(weaponType==MULTI_SHOT){
	       weapons[1] = stockWeapon();
	       adjustDirection(weapons[1], 2);
	       sprites().add(weapons[1]);
	       weapons[2] = stockWeapon();
	       adjustDirection(weapons[2], -2);
	       sprites().add(weapons[2]);
	       weapons[3] = stockWeapon();
	       adjustDirection(weapons[3], 4);
	       sprites().add(weapons[3]);
	       weapons[4] = stockWeapon();
	       adjustDirection(weapons[4], -4);
	       sprites().add(weapons[4]);
      }
   }
 }

  public void fireEnemyWeapon(AnimatedSprite spr) {
     AnimatedSprite[] weapons = new AnimatedSprite[6];
     faceMain();
     weapons[0] = stockEnemyWeapon(spr,radians);
     adjustDirectionEnemy(weapons[0],0);
     sprites().add(weapons[0]);
 }

 private void adjustDirection(AnimatedSprite sprite, double angle) {
     angle = sprite.faceAngle() + angle;
     if (angle < 0) angle += 360;
     else if (angle > 360) angle -= 360;
     sprite.setFaceAngle(angle);
     sprite.setMoveAngle(sprite.faceAngle()-90);
     angle = sprite.moveAngle();
     double svx = calcAngleMoveX(angle) * WEAPON_SPEED;
     double svy = calcAngleMoveY(angle) * WEAPON_SPEED;
     sprite.setVelocity(new Point2D(svx, svy));
 }

  private void adjustDirectionEnemy(AnimatedSprite sprite, double angle) {
     angle = sprite.faceAngle() + angle;
     if (angle < 0) angle += 360;
     else if (angle > 360) angle -= 360;
     sprite.setFaceAngle(angle);
     sprite.setMoveAngle(sprite.faceAngle()-90);
     angle = sprite.moveAngle();
     double svx = calcAngleMoveX(angle) * ENEMY_WEAPON_SPEED;
     double svy = calcAngleMoveY(angle) * ENEMY_WEAPON_SPEED;
     sprite.setVelocity(new Point2D(svx, svy));
 }

 private AnimatedSprite stockWeapon() {
     //the main is always the first sprite in the linked list
     AnimatedSprite main = (AnimatedSprite)sprites().get(0);
     AnimatedSprite wep = new AnimatedSprite(this, graphics());
     wep.setAlive(true);
     wep.setImage(weaponImage[weaponType].getImage());
     wep.setFrameWidth(weaponImage[weaponType].width());
     wep.setFrameHeight(weaponImage[weaponType].height());
     wep.setSpriteType(SPRITE_WEAPON);
     wep.setLifespan(WEAPON_LIFESPAN);
     wep.setFaceAngle(main.faceAngle());
     wep.setMoveAngle(main.faceAngle() - 90);
     //set the weapons velocity
     double angle = wep.moveAngle();
     double svx = calcAngleMoveX(angle) * WEAPON_SPEED;
     double svy = calcAngleMoveY(angle) * WEAPON_SPEED;
     wep.setVelocity(new Point2D(svx, svy));
     //set the weapons starting position
     double x = (main.center().X() - wep.imageWidth()/2);
     double y = (main.center().Y() - wep.imageHeight()/2);
     wep.setPosition(new Point2D(x,y));
     return wep;
 }

  private AnimatedSprite stockEnemyWeapon(AnimatedSprite spr, double radians) {
     //the main is always the first sprite in the linked list
     AnimatedSprite wep = new AnimatedSprite(this, graphics());
     wep.setAlive(true);
     wep.setImage(enemyWeaponImage[enemyWeaponType].getImage());
     wep.setFrameWidth(enemyWeaponImage[enemyWeaponType].width());
     wep.setFrameHeight(enemyWeaponImage[enemyWeaponType].height());
     wep.setSpriteType(SPRITE_ENEMY_WEAPON);
     wep.setLifespan(ENEMY_WEAPON_LIFESPAN);
     wep.setFaceAngle(radians);
     wep.setMoveAngle(radians - 90);
     //set the weapons velocity
     double angle = wep.moveAngle();
     double svx = calcAngleMoveX(angle) * WEAPON_SPEED;
     double svy = calcAngleMoveY(angle) * WEAPON_SPEED;
     wep.setVelocity(new Point2D(svx, svy));
     //set the weapons starting position
     double x = (spr.center().X() - wep.imageWidth()/2);
     double y = (spr.center().Y() - wep.imageHeight()/2);
     wep.setPosition(new Point2D(x,y));
     return wep;
 }

 public void bumpScore(int howmuch) {
     score += howmuch;
     if (score > highscore)
         highscore = score;
 }

 public void mouseControl(AnimatedSprite sprite){
     radians = (((Math.atan((moveY - mainY)/(moveX - mainX)))*180)/Math.PI)+90; // radians calculation formula
     sprite.setFaceAngle(radians);
     if (moveX-mainX<0)
     {
         sprite.setFaceAngle(sprite.faceAngle()+180);
     }
 }

  public void faceMain(){
	radians = (((Math.atan((mainY - enemyY)/(mainX - enemyX)))*180)/Math.PI)+90;
      if (mainX-enemyX<0){
          radians+=180;
      }
 }

  /*****************************************************
 * Spawn an enemy
 *****************************************************/

  public void startExplosion0(Point2D point) {
  //create a new explosion at the passed location
  AnimatedSprite expl = new AnimatedSprite(this,graphics());
  expl.setSpriteType(SPRITE_EXPLOSION);
  expl.setAlive(true);
  expl.setAnimImage(explosions[0].getImage());
  expl.setTotalFrames(25);
  expl.setColumns(5);
  expl.setFrameWidth(64);
  expl.setFrameHeight(64);
  expl.setFrameDelay(0);
  expl.setPosition(point);
     //add the new explosion to the sprite list
   sprites().add(expl);
  }

  public void startExplosion1(Point2D point) {
  //create a new explosion at the passed location
  AnimatedSprite expl = new AnimatedSprite(this,graphics());
  expl.setSpriteType(SPRITE_EXPLOSION);
  expl.setAlive(true);
  expl.setAnimImage(explosions[1].getImage());
  expl.setTotalFrames(16);
  expl.setColumns(4);
  expl.setFrameWidth(64);
  expl.setFrameHeight(64);
  expl.setFrameDelay(0);
  expl.setPosition(point);
     //add the new explosion to the sprite list
   sprites().add(expl);
  }

  public void spawnGhost(Point2D point) {
     AnimatedSprite enemy = new AnimatedSprite(this,graphics());
     enemy.setSpriteType(SPRITE_ENEMY_GHOST);
     enemy.setAlive(true);
     int num = rand.nextInt(4);
     enemy.setAnimImage(ghosts[num].getImage());
     enemy.setTotalFrames(2);
     enemy.setColumns(1);
     enemy.setFrameWidth(50);
     enemy.setFrameHeight(50);
     enemy.setFrameDelay(10);
     enemy.setPosition(point);
     enemy.setMaxHealth(3);
     enemy.setSpeedY(0.5);
     randomDirY(enemy);
     enemy.setSpeedX(1);
     enemy.setHealth(enemy.maxHealth());
//add the new enemy to the sprite list
     sprites().add(enemy);
 }

 public void spawnGhostDying(Point2D point) {
     AnimatedSprite enemy = new AnimatedSprite(this,graphics());
     enemy.setSpriteType(SPRITE_ENEMY_GHOST_DYING);
     enemy.setAlive(true);
     enemy.setAnimImage(ghosts[5].getImage());
     enemy.setTotalFrames(2);
     enemy.setColumns(1);
     enemy.setFrameWidth(50);
     enemy.setFrameHeight(50);
     enemy.setFrameDelay(10);
     enemy.setPosition(point);
     enemy.setMaxHealth(1);
     enemy.setSpeedY(0);
     randomDirY(enemy);
     enemy.setSpeedX(3);
     enemy.setHealth(enemy.maxHealth());
//add the new enemy to the sprite list
     sprites().add(enemy);
 }


 public void spawnFBP(Point2D point) {
     AnimatedSprite enemy = new AnimatedSprite(this,graphics());
     enemy.setSpriteType(SPRITE_ENEMY_FBP);
     enemy.setAlive(true);
     enemy.setAnimImage(enemies[0].getImage());
     enemy.setTotalFrames(10);
     enemy.setColumns(1);
     enemy.setFrameWidth(80);
     enemy.setFrameHeight(50);
     enemy.setFrameDelay(4);
     enemy.setPosition(point);
     enemy.setMaxHealth(35);
     enemy.setSpeedY(1);
     randomDirY(enemy);
     enemy.setSpeedX(0.6);
     enemy.setHealth(enemy.maxHealth());
//add the new enemy to the sprite list
     sprites().add(enemy);
 }

 public void spawnBallerina(Point2D point) {
     AnimatedSprite enemy = new AnimatedSprite(this,graphics());
     enemy.setSpriteType(SPRITE_ENEMY_BALLERINA);
     enemy.setAlive(true);
     enemy.setAnimImage(enemies[1].getImage());
     enemy.setTotalFrames(1);
     enemy.setColumns(1);
     enemy.setFrameWidth(80);
     enemy.setFrameHeight(80);
     enemy.setFrameDelay(4);
     enemy.setPosition(point);
     enemy.setMaxHealth(20);
     enemy.setSpeedY(2);
     randomDirY(enemy);
     enemy.setSpeedX(2);
     enemy.setHealth(enemy.maxHealth());
//add the new enemy to the sprite list
     sprites().add(enemy);
 }

   public void addBillInit() {
     AnimatedSprite main = new AnimatedSprite(this, graphics());
     main.setSpriteType(SPRITE_MAIN);
     main.setImage(mainImage[0].getImage());
     main.setFrameWidth(main.imageWidth());
     main.setFrameHeight(main.imageHeight());
     main.setPosition(new Point2D(1300, 350));
     sprites().add(main);
     main.setAlive(true);
     main.setState(STATE_NORMAL);
     main.setMaxHealth(mainHealth);
     main.setHealth(main.maxHealth());
   }

   public void addBillStanding(AnimatedSprite main) {
  	 main.setAnimImage(mainImage[1].getImage());
     main.setTotalFrames(0);
     main.setColumns(0);
     main.setFrameWidth(50);
     main.setFrameHeight(50);
     main.setFrameDelay(2);
   }

     public void addBillWalking(AnimatedSprite main) {
     main.setAnimImage(mainImage[1].getImage());
     main.setTotalFrames(10);
     main.setColumns(1);
     main.setFrameWidth(50);
     main.setFrameHeight(50);
     main.setFrameDelay(2);
 }
 /*
  public void addBillThrowing(AnimatedSprite main) {
     AnimatedSprite arm = new AnimatedSprite(this, graphics());
     arm.setSpriteType(SPRITE_ARM);
     arm.setImage(mainImage[3].getImage());
     arm.setFrameWidth(arm.imageWidth());
     arm.setFrameHeight(arm.imageHeight());
     arm.setPosition(new Point2D(main.position().X(),main.position().Y()));
     sprites().add(arm);
     arm.setAlive(true);
 }
*/
 public void autoMove(AnimatedSprite spr){
  reverseAtEdgeY(spr);
     spr.setPosition(new Point2D(spr.position().X()+spr.speedX(),spr.position().Y()+spr.speedY()));
 }

 public void autoMoveReverse(AnimatedSprite spr){
  reverseAtEdgeY(spr);
     spr.setPosition(new Point2D(spr.position().X()-spr.speedX(),spr.position().Y()-spr.speedY()));
 }


 /*****************************************************
 * cause sprite to stop at screen edges
 *****************************************************/
 public void stopAtEdge(AnimatedSprite spr) {/////////////////////////////////////////////////////////////////////////////////////////////////
     int w = spr.frameWidth()-1;
     int h = spr.frameHeight()-1;
     if (spr.position().X() < 0)
         spr.position().setX(0);
         //spr.position().setX(spr.position().X()+1);
     else if (spr.position().X() > SCREENWIDTH-h)
     	 //spr.position().setX(spr.position().X()-1);
         spr.position().setX(SCREENWIDTH-w);
     if (spr.position().Y() < 0)
         spr.position().setY(0);
         //spr.position().setY(spr.position().Y()+1);
     else if (spr.position().Y() > SCREENHEIGHT-h)
         spr.position().setY(SCREENHEIGHT-h);
         //spr.position().setY(spr.position().Y()-1);
 }
 
 public boolean outOfBounds(AnimatedSprite spr){
 	 int w = spr.frameWidth()-1;
     int h = spr.frameHeight()-1;
     if ((spr.position().X() < 0)||
     	(spr.position().X() > SCREENWIDTH-h)||
     	(spr.position().Y() < 0)||
     	(spr.position().Y() > SCREENHEIGHT-h))
     	{return true;}
     else
     	return false; 
 }

  /***************************************************** // doesnt work, fix later
 * cause sprite to bounce off screen edges
 *****************************************************/
 /*
 public void bounceAtEdge(AnimatedSprite spr) {
     int w = spr.frameWidth()-1;
     int h = spr.frameHeight()-1;
     double oppo = 90;
     if (spr.position().X() < 0) // left edge
         adjustDirection2(spr, oppo);
     else if (spr.position().X() > SCREENWIDTH-h) // right edge
         adjustDirection2(spr, oppo);
     if (spr.position().Y() < 0) // top edge
         adjustDirection2(spr, oppo);
     else if (spr.position().Y() > SCREENHEIGHT-h) // bottom edge
         adjustDirection2(spr, oppo);
 }
 */
 /*****************************************************
 * reverse enemies that reach top or bottom
 *****************************************************/
  public void reverseAtEdgeY(AnimatedSprite spr) {
     int h = spr.frameHeight()-1;
     if (spr.position().Y() <= 0) // top edge
         reverseDirY(spr);
     else if (spr.position().Y() >= SCREENHEIGHT-h) // bottom edge
         reverseDirY(spr);
 }

 public void reverseDirY(AnimatedSprite spr){
  if(spr.speedY()>0){
   spr.setSpeedY(0-spr.speedY());
  }
  else
   spr.setSpeedY(Math.abs(spr.speedY()));
 }

 public void randomDirY(AnimatedSprite spr){
  if(rand.nextInt(10)>5){
   reverseDirY(spr);
  }
 }

 public void spawnRandEnemy(){
     if(rand.nextInt(1000)>990){
         spawnGhost(new Point2D(0, rand.nextInt(600)));
     }
     if(rand.nextInt(1000)>990){
      spawnFBP(new Point2D(0, rand.nextInt(600)));
 }
 }

/*****************************************************
 * kill sprites that are off screen
 *****************************************************/
 public void killOffScreen(AnimatedSprite spr) {
     int w = spr.frameWidth()-1;
     int h = spr.frameHeight()-1;
     if (spr.position().X() < 0-w || // left edge
       spr.position().X() > SCREENWIDTH || // right edge
       spr.position().Y() < 0-h || // top edge
       spr.position().Y() > SCREENHEIGHT // bottom edge
      )
      {
      	spr.setAlive(false);
	      	if(isEnemy(spr.spriteType())){
	      		enemySprites-=1;
	      	}
      	}
 }

 public void drawInventory(Graphics2D g2d){
	  int y=0;
	  int y2=0;
	  //g2d.setColor(Color.GRAY);
	  //g2d.fillRect(SCREENWIDTH-35,0,35,200);
	  g2d.setFont(new Font("Verdana", Font.BOLD, 12));
	  g2d.setColor(Color.WHITE);

	 g2d.drawImage(arrow.getImage(),SCREENWIDTH-35,weaponType*25,15,20,this);
	  g2d.drawImage(weaponImage[PENCIL].getImage(),SCREENWIDTH-24,0,24,24,this);
	 g2d.drawString("inf.", SCREENWIDTH-80, ++y2*25-10);
	 if(maxWeapons>=1){
	  g2d.drawImage(weaponImage[MULTI_SHOT].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[MULTI_SHOT], SCREENWIDTH-80, ++y2*25-10);
	 }
	 if(maxWeapons>=2){
	  g2d.drawImage(weaponImage[CD].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[CD], SCREENWIDTH-80, ++y2*25-10);
	 }
	 if(maxWeapons>=3){
	  g2d.drawImage(weaponImage[SCISSORS].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[SCISSORS], SCREENWIDTH-80, ++y2*25-10);
	}
	if(maxWeapons>=4){
	  g2d.drawImage(weaponImage[STAPLER].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[STAPLER], SCREENWIDTH-80, ++y2*25-10);
	 }
	 if(maxWeapons>=5){
	  g2d.drawImage(weaponImage[BULLET].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[BULLET], SCREENWIDTH-80, ++y2*25-10);
	 }
	 if(maxWeapons>=6){
	  g2d.drawImage(weaponImage[MINE].getImage(),SCREENWIDTH-20,++y*25,20,20,this);
	 g2d.drawString(""+ammo[MINE], SCREENWIDTH-80, ++y2*25-10);
	 }
 }

 public void newWeaponNotify(Graphics2D g2d) {
 	int x,y;
 	x=SCREENWIDTH-250;
 	y=(maxWeapons*25)+15;
	String str = "";
 	switch(maxWeapons){
 		case 1:
	 		str="Multi-Shot";
	 	break;
	 	case 2:
	 		str="CD";
	 	break;
	 	case 3:
	 		str="Scissors";
	 	break;
	 	case 4:
	 		str="Stapler";
	 	break;
	 	case 5:
	 		str="RPG";
	 	break;
	 	case 6:
	 		str="Mine";
	 	break;
 	}
 		g2d.setFont(new Font("Verdana", Font.BOLD, 12));
 		if(flash){
 			g2d.setColor(Color.WHITE);
 			if(flct<=0){
 				flash=!flash;
 				flct=10;
 			}
 			flct--;
 		}
 		else{
 			g2d.setColor(Color.BLACK);
 			if(flct<=0){
 				flash=!flash;
 				flct=10;
 			}
 			flct--;
 		}

 		g2d.drawString("NEW WEAPON: "+str+" --->",x-(str.length()*5),y);

 	if (System.currentTimeMillis() > start0 + 3000) {
        start0 = System.currentTimeMillis();
        anCount++;
		if(anCount>=2){
			alreadyNotified=true;
			anCount=0;
		}
 	}
 }

 public void drawHealthBar(AnimatedSprite spr, int x, int y){
  Graphics2D g2d = graphics();
  double w = spr.frameWidth();
  double h = spr.frameHeight();
  double max = spr.maxHealth();
  double currentHealth = spr.health();
  double barLength = (currentHealth/max)*w;
  double healthPercent = (currentHealth/max)*100;
  g2d.setColor(Color.GREEN);
  if(healthPercent<50 && healthPercent>20){g2d.setColor(Color.YELLOW);}
  if(healthPercent<=20){g2d.setColor(Color.RED);}
  g2d.drawRect(x,y-10,(int)w,5);
  g2d.fillRect(x,y-10,(int)barLength,5);
 }

 public void drawMainHealthBar(int x, int y, int w, int h){
  Graphics2D g2d = graphics();
  double healthPercent = (mainHealth/maxMainHealth)*100;
  double barLength = (mainHealth/maxMainHealth)*w;
  g2d.setColor(Color.GREEN);
  if(healthPercent<50 && healthPercent>20){g2d.setColor(Color.YELLOW);}
  if(healthPercent<=20){g2d.setColor(Color.RED);}
  g2d.drawRect(x,y,w,h);
  g2d.fillRect(x,y,(int)barLength,h);
  g2d.setColor(Color.white);
  g2d.setFont(new Font("Verdana", Font.BOLD, 12));
  g2d.drawString(""+(int)mainHealth+"/"+(int)maxMainHealth,x,y+22);
 }

 public void drawhouseHPBar(int x, int y, int w, int h){
  Graphics2D g2d = graphics();
  double healthPercent = (houseHP/maxhouseHP)*100;
  double barLength = (houseHP/maxhouseHP)*w;
  g2d.setColor(Color.GREEN);
  if(healthPercent<50 && healthPercent>20){g2d.setColor(Color.YELLOW);}
  if(healthPercent<=20){g2d.setColor(Color.RED);}
  g2d.drawRect(x,y,w,h);
  g2d.fillRect(x,y,(int)barLength,h);
  g2d.setColor(Color.white);
  g2d.setFont(new Font("Verdana", Font.BOLD, 12));
  g2d.drawString(""+(int)houseHP+"/"+(int)maxhouseHP,x,y+22);
 }

 public void drawCoolDown(Graphics2D g2d){
  double cd = coolDown;
  double w = 50;
  double barLength = ((double)cdinc/(double)coolDown)*w;
  if(barLength>w||cd==0)
   barLength=w;
 g2d.setColor(Color.lightGray);
    g2d.drawRect(SCREENWIDTH-150,5,(int)w,10);
    g2d.fillRect(SCREENWIDTH-150,5,(int)barLength,10);
 }

 public void shakeScreen(){
    if(shakeCount>0){
     bgX=rand.nextInt(shakeSize);
     bgY=rand.nextInt(shakeSize);
     shakeCount--;
     if(mainHit)drawBlood=true;
    }
    else{
     bgX = 0;
     bgY = 0;
     shakeCount=shakeDuration;
     shake=false;
     drawBlood=false;
     mainHit=false;
    }
 }

 public void runLevel(int enemyTypeA,int enemyTypeB,int enemyTypeC, int delay){
 	if (System.currentTimeMillis() > start + delay) {
        start = System.currentTimeMillis();
        enemyCount[currentLevel]--;
        if(enemyCount[currentLevel]>=0){
			int maxChoice=2;

			if(enemyTypeA>0){
				if(choice==0){
					enemy=enemyTypeA;
				}
			}else
				maxChoice--;

			if(enemyTypeB>0){
				if(choice==1){
					enemy=enemyTypeB;
				}
			}else
				maxChoice--;

			if(enemyTypeC>0){
				if(choice==2){
					enemy=enemyTypeC;
				}
			}else
				maxChoice--;

			choice++;
			if(choice>maxChoice)
				choice=0;

	        	switch(enemy){
	        		case 1:
	        			spawnGhost(new Point2D(0,rand.nextInt(700-50)));
	        		break;
	        		case 2:
	        			spawnFBP(new Point2D(0,rand.nextInt(700-50)));
	        		break;
	        		case 3:
	        			spawnBallerina(new Point2D(0,rand.nextInt(700-50)));
	        		break;
	        	}
				enemySprites+=1;
		}
		if(enemyCount[currentLevel]<=0)
			countFinished=true;
	 	}
	  if(countFinished&&enemySprites<=0){
	        currentLevel+=1;
	        countFinished=false;
	        alreadyNotified=false;
	  }
 }

}