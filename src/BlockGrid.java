
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class BlockGrid {
	
	protected Block[][] blocks = new Block[World.BLOCKS_WIDTH][World.BLOCKS_HEIGHT];
	
	Random random = new Random();
	//backgrounds
	//private FileInputStream air_ground = BlockType.AIR.getFile();
	//private FileInputStream stone_ground = BlockType.STONE.getFile();
	
	//加载文件的构造函数
	public BlockGrid(File loadFile) {
		load(loadFile);
	}
	
	//设置方块的背景图片
	public BlockGrid() {
		// TODO Auto-generated constructor stub
		load(new File("scene.xml"));
		/*
		for (int x = 0; x < BLOCKS_WIDTH - 1; x++)
			for (int y = 0; y < BLOCKS_HEIGHT - 1; y++)
				blocks[x][y] = new Block(BlockType.AIR, x * BLOCK_SIZE, y * BLOCK_SIZE);
		*/
	}
	
	public void clear() {
		// TODO Auto-generated constructor stub
		for (int x = 0; x < World.BLOCKS_WIDTH - 1; x++)
			for (int y = 0; y < World.BLOCKS_HEIGHT - 1; y++)
				blocks[x][y] = new Block(BlockType.AIR, x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
	}
	
	//加载游戏格局
	public void load(File loadFile) {
		
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(loadFile);
			Element root = document.getRootElement();
			for (Object block : root.getChildren()) {
				Element e = (Element)block;
				int x = Integer.parseInt(e.getAttributeValue("x"));
				int y = Integer.parseInt(e.getAttributeValue("y"));
				blocks[x][y] = new Block(BlockType.valueOf(e.getAttributeValue("type")), x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//保存游戏格局
	public void save(File saveFile) {
		Document document = new Document();
		Element root = new Element("blocks");
		document.setRootElement(root);
		for (int x = 0; x < World.BLOCKS_WIDTH - 1; x++)
			for (int y = 0; y < World.BLOCKS_HEIGHT - 1; y++) {
				Element block = new Element("block");
				block.setAttribute("x", String.valueOf((int)(blocks[x][y].getX() / World.BLOCK_SIZE)));
				block.setAttribute("y", String.valueOf((int)(blocks[x][y].getY() / World.BLOCK_SIZE)));
				block.setAttribute("type", String.valueOf(blocks[x][y].getType()));
				root.addContent(block);
			}
		
		XMLOutputter output = new XMLOutputter();
		try {
			output.output(document, new FileOutputStream(saveFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAt(int x, int y, BlockType b) {
		blocks[x][y] = new Block(b, x * World.BLOCK_SIZE, y * World.BLOCK_SIZE);
	}
	
	public Block getAt(int x, int y) {
		return blocks[x][y];
	}
	
	public void draw() {
		for (int x = 0; x < World.BLOCKS_WIDTH - 1; x++)
			for (int y = 0; y < World.BLOCKS_HEIGHT - 1; y++)
				blocks[x][y].draw();
							
	}
}
