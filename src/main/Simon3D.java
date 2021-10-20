package main;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Simon3D extends Applet implements MouseListener,ActionListener{
	int count = 1;
	int round = 0;
	static int highScore = 0;
	boolean gameRunning = false;
	static boolean hardMode = false;
	ArrayList<String> simonsPattern = new ArrayList<String>();
	ArrayList<String> playerPattern = new ArrayList<String>();
	String[] colors = new String[] {"yellow", "red", "blue", "green"};
	String[] hardColors = new String[] {"yellowhard", "redhard", "bluehard", "greenhard","cyanhard","seagreenhard","orangehard","magentahard"};
	Label roundText;
	Label highScoreText;
	PickCanvas pc;
	Random rand = new Random();
	static MainFrame mainFrame;
	Primitive redButton; 
	Primitive greenButton; 
	Primitive blueButton; 
	Primitive yellowButton;
	Primitive redHardButton;
	Primitive greenHardButton;
	Primitive cyanHardButton;
	Primitive yellowHardButton;
	Primitive blueHardButton;
	Primitive magentaHardButton;
	Primitive orangeHardButton;
	Primitive seagreenHardButton;
	PointLight mainLight;
	PointLight redLight;
	PointLight greenLight;
	PointLight blueLight;
	PointLight yellowLight;
	PointLight redHardLight;
	PointLight greenHardLight;
	PointLight yellowHardLight;
	PointLight blueHardLight;
	PointLight seagreenHardLight;
	PointLight magentaHardLight;
	PointLight cyanHardLight;
	PointLight orangeHardLight;
	
	/*
	 * main():
	 * 
	 * calls startDialog() to show prompt for starting a game
	 * 
	 * lines 107-110
	 */
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		System.setProperty("sun.awt.noerasebackground", "true");
		startDialog();
	}
	
	/*
	 * init():
	 * 
	 * initialization method
	 * 
	 * creates universe and adds all branches 
	 * 
	 * lines 121-156
	 */
	public void init() {
		GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(gc);
		SimpleUniverse su = new SimpleUniverse(canvas);
		su.getViewingPlatform().setNominalViewingTransform();
		setLayout(new BorderLayout());
		//panel for UI
		Panel menu = new Panel();
		Panel labelsPanel = new Panel();
		labelsPanel.setLayout(new GridLayout(1,2));
		menu.setLayout(new GridLayout(2,1));
		add(menu, BorderLayout.NORTH);
		Button newGameButton = new Button("New Game");
		roundText = new Label("Round: " + round);
		highScoreText = new Label("Highest Round: " + highScore);
		labelsPanel.add(roundText);
		labelsPanel.add(highScoreText);
		menu.add(newGameButton);
		menu.add(labelsPanel);
		newGameButton.addActionListener(this);
		//top down view
		canvas = new Canvas3D(gc);
		add(canvas);
		canvas.addMouseListener(this);
		BranchGroup angleView = createView(canvas, new Point3d(1,1,1), 
			      new Point3d(0,0,0), new Vector3d(0,1,0), true);
		su.addBranchGraph(angleView);
		//contentBranch
		BranchGroup bg = createContentBranch(canvas);
		//pickview setup
		pc = new PickCanvas(canvas, bg);
		pc.setMode(PickTool.GEOMETRY);
		
		bg.compile();		
		su.addBranchGraph(bg);
	}

	/*
	 * createContentBranch():
	 * 
	 * method that assembles to main branch for the universe
	 * 
	 * contains all geometries, apperances, and sounds
	 * 
	 * lines 167-491
	 */
	private BranchGroup createContentBranch(Canvas3D cv) {
		//branch group
		BranchGroup root = new BranchGroup();
		
		
		//bounds
		BoundingSphere bounds = new BoundingSphere();		
		
		
		//colors
		Color3f white = new Color3f(Color.white);
		Color3f darkGray = new Color3f(Color.darkGray);
		Color3f red = new Color3f(Color.red);
		Color3f green = new Color3f(Color.green);
		Color3f blue = new Color3f(Color.blue);
		Color3f yellow = new Color3f(Color.yellow);
		Color3f orange = new Color3f(0.8f,0.4f,0f);
		Color3f seagreen = new Color3f(0.33f,1f,0.62f);
		Color3f magenta = new Color3f(Color.magenta);
		Color3f cyan = new Color3f(Color.cyan);
		
		
		//apperances
		Appearance whiteAP = new Appearance();
		Appearance darkGrayAP = new Appearance();
		Appearance redAP = new Appearance();
		Appearance greenAP = new Appearance();
		Appearance blueAP = new Appearance();
		Appearance yellowAP = new Appearance();
		Appearance orangeAP = new Appearance();
		Appearance seagreenAP = new Appearance();
		Appearance magentaAP = new Appearance();
		Appearance cyanAP = new Appearance();
		whiteAP.setMaterial(new Material(white, new Color3f(0f,0f,0f), white, new Color3f(1f,1f,1f), 70f));
		darkGrayAP.setMaterial(new Material(darkGray, new Color3f(0f,0f,0f), darkGray, new Color3f(1f,1f,1f), 70f));
		redAP.setMaterial(new Material(red, new Color3f(0f,0f,0f), red, new Color3f(1f,1f,1f), 70f));
		greenAP.setMaterial(new Material(green, new Color3f(0f,0f,0f), green, new Color3f(1f,1f,1f), 70f));
		blueAP.setMaterial(new Material(blue, new Color3f(0f,0f,0f), blue, new Color3f(1f,1f,1f), 70f));
		yellowAP.setMaterial(new Material(yellow, new Color3f(0f,0f,0f), yellow, new Color3f(1f,1f,1f), 70f));
		orangeAP.setMaterial(new Material(orange, new Color3f(0f,0f,0f), orange, new Color3f(1f,1f,1f), 70f));
		seagreenAP.setMaterial(new Material(seagreen, new Color3f(0f,0f,0f), seagreen, new Color3f(1f,1f,1f), 70f));
		magentaAP.setMaterial(new Material(magenta, new Color3f(0f,0f,0f), magenta, new Color3f(1f,1f,1f), 70f));
		cyanAP.setMaterial(new Material(cyan, new Color3f(0f,0f,0f), cyan, new Color3f(1f,1f,1f), 70f));
		
		
		//game base geometry
		Font3D font = new Font3D(new Font("Serif", Font.PLAIN, 1), new FontExtrusion());
		Text3D centerSimonLogo = new Text3D(font, "Simon");
		Shape3D logo = new Shape3D(centerSimonLogo, darkGrayAP);
		Transform3D logoTF = new Transform3D();
		logoTF.rotY(Math.PI/4);
		logoTF.setScale(0.08f);
		logoTF.setTranslation(new Vector3f(0.01f,.15f,0.16f));
		TransformGroup logoTG = new TransformGroup(logoTF);
		logoTG.addChild(logo);
		Sphere centerSphere = new Sphere(0.12f, whiteAP);
		Transform3D centerSphereTF = new Transform3D();
		centerSphereTF.setTranslation(new Vector3f(0f,0.05f,0f));
		TransformGroup centerSphereTG = new TransformGroup(centerSphereTF);
		centerSphereTG.addChild(centerSphere);
		Box gameBase = new Box(.5f,.5f, 0.05f, darkGrayAP);
		Transform3D gameBaseTF = new Transform3D();
		gameBaseTF.rotX(Math.PI/2);
		TransformGroup gameBaseTG = new TransformGroup(gameBaseTF);
		gameBaseTG.addChild(gameBase);
		Box gameBase2 = new Box(.51f,.51f,0.05f, darkGrayAP);
		Transform3D gameBase2TF = new Transform3D();
		gameBase2TF.rotX(Math.PI/2);
		gameBase2TF.setTranslation(new Vector3f(0,-.04f,0));
		TransformGroup gameBase2TG = new TransformGroup(gameBase2TF);
		gameBase2TG.addChild(gameBase2);	
		
		
		
//EASY MODE BUTTONS		
		//red
		Box redButton1 = new Box(0.22f,0.02f,0.12f, redAP);
		redButton1.setName("Red");
		Box redButton2 = new Box(0.12f,0.02f,0.22f, redAP);
		redButton2.setName("Red");
		redButton = redButton1;
		redButton.addChild(redButton2);
		Transform3D redButtonTF = new Transform3D();
		redButtonTF.setTranslation(new Vector3f(0.28f, 0.1f, 0.28f));
		TransformGroup redButtonTG = new TransformGroup(redButtonTF);
		redButtonTG.addChild(redButton);
		redLight = new PointLight(new Color3f(Color.red), new Point3f(0.28f,0.5f,0.28f), new Point3f(1f,2f,0f));
		redLight.setEnable(false);
		redLight.setInfluencingBounds(bounds);
		redLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//green
		Box greenButton1 = new Box(0.22f,0.02f,0.12f, greenAP);
		greenButton1.setName("Green");
		Box greenButton2 = new Box(0.12f,0.02f,0.22f, greenAP);
		greenButton2.setName("Green");
		greenButton = greenButton1;
		greenButton.addChild(greenButton2);
		Transform3D greenButtonTF = new Transform3D();
		greenButtonTF.setTranslation(new Vector3f(-0.22f, 0.1f, 0.28f));
		TransformGroup greenButtonTG = new TransformGroup(greenButtonTF);
		greenButtonTG.addChild(greenButton);
		greenLight = new PointLight(new Color3f(Color.green), new Point3f(-0.22f,0.5f,0.28f), new Point3f(1f,2f,0f));
		greenLight.setEnable(false);
		greenLight.setInfluencingBounds(bounds);
		greenLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//blue
		Box blueButton1 = new Box(0.22f,0.02f,0.12f, blueAP);
		blueButton1.setName("Blue");
		Box blueButton2 = new Box(0.12f,0.02f,0.22f, blueAP);
		blueButton2.setName("Blue");
		blueButton = blueButton1;
		blueButton.addChild(blueButton2);
		Transform3D blueButtonTF = new Transform3D();
		blueButtonTF.setTranslation(new Vector3f(0.28f, 0.1f, -0.22f));
		TransformGroup blueButtonTG = new TransformGroup(blueButtonTF);
		blueButtonTG.addChild(blueButton);
		blueLight = new PointLight(new Color3f(Color.blue), new Point3f(0.28f,0.5f,-0.22f), new Point3f(1f,2f,0f));
		blueLight.setEnable(false);
		blueLight.setInfluencingBounds(bounds);
		blueLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//yellow
		Box yellowButton1 = new Box(0.22f,0.02f,0.12f, yellowAP);
		yellowButton1.setName("Yellow");
		Box yellowButton2 = new Box(0.12f,0.02f,0.22f, yellowAP);
		yellowButton2.setName("Yellow");
		yellowButton = yellowButton1;
		yellowButton.addChild(yellowButton2);
		Transform3D yellowButtonTF = new Transform3D();
		yellowButtonTF.setTranslation(new Vector3f(-0.22f, 0.1f, -0.22f));
		TransformGroup yellowButtonTG = new TransformGroup(yellowButtonTF);
		yellowButtonTG.addChild(yellowButton);
		yellowLight = new PointLight(new Color3f(Color.yellow), new Point3f(-0.44f,0.5f,-0.44f), new Point3f(1f,2f,0f));
		yellowLight.setEnable(false);
		yellowLight.setInfluencingBounds(bounds);
		yellowLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		
		
//HARD MODE BUTTONS
		//red hard button
		Box redHardButton1 = new Box(0.22f,0.02f,0.12f, redAP);
		redHardButton1.setName("RedHard");
		Box redHardButton2 = new Box(0.12f,0.02f,0.22f, redAP);
		redHardButton2.setName("RedHard");
		redHardButton = redHardButton1;
		redHardButton.addChild(redHardButton2);
		Transform3D redHardButtonTF = new Transform3D();
		redHardButtonTF.setScale(0.65f);
		redHardButtonTF.setTranslation(new Vector3f(0.37f, 0.1f, 0.35f));
		TransformGroup redHardButtonTG = new TransformGroup(redHardButtonTF);
		redHardButtonTG.addChild(redHardButton);
		redHardLight = new PointLight(red, new Point3f(0.28f,0.5f,0.28f), new Point3f(2f,2f,0f));
		redHardLight.setEnable(false);
		redHardLight.setInfluencingBounds(bounds);
		redHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//cyan hard button
		Box cyanHardButton1 = new Box(0.22f, 0.02f, 0.12f, cyanAP);
		cyanHardButton1.setName("CyanHard");
		Box cyanHardButton2 = new Box(0.12f, 0.02f, 0.22f, cyanAP);
		cyanHardButton2.setName("CyanHard");
		cyanHardButton = cyanHardButton1;
		cyanHardButton.addChild(cyanHardButton2);
		Transform3D cyanHardButtonTF = new Transform3D();
		cyanHardButtonTF.setScale(0.65f);
		cyanHardButtonTF.setTranslation(new Vector3f(0.04f, 0.1f, 0.35f));
		TransformGroup cyanHardButtonTG = new TransformGroup(cyanHardButtonTF);
		cyanHardButtonTG.addChild(cyanHardButton);
		cyanHardLight = new PointLight(cyan, new Point3f(0.1f,0.5f,0.28f), new Point3f(2f,2f,0f));
		cyanHardLight.setEnable(false);
		cyanHardLight.setInfluencingBounds(bounds);
		cyanHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//green hard button
		Box greenHardButton1 = new Box(0.22f, 0.02f, 0.12f, greenAP);
		greenHardButton1.setName("GreenHard");
		Box greenHardButton2 = new Box(0.12f, 0.02f, 0.22f, greenAP);
		greenHardButton2.setName("GreenHard");
		greenHardButton = greenHardButton1;
		greenHardButton.addChild(greenHardButton2);
		Transform3D greenHardButtonTF = new Transform3D();
		greenHardButtonTF.setScale(0.65f);
		greenHardButtonTF.setTranslation(new Vector3f(-0.3f, 0.1f, 0.35f));
		TransformGroup greenHardButtonTG = new TransformGroup(greenHardButtonTF);
		greenHardButtonTG.addChild(greenHardButton);
		greenHardLight = new PointLight(green, new Point3f(-0.22f,0.5f,0.28f), new Point3f(2f,2f,0f));
		greenHardLight.setEnable(false);
		greenHardLight.setInfluencingBounds(bounds);
		greenHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//blue hard button
		Box blueHardButton1 = new Box(0.22f, 0.02f, 0.12f, blueAP);
		blueHardButton1.setName("BlueHard");
		Box blueHardButton2 = new Box(0.12f, 0.02f, 0.22f, blueAP);
		blueHardButton2.setName("BlueHard");
		blueHardButton = blueHardButton1;
		blueHardButton.addChild(blueHardButton2);
		Transform3D blueHardButtonTF = new Transform3D();
		blueHardButtonTF.setScale(0.65f);
		blueHardButtonTF.setTranslation(new Vector3f(0.37f, 0.1f, -0.29f));
		TransformGroup blueHardButtonTG = new TransformGroup(blueHardButtonTF);
		blueHardButtonTG.addChild(blueHardButton);
		blueHardLight = new PointLight(blue, new Point3f(0.28f,0.5f,-0.22f), new Point3f(2f,2f,0f));
		blueHardLight.setEnable(false);
		blueHardLight.setInfluencingBounds(bounds);
		blueHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//yellow hard button
		Box yellowHardButton1 = new Box(0.22f, 0.02f, 0.12f, yellowAP);
		yellowHardButton1.setName("YellowHard");
		Box yellowHardButton2 = new Box(0.12f, 0.02f, 0.22f, yellowAP);
		yellowHardButton2.setName("YellowHard");
		yellowHardButton = yellowHardButton1;
		yellowHardButton.addChild(yellowHardButton2);
		Transform3D yellowHardButtonTF = new Transform3D();
		yellowHardButtonTF.setScale(0.65f);
		yellowHardButtonTF.setTranslation(new Vector3f(-0.3f, 0.1f, -0.29f));
		TransformGroup yellowHardButtonTG = new TransformGroup(yellowHardButtonTF);
		yellowHardButtonTG.addChild(yellowHardButton);
		yellowHardLight = new PointLight(yellow, new Point3f(-0.44f,0.5f,-0.44f), new Point3f(2f,2f,0f));
		yellowHardLight.setEnable(false);
		yellowHardLight.setInfluencingBounds(bounds);
		yellowHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//magenta hard button
		Box magentaHardButton1 = new Box(0.22f, 0.02f, 0.12f, magentaAP);
		magentaHardButton1.setName("MagentaHard");
		Box magentaHardButton2 = new Box(0.12f, 0.02f, 0.22f, magentaAP);
		magentaHardButton2.setName("MagentaHard");
		magentaHardButton = magentaHardButton1;
		magentaHardButton.addChild(magentaHardButton2);
		Transform3D magentaHardButtonTF = new Transform3D();
		magentaHardButtonTF.setScale(0.65f);
		magentaHardButtonTF.setTranslation(new Vector3f(0.04f, 0.1f, -0.29f));
		TransformGroup magentaHardButtonTG = new TransformGroup(magentaHardButtonTF);
		magentaHardButtonTG.addChild(magentaHardButton);
		magentaHardLight = new PointLight(magenta, new Point3f(0.1f,0.5f,-0.44f), new Point3f(2f,2f,0f));
		magentaHardLight.setEnable(false);
		magentaHardLight.setInfluencingBounds(bounds);
		magentaHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//orange hard button
		Box orangeHardButton1 = new Box(0.22f, 0.02f, 0.12f, orangeAP);
		orangeHardButton1.setName("OrangeHard");
		Box orangeHardButton2 = new Box(0.12f, 0.02f, 0.22f, orangeAP);
		orangeHardButton2.setName("OrangeHard");
		orangeHardButton = orangeHardButton1;
		orangeHardButton.addChild(orangeHardButton2);
		Transform3D orangeHardButtonTF = new Transform3D();
		orangeHardButtonTF.setScale(0.65f);
		orangeHardButtonTF.setTranslation(new Vector3f(-0.3f, 0.1f, 0.032f));
		TransformGroup orangeHardButtonTG = new TransformGroup(orangeHardButtonTF);
		orangeHardButtonTG.addChild(orangeHardButton);
		orangeHardLight = new PointLight(orange, new Point3f(-0.44f,0.5f,-0.08f), new Point3f(2f,2f,0f));
		orangeHardLight.setEnable(false);
		orangeHardLight.setInfluencingBounds(bounds);
		orangeHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		//seagreen hard button
		Box seagreenHardButton1 = new Box(0.22f, 0.02f, 0.12f, seagreenAP);
		seagreenHardButton1.setName("SeagreenHard");
		Box seagreenHardButton2 = new Box(0.12f, 0.02f, 0.22f, seagreenAP);
		seagreenHardButton2.setName("SeagreenHard");
		seagreenHardButton = seagreenHardButton1;
		seagreenHardButton.addChild(seagreenHardButton2);
		Transform3D seagreenHardButtonTF = new Transform3D();
		seagreenHardButtonTF.setScale(0.65f);
		seagreenHardButtonTF.setTranslation(new Vector3f(0.37f, 0.1f, 0.032f));
		TransformGroup seagreenHardButtonTG = new TransformGroup(seagreenHardButtonTF);
		seagreenHardButtonTG.addChild(seagreenHardButton);
		seagreenHardLight = new PointLight(seagreen, new Point3f(0.28f,0.5f,-0.08f), new Point3f(2f,2f,0f));
		seagreenHardLight.setEnable(false);
		seagreenHardLight.setInfluencingBounds(bounds);
		seagreenHardLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		
		
		//ambient lighting
		mainLight = new PointLight(new Color3f(Color.WHITE), new Point3f(0f,1f,0f), new Point3f(1f,2f,0f));
		mainLight.setInfluencingBounds(bounds);
		mainLight.setCapability(PointLight.ALLOW_STATE_WRITE);
		
		
		//background
		Background background = new Background(new Color3f(Color.LIGHT_GRAY));
		background.setApplicationBounds(bounds);
		
		
		//buttons group
		TransformGroup buttonsTG = new TransformGroup();
		buttonsTG.addChild(yellowButtonTG);
		buttonsTG.addChild(redButtonTG);
		buttonsTG.addChild(blueButtonTG);
		buttonsTG.addChild(greenButtonTG);		
		buttonsTG.addChild(redLight);
		buttonsTG.addChild(greenLight);
		buttonsTG.addChild(blueLight);
		buttonsTG.addChild(yellowLight);
		
		
		//hard buttons group
		TransformGroup hardButtonsTG = new TransformGroup();
		hardButtonsTG.addChild(redHardButtonTG);
		hardButtonsTG.addChild(greenHardButtonTG);
		hardButtonsTG.addChild(cyanHardButtonTG);
		hardButtonsTG.addChild(blueHardButtonTG);
		hardButtonsTG.addChild(yellowHardButtonTG);
		hardButtonsTG.addChild(magentaHardButtonTG);
		hardButtonsTG.addChild(orangeHardButtonTG);
		hardButtonsTG.addChild(seagreenHardButtonTG);
		hardButtonsTG.addChild(redHardLight);
		hardButtonsTG.addChild(yellowHardLight);
		hardButtonsTG.addChild(blueHardLight);
		hardButtonsTG.addChild(greenHardLight);
		hardButtonsTG.addChild(cyanHardLight);
		hardButtonsTG.addChild(magentaHardLight);
		hardButtonsTG.addChild(orangeHardLight);
		hardButtonsTG.addChild(seagreenHardLight);
		
		
		//assembling branch group
		if (hardMode) {
			root.addChild(hardButtonsTG);
		}else {
			root.addChild(buttonsTG);
		}
		root.addChild(mainLight);
		root.addChild(logoTG);
		root.addChild(centerSphereTG);
		root.addChild(gameBaseTG);
		root.addChild(gameBase2TG);
		root.addChild(background);
		return root;
	}
	

	/*
	 * createView():
	 * 
	 * creates view branch for universe
	 * 
	 * lines 501-523
	 */
	private BranchGroup createView(Canvas3D cv, Point3d eye,
			    Point3d center, Vector3d vup, boolean first) {
			    View view = new View();
			    view.setProjectionPolicy(View.PARALLEL_PROJECTION);
			    ViewPlatform vp = new ViewPlatform();
			    view.addCanvas3D(cv);
			    view.attachViewPlatform(vp);
			    view.setPhysicalBody(new PhysicalBody());
			    view.setPhysicalEnvironment(new PhysicalEnvironment());
			    if (first = true)
			    {
			    	view.setFieldOfView(0.4 * Math.PI);
			    	view.setFrontClipDistance(0.01);
			    }
			    Transform3D trans = new Transform3D();
			    trans.lookAt(eye, center, vup);
			    trans.invert();
			    TransformGroup tg = new TransformGroup(trans);
			    tg.addChild(vp);
			    BranchGroup bgView = new BranchGroup();
			    bgView.addChild(tg);
			    return bgView;
	}
	  
	  
	/*
	 * ****** MAIN FUNCTIONALITY OF PROGRAM ******
	 * 
	 * mouseClicked():
	 * 
	 * event handler for mouse clicking
	 * 
	 * using picking, the name of the primitive picked is used in a
	 * switch statement to add selection to the playerPattern list
	 * 
	 * the light for the picked primitive is turned on for 1/4 of a 
	 * second and then turned off
	 * 
	 * for each pick made, a comparision is made between the playerPattern
	 * and simonsPattern to determine whether the player has guess the correct
	 * next button/primitive in simonsPattern list
	 * 
	 * when the length of both the playerPattern and simonsPattern is equal
	 * a final comparision is made, if the lists are equal then createPattern()
	 * is called to increase simonsPattern by 1 and the playerPattern is cleared
	 * for the pattern cycle
	 * 
	 * at anytime if a comparison is made and the lists do not match
	 * endGameDialog() is called to show the players score and a new game button
	 * to start a new game
	 * 
	 * lines 555-862
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
	    pc.setShapeLocation(mouseEvent);
	    PickResult result = pc.pickClosest();
	    if(result != null){
	    	Primitive p = (Primitive)result.getNode(PickResult.PRIMITIVE);
	    	Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D);
	    	if (p != null) {
	    		//for button action
	    		if(gameRunning == true) {
	    			count++;
	    		}
	    		switch(p.getName()) {
	    		case "SeagreenHard": 
	    			playerPattern.add("seagreenhard");
	    			seagreenHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					seagreenHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "OrangeHard": 
	    			playerPattern.add("orangehard");
	    			orangeHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					orangeHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "MagentaHard": 
	    			playerPattern.add("magentahard");
	    			magentaHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					magentaHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "CyanHard": 
	    			playerPattern.add("cyanhard");
	    			cyanHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cyanHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "RedHard": 
	    			playerPattern.add("redhard");
	    			redHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					redHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "BlueHard": 
	    			playerPattern.add("bluehard");
	    			blueHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					blueHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "GreenHard": 
	    			playerPattern.add("greenhard");
	    			greenHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					greenHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "YellowHard": 
	    			playerPattern.add("yellowhard");
	    			yellowHardLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					yellowHardLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "Red": 
	    			playerPattern.add("red");
	    			redLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					redLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "Yellow": 
	    			playerPattern.add("yellow");
	    			yellowLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					yellowLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "Green": 
	    			playerPattern.add("green");
	    			greenLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					greenLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		case "Blue": 
	    			playerPattern.add("blue");
	    			blueLight.setEnable(true);
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					blueLight.setEnable(false);
		    		if(gameRunning == true) {
						if(!compareList(simonsPattern, playerPattern)) {
							if(round > highScore) {
								highScoreText.setText("Highest Round: " + round);
							}
		    				gameRunning = false;
		    				simonsPattern.clear();
		    				playerPattern.clear();
		    				roundText.setText("Round: " + round);
		    				endGameDialog();
						}
		    		}
					break;
	    		}
	    		
	    		
	    		if(count == simonsPattern.size()) {
	    			boolean matching = compareList(simonsPattern, playerPattern);
	    			
	    			if(matching) {				
	    				round++;
	    				roundText.setText("Round: " + round);
	    				count = 0;
	    				playerPattern.clear();
	    				simonsPattern = createPattern(simonsPattern);
	    				showPattern(simonsPattern);
	    			}else {
						if(round > highScore) {
							highScoreText.setText("Highest Round: " + round);
						}
	    				gameRunning = false;
	    				simonsPattern.clear();
	    				playerPattern.clear();
	    				roundText.setText("Round: " + round);
	    				endGameDialog();
	    			}
	    		}    			            
	         } else if (s != null) {

	         }else{
	        	 
	            System.out.println("null");
	         }
	    }
	}
	

	/*
	 * showPattern():
	 * 
	 * method used to show player the pattern
	 * 
	 * iterates through simonsPattern list and uses switch statement
	 * to turn on light correlating to the current string for 1/2 second
	 * if on hard mode or 1 second if not on hard mode
	 * 
	 * lines 876-1000
	 */
	private void showPattern(ArrayList<String> simonsPattern) {
		int timeDelay;
		if(hardMode) {
			timeDelay = 500;
		}else {
			timeDelay = 1000;
		}
		for(String s: simonsPattern) {
			try {
				TimeUnit.MILLISECONDS.sleep(timeDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			switch(s) {
			case "seagreenhard": 
				seagreenHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				seagreenHardLight.setEnable(false);
				break;
			case "orangehard": 
				orangeHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				orangeHardLight.setEnable(false);
				break;
			case "magentahard": 
				magentaHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				magentaHardLight.setEnable(false);
				break;
			case "cyanhard": 
				cyanHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				cyanHardLight.setEnable(false);
				break;
			case "redhard": 
				redHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				redHardLight.setEnable(false);
				break;
			case "bluehard": 
				blueHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				blueHardLight.setEnable(false);
				break;
			case "yellowhard": 
				yellowHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				yellowHardLight.setEnable(false);
				break;
			case "greenhard": 
				greenHardLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				greenHardLight.setEnable(false);
				break;
			case "yellow": 
				yellowLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				yellowLight.setEnable(false);
				break;
			case "red":
				redLight.setEnable(true);
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				redLight.setEnable(false);
				break;
			case "blue": 
				blueLight.setEnable(true);	
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				blueLight.setEnable(false);
				break;
			case "green": 
				greenLight.setEnable(true);	
				try {
					TimeUnit.MILLISECONDS.sleep(timeDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				greenLight.setEnable(false);
				break;
			}
		}
	}

	/*
	 * compareList():
	 * 
	 * method to compare lists for equality
	 * 
	 * for loop uses playerPattern.size() for limit so that
	 * comparison can be made for every pick 
	 * 
	 * lines 1012-1019
	 */
	private boolean compareList(ArrayList<String> simonsPattern, ArrayList<String> playerPattern) {
		for(int i = 0; i < playerPattern.size();i++) {
			if(!simonsPattern.get(i).equals(playerPattern.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * createPattern():
	 * 
	 * adds new random color to simonsPattern
	 * 
	 * called at the end of each game cycle, when simonsPattern 
	 * and playersPattern is same size, if patterns match
	 * 
	 * lines 1031-1038
	 */
	private ArrayList<String> createPattern(ArrayList<String> simonsPattern) {
		if(hardMode) {
			simonsPattern.add(hardColors[rand.nextInt(8)]);
		}else {
			simonsPattern.add(colors[rand.nextInt(4)]);
		}		
		return simonsPattern;
	}
	
	/*
	 * startDialog()
	 * 
	 * awt dialog box used to start game
	 * 
	 * this is where player makes selection of hard mode
	 * or easy mode
	 * 
	 * MainFrame is created here
	 * 
	 * lines 1052-1137
	 */
	private static void startDialog() {
		Frame f = new Frame();
		Dialog d = new Dialog(f, "Simon 3D", true);
		Button newGame = new Button("Okay");
		d.setLayout(new GridLayout(2,1));
		Panel topPanel = new Panel();
		topPanel.add(newGame);
		d.add(topPanel);
		Panel bottomPanel = new Panel();
		d.add(bottomPanel);
		bottomPanel.setLayout(new GridLayout(1,2));
		Checkbox hardModeBox = new Checkbox("Hard Mode?");
		Checkbox easyModeBox = new Checkbox("Easy Mode?");
		bottomPanel.add(easyModeBox);
		bottomPanel.add(hardModeBox);
		easyModeBox.setState(true);
		
		hardModeBox.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(easyModeBox.getState()) {
					easyModeBox.setState(false);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		easyModeBox.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(hardModeBox.getState()) {
					hardModeBox.setState(false);
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(hardModeBox.getState()) {
					hardMode = true;
				}else {
					hardMode = false;
				}
				mainFrame = new MainFrame(new Simon3D(), 1750, 900);	
				mainFrame.setTitle("Simon 3D");
				d.setVisible(false);
			}
			
		});
		
	    d.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	          public void windowClosing(java.awt.event.WindowEvent e) {
	             Window window = SwingUtilities.getWindowAncestor(e.getComponent());
	             window.dispose();
	          }
	    });
	    
		d.setSize(300,95);
		d.setVisible(true);
	}
	
	/*
	 * endGameDialog():
	 * 
	 * awt dialog box used to display players final score
	 * and ask if the player wishes to start another game
	 * 
	 * if dialog box is closed (a.k.a does not want to start 
	 * another game) initial startGameDialog() us show again
	 * 
	 * lines 1150-1220
	 */
	private void endGameDialog() {
		Frame f = new Frame();
		Dialog d = new Dialog(f, "GAME OVER", true);
		Button newGameDialogButton = new Button("New Game?");
		d.setLayout(new FlowLayout());       
		
		newGameDialogButton.addActionListener ( new ActionListener()  
        {  
            public void actionPerformed( ActionEvent e )  
            {  
            	d.setVisible(false); 
            	gameRunning = true;
            	playerPattern.clear();
        		count = 0;
        		round = 1;
        		roundText.setText("Round: " + round);
        		simonsPattern = createPattern(simonsPattern);
        		showPattern(simonsPattern);
        		 
            }  
        }); 
        
	    d.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	          public void windowClosing(java.awt.event.WindowEvent e) {
	        	 round = 0;
	        	 roundText.setText("Round: " + round);
	             Window window = SwingUtilities.getWindowAncestor(e.getComponent());
	             window.dispose();
	        	 mainFrame.dispose();
	        	 startDialog();
	          }
	    });
	     
        d.add(new Label("You made it to round " + round));
        d.add(newGameDialogButton);
		d.setSize(300,85);
		d.setVisible(true);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameRunning = true;
		playerPattern.clear();
		count = 0;
		round = 1;
		roundText.setText("Round: " + round);
		simonsPattern = createPattern(simonsPattern);
		showPattern(simonsPattern);				
		
	}
}