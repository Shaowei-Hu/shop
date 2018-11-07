import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductDetail extends React.Component<IProductDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="shopApp.product.detail.title">Product</Translate> [<b>{productEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="brand">
                <Translate contentKey="shopApp.product.brand">Brand</Translate>
              </span>
            </dt>
            <dd>{productEntity.brand}</dd>
            <dt>
              <span id="name">
                <Translate contentKey="shopApp.product.name">Name</Translate>
              </span>
            </dt>
            <dd>{productEntity.name}</dd>
            <dt>
              <span id="releaseDate">
                <Translate contentKey="shopApp.product.releaseDate">Release Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={productEntity.releaseDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="comment">
                <Translate contentKey="shopApp.product.comment">Comment</Translate>
              </span>
            </dt>
            <dd>{productEntity.comment}</dd>
            <dt>
              <span id="manufactureOrigin">
                <Translate contentKey="shopApp.product.manufactureOrigin">Manufacture Origin</Translate>
              </span>
            </dt>
            <dd>{productEntity.manufactureOrigin}</dd>
            <dt>
              <span id="meterials">
                <Translate contentKey="shopApp.product.meterials">Meterials</Translate>
              </span>
            </dt>
            <dd>{productEntity.meterials}</dd>
            <dt>
              <span id="externalUrl">
                <Translate contentKey="shopApp.product.externalUrl">External Url</Translate>
              </span>
            </dt>
            <dd>{productEntity.externalUrl}</dd>
            <dt>
              <span id="originalPrice">
                <Translate contentKey="shopApp.product.originalPrice">Original Price</Translate>
              </span>
            </dt>
            <dd>{productEntity.originalPrice}</dd>
            <dt>
              <span id="actualPrice">
                <Translate contentKey="shopApp.product.actualPrice">Actual Price</Translate>
              </span>
            </dt>
            <dd>{productEntity.actualPrice}</dd>
            <dt>
              <span id="garantie">
                <Translate contentKey="shopApp.product.garantie">Garantie</Translate>
              </span>
            </dt>
            <dd>{productEntity.garantie ? 'true' : 'false'}</dd>
            <dt>
              <span id="photo">
                <Translate contentKey="shopApp.product.photo">Photo</Translate>
              </span>
            </dt>
            <dd>{productEntity.photo}</dd>
            <dt>
              <span id="state">
                <Translate contentKey="shopApp.product.state">State</Translate>
              </span>
            </dt>
            <dd>{productEntity.state}</dd>
            <dt>
              <span id="creationDate">
                <Translate contentKey="shopApp.product.creationDate">Creation Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={productEntity.creationDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modificationDate">
                <Translate contentKey="shopApp.product.modificationDate">Modification Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={productEntity.modificationDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/product" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/product/${productEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ product }: IRootState) => ({
  productEntity: product.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductDetail);
