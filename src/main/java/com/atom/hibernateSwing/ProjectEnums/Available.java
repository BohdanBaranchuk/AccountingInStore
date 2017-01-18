package com.atom.hibernateSwing.ProjectEnums;

import com.atom.hibernateSwing.model.*;

/**
 * For each product select the status. The every new product has the status "Yes". If the product is sold the status is
 * changed from <i><b>"Yes"</b></i> to <i><b>"No"</b></i>
 * <hr>
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see Products
 */
public enum Available {
    /**
     * If product is available in it is presents in the store
     */
    Yes,
    /**
     * If product is unavailable and it isn't in the store
     */
    No
}
