import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './post.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PostDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">Post</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{postEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{postEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{postEntity.description}</dd>
          <dt>
            <span id="is_promoted">Is Promoted</span>
          </dt>
          <dd>{postEntity.is_promoted ? 'true' : 'false'}</dd>
          <dt>
            <span id="created_at">Created At</span>
          </dt>
          <dd>{postEntity.created_at ? <TextFormat value={postEntity.created_at} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{postEntity.price}</dd>
          <dt>User Post</dt>
          <dd>{postEntity.user_post ? postEntity.user_post.login : ''}</dd>
          <dt>Judet Post</dt>
          <dd>{postEntity.judet_post ? postEntity.judet_post.name : ''}</dd>
          <dt>City Post</dt>
          <dd>{postEntity.city_post ? postEntity.city_post.name : ''}</dd>
          <dt>Category Post</dt>
          <dd>{postEntity.category_post ? postEntity.category_post.name : ''}</dd>
          <dt>Sub Category Post</dt>
          <dd>{postEntity.subCategory_post ? postEntity.subCategory_post.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PostDetail;
