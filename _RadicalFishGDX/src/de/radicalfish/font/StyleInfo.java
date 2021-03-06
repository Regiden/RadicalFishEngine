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
package de.radicalfish.font;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.C4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.U4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.V4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.X4;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y1;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y2;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y3;
import static com.badlogic.gdx.graphics.g2d.SpriteBatch.Y4;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.NumberUtils;
import de.radicalfish.GameContainer;
import de.radicalfish.font.commands.StyleCommand;

/**
 * This class holds the information about position, origin, scaling, rotation and color for a single character. It will
 * be used in the in the {@link StyleCommand#execute(GameContainer, StyleInfo)} method. The origin should be auto set to
 * the center of the current {@link TextureRegion}. That said this also means the size will be set to the size of the
 * {@link TextureRegion}.
 * <p>
 * In the {@link StyleCommand#execute(GameContainer, StyleInfo)} method you add new values to any of the fields which
 * will be used to draw the {@link TextureRegion}.
 * <p>
 * You can use the class to draw a texture too. just set the values like you want and call
 * {@link StyleInfo#createVertices(TextureRegion, float, float)} to get the vertices (eg. for a {@link SpriteBatch}).
 * 
 * @author Stefan Lange
 * @version 1.0.0
 * @since 03.09.2012
 */
public class StyleInfo {
	
	/** the vertices used do draw the region in the {@link SpriteBatch}. */
	public final float[] verts = new float[20];
	
	/** The position to add to the current character position */
	public final Vector2 offset = new Vector2(0, 0);
	/** The size of the region (will be auto set for every character). */
	public final Vector2 size = new Vector2(0, 0);
	/** The origin of the region (will be auto set for every character to the center of the region). */
	public final Vector2 origin = new Vector2(0, 0);
	/** The scale of the region (default is 1, 1). */
	public final Vector2 scale = new Vector2(1, 1);
	
	/** The top left color. */
	public Color colorTopLeft = Color.WHITE.cpy();
	/** The top right color. */
	public Color colorTopRight = Color.WHITE.cpy();
	/** The bottom left color. */
	public Color colorBottomLeft = Color.WHITE.cpy();
	/** The bottom right color. */
	public Color colorBottomRight = Color.WHITE.cpy();
	
	private static Color temp = new Color(Color.WHITE);
	
	/** The rotation of the character. */
	public float rotation = 0.0f;
	
	// METHODS
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * Calculates the position of the current character. the uv coordinates should be taken from the region of the base
	 * texture.
	 * 
	 * @param region
	 *            the region we draw
	 * @param x
	 *            the position to draw the character to
	 * @param y
	 *            the position to draw the character to
	 * @return the array containing the vertices.
	 */
	public float[] createVertices(float u, float v, float u2, float v2, float x, float y) {
		// bottom left and top right corner points relative to origin
		final float worldOriginX = (x + offset.x) + origin.x;
		final float worldOriginY = (y + offset.y) + origin.y;
		float fx = -origin.x;
		float fy = -origin.y;
		float fx2 = size.x - origin.x;
		float fy2 = size.y - origin.y;
		
		// scale
		if (scale.x != 1 || scale.y != 1) {
			fx *= scale.x;
			fy *= scale.y;
			fx2 *= scale.x;
			fy2 *= scale.y;
		}
		
		// construct corner points, start from top left and go counter clockwise
		final float p1x = fx;
		final float p1y = fy;
		final float p2x = fx;
		final float p2y = fy2;
		final float p3x = fx2;
		final float p3y = fy2;
		final float p4x = fx2;
		final float p4y = fy;
		
		float x1;
		float y1;
		float x2;
		float y2;
		float x3;
		float y3;
		float x4;
		float y4;
		
		// rotate
		if (rotation != 0) {
			final float cos = MathUtils.cosDeg(rotation);
			final float sin = MathUtils.sinDeg(rotation);
			
			x1 = cos * p1x - sin * p1y;
			y1 = sin * p1x + cos * p1y;
			
			x2 = cos * p2x - sin * p2y;
			y2 = sin * p2x + cos * p2y;
			
			x3 = cos * p3x - sin * p3y;
			y3 = sin * p3x + cos * p3y;
			
			x4 = x1 + (x3 - x2);
			y4 = y3 - (y2 - y1);
		} else {
			x1 = p1x;
			y1 = p1y;
			
			x2 = p2x;
			y2 = p2y;
			
			x3 = p3x;
			y3 = p3y;
			
			x4 = p4x;
			y4 = p4y;
		}
		
		x1 += worldOriginX;
		y1 += worldOriginY;
		x2 += worldOriginX;
		y2 += worldOriginY;
		x3 += worldOriginX;
		y3 += worldOriginY;
		x4 += worldOriginX;
		y4 += worldOriginY;
		
		verts[X1] = x1;
		verts[Y1] = y1;
		verts[C1] = colorTopLeft.toFloatBits();
		verts[U1] = u;
		verts[V1] = v;
		
		verts[X2] = x2;
		verts[Y2] = y2;
		verts[C2] = colorBottomLeft.toFloatBits();
		verts[U2] = u;
		verts[V2] = v2;
		
		verts[X3] = x3;
		verts[Y3] = y3;
		verts[C3] = colorBottomRight.toFloatBits();
		verts[U3] = u2;
		verts[V3] = v2;
		
		verts[X4] = x4;
		verts[Y4] = y4;
		verts[C4] = colorTopRight.toFloatBits();
		verts[U4] = u2;
		verts[V4] = v;
		
		return verts;
	}
	/**
	 * Calculates the position of the current character.
	 * 
	 * @param region
	 *            the region we draw
	 * @param x
	 *            the position to draw the character to
	 * @param y
	 *            the position to draw the character to
	 * @return the array containing the vertices.
	 */
	public float[] createVertices(TextureRegion region, float x, float y) {
		return createVertices(region.getU(), region.getV2(), region.getU2(), region.getV(), x, y);
	}
	
