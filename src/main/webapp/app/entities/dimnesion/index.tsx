import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dimnesion from './dimnesion';
import DimnesionDetail from './dimnesion-detail';
import DimnesionUpdate from './dimnesion-update';
import DimnesionDeleteDialog from './dimnesion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DimnesionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DimnesionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DimnesionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dimnesion} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DimnesionDeleteDialog} />
  </>
);

export default Routes;
