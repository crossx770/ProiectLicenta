import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Post from './post';
import PostDetail from './post-detail';
import PostUpdate from './post-update';
import PostDeleteDialog from './post-delete-dialog';
import PostUser from './post-user'

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PostUpdate} />
      <ErrorBoundaryRoute path={`${match.url}/user-posts`} component={PostUser} />
      <ErrorBoundaryRoute path={`${match.url}/user-posts/:id/edit`} component={PostUser} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PostDetail} />
      <ErrorBoundaryRoute path={match.url} component={Post} />
      
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/user-posts/:id/delete`} component={PostDeleteDialog} />
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PostDeleteDialog} />
  </>
);

export default Routes;
