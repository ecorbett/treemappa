package org.gicentre.treemappa;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

//***************************************************************************************************
/** Class to represent a single node in a treemap.
 *  @author Jo Wood, giCentre.
 *  @version 3.2.1, 25th March, 2011.
 */
// ***************************************************************************************************

/* This file is part of the giCentre treeMappa library. treeMappa is free software: you can 
 * redistribute it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * treeMappa is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this
 * source code (see COPYING.LESSER included with this source code). If not, see 
 * http://www.gnu.org/licenses/.
 */

class NodePanel
{
	// --------------------------------- Object variables ----------------------------------

	private Rectangle2D footprint;
	private Point2D geoCentre;
	private String label;
	private Color colour;
	private boolean isLeaf,isDummy;
	private int level;

	// ----------------------------------- Constructors ------------------------------------

	/** Creates the visual representation of the tree map node with the
	 *  given label and footprint. A colour based on the given hue is assigned to this node.
	 *  @param label Textual label of the node.
	 *  @param footprint Spatial bounds of the node. 
	 *  @param geoCentre Geographic coordinates of node centroid. 
	 *  @param isLeaf True if node represents a leaf.
	 *  @param isDummy True if node is a blank dummy node.
	 *  @param hue Hue value of the colour of this node.
	 *  @param level Level of node (0 is root, 1 is child, 2 is grandchild etc.).
	 */
	NodePanel(TreeMapPanel tmPanel, String label, Rectangle2D footprint, Point2D geoCentre, boolean isLeaf, boolean isDummy, float hue, int level)
	{
		this(tmPanel,label,footprint,geoCentre,isLeaf,isDummy,hue, null,null,level);
	}

	/** Creates the visual representation of the tree map node with the
	 *  given label and footprint. If <code>colour</code> and <code>parentColour</code> are null,
	 *  a random colour is assigned to this node. Otherwise, if <code>colour</code> is null but a
	 *  parent colour is provided, a random perturbation of that parent colour is assigned.
	 *  @param label Textual label of the node.
	 *  @param footprint Spatial bounds of the node. 
	 *  @param geoCentre Geographic coordinates of node centroid.
	 *  @param isLeaf True if node represents a leaf.
	 *  @param isDummy True if node is a blank dummy node.
	 *  @param hue Hue value of the colour of this node if not explicitly identified by the <code>colour</code> parameter and no parent colour to inherit.
	 *  @param colour Colour for this node, or null if to be generated by the constructor.
	 *  @param parentColour Colour of parent node, or null if not assigned. 
	 *  @param level Level of node (0 is root, 1 is child, 2 is grandchild etc.).
	 */
	NodePanel(TreeMapPanel tmPanel, String label, Rectangle2D footprint, Point2D geoCentre, boolean isLeaf, boolean isDummy, float hue, Color colour, Color parentColour, int level)
	{
		this.label = label;
		this.footprint = footprint;
		this.geoCentre = geoCentre;
		this.isLeaf = isLeaf;
		this.isDummy = isDummy;
		this.level = level;

		float colourVar = tmPanel.getMutation()*127;		// Scale between 0-255

		tmPanel.setBounds(footprint.getBounds());
		if (colour == null)
		{
			if (parentColour == null)
			{
				this.colour = Color.getHSBColor(hue, 0.4f, 0.8f);
			}
			else
			{
				int newRed = (int)(parentColour.getRed() + (tmPanel.getRand().nextFloat()-0.5)*colourVar);
				if (newRed < 0)
				{
					newRed = 0;
				}
				else if (newRed >255) 
				{
					newRed = 255;
				}

				int newGreen= (int)(parentColour.getGreen() + (tmPanel.getRand().nextFloat()-0.5)*colourVar);
				if (newGreen < 0)
				{
					newGreen = 0;
				}
				else if (newGreen >255)
				{
					newGreen = 255;
				}

				int newBlue = (int)(parentColour.getBlue() + (tmPanel.getRand().nextFloat()-0.5)*colourVar);
				if (newBlue < 0)
				{
					newBlue = 0;
				}
				else if (newBlue >255)
				{
					newBlue = 255;
				}

				this.colour = new Color(newRed,newGreen,newBlue);
			}
		}
		else
		{
			this.colour = colour;
		}
	}

	/** Reports the spatial bounds of the node.
	 *  @return Spatial bounds of the node (in pixel coordinates).
	 */
	Rectangle2D getBounds()
	{
		return footprint;
	}

	/** Reports the affine transformed geospatial location of the node. 
	 *  @return Geospatial centre of the node (in pixel coordinates).
	 */
	Point2D getGeoBounds()
	{
		return geoCentre;
	}

	/** Reports the textual label of the node.
	 *  @return Text to be displayed on the node.
	 */
	String getLabel()
	{
		return label;
	}

	/** Reports the colour of the node. Non-leaf nodes have no colour.
	 *  @return Colour of leaf node, or null if not a leaf.
	 */
	Color getColour()
	{
		return colour;
	}

	/** Reports the colour of the node as an HTML Hex string in the form '#rrggbb'. Non-leaf nodes have no colour.
	 *  @return Colour of leaf node, or null if not a leaf.
	 */
	String getHexColour()
	{
		if (colour == null)
		{
			return null;
		}
		return new String("#"+Integer.toHexString((colour.getRGB() & 0xffffff) | 0x1000000).substring(1));
	}

	/** Reports the depth of this node. 0 is the root node, 1 is a child of the root, 2 is a grandchild etc.
	 *  @return Level of node.
	 */
	int getLevel() 
	{
		return level;
	}

	/** Reports whether or not this node is a leaf.
	 *  @return True if this node is a leaf.
	 */
	boolean isLeaf()
	{
		return isLeaf;
	}

	/** Reports whether or not this node is a blank dummy node used for spacing.
	 *  @return True if this is a dummy node.
	 */
	boolean isDummy()
	{
		return isDummy;
	}
}