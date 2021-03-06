/*
 * Copyright (c) 2012, Stefan Lange
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Stefan Lange nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package de.radicalfish.tests;
import de.radicalfish.GameContainer;
import de.radicalfish.context.GameContext;
import de.radicalfish.context.GameDelta;
import de.radicalfish.font.Font;
import de.radicalfish.graphics.Graphics;
import de.radicalfish.state.StateBasedGame;
import de.radicalfish.tests.states.SimpleState;
import de.radicalfish.tests.utils.RadicalFishTest;
import de.radicalfish.util.RadicalFishException;
import de.radicalfish.world.GameWorld;

public class StatesTest extends StateBasedGame implements RadicalFishTest {
	
	// INIT
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public GameContext initGameContext(GameContainer container) throws RadicalFishException {
		return null;
	}
	public GameWorld initWorld(GameContainer container) throws RadicalFishException {
		return null;
	}
	public void initStates(GameContext context) throws RadicalFishException {
		context.getContainer().getGraphics().setClearColor(0.7f, 0.1f, 0.2f);
		addState(new SimpleState(0));
		addState(new SimpleState(1));
	}
	
	// GAME METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	protected void preUpdate(GameContext context, GameWorld world, GameDelta delta) throws RadicalFishException {
	}
	protected void postUpdate(GameContext context, GameWorld world, GameDelta delta) throws RadicalFishException {
	}
	protected void preRender(GameContext context, GameWorld world, Graphics g) throws RadicalFishException {
		
	}
	protected void postRender(GameContext context, GameWorld world, Graphics g) throws RadicalFishException {
		Font font = context.getFont();
		
		g.getSpriteBatch().begin();
		font.draw(g.getSpriteBatch(), "Press Enter to move forward to the next state", 5, 35);
		g.getSpriteBatch().end();
	}
	
	
	// OVERRIDE FOR TESTS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public void initContainer(GameContainer container) {
		container.setSmoothDeltas(true);
	}
	public String getTitle() {
		return "States Test";
	}
	public int getWidth() {
		return 800;
	}
	public int getHeight() {
		return 600;
	}
	public boolean needsGL20() {
		return true;
	}
	
}
