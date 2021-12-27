package info.fandroid.drop;

// Процесс импортирования необходимых пакетов и файлов, классов, библиотек итд

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.Rectangle;
import java.util.Iterator;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DropKey implements Screen  {  //Создание и заполнение необходимых ресурсов

	public final int W = Gdx.graphics.getWidth(); //2100;
	public final int H = Gdx.graphics.getHeight();//1000;

	private int NUMB;
	private int NUMB2;
	private final info.fandroid.drop.Drop game;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture dropImage;  // Игровые объекты
	private Texture drop2Image;
	private Texture DOPING;
	private Texture dropImage2;

	private Texture st1;
	private Texture st2;
	private Texture st3;
	private Texture st4;
	private Texture st5;
	private Texture st6;

	private Texture gamoverImage;  // Изображения состояния игры
	private Texture winImage;
	private Texture newLevelImage;

	private Texture heroImageMain;  // Ссылки на ресурсы
	private Texture mainImage;

	private Texture heroImage;  //  Текстыры персонажа
	private Texture heroImageL;
	private Texture heroImageR;

	private Texture areaImage;   //   Сцена
	private Texture progressImage;

	private Texture retryImage;    //   Кнопки
	private  Texture mainImagePause;
	private Texture mainImageReplay;

	private Sound dropSound;   //  Медиа итп
	private Sound doppingSound;
	private Music loseMusic;
	private Music backGroundnMusic;
	private Music winMusic;
	private Rectangle hero;
	private Vector3 touchPos;
	private Array<Rectangle> A_BunchOfPlants;
	private Array<Rectangle> A_BunchOfRubbish;
	private Array<Rectangle> A_BunchOfDopping;
	private Array<Texture> AnimationMeteor;
	private Rectangle RetryState;
	private Rectangle Cursor;

	private long lastDropTime;
	private long lastDropTime2;
	private long lastDoppingTime;
	private long ButtCheck;
	private long timeCheck;
	private long pointsCheck;
	private long animationCheck;
	private long timeCheckImage;

	private boolean KEY = false;
	private boolean END = false;
	private boolean WIN = false;
	private boolean LOSE = false;
	private boolean BUTTONCHANGE = false;
	private boolean ThreadStop = false;
	private boolean NewLevel = false;
	private boolean CheckLevel = false;
	private boolean Boom = false;

	private float rotate = 360.0f;
	private int speed = 350;
	private int CountLevel = 0;
	private int speedAut;
	private int animationFse = 0;
	private int TIME = 0;
	private int dropsKeys = 0;
	private int random;
	private int SpeedDifference = 100;
	private int AddSpace = 180;
	private int WinScore = 50;
	//----------------------------------------
	private static final int FRAME_COLS = 5;
	private static final int FRAME_ROWS = 2;
	private Animation walkAnimation;
	private Texture walkSheet;
	private TextureRegion[] walkFrames;
	private TextureRegion currentFrame;
	private float stateTime;
	private long BoomCh;
	private int coordinates;
	private int index = 0;

	//-------------------------------------------------------

	DropKey(final Drop gam) {  // Создание игры (объекта) и настройка камеры

		this.game = gam;

		camera = new OrthographicCamera();
		 camera.setToOrtho(false, W, H);

		batch = new SpriteBatch();
		 touchPos = new Vector3();

		 //----------------------------------------------------------  Загрузка PNG ресурсов

			 areaImage = new Texture("Area.png");
			 progressImage = new Texture("ProgressTable.png");

			heroImage = new Texture("WalleDefault.png");
			 heroImageL = new Texture("WalleLeft.png");
			  heroImageR = new Texture("WalleRight.png");

			dropImage = new Texture("cactus.png");
			dropImage2 = new Texture("Ognet.png");
			 drop2Image = new Texture("MetSt0.png");
			  DOPING = new Texture("Dopping.png");

			gamoverImage = new Texture("GameOver.png");
			 retryImage = new Texture("RetryButton.png");
			  mainImagePause = new Texture("PauseButton.png");
			   mainImageReplay = new Texture("ReplayButton.png");
			     winImage = new Texture("winImage.png");
			     newLevelImage = new Texture("newLevel.png");

			     st1 = new Texture("MetSt2.png");
			  st2 = new Texture("MetSt3.png");
			    st3 = new Texture("MetSt4.png");
			  st4 = new Texture("MetSt5.png");
			    st5 = new Texture("MetSt6.png");
			  st6 = new Texture("MetSt7.png");
			walkSheet = new Texture(Gdx.files.internal("boom.png")); // #9

              //---------------------------------------------  Загрузка аудиофайлов (ресурсов)

			dropSound = Gdx.audio.newSound(Gdx.files.internal("dropSound.mp3"));
			doppingSound = Gdx.audio.newSound(Gdx.files.internal("DoppingSound.mp3"));
			backGroundnMusic = Gdx.audio.newMusic(Gdx.files.internal("AskyMusic.mp3"));
			  loseMusic = Gdx.audio.newMusic(Gdx.files.internal("loseMusic.mp3"));
			   winMusic = Gdx.audio.newMusic(Gdx.files.internal("musicWin.mp3"));

		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS); // #10
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		walkAnimation = new Animation(0.06f, walkFrames);
		stateTime = 0f;

		heroImageMain = heroImage;  // Выставление текстур по умолчанию
		  mainImage = mainImagePause;

		backGroundnMusic.setLooping(true);
		  backGroundnMusic.play();
		  backGroundnMusic.setVolume(0.3f);

		hero = new Rectangle();   // Главный персонаж, создание и его координирование
		 hero.x = W / 2 - 195 / 2;
		  hero.y = 60;
		hero.width = 170;
		 hero.height = 220;

		RetryState = new Rectangle();  // Кнопка начала игры заново
		 RetryState.width = 150;
		  RetryState.height = 150;
		   RetryState.x = W / 2 - 50;
		     RetryState.y = H - 400;

		Cursor = new Rectangle();   // Создание активной области курсора (пользовательского ввода)
		  Cursor.width = 45;
		     Cursor.height = 45;

		A_BunchOfPlants = new Array<Rectangle>();  // Создание массивов игровых объектов
		 spawnPlant();
		  A_BunchOfRubbish = new Array<Rectangle>();
		   spawnRubbish();
		    A_BunchOfDopping = new Array<Rectangle>();

		timeCheck = TimeUtils.nanoTime();
		timeCheckImage = TimeUtils.nanoTime();
		pointsCheck = TimeUtils.nanoTime();
		animationCheck = TimeUtils.millis();

		AnimationMeteor = new Array<Texture>();

		AnimationMeteor.add(st1);
		AnimationMeteor.add(st2);
		AnimationMeteor.add(st3);
		AnimationMeteor.add(st4);
		AnimationMeteor.add(st5);
		AnimationMeteor.add(st6);
	}
	//------------------------------------------------------

	private void spawnPlant() {    // Создание полей и обЪекта РАСТЕНИЕ

		Rectangle PlantUnit = new Rectangle();

		if (!KEY) {
			PlantUnit.x = W / 2;
			PlantUnit.y = H + 200;
		}
		if (KEY) {

			PlantUnit.x = MathUtils.random(300, W - 300 - 100);    //Ключ начала игры
			NUMB = (int) PlantUnit.x;
			PlantUnit.y = H;
		}
		PlantUnit.width = 64;
		PlantUnit.height = 64;

		if (!ThreadStop) {
			A_BunchOfPlants.add(PlantUnit);
			lastDropTime = TimeUtils.nanoTime();
		}
	}  // Создание Растения

	//----------------------------------------------------------------------------------

	private void spawnRubbish() {     // Создание полей и обЪекта МУСОР

		Rectangle RubbishUnit = new Rectangle();
		RubbishUnit.y = 1800;

		if (!KEY) RubbishUnit.x = MathUtils.random(100, W - 200);  // Вступительная инициализация

		if (KEY) {
			RubbishUnit.x = MathUtils.random(100, W - 200);

			if (RubbishUnit.x < NUMB + AddSpace && RubbishUnit.x > NUMB - AddSpace) {    //проверка на положение мусора в поле + его последующее положеник

				random = MathUtils.random(0,2);
				switch (random) {
					case 1: RubbishUnit.x = MathUtils.random(100, NUMB - AddSpace); break;
					case 2: RubbishUnit.x = MathUtils.random(NUMB + AddSpace, W - 200); break;
			}
			}
			if (RubbishUnit.x == NUMB) RubbishUnit.x = MathUtils.random(100, NUMB - AddSpace - 100);

			//--------------------------------------------------------------------------------------

			if (RubbishUnit.x > W - 200) {    //  Последующая проверка и генерирование координат Мусора
				RubbishUnit.x = MathUtils.random(100 + 300, W / 2);
			}

			if (RubbishUnit.x < 100) {
				RubbishUnit.x = MathUtils.random(W / 2, W - 200 - 300);
			}
		}

		RubbishUnit.width = 75;
		RubbishUnit.height = 65;

		 NUMB2 = (int) RubbishUnit.x;

		if (!ThreadStop) {
			A_BunchOfRubbish.add(RubbishUnit);
			lastDropTime2 = TimeUtils.nanoTime();
		}
	}

	private void spawnDoping() {

		Rectangle DoppingUnit = new Rectangle();
		DoppingUnit.y = H + 100;

		DoppingUnit.x = MathUtils.random(100, W - 200);

		if( DoppingUnit.x > NUMB2 - AddSpace && DoppingUnit.x < NUMB2 + AddSpace)

			random = MathUtils.random(0,3);

		switch (random) {
			case 1:
				DoppingUnit.x = MathUtils.random(NUMB + AddSpace, W - 200);
				break;
			case 2:
				DoppingUnit.x = MathUtils.random(100, NUMB2 - AddSpace);
				break;
		}

		DoppingUnit.width = 64;
		DoppingUnit.height = 64;

		if(!ThreadStop) {
			A_BunchOfDopping.add(DoppingUnit);
			lastDoppingTime = TimeUtils.nanoTime();
		}
	}
	     //----------------------------------------------------

	@Override
	public void render(float delta) {

		Gdx.graphics.getGL20().glClearColor(0.3f,0.3f,0.3f,1);  //  Очистка игрового поля, закрашивание его в черный цвет для последующей анимации
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//----------------------------------------------------------  Периодическая очистка массивов
		if(A_BunchOfPlants.size > 500){
		    A_BunchOfPlants.clear();
        }
		if(A_BunchOfRubbish.size > 500) {
		    A_BunchOfRubbish.clear();
        }
		if(A_BunchOfDopping.size > 500) {
			A_BunchOfDopping.clear();
		}
		//----------------------------------------------------------

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		if(END) {  //  Отрисовка при проигрыше

            game.batch.draw(areaImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.batch.draw(mainImage, W / 4, H / 3 + 150, W / 2, H / 3 + 70);
            game.batch.draw(retryImage, W / 2 - 80, H / 4);
            game.batch.draw(heroImageMain, hero.x, hero.y);

            if (TimeUtils.nanoTime() - timeCheck > 800000000) {  // Анимация мигания надписи результатов
				game.font.draw(game.batch, "You progress: " + dropsKeys + " points.", 20, H - 20);
				game.font.draw(game.batch, "Time: " + TIME + " sec.", 700, H - 20);

                if ((TimeUtils.nanoTime() - timeCheck) > 1800000000) {
                    timeCheck = 0;
                    timeCheck = TimeUtils.nanoTime();
                }
            }
        }
		if (!END) {  //  Стандартная отрисовка

			game.batch.draw(areaImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Создание заднего фона
			game.font.draw(game.batch, "You progress: " + dropsKeys + " points.", 20, H - 20);
			game.font.draw(game.batch, "Time: " + TIME + " sec.", 700, H - 20);
			game.batch.draw(mainImage, W - 160, H - 140);
			game.batch.draw(heroImageMain, hero.x, hero.y);

			BOOM();

			if (TimeUtils.nanoTime() - timeCheck > 1000000000 && !END) {
				TIME++;
				timeCheck = 0;
				timeCheck = TimeUtils.nanoTime();

			}

			if(dropsKeys == 30) {
				NewLevel = true;
				ButtCheck = 0;
			}

			if (dropsKeys == 31) {
				NewLevel = false;
				dropImage = dropImage2;
			}

			if(TIME < 67 && dropsKeys > 29) {

				if (TimeUtils.nanoTime() - ButtCheck < 300000000) {  // Анимация мигания надписи
					game.batch.draw(newLevelImage, W / 4 - 100, -100, 1200, 1000);
				}
				if ((TimeUtils.nanoTime() - ButtCheck) > 700000000) {
					ButtCheck = 0;
					ButtCheck = TimeUtils.nanoTime();
				}
			}

			if (TimeUtils.millis() - animationCheck > 40) {

				animationCheck = 0;
				animationCheck = TimeUtils.millis();
				animationFse++;

				if (animationFse == 6) animationFse = 0;
			}

			//-------------------------------------------------------------

				for (Rectangle PlantUnit : A_BunchOfPlants) {   // Отрисовка и очистка массива РАСТЕНИЙ

					game.batch.draw(dropImage, PlantUnit.x, PlantUnit.y);

					if (A_BunchOfPlants.size > 20) {
						for (int a = 0; a <= 10; a++) {
							A_BunchOfPlants.removeIndex(a);
						}
					}
				}

				for (Rectangle RubbishUnit : A_BunchOfRubbish) {    // Отрисовка и очистка массива МУСОРА

					drop2Image = AnimationMeteor.get(animationFse);
					game.batch.draw(drop2Image, RubbishUnit.x, RubbishUnit.y);

					if (A_BunchOfRubbish.size > 20) {
						for (int b = 0; b <= 10; b++) {
							A_BunchOfRubbish.removeIndex(b);
						}
					}
				}

				for (Rectangle DoppingUnit : A_BunchOfDopping) {    // Отрисовка и очистка массива МУСОРА

					game.batch.draw(DOPING, DoppingUnit.x, DoppingUnit.y);

					if (A_BunchOfDopping.size > 20) {
						for (int r = 0; r <= 10; r++) {
							A_BunchOfDopping.removeIndex(r);
						}
					}
				}
		}
		game.batch.end();
		//--------------------------------------------  Мониторинг нажатий и обработка направления движения

		if (!END && !ThreadStop) {

			if (Gdx.input.isTouched() && Gdx.input.getX() < W / 2) {

				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				heroImageMain = heroImageL;
				hero.x -= 16;

			}

			 if (Gdx.input.isTouched() &&  Gdx.input.getX() > W / 2 && Cursor.y > 150) {

				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				heroImageMain = heroImageR;
				hero.x += 16;

			}

			if ((!Gdx.input.isTouched() || (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.RIGHT))) && (TimeUtils.nanoTime() - timeCheckImage >  1000000000)) {
				heroImageMain = heroImage;
				timeCheckImage = 0;
			}
			//---------------------------------------------

			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

				heroImageMain = heroImageL;
				hero.x -= 750 * Gdx.graphics.getDeltaTime();
			}

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

				heroImageMain = heroImageR;
				hero.x += 750 * Gdx.graphics.getDeltaTime();
			}
		}

		if(Gdx.input.getX() == W / 2) heroImageMain = heroImage;

		//-------------------------------------  Уровни и дополнительные анимации

		if (dropsKeys == WinScore) {

			mainImage = winImage;
			WIN = true;

			try {
				GamState();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//--------------------------   Отслеживание границ игрового поля

		if (hero.x < 0) hero.x = 0;
		if (hero.x > W - 190) hero.x = W - 190;

		//-----------------------------------------------------    Рассчет координаты пользовательского ввода и обработка кнопки

		Cursor.x = Gdx.input.getX();
		Cursor.y = Gdx.input.getY();

		if ((Cursor.x > W - 200 && Cursor.y < 200) && (Gdx.input.isTouched() || Gdx.input.isCursorCatched()) && !BUTTONCHANGE && !END && ((TimeUtils.nanoTime() - ButtCheck > 500000000))) {

			mainImage = mainImageReplay;
			BUTTONCHANGE = true;
			ThreadStop = true;
			ButtCheck = 0;
			ButtCheck = TimeUtils.nanoTime();

			pause();
		}

		if ((Cursor.x > W - 200 && Cursor.y < 200) && (Gdx.input.isTouched() || Gdx.input.isCursorCatched()) && BUTTONCHANGE && !END && (TimeUtils.nanoTime() - ButtCheck > 500000000)) {

			mainImage = mainImagePause;
			BUTTONCHANGE = false;
			ThreadStop = false;
			ButtCheck = 0;
			ButtCheck = TimeUtils.nanoTime();

			resume();
		}
		//----------------------------------------------------    Создание нового мусора, процесс подсчета времени

		if ((TimeUtils.nanoTime() - lastDropTime > 2100000000 && !ThreadStop) && !NewLevel) { //Создание нового сапога, процесс подсчета времени

			lastDropTime = 0;
			KEY = true;
			spawnPlant();
		}
		if ((TimeUtils.nanoTime() - lastDropTime2 > 1300000000 && !ThreadStop) && !NewLevel) {

			lastDropTime2 = 0;
			KEY = true;
			spawnRubbish();
		}
		if((TimeUtils.nanoTime() / 20 - lastDoppingTime / 20 > 1800000000 && !ThreadStop && dropsKeys > 15) && !NewLevel) {

			lastDoppingTime = 0;
			spawnDoping();
		}

		//------------------------------------   Создание анимаций и движения объектов

		if (!ThreadStop || !END) {

			Iterator<Rectangle> iterator = A_BunchOfPlants.iterator();

			while ( iterator.hasNext()) {

				Rectangle PlantUnit = iterator.next();

				if (!END && !ThreadStop) {
					PlantUnit.y -= (speed - SpeedDifference) * Gdx.graphics.getDeltaTime();
				}

				if (PlantUnit.y + 64 < 0) {

				    mainImage = gamoverImage;
					LOSE = true;
					try { GamState(); }
					catch (InterruptedException e) { e.printStackTrace();}
				}

				if (PlantUnit.overlaps(hero)) {

					dropsKeys++;
					dropSound.play();
					speed += 3;
					iterator.remove();
				}
			}

			Iterator<Rectangle> iterator2 = A_BunchOfRubbish.iterator();
			while ( iterator2.hasNext()) {

				Rectangle RubbishUnit = iterator2.next();

				if (!END && !ThreadStop) {
					RubbishUnit.y -= speed * Gdx.graphics.getDeltaTime();
				}

				if(RubbishUnit.y <= 100) {
					coordinates = (int) RubbishUnit.x;
				}

				if (RubbishUnit.y < 10) {
					iterator2.remove();
					Boom = true;
                    BoomCh = 0;
                    BoomCh = TimeUtils.nanoTime();
				}

                if(Boom && TimeUtils.nanoTime() - BoomCh > 510000000) {
                    Boom = false;
                    stateTime = 0f;
                }

				if (RubbishUnit.overlaps(hero)) {

                    mainImage = gamoverImage;
					LOSE = true;
                    try { GamState(); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
			}


			Iterator<Rectangle> iterator3 = A_BunchOfDopping.iterator();
			while ( iterator3.hasNext()) {

				Rectangle DoppingUnit = iterator3.next();

				if (!END && !ThreadStop) {
					DoppingUnit.y -= (speed - 80) * Gdx.graphics.getDeltaTime();
				}

				if (DoppingUnit.y < -300) {
					iterator3.remove();
				}

				if (DoppingUnit.overlaps(hero)) {

					iterator3.remove();
					speed -= 40;
					doppingSound.play(0.15f);
				}
			}
		}
	}
	//-----------------------------------------------------------------

	@Override
	public void resize(int width, int height) { camera.setToOrtho(false);}

	@Override
	public void pause() {

		backGroundnMusic.pause();
		speedAut = speed;
		speed = 0;
		ThreadStop = true;
		lastDropTime2 = 0;
		lastDropTime = 0;
		lastDoppingTime = 0;
	}

	@Override
	public void resume() {

		backGroundnMusic.play();
		speed = speedAut;
		speedAut = 0;
		ThreadStop = false;
		lastDropTime2 = 0;
		lastDropTime = 0;
		lastDoppingTime = 0;
	}

	@Override
	public void hide() {}

	private void BOOM() {
		if(coordinates != 0 && Boom) {
			stateTime += Gdx.graphics.getDeltaTime();
			for (int i = 0; i < FRAME_ROWS ; i++) {
				for (int j = 0; j < FRAME_COLS; j++) {
					currentFrame = (TextureRegion) walkAnimation.getKeyFrame(stateTime, true);
					game.batch.draw(currentFrame, coordinates - 25, 20);
				}
			}
		}
	}

	private void GamState() throws InterruptedException {

		END = true;
		dropSound.dispose();
		backGroundnMusic.dispose();
		show();

         ////////////////////   Работа с кнопками

		if((Cursor.overlaps(RetryState) && (Gdx.input.isTouched() || Gdx.input.isCursorCatched())) ) {

			dispose();
			game.setScreen(new DropKey(game));
			camera.update();
		}
	}
          ///////////////////////

	@Override
	public void dispose() {   // Очистка памяти

		dropImage.dispose();
		drop2Image.dispose();
		DOPING.dispose();
		heroImage.dispose();
		heroImageL.dispose();
		heroImageR.dispose();
		dropSound.dispose();
		doppingSound.dispose();
		backGroundnMusic.dispose();
		areaImage.dispose();
		loseMusic.dispose();
		winMusic.dispose();
		A_BunchOfRubbish.clear();
		A_BunchOfPlants.clear();
		A_BunchOfDopping.clear();
		AnimationMeteor.clear();
		st1.dispose();
		st2.dispose();
		st3.dispose();
		st4.dispose();
		st5.dispose();
		st6.dispose();
		walkSheet.dispose();

	}
	@Override
	public void show() {   //  Фоновая музыка и ее воспроизведение

		if(WIN) winMusic.play();
		if(LOSE) loseMusic.play();
		else backGroundnMusic.play();
	}
}
