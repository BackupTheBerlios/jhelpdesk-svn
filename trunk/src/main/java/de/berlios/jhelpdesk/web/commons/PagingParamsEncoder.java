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

import org.displaytag.util.ParamEncoder;
import static org.displaytag.tags.TableTagParameters.PARAMETER_PAGE;
import static org.displaytag.tags.TableTagParameters.PARAMETER_SORT;
import static org.displaytag.tags.TableTagParameters.PARAMETER_ORDER;

public class PagingParamsEncoder {

    private final static int DEFAULT_OFFSET = 0;
    private HttpServletRequest request;
    private ParamEncoder paramEncoder;
    private String columnToSort;
    private int pageSize;

    public PagingParamsEncoder(String tableName, String defaultColumnToSort,
            HttpServletRequest request, int pageSize) {
        this.columnToSort = defaultColumnToSort;
        this.request = request;
        this.pageSize = pageSize;
        this.paramEncoder = new ParamEncoder(tableName);
    }

    public int getOffset() {
        int offset = DEFAULT_OFFSET;
        String liczba2 = this.paramEncoder.encodeParameterName(PARAMETER_PAGE);
        if ((request.getParameter(liczba2) != null)
                && (request.getParameter(liczba2).length() > 0)) {
            offset = (Integer.parseInt(request.getParameter(liczba2)) - 1) * pageSize;
        }
        return offset;
    }

    public String getColumnToSort() {
        String myCompanyColumnSorting = this.paramEncoder.encodeParameterName(PARAMETER_SORT);
        if ((request.getParameter(myCompanyColumnSorting) != null)
                && (request.getParameter(myCompanyColumnSorting).length() > 0)) {
            columnToSort = request.getParameter(myCompanyColumnSorting);
        }
        return columnToSort;
    }

    public boolean getOrder() {
        String mySortOrder = null;
        String myCompanySortOrder = this.paramEncoder.encodeParameterName(PARAMETER_ORDER);
        if ((request.getParameter(myCompanySortOrder) != null)
                && (request.getParameter(myCompanySortOrder).length() > 0)) {
            mySortOrder = request.getParameter(myCompanySortOrder);
        }
        return ((mySortOrder != null) && mySortOrder.equalsIgnoreCase("1")) ? false : true;
    }
}
