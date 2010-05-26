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
package de.berlios.jhelpdesk.web.commons;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class PagingParamsEncoder {

    private final static int DEFAULT_OFFSET = 0;
    private String tableName;
    private String columnToSort;
    private HttpServletRequest request;
    private int pageSize;

    public PagingParamsEncoder(String tableName, String defaultColumnToSort,
            HttpServletRequest request, int pageSize) {
        this.tableName = tableName;
        this.columnToSort = defaultColumnToSort;
        this.request = request;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        int offset = DEFAULT_OFFSET;
        String liczba2 = new ParamEncoder(tableName).encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        if ((request.getParameter(liczba2) != null)
                && (request.getParameter(liczba2).length() > 0)) {
            offset = (Integer.parseInt(request.getParameter(liczba2)) - 1) * pageSize;
        }
        return offset;
    }

    public String getColumnToSort() {
        String myCompanyColumnSorting = new ParamEncoder(tableName).encodeParameterName(TableTagParameters.PARAMETER_SORT);
        if ((request.getParameter(myCompanyColumnSorting) != null)
                && (request.getParameter(myCompanyColumnSorting).length() > 0)) {
            columnToSort = request.getParameter(myCompanyColumnSorting);
        }
        return columnToSort;
    }

    public boolean getOrder() {
        String mySortOrder = null;
        String myCompanySortOrder = new ParamEncoder(tableName).encodeParameterName(TableTagParameters.PARAMETER_ORDER);
        if ((request.getParameter(myCompanySortOrder) != null)
                && (request.getParameter(myCompanySortOrder).length() > 0)) {
            mySortOrder = request.getParameter(myCompanySortOrder);
        }
        return ((mySortOrder != null) && mySortOrder.equalsIgnoreCase("1")) ? false : true;
    }
}
