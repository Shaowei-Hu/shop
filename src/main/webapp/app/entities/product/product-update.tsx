import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProductUpdateState {
  isNew: boolean;
}

export class ProductUpdate extends React.Component<IProductUpdateProps, IProductUpdateState> {
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
    values.releaseDate = new Date(values.releaseDate);
    values.creationDate = new Date(values.creationDate);
    values.modificationDate = new Date(values.modificationDate);

    if (errors.length === 0) {
      const { productEntity } = this.props;
      const entity = {
        ...productEntity,
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
    this.props.history.push('/entity/product');
  };

  render() {
    const { productEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="shopApp.product.home.createOrEditLabel">
              <Translate contentKey="shopApp.product.home.createOrEditLabel">Create or edit a Product</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="product-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="brandLabel" for="brand">
                    <Translate contentKey="shopApp.product.brand">Brand</Translate>
                  </Label>
                  <AvField id="product-brand" type="text" name="brand" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="shopApp.product.name">Name</Translate>
                  </Label>
                  <AvField id="product-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="releaseDateLabel" for="releaseDate">
                    <Translate contentKey="shopApp.product.releaseDate">Release Date</Translate>
                  </Label>
                  <AvInput
                    id="product-releaseDate"
                    type="datetime-local"
                    className="form-control"
                    name="releaseDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.productEntity.releaseDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="commentLabel" for="comment">
                    <Translate contentKey="shopApp.product.comment">Comment</Translate>
                  </Label>
                  <AvField id="product-comment" type="text" name="comment" />
                </AvGroup>
                <AvGroup>
                  <Label id="manufactureOriginLabel" for="manufactureOrigin">
                    <Translate contentKey="shopApp.product.manufactureOrigin">Manufacture Origin</Translate>
                  </Label>
                  <AvField id="product-manufactureOrigin" type="text" name="manufactureOrigin" />
                </AvGroup>
                <AvGroup>
                  <Label id="meterialsLabel" for="meterials">
                    <Translate contentKey="shopApp.product.meterials">Meterials</Translate>
                  </Label>
                  <AvField id="product-meterials" type="text" name="meterials" />
                </AvGroup>
                <AvGroup>
                  <Label id="externalUrlLabel" for="externalUrl">
                    <Translate contentKey="shopApp.product.externalUrl">External Url</Translate>
                  </Label>
                  <AvField id="product-externalUrl" type="text" name="externalUrl" />
                </AvGroup>
                <AvGroup>
                  <Label id="originalPriceLabel" for="originalPrice">
                    <Translate contentKey="shopApp.product.originalPrice">Original Price</Translate>
                  </Label>
                  <AvField id="product-originalPrice" type="string" className="form-control" name="originalPrice" />
                </AvGroup>
                <AvGroup>
                  <Label id="actualPriceLabel" for="actualPrice">
                    <Translate contentKey="shopApp.product.actualPrice">Actual Price</Translate>
                  </Label>
                  <AvField id="product-actualPrice" type="string" className="form-control" name="actualPrice" />
                </AvGroup>
                <AvGroup>
                  <Label id="garantieLabel" check>
                    <AvInput id="product-garantie" type="checkbox" className="form-control" name="garantie" />
                    <Translate contentKey="shopApp.product.garantie">Garantie</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="photoLabel" for="photo">
                    <Translate contentKey="shopApp.product.photo">Photo</Translate>
                  </Label>
                  <AvField id="product-photo" type="text" name="photo" />
                </AvGroup>
                <AvGroup>
                  <Label id="stateLabel" for="state">
                    <Translate contentKey="shopApp.product.state">State</Translate>
                  </Label>
                  <AvField id="product-state" type="text" name="state" />
                </AvGroup>
                <AvGroup>
                  <Label id="creationDateLabel" for="creationDate">
                    <Translate contentKey="shopApp.product.creationDate">Creation Date</Translate>
                  </Label>
                  <AvInput
                    id="product-creationDate"
                    type="datetime-local"
                    className="form-control"
                    name="creationDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.productEntity.creationDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="modificationDateLabel" for="modificationDate">
                    <Translate contentKey="shopApp.product.modificationDate">Modification Date</Translate>
                  </Label>
                  <AvInput
                    id="product-modificationDate"
                    type="datetime-local"
                    className="form-control"
                    name="modificationDate"
                    value={isNew ? null : convertDateTimeFromServer(this.props.productEntity.modificationDate)}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/product" replace color="info">
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
  productEntity: storeState.product.entity,
  loading: storeState.product.loading,
  updating: storeState.product.updating,
  updateSuccess: storeState.product.updateSuccess
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
)(ProductUpdate);
