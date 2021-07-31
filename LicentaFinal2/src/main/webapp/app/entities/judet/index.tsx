import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Judet from './judet';
import JudetDetail from './judet-detail';
import JudetUpdate from './judet-update';
import JudetDeleteDialog from './judet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={JudetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={JudetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={JudetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Judet} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={JudetDeleteDialog} />
  </>
);

export default Routes;
