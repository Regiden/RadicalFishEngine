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
package de.radicalfish.debug;
import com.badlogic.gdx.utils.Array;
import de.radicalfish.Game;
import de.radicalfish.GameContainer;

/**
 * This callback can be used under the desktop implementation of a game to fit in the RadicalFishDebug extension. You can
 * set the callback in the {@link GameContainer#setDebugCallBack(DebugCallback)} method. Note that this will only work
 * under desktop implementations.
 * <p>
 * The callback extends the {@link Game} interface to leave you in charge of rendering and updating. The methods will
 * always be called after the {@link Game}-methods have been called.
 * 
 * @author Stefan Lange
 * @version 1.0.0
 * @since 14.08.2012
 */
public interface DebugCallback extends Game {
	
	/**
	 * Adds a Performance listener to the debug mechanism.
	 * 
	 * @param listener
	 */
	public void addPerformanceListener(PerformanceListener listener);
	/**
	 * Removes the <code>listener</code> from teh debuf mechanism.
	 */
	public void removePerformanceListener(PerformanceListener listener);
	
	/**
	 * @return a list containing all the performance listener.
	 */
	public Array<PerformanceListener> getPerformanceListener();
	
	/**
	 * Method to display or hide the debug.
	 */
	public void setVisible(boolean visible);
	/**
	 * @return true if the debug is currently visible
	 */
	public boolean isVisible();
	
}
