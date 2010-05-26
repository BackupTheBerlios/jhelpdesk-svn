/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright: (C) 2006 jHelpdesk Developers Team
 */
package de.berlios.jhelpdesk.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jjhop
 */
public class StampUtils {

    public static String craeteStampFromObjects(Object first, Object... rest) {
        StringBuilder sb = new StringBuilder(Thread.currentThread().getName());
        sb.append(Thread.currentThread().getId());
        sb.append(first);
        for (Object o : rest) {
            sb.append(o);
        }
        sb.append(System.nanoTime());
        return DigestUtils.shaHex(sb.toString());
    }
}

        