import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import PostUser from 'app/entities/post/post-user'
import PostUpdate from './entities/post/post-update';
import PostDeleteDialog from './entities/post/post-delete-dialog';
import PostCompleteDetails from './entities/post/post-complete-details';
import Post from 'app/entities/post/post'

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => {
  return (
    <div className="view-routes">
      <Switch>
        <ErrorBoundaryRoute path="/login" component={Login} />
        <ErrorBoundaryRoute path="/logout" component={Logout} />
        <ErrorBoundaryRoute path="/account/register" component={Register} />
        <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
        <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
        <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
        <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
        <ErrorBoundaryRoute path="/" exact component={Home} />
        <ErrorBoundaryRoute path="/post/user-posts" exact component={PostUser} />
        <ErrorBoundaryRoute path="/post/user-posts/:id/edit" exact component={PostUpdate} />
        <ErrorBoundaryRoute exact path={`/post/user-posts/:id/delete`} component={PostDeleteDialog} />
        <ErrorBoundaryRoute path="/post/new/" exact component ={PostUpdate} />
        <ErrorBoundaryRoute path="/post-details/:id" exact component ={PostCompleteDetails} />
        <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        <ErrorBoundaryRoute component={PageNotFound} />
      </Switch>
    </div>
  );
};

export default Routes;