	/**
	 * Resets the complete {@link StyleInfo} to the default values.
	 */
	public void reset() {
		resetGeom();
		resetColor();
	}
	/**
	 * Resets the geometry values to the default. Does not reset the color!
	 */
	public void resetGeom() {
		rotation = 0.0f;
		
		offset.set(0, 0);
		size.set(0, 0);
		origin.set(0, 0);
		scale.set(1, 1);
	}
	/**
	 * Resets the color values to the default (which is white).
	 */
	public void resetColor() {
		colorTopLeft.set(1, 1, 1, 1);
		colorTopRight.set(1, 1, 1, 1);
		colorBottomLeft.set(1, 1, 1, 1);
		colorBottomRight.set(1, 1, 1, 1);
	}
	
	// INTERN
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	private void setColor(Color color, Color other, boolean alpha) {
		color.r = other.r;
		color.g = other.g;
		color.b = other.b;
		if (alpha) {
			color.a = other.a;
		}
	}
	
	// SETTER
	// ŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻŻ
	/**
	 * Sets the color to all corners.
	 */
	public void setColor(Color color) {
		setColor(color, true);
	}
	/**
	 * Sets the color to all corners.
	 */
	public void setColor(Color color, boolean alpha) {
		setColor(colorTopLeft, color, alpha);
		setColor(colorTopRight, color, alpha);
		setColor(colorBottomLeft, color, alpha);
		setColor(colorBottomRight, color, alpha);
	}
	/**
	 * Sets the color to all corners.
	 */
	public void setColor(float color) {
		int intBits = NumberUtils.floatToIntColor(color);
		temp.r = (intBits & 0xff) / 255f;
		temp.g = ((intBits >>> 8) & 0xff) / 255f;
		temp.b = ((intBits >>> 16) & 0xff) / 255f;
		temp.a = ((intBits >>> 24) & 0xff) / 255f;
		setColor(colorTopLeft, temp, true);
		setColor(colorTopRight, temp, true);
		setColor(colorBottomLeft, temp, true);
		setColor(colorBottomRight, temp, true);
	}
	/**
	 * Sets the color to all corners.
	 * 
	 * @param alpha1
	 *            true if the alpha value of the color is 1.0 to work around the packed color problem.
	 */
	public void setColor(float color, boolean alpha1) {
		int intBits = NumberUtils.floatToIntColor(color);
		temp.r = (intBits & 0xff) / 255f;
		temp.g = ((intBits >>> 8) & 0xff) / 255f;
		temp.b = ((intBits >>> 16) & 0xff) / 255f;
		temp.a = alpha1 ? 1.0f : ((intBits >>> 24) & 0xff) / 255f;
		colorTopLeft.set(temp);
		colorTopRight.set(temp);
		colorBottomLeft.set(temp);
		colorBottomRight.set(temp);
	}
	
	/**
	 * Sets the color for a gradient from left to right.
	 */
	public void setHorizontalGradient(Color left, Color right) {
		colorTopLeft.set(left);
		colorTopRight.set(right);
		colorBottomLeft.set(left);
		colorBottomRight.set(right);
	}
	/**
	 * Sets the color for a gradient from top to bottom.
	 */
	public void setVerticalGradient(Color top, Color bottom) {
		colorTopLeft.set(top);
		colorTopRight.set(top);
		colorBottomLeft.set(bottom);
		colorBottomRight.set(bottom);
	}
	
}
