package States;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.Button;
import main.Main;
import main.SaveModel;

public class StartUpState extends BasicGameState {

	public static Image cat = null;
	public static int[][] Pixels = null;
	public static BufferedImage img = null;
	public static int[] highestPixel = null;
	public static int[] lowestPixel = null;
	public static Button save = new Button("Save", Main.WIDTH - 100, 0);
	
	public static int borderPixelsNum = 0;
	public static String[] boarderPixels = null;
	
	public static String ImageName = "Cat.png";
	
	public static boolean touchingPink(int x, int y) {
		try{
			if(Pixels[x - 1][y] == -65281) return true;
			if(Pixels[x + 1][y] == -65281) return true;
			if(Pixels[x][y - 1] == -65281) return true;
			if(Pixels[x][y + 1] == -65281) return true;
		} catch(Exception e) {}	
		
		return false;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		cat = new Image("Resources/" + ImageName, false, Image.FILTER_NEAREST);
		try {
			img = ImageIO.read(new File("src/Resources/" + ImageName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		highestPixel = new int[cat.getWidth()];
		lowestPixel = new int[cat.getWidth()];
		
		for(int i = 0; i < cat.getWidth(); i++) {
			lowestPixel[i] = 0;
			highestPixel[i] = -1;
		}
		
		Pixels = new int[cat.getWidth()][cat.getHeight()];
		
		for(int x = 0; x < cat.getWidth(); x++) {
			for(int y = 0; y < cat.getHeight(); y++) {
				Pixels[x][y] = img.getRGB(x, y);
				
				//if the color is not magic pink
				if(Pixels[x][y] != -65281) {
					
					if(y >= lowestPixel[x]) lowestPixel[x] = y;
					if(highestPixel[x] < 0) highestPixel[x] = y;
				}
			}
		}
		
		for(int x = 0; x < cat.getWidth(); x++) {
			for(int y = 0; y < cat.getHeight(); y++) {
				if(Pixels[x][y] != -65281) {
					
					if(x != 0 && x != cat.getWidth() && touchingPink(x, y) || x == 0 || x == cat.getWidth() - 1) {
						borderPixelsNum++;
					}
				}
			}
		}
		
		boarderPixels = new String[borderPixelsNum];
		int currPixel = 0;
		
		for(int x = 0; x < cat.getWidth(); x++) {
			for(int y = 0; y < cat.getHeight(); y++) {
				if(Pixels[x][y] != -65281) {
					if(x != 0 && x != cat.getWidth() && touchingPink(x, y) || x == 0 || x == cat.getWidth() - 1) {
					boarderPixels[currPixel] = "v " + (x) + ".000000 " + (-y + cat.getHeight())+ ".000000 0.000000"; 
					currPixel++;
				}
				}
			}
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
	
		boolean Click = arg0.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		if(save.isMouseOver(arg0) && Click) SaveModel.writeFile();
	
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setBackground(Color.blue);
		g.scale(10, 10);
		
		//cat.draw();
		for(int x = 0; x < cat.getWidth(); x++) {
			for(int y = 0; y < cat.getHeight(); y++) {
				//only want to draw if its touching the pink
				if(Pixels[x][y] != -65281) {
					if(x != 0 && x != cat.getWidth() && touchingPink(x, y) || x == 0 || x == cat.getWidth() - 1) {
						g.setColor(new Color(Pixels[x][y]));
						g.fillRect(x, y, 1, 1);
						//make so it spits out a blender readable file
						//containing x and y information of each border pixel position
					}
				}
			}
		}
		
		g.resetTransform();
		save.draw(arg0, g);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
