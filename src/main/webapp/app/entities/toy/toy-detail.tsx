import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './toy.reducer';
import { IToy } from 'app/shared/model/toy.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IToyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ToyDetail extends React.Component<IToyDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { toyEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="shopApp.toy.detail.title">Toy</Translate> [<b>{toyEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="recommendedAge">
                <Translate contentKey="shopApp.toy.recommendedAge">Recommended Age</Translate>
              </span>
            </dt>
            <dd>{toyEntity.recommendedAge}</dd>
            <dt>
              <span id="gender">
                <Translate contentKey="shopApp.toy.gender">Gender</Translate>
              </span>
            </dt>
            <dd>{toyEntity.gender}</dd>
            <dt>
              <span id="purchaseDate">
                <Translate contentKey="shopApp.toy.purchaseDate">Purchase Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={toyEntity.purchaseDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/toy" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/toy/${toyEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ toy }: IRootState) => ({
  toyEntity: toy.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ToyDetail);
