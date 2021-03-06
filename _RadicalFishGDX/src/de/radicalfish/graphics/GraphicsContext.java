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
package de.radicalfish.graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * An implementation of the {@link OrthographicCamera} which makes the origin the top left corner. It also supports
 * different scale values for <code>x</code>, and <code>y</code>. It's used to supply a context like GL10 does for
 * scaling or translating the whole context.
 * 
 * TODO add rotation
 * TODO add offsets for viewport origin
 * 
 * @author Stefan Lange
 * @version 1.0.0
 * @since 09.08.2012
 */
public class GraphicsContext extends OrthographicCamera {
	
	private static final Vector3 temp = new Vector3();
	private static float y, height;
	
	private float scaleX = 1.0f, scaleY = 1.0f;
	private boolean ydown = true;
	
	public GraphicsContext(int viewportWidth, int viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		this.near = 0;
	}
	
	// METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * Scales by <code>scale</code> on both axis.
	 */
	public void scale(float scale) {
		scaleX *= scale;
		scaleY *= scale;
	}
	/**
	 * Scales <code>x</code> and <code>y</code> on the x axis and y axis.
	 */
	public void scale(float x, float y) {
		scaleX *= x;
		scaleY *= y;
	}
	
	// OVERRIDE
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	public void update() {
		checkScale();
		y = ydown ? viewportHeight / scaleY : 0;
		height = ydown ? 0 : viewportHeight / scaleY;
		projection.setToOrtho(0, viewportWidth / scaleX, y, height, Math.abs(near), Math.abs(far));
		view.setToLookAt(position, temp.set(position).add(direction), up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
	}
	public void setToOrtho(float viewportWidth, float viewportHeight) {
		this.viewportWidth = viewportWidth;
		this.viewportHeight = viewportHeight;
		update();
	}
	
	// INTERN
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	private void checkScale() {
		if (scaleX < 0) {
			scaleX = 0f;
		}
		if (scaleY < 0) {
			scaleY = 0f;
		}
	}
	
	// SETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * Sets the y direction of the viewport. true stand for y-down while false stand for y-up.
	 */
	public void setYDown(boolean ydown) {
		this.ydown = ydown;
		update();
	}
	
	/**
	 * sets the scale on both axis.
	 */
	public void setScale(float scale) {
		scaleX = scale;
		scaleY = scale;
	}
	/**
	 * Sets <code>x</code> and <code>y</code> to the x scale and y scale.
	 */
	public void setScale(float x, float y) {
		scaleX = x;
		scaleY = y;
	}
	
	// GETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * @return true if the viewport direction is y-down, false otherwise.
	 */
	public boolean isYDown() {
		return ydown;
	}
	
	public float getScaleX() {
		return scaleX;
	}
	public float getScaleY() {
		return scaleY;
	}
}
