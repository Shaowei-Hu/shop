import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './toy.reducer';
import { IToy } from 'app/shared/model/toy.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IToyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IToyUpdateState {
  isNew: boolean;
}

export class ToyUpdate extends React.Component<IToyUpdateProps, IToyUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.purchaseDate = new Date(values.purchaseDate);

    if (errors.length === 0) {
      const { toyEntity } = this.props;
      const entity = {
        ...toyEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/toy');
  };

  render() {
    const { toyEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="shopApp.toy.home.createOrEditLabel">
              <Translate contentKey="shopApp.toy.home.createOrEditLabel">Create or edit a Toy</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : toyEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="toy-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="recommendedAgeLabel" for="recommendedAge">
                    <Translate contentKey="shopApp.toy.recommendedAge">Recommended Age</Translate>
                  </Label>
                  <AvField id="toy-recommendedAge" type="text" name="recommendedAge" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel" for="gender">
                    <Translate contentKey="shopApp.toy.gender">Gender</Translate>
                  </Label>
                  <AvField id="toy-gender" type="text" name="gender" />
                </AvGroup>
                <AvGroup>
                  <Label id="purchaseDateLabel" for="purchaseDate">
                    <Translate contentKey="shopApp.toy.purchaseDate">Purchase Date</Translate>
                  </Label>
                  <AvInput
                    id="toy-purchaseDate"
                    type="datetime-local"
                    className="form-control"
                    name="purchaseDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.toyEntity.purchaseDate)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/toy" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  toyEntity: storeState.toy.entity,
  loading: storeState.toy.loading,
  updating: storeState.toy.updating,
  updateSuccess: storeState.toy.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ToyUpdate);
