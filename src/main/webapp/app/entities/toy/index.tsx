import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Toy from './toy';
import ToyDetail from './toy-detail';
import ToyUpdate from './toy-update';
import ToyDeleteDialog from './toy-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ToyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ToyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ToyDetail} />
      <ErrorBoundaryRoute path={match.url} component={Toy} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ToyDeleteDialog} />
  </>
);

export default Routes;
