//
// $Id$
//
// Nenya library - tools for developing networked games
// Copyright (C) 2002-2011 Three Rings Design, Inc., All Rights Reserved
// http://code.google.com/p/nenya/
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation; either version 2.1 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package com.threerings.media.util {

import flash.geom.Rectangle;

/**
 * Used in conjunction with a {@link Path}.
 */
public interface Pathable
{
    /**
     * Returns the pathable's current x coordinate.
     */
    function getX () :Number;

    /**
     * Returns the pathable's current y coordinate.
     */
    function getY () :Number;

    /**
     * Updates the pathable's current coordinates.
     */
    function setLocation (x :Number, y :Number) :void;

    /**
     * Will be called by a path when it moves the pathable in the
     * specified direction. Pathables that wish to face in the direction
     * they are moving can take advantage of this callback.
     *
     * @see DirectionCodes
     */
    function setOrientation (orient :int) :void;

    /**
     * Should return the orientation of the pathable, or {@link
     * DirectionCodes#NONE} if the pathable does not support orientation.
     */
    function getOrientation () :int;

    /**
     * Called by a path when this pathable is made to start along a path.
     */
    function pathBeginning () :void;

    /**
     * Called by a path when this pathable finishes moving along its path.
     */
    function pathCompleted (timestamp :int) :void;
}
}