import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dimnesion.reducer';
import { IDimnesion } from 'app/shared/model/dimnesion.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDimnesionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DimnesionDetail extends React.Component<IDimnesionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dimnesionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="shopApp.dimnesion.detail.title">Dimnesion</Translate> [<b>{dimnesionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="length">
                <Translate contentKey="shopApp.dimnesion.length">Length</Translate>
              </span>
            </dt>
            <dd>{dimnesionEntity.length}</dd>
            <dt>
              <span id="width">
                <Translate contentKey="shopApp.dimnesion.width">Width</Translate>
              </span>
            </dt>
            <dd>{dimnesionEntity.width}</dd>
            <dt>
              <span id="height">
                <Translate contentKey="shopApp.dimnesion.height">Height</Translate>
              </span>
            </dt>
            <dd>{dimnesionEntity.height}</dd>
            <dt>
              <span id="weight">
                <Translate contentKey="shopApp.dimnesion.weight">Weight</Translate>
              </span>
            </dt>
            <dd>{dimnesionEntity.weight}</dd>
          </dl>
          <Button tag={Link} to="/entity/dimnesion" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/dimnesion/${dimnesionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dimnesion }: IRootState) => ({
  dimnesionEntity: dimnesion.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DimnesionDetail);
