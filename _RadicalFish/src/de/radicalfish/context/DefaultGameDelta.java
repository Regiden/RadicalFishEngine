/*
 * Copyright (c) 2011, Stefan Lange
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
package de.radicalfish.context;
import org.newdawn.slick.SlickException;
import de.radicalfish.extern.Easing;
import de.radicalfish.extern.SimpleFX;
import de.radicalfish.world.World;

/**
 * An Implementation of the {@link GameDelta} interface which supports easing to a target speed.
 * 
 * @author Stefan Lange
 * @version 1.0.0
 * @since 15.04.2012
 */
public class DefaultGameDelta implements GameDelta {
	
	private SimpleFX tween;
	
	private int normalDelta = 16;
	private float delta = 16f;
	
	public DefaultGameDelta() {
		tween = new SimpleFX(1.0f, 1.0f, 0, Easing.LINEAR);
	}
	
	// METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public void update(GameContext context, World world, int delta) throws SlickException {
		if (!tween.finished()) {
			tween.update(delta);
			if (tween.finished()) {
				tween.setValue(tween.getEnd());
			}
		}
		normalDelta = delta;
		this.delta = normalDelta * tween.getValue();
	}
	public void slowDown(float target, int time, Easing easing) throws SlickException {
		if (easing == null) {
			setSpeed(target);
		} else {
			if (target >= 0.0 && target <= 1.0) {
				if (target == tween.getValue()) {
					return;
				}
				tween.setDuration(time);
				tween.setEnd(target);
				tween.setStart(tween.getValue());
				tween.setEasing(easing);
				tween.restart();
			} else {
				throw new SlickException("target value is out of bounds: " + target + " (must be 0 - 1)");
			}
		}
		
	}
	
	// GETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public float getSpeed() {
		return tween.getValue();
	}
	public float getTargetSpeed() {
		return tween.getEnd();
	}
	public float getDelta() {
		return delta;
	}
	public int getNormalDelta() {
		return normalDelta;
	}
	public boolean reachedTarget() {
		return tween.finished();
	}
	
	// SETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public void setSpeed(float speed) throws SlickException {
		if (speed >= 0.0 && speed <= 1.0) {
			tween.finish();
		} else {
			throw new SlickException("speed value is out of bounds: " + speed + " (must be 0 - 1)");
		}
	}
	
}
