//
// Nenya library - tools for developing networked games
// Copyright (C) 2002-2012 Three Rings Design, Inc., All Rights Reserved
// https://github.com/threerings/nenya
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

package com.threerings.media.image;

import java.util.Collection;
import java.io.Serializable;
import java.awt.Color;
import com.samskivert.util.HashIntMap;

/**
 * A repository of image recoloration information. It was called the recolor repository but the
 * re-s cancelled one another out.
 */
public class ColorPository implements Serializable
{
  /**
   * Used to store information on a class of colors. These are public to simplify the XML
   * parsing process, so pay them no mind.
   */
  public static class ClassRecord implements Serializable, Comparable<ClassRecord>
  {
    /** An integer identifier for this class. */
    public int classId;

    /** The name of the color class. */
    public String name;

    /** The source color to use when recoloring colors in this class. */
    public Color source;

    /** Data identifying the range of colors around the source color
     * that will be recolored when recoloring using this class. */
    public float[] range;

    /** The default starting legality value for this color class. See
     * {@link ColorRecord#starter}. */
    public boolean starter;

    /** The default colorId to use for recoloration in this class, or
     * 0 if there is no default defined. */
    public int defaultId;

    /** A table of target colors included in this class. */
    public HashIntMap<ColorRecord> colors = new HashIntMap<ColorRecord>();

    // from interface Comparable<ClassRecord>
    public int compareTo (ClassRecord other) {
      return name.compareTo(other.name);
    }

    /** The hsv array shared by all Colorizations created from this instance. */
    protected transient float[] _hsv;

    /** The fhsv array shared by all Colorizations created from this instance. */
    protected transient int[] _fhsv;

    protected transient ColorRecord[] _starters;

    /** Increase this value when object's serialized state is impacted
     * by a class change (modification of fields, inheritance). */
    private static final long serialVersionUID = 2;
  }

  /**
   * Used to store information on a particular color. These are public to simplify the XML
   * parsing process, so pay them no mind.
   */
  public static class ColorRecord implements Serializable, Comparable<ColorRecord>
  {
    /** The colorization class to which we belong. */
    public ClassRecord cclass;

    /** A unique colorization identifier (used in fingerprints). */
    public int colorId;

    /** The name of the target color. */
    public String name;

    /** Data indicating the offset (in HSV color space) from the
     * source color to recolor to this color. */
    public float[] offsets;

    /** Tags this color as a legal starting color or not. This is a shameful copout, placing
     * application-specific functionality into a general purpose library class. */
    public boolean starter;

    // from interface Comparable<ColorRecord>
    public int compareTo (ColorRecord other) {
      return name.compareTo(other.name);
    }

    /** Increase this value when object's serialized state is impacted
     * by a class change (modification of fields, inheritance). */
    private static final long serialVersionUID = 2;
  }

  public Collection<ClassRecord> getClasses ()
  {
    return _classes.values();
  }

  /** Our mapping from class names to class records. */
  protected HashIntMap<ClassRecord> _classes = new HashIntMap<ClassRecord>();

  /** Increase this value when object's serialized state is impacted by
   * a class change (modification of fields, inheritance). */
  private static final long serialVersionUID = 1;
}
