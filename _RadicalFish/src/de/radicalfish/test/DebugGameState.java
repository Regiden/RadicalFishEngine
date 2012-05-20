package de.radicalfish.test;
import java.net.URL;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;
import de.matthiasmann.twl.ActionMap;
import de.matthiasmann.twl.Alignment;
import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.FPSCounter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.renderer.Renderer;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import de.radicalfish.TWLGameState;
import de.radicalfish.World;
import de.radicalfish.context.GameContext;
import de.radicalfish.context.GameDelta;
import de.radicalfish.debug.DeveloperConsole;
import de.radicalfish.debug.LogConsole;
import de.radicalfish.debug.Logger;
import de.radicalfish.debug.OptionsPanel;
import de.radicalfish.debug.TWLInputForwarder;
import de.radicalfish.debug.TWLRootPane;
import de.radicalfish.debug.ToneEditor;
import de.radicalfish.debug.ToolBox;
import de.radicalfish.debug.parser.PropertyInputParser;
import de.radicalfish.debug.parser.URLInputParser;

public class DebugGameState extends TWLGameState {
	
	private TWLRootPane root;
	private GUI gui;
	
	public DebugGameState(GameContext context, World world, int id) {
		super(context, world, id);
	}
	
	// GAME METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public void init(GameContext context, World world) throws SlickException {
		initGUI(context);
		buildGUI(context);
		updateGUI();
	}
	public void update(GameContext context, World world, GameDelta delta) throws SlickException {
		updateGUI();
		
		if (context.getInput().isKeyPressed(Input.KEY_BACKSLASH)) {
			root.setVisible(!root.isVisible());
			if (root.isVisible()) {
				if (root.getCurrentFocusOwner() != null) {
					root.getCurrentFocusOwner().requestKeyboardFocus();
				}
			}
		}
		
	}
	public void render(GameContext context, World world, Graphics g) throws SlickException {
		gui.draw();
	}
	
	@Override
	public void keyPressed(int key, char c) {
		
		super.keyPressed(key, c);
	}
	
	// METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * @param visible
	 *            the visibility of the debug view
	 */
	public void setVisible(boolean visible) {
		root.setVisible(visible);
	}
	/**
	 * @return true if the debug view is visible.
	 */
	public boolean isVisible() {
		return root.isVisible();
	}
	
	// INTERN
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	private void updateGUI() {
		gui.setSize();
		gui.handleTooltips();
		gui.updateTimers();
		gui.invokeRunables();
		gui.validateLayout();
		gui.setCursor();
	}
	private void buildGUI(final GameContext context) {
		final OptionsPanel options = new OptionsPanel(context.getSettings());
		final DeveloperConsole console = new DeveloperConsole();
		final LogConsole log = new LogConsole();
		final ToneEditor toneeditor = new ToneEditor();
		
		ToolBox toolbox = new ToolBox(context.getContainerWidth(), context.getContainerHeight());
		toolbox.setCanAcceptKeyboardFocus(false);
		
		toolbox.addButton("Options", options);
		toolbox.addButton("Console", console);
		toolbox.addButton(" Log ", log);
		toolbox.addButton(" Tone ", toneeditor);
		
		toolbox.addFiller();
		toolbox.addSeparator();
		{
			Label fps = new Label("FPS:");
			FPSCounter fpsCounter = new FPSCounter();
			
			toolbox.addCustomWidget(fps, Alignment.CENTER);
			toolbox.addCustomWidget(fpsCounter, Alignment.CENTER);
		}
		toolbox.addSeparator();
		
		toolbox.addButton("Adjust", new Runnable() {
			public void run() {
				options.adjustSize();
				console.adjustSize();
				log.adjustSize();
				toneeditor.adjustSize();
			}
		});
		toolbox.addButton("Close All", new Runnable() {
			public void run() {
				options.setVisible(false);
				console.setVisible(false);
				log.setVisible(false);
				toneeditor.setVisible(false);
			}
		});
		toolbox.addButton("Exit", new Runnable() {
			public void run() {
				context.getContainer().exit();
			}
		});
		
		toolbox.createToolbox();
		options.setVisible(false);
		log.setVisible(false);
		toneeditor.setVisible(false);
		console.setVisible(false);
		
		console.addInputParser(new URLInputParser());
		console.addInputParser(new PropertyInputParser(context.getSettings()));
		
		Logger.addLogListener(log);
		
		root.add(options);
		root.add(toolbox);
		root.add(console);
		root.add(log);
		root.add(toneeditor);
		
		root.registerWidgetForVisible(console, Event.KEY_C);
		root.registerWidgetForVisible(options, Event.KEY_O);
		root.registerWidgetForVisible(log, Event.KEY_L);
		root.registerWidgetForVisible(toneeditor, Event.KEY_T);
		
	}
	private void initGUI(GameContext context) throws SlickException {
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		try {
			root = new TWLRootPane(this);
			root.setTheme("");
			
			ActionMap actionMap = new ActionMap();
			actionMap.addMapping(this);
			
			root.setActionMap(actionMap);
			
			Renderer renderer = new LWJGLRenderer();
			ThemeManager theme = loadTheme(renderer, context);
			
			gui = new GUI(root, renderer);
			gui.setTheme("");
			gui.applyTheme(theme);
			gui.update();
			
			TWLInputForwarder inputForwarder = new TWLInputForwarder(gui, context.getInput());
			context.getInput().addPrimaryListener(inputForwarder);
		} catch (Throwable e) {
			throw new SlickException("Could not initialize TWL GUI", e);
		} finally {
			GL11.glPopAttrib();
		}
	}
	private ThemeManager loadTheme(Renderer renderer, GameContext context) throws Exception {
		String gui = context.getSettings().getProperty("gui.path", "null");
		if (gui.equals("null")) {
			throw new SlickException("no gui.path property is set!");
		}
		URL url = ResourceLoader.getResource(gui);
		assert url != null;
		return ThemeManager.createThemeManager(url, renderer);
	}
	
	// GETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public int getID() {
		return 100;
	}
	
}
