// <copyright file="mod_chocobo_properties.java">
// Copyright (c) 2012 All Right Reserved, http://chococraft.arno-saxena.de/
//
// THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY 
// KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
// PARTICULAR PURPOSE.
//
// </copyright>
// <author>Arno Saxena</author>
// <author>EddieV (initial source)</author>
// <email>al-s@gmx.de</email>
// <date>2012-05-09</date>
// <summary>basic chococraft properties</summary>

package net.minecraft.src;

import java.util.*;

public class mod_chocobo_properties extends Properties
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public mod_chocobo_properties()
    {
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized Enumeration keys()
    {
        Enumeration enumeration = super.keys();
        Vector vector = new Vector();
        for (; enumeration.hasMoreElements(); vector.add(enumeration.nextElement())) { }
        Collections.sort(vector);
        return vector.elements();
    }
}
