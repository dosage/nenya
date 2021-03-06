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

package com.threerings.cast;

/**
 * Actions are referenced by name and this interface defines constants for
 * standard actions and action suffixes used by the shadow and cropping
 * support.
 */
public interface StandardActions
{
    /** The name of the standard standing action. */
    public static final String STANDING = "standing";

    /** The name of the standard walking action. */
    public static final String WALKING = "walking";

    /** A special action sub-type for shadow imagery. */
    public static final String SHADOW_TYPE = "shadow";

    /** A special action sub-type for crop imagery. */
    public static final String CROP_TYPE = "crop";
}
