import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

const entityMenuItems = (
  <>
    <MenuItem icon="location-arrow" to="/judet">
      Judets
    </MenuItem>
    <MenuItem icon="thumbtack" to="/city">
      Cities
    </MenuItem>
    <MenuItem icon="toolbox" to="/category">
      Category
    </MenuItem>
    <MenuItem icon="tools" to="/sub-category">
      Subcategory
    </MenuItem>
  </>
);

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    {entityMenuItems}
  </NavDropdown>
);
